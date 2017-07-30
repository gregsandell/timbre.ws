package net.street18.web;

import org.apache.log4j.Logger;

import java.util.HashMap;


public class WhydahAssetsSingleton {
		private HashMap assetsHashMap;
		private static WhydahAssetsSingleton  ourInstance = null;
		private static Logger logger = Logger.getLogger(WhydahAssetsSingleton.class.getName());
		private static final String GENERIC_HEADER = "/res/images/generic1/header_bkg_mn.gif";
		private static final String GENERIC_ARROW = "/res/images/common/placeholders/quickLinksBullet.jpg";
		private static final String GENERIC_SQUARE = "/res/images/common/placeholders/squareBullet.jpg";
		public synchronized static WhydahAssetsSingleton getInstance() {
			if (ourInstance == null) {
				ourInstance = new WhydahAssetsSingleton();
				logger.info("First instance of WhydahAssetsSingleton created");
			}
			return ourInstance;
		}
		private WhydahAssetsSingleton() {
			WhydahAssets whydahAssets;
			assetsHashMap = new HashMap();

			whydahAssets =
				new WhydahAssets(Globals.AUSTRIA, "OliveKhaki", "MidnightBlue", "VeryPalePurple", "SwimmingPoolBlue");
			assetsHashMap.put(Globals.AUSTRIA, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.CHILE, "MediumPurple", "DarkBlue", "PastelPurple6", "LightCyan");
			assetsHashMap.put(Globals.CHILE, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.ARGENTINA, "DarkViolet", "LightAvocado", "PaleYellow", "PastelPurple9");
			assetsHashMap.put(Globals.ARGENTINA, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.GERMANY, "DarkOliveKhaki", "DeepPurple", "HazySkyBlue2", "PalestGreen2");
			assetsHashMap.put(Globals.GERMANY, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.SPAIN, "Bronze3", "DeepPurple", "HazySkyBlue4", "PaleYellow");
			assetsHashMap.put(Globals.SPAIN, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.JAPAN, "MidnightTeal2", "Rust3", "HazyPink", "LightSeaFoam");
			assetsHashMap.put(Globals.JAPAN, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.FRANCE, "DarkSkyBlue", "DarkPurple2", "RobinsEggBlue", "SeaFoam2");
			assetsHashMap.put(Globals.FRANCE, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.ITALY, "DayGloPurple", "MidnightBrown", "LightPutty", "PastelPurple8");
			assetsHashMap.put(Globals.ITALY, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.AUSTRALIA, "DarkGold", "DarkPurple2", "LightTan2", "PastelSkyBlue5");
			assetsHashMap.put(Globals.AUSTRALIA, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.CHINA, "DarkTeal", "DarkMaroon", "PaleYellow", "SeaFoam");
			assetsHashMap.put(Globals.CHINA, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.NETHERLANDS, "OrangeRust", "MidnightTeal", "HazySkyBlue3", "TanGold2");
			assetsHashMap.put(Globals.NETHERLANDS, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.IRELAND, "Avocado", "RoyalPurple", "PastelPurple3", "VeryPaleLime");
			assetsHashMap.put(Globals.IRELAND, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.ENGLAND, "RoyalBlue", "DarkMaroon", "PaleYellow", "HazySkyBlue");
			assetsHashMap.put(Globals.ENGLAND, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.NEWZEALAND, "Bronze2", "MidnightGreen", "LightSeaFoam2", "PaleGold3");
			assetsHashMap.put(Globals.NEWZEALAND, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.ECUADOR, "RustBrown", "PaleGold4", "LightYellow2", "HazyPastelPurple");

			assetsHashMap.put(Globals.ECUADOR, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.SUMMER, "FadedLawn", "MidnightBlue2", "RobinsEggBlue2", "VeryPaleLime2");
			assetsHashMap.put(Globals.SUMMER, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.EU_SPECIAL_CASE, "Violet", "MidnightPurple2", "PastelSkyBlue6", "PastelPurple10");
			assetsHashMap.put(Globals.EU_SPECIAL_CASE, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.STUDENTS, "EasterPurple", "LightAvocado", "PaleYellow3", "PastelPurple9");
			assetsHashMap.put(Globals.STUDENTS, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.PARENTS, "Teal", "DarkPurple2", "PastelPurple6", "LightCyan");
			assetsHashMap.put(Globals.PARENTS, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.ALUMNI, "Navy", "Mustard", "LightTan2", "PastelPurple11");
			assetsHashMap.put(Globals.ALUMNI, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.FACULTY, "Gold", "MidnightGreen", "LightSeaFoam2", "LightTan4");
			assetsHashMap.put(Globals.FACULTY, whydahAssets);

			whydahAssets =
				new WhydahAssets(Globals.GENERIC_COLOR_SCHEME, "Navy", "MidnightBlue", "PaleYellow", "PastelPurple11");
			assetsHashMap.put(Globals.GENERIC_COLOR_SCHEME, whydahAssets);




		}
		public WhydahAssets get(String countryKey)  {
			WhydahAssets whydahAssets = (WhydahAssets)assetsHashMap.get(countryKey);
			if (whydahAssets == null) {
				logger.error("Could not retrive a WhydahAssets object with country key [" + countryKey + "]");
			}
			return(whydahAssets);
		}
}
