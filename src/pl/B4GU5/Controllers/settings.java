
package pl.B4GU5.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class settings {
	
    static String pack;
    static String packVer;
    static String ram;
    static String launcherVer;
    static String token;
    static String console;
    static String premium;
    static String workingDir = System.getProperty("user.home")+"//TechnicWorld";
    public settings() {
        
    }

    public static void loadSettings() throws FileNotFoundException {
    	String filename = workingDir+"\\launcher_settings.json";
        File file = new File(filename);
        if (file.exists()) {
	        Scanner inputFile = new Scanner(file);
	        String jsonTxt = "";
	        while (inputFile.hasNext())
	        {
	           jsonTxt += inputFile.nextLine();
	
	        }
	        inputFile.close();
	        JSONObject json = new JSONObject(jsonTxt);
	        pack = json.get("selectedPack").toString();
	        packVer = json.get("packVersion").toString();
	        ram = json.get("ram").toString();
	        launcherVer = json.get("launcherVersion").toString();
	        token = json.get("token").toString();
	        premium = json.get("premium").toString();
	        if (launcherVer.equals("-1")) {
	        	if (!json.has("console")) {
	        		console = "false";
	        	} else {
	        		console = json.get("console").toString();
	        	}
	        	
	        	
	        } else {
	        	console = json.get("console").toString();
	        }
	        
        } else {
	        pack =				"select";
	        packVer =			"-1";
	        ram =				"1";
	        launcherVer = 		"-1";
	        token =				"-1";
	        premium =			"false";
	        console =			"false";
        }
    }
    public static String getString() {
    	return "Selected pack: "+pack+", Version: "+packVer+", Ram: "+ram+", Launcher version: "+launcherVer+", token: "+token+", premium: "+premium;
    }
    
    //SAVE SETTINGS
	static public boolean saveSettings() {
    	Map<String,String> jsonCreator=new HashMap<String,String>();
    	jsonCreator.put("selectedPack", pack);
    	jsonCreator.put("packVersion", packVer);
    	jsonCreator.put("ram", ram);
    	jsonCreator.put("launcherVersion", launcherVer);
    	jsonCreator.put("token", token);
    	jsonCreator.put("console", console);
    	jsonCreator.put("premium", premium);

    	JSONObject j=new JSONObject(jsonCreator);
    	//System.out.println(j);
    	try {
			FileWriter fileWriter = new FileWriter(workingDir+"\\launcher_settings.json");
			fileWriter.write(j.toString());
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        return true;
    } 
    
    //SET
    static public void setPack(String txt) {
        pack = txt;
    }    
    static public void setPackVersion(String txt) {
        packVer = txt;
    }    
    static public void setRam(String txt) {
        ram = txt;
    }    
    static public void setLauncherVersion(String txt) {
        launcherVer = txt;
    }    
    static public void setToken(String txt) {
        token = txt;
    }       
    static public void setConsole(String txt) {
        console = txt;
    }     
    static public void setPremium(String txt) {
        premium = txt;
    }
    
    //GET
    static public String getPack() {
        return pack;
    } 
    
    static public String getPackVersion() {
        return packVer;
    }    
    
    static public String getRam() {
        return ram;
    }    
    
    static public String getLauncherVersion() {
        return launcherVer;
    }    
    
    static public String getToken() {
        return token;
    }  
    
    static public String getConsole() {
        return console;
    }    
    
    static public String getPremium() {
        return premium;
    }    

 

}
