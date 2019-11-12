package pl.B4GU5.Utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class logger implements LogType {
	@FXML TextFlow console;
	public static String logi = "\n";
	public static void log(String string, String text) {
		System.out.println("("+string+"): "+text);
		logi+="("+string+"): "+text+"\n";

		
	}
	public void addLog(String text1) {
		Text text= new Text(text1);
		System.out.println(text);
		console.getChildren().add(text);
	}
	public void showConsole() {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Console.fxml"));
		    
		    Scene scene = new Scene(root, 790, 440);
		        
		    stage.getIcons().add(new Image("file:icon.png"));
		    stage.setResizable(false);
		    stage.setScene(scene);
		    stage.show();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	public void hideConsole() {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Console.fxml"));
		    
		    Scene scene = new Scene(root, 790, 440);
		        
		    stage.getIcons().add(new Image("file:icon.png"));
		    stage.setResizable(false);
		    stage.setScene(scene);
		    stage.show();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
}
