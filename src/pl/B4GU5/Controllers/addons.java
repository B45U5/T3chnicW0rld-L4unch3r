package pl.B4GU5.Controllers;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class addons extends Application {
	static String sw;
	static String swlink;
	@FXML
	private Pane contentPane;
	String ID = "null";
	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("addons.fxml"));
        
        Scene scene = new Scene(root, 414, 464);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.setResizable(false);
        stage.setTitle("TechnicWorld | Dodatki");
        stage.setScene(scene);
        stage.show();
	}
	public static void serwer(String serwer) {
		sw=System.getProperty("user.home")+"//TechnicWorld//"+serwer;
		swlink=serwer;
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	@FXML
	protected void initialize() throws JSONException, IOException {
		zaladuj();
	}
		double position = -77;
		double size0 = 77;
		public void zaladuj() throws JSONException, IOException {
			position = -77;
			size0 = 77;
			contentPane.getChildren().clear();

	        Runnable updatethread = new Runnable() {
	        	public void run() {

					        try {
					            JSONObject json = urlJson.readJsonFromUrl("https://technicworld.pl/api/addons_"+swlink+".json");
					            JSONArray arr = json.getJSONArray("mods");
					            String content = org.apache.commons.io.IOUtils.toString(new URL("https://technicworld.pl/api/mods-"+swlink+".json"), "utf8");
					            for (int i = 0; i < arr.length(); i++) {
					            	System.out.println(content);
					            	String nazwa = arr.getJSONObject(i).getString("dispname");
					            	String obrazek = arr.getJSONObject(i).getString("img");
					            	String ID = arr.getJSONObject(i).getString("name");
					            	if (content.contains(ID)) {
									Platform.runLater(new Runnable() {
									    @Override
								        public void run() {
									    	try {
												addViewPane(nazwa, obrazek, ID);
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
								        }
								        
									});
					            	}
									try {
									    Thread.sleep(10);
									} catch(InterruptedException ex) {
									    Thread.currentThread().interrupt();
									}
					            }
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

			    }
			};
			ExecutorService executor = Executors.newCachedThreadPool();
		    executor.submit(updatethread);


		}
	 	FXMLLoader loader = new FXMLLoader(getClass().getResource("listPaneItem.fxml"));
		public void addViewPane(String Nazwa0, String img, String ID0) throws IOException {
			
			System.out.println("Trwa dodawanie... Lokalizacja (Y): ");
			double size = contentPane.getHeight();

			System.out.println(size0);
		    addons_act vwP = new addons_act();
			contentPane.setPrefHeight(size0);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("listPaneItem.fxml"));


		    loader.setController(vwP);

		    Pane view = (Pane) loader.load();
		    position += 77;
		    contentPane.getChildren().add(view);
		    view.setLayoutY(position);
					
			vwP.Nazwa(Nazwa0);
			vwP.Obrazek(img);
			vwP.ID(ID0);
			  Path p = Paths.get(sw+"//Minecraft//mods//"+ID0);
			  Path p2 = Paths.get(sw+"//Minecraft//mods//"+ID0+".disabled");
			  System.out.println(p);
              boolean enabled = Files.exists(p);
              boolean disabled = Files.exists(p2);
          
      
              if (enabled) {
	              System.out.println("File enabled");
	              vwP.aktywny();
	              System.out.println(vwP.active);
	              vwP.aktywuj();
              } else if (disabled) {
                  System.out.println("File disabled");
                  vwP.nieaktywny();
                  System.out.println(vwP.active);
                  vwP.deaktywuj();;
              } else {
                  System.out.println("File's status is unknown!");
              }
			EventHandler<MouseEvent> paneOnMouseClicked = new EventHandler<MouseEvent>() {

			    @Override
			    public void handle(MouseEvent t) {
					ID = ID0;
			    	//load_content(Nazwa0, Autor0, Opis0, wersjaMC0, Obrazek0, Wersja0, Pobran0, Kiedy0, ID0, video0);
					if (!vwP.aktywny_infor()) {

						Path p3 = Paths.get(sw+"//Minecraft//mods//"+ID0);
						Path p4 = Paths.get(sw+"//Minecraft//mods//"+ID0+".disabled");

						try {
							Files.move(p4, p3);
							vwP.aktywny();
							System.out.println(vwP.active);
							vwP.aktywuj();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (vwP.aktywny_infor()) {

						Path p3 = Paths.get(sw+"//Minecraft//mods//"+ID0);
						Path p4 = Paths.get(sw+"//Minecraft//mods//"+ID0+".disabled");

						try {
							Files.move(p3, p4);
							vwP.nieaktywny();
							System.out.println(vwP.active);
							vwP.deaktywuj();;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					t.consume(); // consume event -> no further propagation
			    
			    	}
			    };


			view.addEventHandler(MouseEvent.MOUSE_CLICKED, paneOnMouseClicked);
			size0 += 77;

		}
		public void active() {
			
		}
		public void deactive() {
			
		}
}
