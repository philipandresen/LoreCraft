package Levels;

import java.util.ArrayList;

//continents are here to help with biome generation, landmark generation, and path generation.
//The basic idea is that this data will end up getting dumped to the World object.

public class Continent {

	private String[] nameList = {"INASTRULA","FULYUMA","IVARU","ORAWA","LAZID",
		"LOLYA","YOZEK","AGOTHE","ARATU","POTAKI","SYMPTARON","RIMILY","IBUSUR","PRISPOROCE",
		"HIFAL","IMICOTZU","UZERO","NOCURA","URIMOTOCHEWKA","EVETEKO","AMAVINAS","ALIBABO",
		"JIRAZURO","NIGIMORAD","HAPIN","TITANISADIR","OXIDRA","ODACRUN","HODRULIV","ACRULIM",
		"IZURUL","AVRAQURUN","ACKALYOR","IBITRIZU","TUNEC","VIZUPURABIZI","RALIV","ZAGROF",
		"YAVUNU","ATRULE","ESROZA","NUNOMI","UNUSHE","EXIDIR","ISRIGAM","ARONO","RAKU","IRANAZAQ",
		"DUVIGA","OBLIVIN","YOPINALE","MANIKROFT","ASLINOR","LYUNI","EVORILA","THAZUV","YAXIR"};
	//Classification determines what size continent this is for naming conventions.
	//smaller continents will have names like "The island of..."
	//Large continents will be known simply as their own names.
	private int classification;
}
