package terrainGeneration;

import java.util.Random;

import Game.Direction;

public class DiamondSquareGenerator {

	
	final int DATA_SIZE;
	private Integer[][] data;
	Integer[] borderNorth;
	Integer[] borderSouth;
	Integer[] borderEast;
	Integer[] borderWest;
	
	
	public DiamondSquareGenerator(int size) {
		DATA_SIZE = size;
		data = new Integer[DATA_SIZE][DATA_SIZE];
		borderNorth = new Integer[DATA_SIZE];
		borderSouth = new Integer[DATA_SIZE];
		borderEast = new Integer[DATA_SIZE];
		borderWest = new Integer[DATA_SIZE];
		//set default borders. All other values are still null at this point.
		borderNorth[0] = 0;
		borderSouth[0] = 0;
		borderEast[0] = 0;
		borderWest[0] = 0;
		borderNorth[DATA_SIZE-1] = 0;
		borderSouth[DATA_SIZE-1] = 0;
		borderEast[DATA_SIZE-1] = 0;
		borderWest[DATA_SIZE-1] = 0;

		
	}
	
	public void setBorder(Integer border[], Direction dir){
		switch (dir) {
			case North:
				borderNorth = border;
				break;
			case South:
				borderSouth = border;
				break;
			case East:
				borderEast = border;
				break;
			case West:
				borderWest = border;
				break;
				
			
		}
		
	}

	public void setPoint(int x, int y, int altitude){
		data[x][y] = altitude;
	}
	
	public Integer[][] newTerrain(boolean shouldWrap, int noiseRange) {
		//size of grid to generate, note this must be a
		//value 2^n+1
		boolean Wrap = shouldWrap;
		//an initial seed value for the corners of the data
		//final int SEED = 0000;
		//seed the data
		int reduceIterations = 0;
		double heightModifier = 0; //0 is normal  // 1 is twice higher // -1 is twice lower
		for (int i = 0 ; i<DATA_SIZE ; i++ ){
			data[0][i] = borderWest[i];
			data[DATA_SIZE-1][i] = borderEast[i];
			data[i][0] = borderNorth[i];
			data[i][DATA_SIZE-1] = borderSouth[i];
		}
		//printData();

		double h = noiseRange;//the range (-h -> +h) for the average offset
		Random r = new Random();//for the new value in range of h
		//side length is distance of a single square side
		//or distance of diagonal in diamond
		int skipIter = reduceIterations;
		for(int sideLength = DATA_SIZE-1 ;
				//side length must be >= 2 so we always have
				//a new value (if its 1 we overwrite existing values
				//on the last iteration)
				sideLength >= 2;
				//each iteration we are looking at smaller squares
				//diamonds, and we decrease the variation of the offset
				sideLength /=2, h/= 2.0){
			//half the length of the side of a square
			//or distance from diamond center to one corner
			//(just to make calcs below a little clearer)
			int halfSide = sideLength/2;

			//generate the new square values
			for(int x=0;x<DATA_SIZE-1;x+=sideLength){
				for(int y=0;y<DATA_SIZE-1;y+=sideLength){
					//x, y is upper left corner of square
					//calculate average of existing corners
					double avg = data[x][y] + //top left
							data[x+sideLength][y] +//top right
							data[x][y+sideLength] + //lower left
							data[x+sideLength][y+sideLength];//lower right
					avg /= 4.0;
					//We calculate random value in range of 2h
					//and then subtract h so the end value is
					//in the range (-h, +h)
					avg = avg + (r.nextDouble()*2*h) - h*(-heightModifier+1);
					if (skipIter>0) {
						avg=0;
						skipIter--;
					}
					//center is average plus random offset
					if (data[x+halfSide][y+halfSide] == null){
						data[x+halfSide][y+halfSide] = (int) (avg);
					}
				}
			}

			//generate the diamond values
			//since the diamonds are staggered we only move x
			//by half side
			//NOTE: if the data shouldn't wrap then x < DATA_SIZE
			//to generate the far edge values
			int A = 0;
			if (Wrap) A=-1;
			skipIter=reduceIterations;
			for(int x=0;x<DATA_SIZE+A;x+=halfSide){
				//and y is x offset by half a side, but moved by
				//the full side length
				//NOTE: if the data shouldn't wrap then y < DATA_SIZE
				//to generate the far edge values
				for(int y=(x+halfSide)%sideLength;y<DATA_SIZE+A;y+=sideLength){
					//x, y is center of diamond
					//note we must use mod  and add DATA_SIZE for subtraction 
					//so that we can wrap around the array to find the corners
					
					//I am modifying here to make the data not wrap. If we try to grab data from outside
					//the array we will instead use fewer values.
					int top, bottom, left, right, runningAvg, included;
					included = 0;
					runningAvg=0;
					top = (y-halfSide);
					if (top>=0) {
						runningAvg += data[x][top];
						included ++;
					} else {
						if (data[x][0] != null){
							runningAvg += data[x][0];
							included ++;
						}
					}
					bottom = (y+halfSide);
					if (bottom <= (DATA_SIZE-1)) {
						runningAvg += data[x][bottom];;
						included ++;
					} else {
						if (data[x][(DATA_SIZE-1)] != null){
							runningAvg += data[x][(DATA_SIZE-1)];
							included ++;
						}
					}
					left = (x-halfSide);
					if (left>=0) {
						runningAvg += data[left][y];
						included ++;
					} else {
						if (data[0][y] != null){
							runningAvg += data[0][y];
							included ++;
						}
					}
					right = (x+halfSide);
					if (right>=0 && right <= (DATA_SIZE-1)) {
						runningAvg += data[right][y];;
						included ++;
					} else {
						if (data[(DATA_SIZE-1)][y] != null){
							runningAvg += data[(DATA_SIZE-1)][y];
							included ++;
						}
					}
					
					/*double avg = 
							data[(x-halfSide+DATA_SIZE-1)%(DATA_SIZE-1)][y] + //left of center
							data[(x+halfSide)%(DATA_SIZE-1)][y] + //right of center
							data[x][(y+halfSide)%(DATA_SIZE-1)] + //below center
							data[x][(y-halfSide+DATA_SIZE-1)%(DATA_SIZE-1)]; //above center
					
					avg /= 4.0;*/
					double avg = 0;
					
					if (included>0){
						avg=runningAvg/included;
					}
					
					//new value = average plus random offset
					//We calculate random value in range of 2h
					//and then subtract h so the end value is
					//in the range (-h, +h)
					avg = avg + (r.nextDouble()*2*h) - h*(-heightModifier+1);
					if (skipIter>0) {
						avg=0;
						skipIter--;
					}
					//update value for center of diamond
					
					if (data[x][y] == null) data[x][y] = (int) avg;

					//wrap values on the edges, remove
					//this and adjust loop condition above
					//for non-wrapping values.
					if (Wrap){
						if(x == 0)  data[DATA_SIZE-1][y] = data[0][y];
						if(y == 0)  data[x][DATA_SIZE-1] = data[x][0];
					}
				}
			}
		}

		
		
		//print out the data
		//printData();

		return data;
	}

	
	
	public void printData(){
		for(Integer[] row : data){
			if (row != null){
				for(Integer d : row){
					System.out.printf("%5d",d);
				}
				System.out.println();
			}
		}
		System.out.println();
	}
}
