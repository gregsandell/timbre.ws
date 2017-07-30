package net.street18.web;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Feb 23, 2004
 * Time: 1:28:26 PM
 * To change this template use Options | File Templates.
 */
public class WhydahAssets {
	private String country					= "";
	private String mainColorName 			= "";
	private String darkAccentColorName					= "";
	private String lightAccent1ColorName				= "";
	private String lightAccent2ColorName 	= "";
	private String mainHex 			= "";
	private String darkAccentHex					= "";
	private String lightAccent1Hex					= "";
	private String lightAccent2Hex 		= "";
	private String arrowBullet 			= "";
	private String squareBullet 		= "";
	private String textColorName 		= "";
	private String textColorHex 		= "";
	private String hoverColorName 	= "";
	private String hoverColorHex 		= "";
  private static final String TEXT_COLOR_NAME =  "MidnightBlue";
	private static final String TEXT_HOVER_NAME =  "OliveKhaki";
	private static final String PROGRAMS_DIR = "/res/images/programs/";
	private static final String ARROWS_DIR = PROGRAMS_DIR + "arrowBullets/";
	private static final String SQUARES_DIR = PROGRAMS_DIR + "squareBullets/";
	private static final String CENTER_HEADERS_DIR = PROGRAMS_DIR + "headers/centers/";
	private static final String COUNTRY_HEADERS_DIR = PROGRAMS_DIR + "headers/countries/";

	public WhydahAssets(String country, String main, String darkAccent, String lightAccent1, String lightAccent2) {
		setCountry(country);
		setMainColorName(main);
		setDarkAccentColorName(darkAccent);
		setLightAccent1ColorName(lightAccent1);
		setLightAccent2ColorName(lightAccent2);
		setTextColorName(TEXT_COLOR_NAME);
		// setHoverColorName(TEXT_HOVER_NAME);
		setHoverColorName(getMainColorName());
		setArrowBullet(ARROWS_DIR + getCountry() + "ArrowBullet.gif");
		setSquareBullet(SQUARES_DIR + getCountry() + "SquareBullet.gif");
	}

	public String getMainColorName() {
		return mainColorName;
	}

	public void setMainColorName(String mainColorName) {
		this.mainColorName = mainColorName;
		setMainHex(ColorMap.get(mainColorName));
	}

	public String getDarkAccentColorName() {
		return darkAccentColorName;
	}

	public void setDarkAccentColorName(String darkAccentColorName) {
		this.darkAccentColorName = darkAccentColorName;
		setDarkAccentHex(ColorMap.get(darkAccentColorName));
	}

	public String getLightAccent1ColorName() {
		return lightAccent1ColorName;
	}

	public void setLightAccent1ColorName(String lightAccent1ColorName) {
		this.lightAccent1ColorName = lightAccent1ColorName;
		setLightAccent1Hex(ColorMap.get(lightAccent1ColorName));
	}

	public String getLightAccent2ColorName() {
		return lightAccent2ColorName;
	}

	public void setLightAccent2ColorName(String lightAccent2ColorName) {
		this.lightAccent2ColorName = lightAccent2ColorName;
		setLightAccent2Hex(ColorMap.get(lightAccent2ColorName));
	}

	public String getMainHex() {
		return mainHex;
	}

	public void setMainHex(String mainHex) {
		this.mainHex = mainHex;
	}

	public String getDarkAccentHex() {
		return darkAccentHex;
	}

	public void setDarkAccentHex(String darkAccentHex) {
		this.darkAccentHex = darkAccentHex;
	}

	public String getLightAccent1Hex() {
		return lightAccent1Hex;
	}

	public void setLightAccent1Hex(String lightAccent1Hex) {
		this.lightAccent1Hex = lightAccent1Hex;
	}

	public String getLightAccent2Hex() {
		return lightAccent2Hex;
	}

	public void setLightAccent2Hex(String lightAccent2Hex) {
		this.lightAccent2Hex = lightAccent2Hex;
	}

	public String getArrowBullet() {
		return arrowBullet;
	}

	public void setArrowBullet(String arrowBullet) {
		this.arrowBullet = arrowBullet;
	}

	public String getSquareBullet() {
		return squareBullet;
	}

	public void setSquareBullet(String squareBullet) {
		this.squareBullet = squareBullet;
	}

	public String getTextColorName() {
		return textColorName;
	}

	public void setTextColorName(String textColorName) {
		this.textColorName = textColorName;
		setTextColorHex(ColorMap.get(textColorName));
	}

	public String getTextColorHex() {
		return textColorHex;
	}

	public void setTextColorHex(String textColorHex) {
		this.textColorHex = textColorHex;
	}

	public String getHoverColorName() {
		return hoverColorName;
	}

	public void setHoverColorName(String hoverColorName) {
		this.hoverColorName = hoverColorName;
		setHoverColorHex(ColorMap.get(hoverColorName));
	}

	public String getHoverColorHex() {
		return hoverColorHex;
	}

	public void setHoverColorHex(String hoverColorHex) {
		this.hoverColorHex = hoverColorHex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
