// This entire file is part of my masterpiece.
// Connor Usry
package sharedobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import exceptions.FaultyPropertiesFileException;
import sharedobjects.PropertiesManager;

public class PropertiesManager {
	private static final String DEFAULT_PROPERTIES_RESOURCE = "GUI.view";
	private static ResourceBundle myResource = ResourceBundle.getBundle(DEFAULT_PROPERTIES_RESOURCE);;
	
	public PropertiesManager() {};
	
	public static String getString(String s) {
		try {
			String value = myResource.getString(s);
			return value;			
		} catch (Exception e){
			throw new FaultyPropertiesFileException("Property: \"" + s + "\" (String) not found in properties file!");
		}
	}

	public static int getInt(String s) {
		try {
			String value = myResource.getString(s);
			return Integer.parseInt(value);
		} catch (Exception e){
			throw new FaultyPropertiesFileException("Property: \"" + s + "\" (int) not found in properties file!");
		}
	}
	
	public static double getDouble(String s) {
		try {
			String value = myResource.getString(s);
			return Double.parseDouble(value);
		} catch (Exception e){
			throw new FaultyPropertiesFileException("Property: \"" + s + "\" (Double) not found in properties file!");
		}
	}
	
	public static List<Double> getDoubleList(String s) {
		try {
			String[] values = myResource.getString(s).split(",");
			List<Double> output = new ArrayList<Double>();
			for (String val : values){
				output.add(Double.parseDouble(val));
			}
			return output;
		} catch (Exception e){
			throw new FaultyPropertiesFileException("Property: \"" + s + "\" (Double's seperated by commas) not found in properties file!");
		}
	}
	
	public static List<Integer> getIntList(String s) {
		try {
			String[] values = myResource.getString(s).split(",");
			List<Integer> output = new ArrayList<Integer>();
			for (String val : values){
				output.add(Integer.parseInt(val));
			}
			return output;
		} catch (Exception e){
			throw new FaultyPropertiesFileException("Property: \"" + s + "\" (Integers seperated by commas) not found in properties file!");
		}
	}
}




//private static final String DEFAULT_GUI_RESOURCE = "GUI.view";
//protected static ResourceBundle myResource;
//myResource = ResourceBundle.getBundle(DEFAULT_GUI_RESOURCE);
//myResource.getString("defaultPenState")