package net.street18.web;

import org.apache.log4j.Logger;


import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Oct 27, 2003
 * Time: 9:51:09 AM
 * To change this template use Options | File Templates.
 */
public class ColorMap {
	private static Logger logger = Logger.getLogger(ColorMap.class.getName());
	private static HashMap h = new HashMap();
	private static final String defaultColor = "#ffffff";
	static {
		/* Misc */
		h.put("Black", 						"#000000");
    h.put("White", 						"#FFFFFF");

		/* Oranges */
		h.put("PeachyOrange", 		"#ff9900");
		h.put("Orange", 					"#ff6633");
		h.put("PalePeach", 				"#fff0c1");
		h.put("OrangeRust", 			"#c86002");
    h.put("OrangeRust2", 			"#cc6600");
		h.put("Mustard",					"#dba424");

		/* Blues */
    h.put("LogoBlue", 				"#5d7ac0");
		h.put("LogoBlue2", 				"#728ad6");
		h.put("LightBlue", 				"#94b5d6");
		h.put("SkyBlue", 					"#b9c4f2");
		h.put("SkyBlue2", 				"#6699cc");
		h.put("SkyBlue3", 				"#9BAAD2");
		h.put("SkyBlue3", 				"#7991CC");
		h.put("SkyBlue4", 				"#729BC6");
		h.put("Blue", 						"#9EABCD");
		h.put("Navy", 						"#3453a4");
		h.put("LightNavy", 				"#336699");
		h.put("PastelSkyBlue", 		"#98b6d5");
		h.put("PastelSkyBlue2", 	"#c3dbf4");
		h.put("PastelSkyBlue3", 	"#9EBCD8");
		h.put("PastelSkyBlue4", 	"#DBE3F8");
		h.put("PastelSkyBlue5", 	"#c2cfef");
		h.put("PastelSkyBlue6", 	"#d7e3e8");
    h.put("PastelSkyBlue7",   "#b5cae6");
		h.put("PastelSkyBlue8",   "#c7d7ef");
		h.put("PaleBlue", 				"#eff2f9");
		h.put("MidnightBlue", 		"#000066");
		h.put("MidnightBlue2", 		"#000075");
		h.put("MidnightBlue3", 		"#000033");
		h.put("Turquoise", 				"#006699");
		h.put("DarkBlue", 				"#333366");
		h.put("LightCyan", 				"#caeaff");
		h.put("RoyalBlue", 				"#4257BB");
		h.put("HazySkyBlue", 			"#cedaf2");
		h.put("HazySkyBlue2", 		"#d6e4e9");
		h.put("HazySkyBlue3", 		"#e1e8f2");
		h.put("HazySkyBlue4", 		"#dbe4e8");
		h.put("HazySkyBlue5", 		"#f4f4fb");
		h.put("HazySkyBlue6",			"#e8eef4");
		h.put("HazySkyBlue7",			"#f2f3f9");
		h.put("HazySkyBlue8",			"#f3f8fc");
		h.put("CornflowerBlue", 	"#899DD3");
		h.put("DarkSkyBlue", 			"#5C91CF");
		h.put("RobinsEggBlue", 		"#d7ebff");
		h.put("RobinsEggBlue2", 	"#d8e4ed");
		h.put("SwimmingPoolBlue", "#b7daf5");
		h.put("EasterBlue", 			"#000099");
		h.put("EasterBlue2", 			"#003399");
		h.put("PaleRoyalBlue",		"#4666b5");

		/* Greens */
    h.put("MediumGreen", 			"#9AA98E");
		h.put("DarkestGreen2", 		"#5A6850");
		h.put("PaleGreen", 				"#CDD7C5");
		h.put("DarkGreen", 				"#8D9983");
		h.put("DarkGreen2", 			"#819572");
		h.put("LightGreen", 			"#CED8C6");
		h.put("LightGreen2", 			"#B9C4B1");
		h.put("PalestGreen", 			"#E3E9E2");
		h.put("PalestGreen2", 		"#eaefd6");
    h.put("DarkestGreen", 		"#67735D");
		h.put("OliveKhaki", 			"#666633");
		h.put("Khaki", 						"#9aa98e");
		h.put("MidnightKhaki", 		"#224444");
		h.put("Teal",					 		"#0b7277");
		h.put("DarkTeal", 				"#006666");
		h.put("SeaFoam", 					"#b0d7d1");
		h.put("SeaFoam2", 				"#b6daad");
		h.put("SeaFoam3", 				"#c6dfc7");
		h.put("LightTeal", 				"#03A09C");
		h.put("LightSeaFoam", 		"#daf3eb");
		h.put("LightSeaFoam2", 		"#dde8d9");
		h.put("MidnightTeal", 		"#003e5b");
		h.put("MidnightTeal2", 		"#02808a");
		h.put("MidnightTeal3", 		"#003333");
		h.put("FadedLawn", 				"#d1df6f");
		h.put("PaleLime", 				"#eef9c8");
		h.put("VeryPaleLime", 		"#e8f5cf");
		h.put("DarkOliveKhaki", 	"#738464");
		h.put("VeryPaleLime2", 		"#f4fbce");
		h.put("LightAvocado", 		"#759201");
		h.put("LightAvocado2", 		"#5E950B");
		h.put("Avocado", 					"#4e831d");
		h.put("MidnightGreen",		"#476633");
		h.put("LimeGreen",				"#99cc00");
		h.put("AvocadoKhaki", 		"#666600");
		h.put("AvocadoKhaki2", 		"#919045");       /* photo contest Spring text */
		h.put("LawnGreen", 				"#009933");
		h.put("WetLawnGreen", 		"#006600");
		h.put("LimeLawn", 				"#669900");
    h.put("ForestGreen", 			"#336633");


		/* Yellows */
		h.put("LightYellow", 			"#faf7e1");
		h.put("LightYellow2", 		"#fdfcc4");
		h.put("PaleYellow", 			"#ffffcc");
		h.put("DarkYellow", 			"#d5cd89");
		h.put("PaleYellow2", 			"#fefabe");
		h.put("PaleYellow3", 			"#f5f7ce");
		h.put("Gold", 						"#d9c75e");
		h.put("PaleGold", 				"#fbd784");
		h.put("PaleGold2", 				"#ffff99");
		h.put("PaleGold3", 				"#fff0c1");
		h.put("PaleGold4", 				"#e7bb0c");
		h.put("DesertGold", 			"#f8db0e");
		h.put("DarkGold",					"#f8b323");

    /* Purples */
		h.put("Purple", 					"#686CA1");
		h.put("DarkPurple", 			"#716588");
		h.put("LightPurple", 			"#CFC8DF");
		h.put("DeepPurple", 			"#003366");
		h.put("Purple2", 					"#6A4394");
		h.put("DarkViolet", 			"#68317d");
		h.put("PastelPurple", 		"#d0c5d8");
		h.put("MediumPurple", 		"#9460B7");
		h.put("DarkPurple2", 			"#330066");
		h.put("DarkViolet2", 			"#663366");
		h.put("PastelPurple2", 		"#e1e1ff");
		h.put("PastelPurple3", 		"#cccce6");
		h.put("PastelPurple4", 		"#d5d5ea");
		h.put("PastelPurple5", 		"#e0d3f5");
		h.put("PastelPurple6",		"#e3d6ed");
		h.put("PastelPurple7", 		"#ddddff");
		h.put("PastelPurple8", 		"#d9cedf");
		h.put("PastelPurple9", 		"#e7dae7");
		h.put("PastelPurple10", 	"#f1e0ef");
		h.put("PastelPurple11", 	"#e4eaf8");
		h.put("HazyPastelPurple",	"#e9eef5");
		h.put("VeryPalePurple", 	"#e7e7de");
		h.put("MidnightPurple", 	"#4a004a");
		h.put("MidnightPurple2", 	"#3d0462");
		h.put("DarkMaroon", 			"#4a004a");
    h.put("DarkMaroon2", 			"#660066");
		h.put("Violet", 					"#993399");
		h.put("DayGloPurple", 		"#BA91EC");
		h.put("RoyalPurple",			"#543e8e");
		h.put("EasterPurple",			"#6638a5");
		h.put("EasterPurple2",		"#660099");
		h.put("EasterPurple2",		"#663399");
    h.put("PurpleBrown",		  "#336666");

		/* Reds */
    h.put("Rust", 						"#954141");
    h.put("Rust2", 						"#934a41");
		h.put("Rust3",						"#993300");
		h.put("DarkRust",					"#990000");
		h.put("RustBrown",				"#b92c13");
		h.put("HazyPink",					"#f9e2dd");

    /* Browns */
    h.put("LightTan", 				"#E5DDCC");
    h.put("Tan", 							"#DED5AF");
		h.put("Tan2", 						"#d4d3ae");       /* photo contest spring cell */
    h.put("LightBrown", 			"#B29A49");
    h.put("LightTan", 				"#F8F8DC");
		h.put("BrownMustard", 		"#F8F2D1");
		h.put("EarthBrown", 			"#5A6850");
		h.put("GreyBrown", 				"#666666");
		h.put("TanGrey", 					"#D2D2C1");
		h.put("Olive", 						"#999900");
		h.put("LightTan2", 				"#fff3ce");
		h.put("LightTan3", 				"#fff0c1");
		h.put("LightTan4", 				"#ffffec");
		h.put("Putty", 						"#d8d8b1");
		h.put("Putty2", 					"#dcdcb8");
		h.put("Putty3", 					"#CCCC99");
		h.put("LightPutty", 			"#ebebd6" );
		h.put("LightPutty2", 			"#e4e8a4");
		h.put("LightPutty3", 			"#e6c19d");       /* photo contest Fall cell */
		h.put("PaleOlive", 				"#f0f7a6");
		h.put("TanGold", 					"#ece268");
		h.put("TanGold2", 				"#f2ecbf");
		h.put("Bronze", 					"#b6b63d");
		h.put("Bronze2",					"#cda007");
		h.put("Bronze3",					"#c6ae06");
		h.put("Bronze4",					"#cdad41");
		h.put("Bronze5",					"#cdad41");
		h.put("Bronze6",					"#a34d02");       /* photo contest Fall text */
		h.put("Olive2", 					"#939300");
		h.put("MidnightBrown", 		"#330033" );
		h.put("MidnightBrown2", 	"#333333" );
		h.put("SandStone", 				"#f1d8b1");
		h.put("MidnightChocolate","#500131");

		/* Greys */
    h.put("BlueGrey", 				"#D0D8EA");
		h.put("DarkGrey", 				"#808080");
		h.put("Grey", 						"#999999");


    /* Functions */
		h.put("Band4", h.get("Yellow"));
		h.put("BoldText1", h.get("Rust2"));
    h.put("Band3", h.get("LightGreen"));
		h.put("Band5", h.get("MediumGreen"));
		h.put("HomeBodyText", h.get("LogoBlue"));
    h.put("HomepageBackground", h.get("LightGreen"));
    h.put("Band5", h.get("Blue"));
    h.put("Band6", h.get("White"));
    h.put("Band7", h.get("DarkestGreen"));
    h.put("GenericPageBackground", h.get("LightBlue"));
		h.put("GenericQuickHeaderColor", 				h.get("LightYellow"));
		h.put("NewZealandQuickHeaderColor", 		h.get("LightPurple"));
		h.put("NewZealandLight", h.get("LightTan3"));
		h.put("EuropeanUnion", h.get("Violet"));
		h.put("EuropeanUnionLight", h.get("PastelPurple4"));
		h.put("EuropeanUnionQuickHeaderColor", 	h.get("LightPurple"));
		h.put("SummerQuickHeaderColor", h.get("LightPurple"));
		h.put("NewZealandDark", h.get("DarkViolet2"));
		h.put("GenericLight", h.get("PaleBlue"));
		h.put("EuropeanUnionDark", h.get("MidnightBlue"));
		h.put("SummerDark", h.get("MidnightBlue2"));
		h.put("GenericTitleColor", h.get("MidnightBlue"));
		h.put("Generic", h.get("PeachyOrange"));
		h.put("GenericDark", h.get("Orange"));
		h.put("Summer", h.get("FadedLawn"));
		h.put("SummerLight", h.get("PaleYellow"));
		h.put("NewZealand", h.get("PaleGold"));
	}
	public  static String get(String colorName) {
		String color = (String)h.get(colorName);
		if ((color == null) || (color.length() == 1)) {
			color = defaultColor;
			logger.error("No color found for name [" + colorName + "]");
		}
		return(color);
	}
}
