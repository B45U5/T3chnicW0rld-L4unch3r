package pl.B4GU5.Controllers;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.event.HyperlinkEvent;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.codefx.libfx.control.webview.WebViewHyperlinkListener;
import org.codefx.libfx.control.webview.WebViews;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.rx14.jmclaunchlib.LaunchSpec;
import uk.co.rx14.jmclaunchlib.LaunchTask;
import uk.co.rx14.jmclaunchlib.LaunchTaskBuilder;
import uk.co.rx14.jmclaunchlib.util.OS;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Main extends Application {
	@FXML
	private WebView browser;
	@FXML
	private Text ram;
	@FXML
	private AnchorPane loader;
	@FXML
	private Button menubtn;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private StackPane stackPane;
	@FXML
	private AnchorPane anchorMainPane;
	@FXML
	private Button play;
	@FXML
	private Button loginbtn;
	@FXML
	private Pane contentPane;
	@FXML
	private Pane menu;
	@FXML
	private AnchorPane consolePane;
	@FXML
	private JFXTextArea console;
	@FXML
	private JFXSpinner progress;
	@FXML
	private AnchorPane progresspane;
	@FXML
	private Label download;
	@FXML
	private JFXPasswordField passwordfield;
	@FXML
	private JFXTextField nickfield;
	@FXML
	private JFXCheckBox consoleToggle;
	@FXML 
	private JFXSlider slider;
	@FXML
	private Text loginlabel;
	@FXML
	private AnchorPane addonsPane;
	@FXML
	private AnchorPane mainPacks;
	private static Stage title;
	private String nick;
	private String version = "3.1.2A";
	boolean Disable = true;
	boolean atLogin = true;
	boolean atDownloadMods = true;
	boolean atRunMinecraft = false;
	public List<modsListClass> modsList = new ArrayList<modsListClass>();
	public List<modsListClass> modsListInDir = new ArrayList<modsListClass>();
	List<String> addonsList = new ArrayList<String>();
	int jsonSize;
	int mods = 0;
	String modsPath = settings.workingDir+"//"+settings.getPack()+"//Minecraft//mods";
	String urlModsPath = "https://technicworld.pl/api/modpack-"+settings.getPack();
	String urlAddonsList = "https://technicworld.pl/api/addons_"+settings.getPack()+".json";
	String urlModsList = "https://technicworld.pl/api/mods-"+settings.getPack()+".json";
	String urlPackVersion = settings.getPackVersion();
	String ac;
	public void start(Stage stage) {
		try {
			settings.loadSettings();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			erroralert(e);
		}
		try {
			Parent root = FXMLLoader.load(getClass().getResource("mainPane.fxml"));
		        title = stage;
		        Scene scene = new Scene(root, 1073, 588);
		        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
		        stage.setResizable(true);
		        stage.setTitle("TechnicWorld | " + settings.getPack());
		        stage.setScene(scene);
		        stage.setMinWidth(1105);
		        stage.setMinHeight(630);
		        stage.show();
		        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent t) {
							settings.saveSettings();
					      Platform.exit();
					      System.exit(0);
					   }
					});

		} catch(Exception e) {
			e.printStackTrace();
	        erroralert(e);
		}		
	}
    Method memorySize = null;
    OperatingSystemMXBean os = null;
    String b = "false";
	@FXML
    private void initialize() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
		
		URL url = new URL("https://technicworld.pl/api/launcher.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		while ((line = in.readLine()) != null) {
			if (!"4.1.1B".equals(line)) {
				updatealert("4.1.1B" + " - " + line, "TODO: What's new \n Nowa wersja launchera jest dostêpna!");
			}
		}
		System.out.println(in.readLine());
        browserload("https://technicworld.pl/index.php?site=lglowna");
        lock(true);
        //updatealert(version, "- Nowy wygl¹d\n- Poprawiony startup minecraft\n- Zaaktualizowano api\n- Poprawiono pobieranie modów");
       
        
        ///LOGIN //
	    Runnable updatethread = new Runnable() {
	        public void run() {
		 File theDir = new File(System.getProperty("user.home")+"//TechnicWorld");

	     // if the directory does not exist, create it
	     if (!theDir.exists()) {
	         System.out.println("creating directory: " + theDir.getName());
	         boolean result = false;
	
	         try{
	             theDir.mkdirs();
	             result = true;
	         } 
	         catch(SecurityException se){
	        	 erroralert(se);
	         }        
	         if(result) {    
	             System.out.println("DIR created");  
	         }
	     }
		 System.out.println(System.getProperty("user.home"));
		 Platform.runLater(new Runnable() {
			 
	            @Override public void run() {
	                try {

						checkd();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						erroralert(e);
					}
	            }
	        });
	        
		  }
		};
		ExecutorService executor = Executors.newCachedThreadPool();
	    executor.submit(updatethread);
	    //RAM

	    try {
	        os = ManagementFactory.getOperatingSystemMXBean();
	        if (Class.forName("com.sun.management.OperatingSystemMXBean").isInstance(os)) {
	            memorySize = os.getClass().getDeclaredMethod("getTotalPhysicalMemorySize");
	            memorySize.setAccessible(true);
	        }
	    } catch (Exception e) {
	    	erroralert(e);
	    }
	    consoleToggle.setSelected(Boolean.parseBoolean(settings.getConsole()));
	    //System.out.println(Boolean.parseBoolean(settings.getConsole())+" vs "+ Boolean.parseBoolean(settings.getConsole()));
	    consoleToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
                    settings.setConsole(new_val.toString());
            }
        });
	    Double memsize = (double) ((Long) memorySize.invoke(os)/1024/1024/1024);
	    ram.setText("Iloœæ pamiêci ram: "+ settings.getRam()+ "GB");
	    slider.setValue(Double.parseDouble(settings.getRam()));
	    slider.setMax(memsize);
	    
	    slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	settings.setRam(String.format("%.0f", new_val));
            	if (Integer.valueOf(settings.getRam()) < 2) {
            		
            		
            		if (b.equals("false")) {
            			alert("Uwaga!","Paczka mo¿e siê nie urchomiæ poniewa¿ zalecane jest przypisanie wiêcej ni¿ 3GB ramu! Przepraszamy!");
            		}
            		b = "true";
            	}
            	ram.setText("Iloœæ pamiêci ram: "+ settings.getRam()+ "GB");   
            	
            }
        });
	    
    }

	private void lock(Boolean disable) {
		if (disable) {
			Disable = true;
		} else {
			Disable = false;
		}
	}
	private boolean isLocked() {
		if (Disable) {
			if (atLogin) {
				alert("Chwileczkê!","Najpierw siê zaloguj!");
			} else if (atDownloadMods) {
				alert("Chwileczkê!","Poczekaj a¿ zostan¹ pobrane wszystkie mody!");
			} else if (atRunMinecraft) {
				alert("Chwileczkê!","Minecraft jest ju¿ uruchomiony!");
			} else {
				alert("Chwileczkê!","Poczekaj a¿ zostan¹ sprawdzone wszystkie mody!");
			}
			
			return(true);
		} else {
			return(false);
		}
		
	}
	public static void main(String[] args) {

		launch(args);
	}
	public void browserload(String URL) {
		loader.setVisible(true);
		browser.setVisible(false);
        WebEngine engine = browser.getEngine();
        engine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
        engine.getLoadWorker().cancel();
        System.out.println(engine.getUserAgent());
        engine.setJavaScriptEnabled(true);
        brcheck();
        WebViewHyperlinkListener eventPrintingListener = event -> {
        	if (event.getURL().toExternalForm().contains("launcher") == true) {
        		brcheck();
        	} else {
        		browserload(event.getURL().toExternalForm()+"&accesstoken="+ac);
        		System.out.println("Klikniêto: "+event.getURL().toExternalForm()+"&accesstoken="+ac);
        	}
        	return false;
        };
        WebViews.addHyperlinkListener(browser, eventPrintingListener, HyperlinkEvent.EventType.ACTIVATED);
        engine.load(URL);

	}
	public void brcheck() {
		loader.setVisible(true);
		browser.setVisible(false);
		WebEngine engine = browser.getEngine();
        //web.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                  @Override public void changed(ObservableValue ov, State oldState, State newState) {

                      if (newState == Worker.State.SUCCEEDED) {
                    	  loader.setVisible(false);
                    	  browser.setVisible(true);
                    }
                      
                    }
                });
	}

	@FXML
	private void menubtn(ActionEvent event) throws IOException {
		if (menu.isVisible()) {
			menu.setVisible(false);
		} else {
			menu.setVisible(true);
		}
	}
	@FXML
	private void packs(MouseEvent event) {
		if(!isLocked()) {
				mainPacks.setVisible(true);
		}
	}
	@FXML
	private void addons(MouseEvent event) {
		if(!isLocked()) {
			if(addonsPane.isVisible()) {
				addonsPane.setVisible(false);
			} else {
				addonsPane.setVisible(true);
				try {
					zaladuj();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					erroralert(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					erroralert(e);
				}
			}
			
		}
	}
	
	//Zaladuj
	String ID = "null";
	double position = -77;
	double size0 = 77;
	public void zaladuj() throws JSONException, IOException {
		ID = "null";
		position = -77;
		size0 = 77;
		contentPane.getChildren().clear();

        Runnable updatethread = new Runnable() {
        	public void run() {

				        try {
				            JSONObject json = urlJson.readJsonFromUrl("https://technicworld.pl/api/addons_"+settings.getPack()+".json");
				            JSONArray arr = json.getJSONArray("mods");
				            String content = org.apache.commons.io.IOUtils.toString(new URL("https://technicworld.pl/api/mods-"+settings.getPack()+".json"), "utf8");
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
											erroralert(e);
										}
							        }
							        
								});
				            	}
								try {
								    Thread.sleep(10);
								} catch(InterruptedException ex) {
									erroralert(ex);
								    Thread.currentThread().interrupt();
								}
				            }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							erroralert(e);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							erroralert(e);
						}

		    }
		};
		ExecutorService executor = Executors.newCachedThreadPool();
	    executor.submit(updatethread);


	}
	FXMLLoader loader1 = new FXMLLoader(getClass().getResource("listPaneItem.fxml"));
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
		  Path p = Paths.get(System.getProperty("user.home")+"//TechnicWorld//"+settings.getPack()+"//Minecraft//mods//"+ID0);
		  Path p2 = Paths.get(System.getProperty("user.home")+"//TechnicWorld//"+settings.getPack()+"//Minecraft//mods//"+ID0+".disable");
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

					Path p3 = Paths.get(System.getProperty("user.home")+"//TechnicWorld//"+settings.getPack()+"//Minecraft//mods//"+ID0);
					Path p4 = Paths.get(System.getProperty("user.home")+"//TechnicWorld//"+settings.getPack()+"//Minecraft//mods//"+ID0+".disable");

					try {
						Files.move(p4, p3);
						vwP.aktywny();
						System.out.println(vwP.active);
						vwP.aktywuj();
					} catch (IOException e) {
						// TODO Auto-generated catch block
					
						e.printStackTrace();
						erroralert(e);
					}
				} else if (vwP.aktywny_infor()) {

					Path p3 = Paths.get(System.getProperty("user.home")+"//TechnicWorld//"+settings.getPack()+"//Minecraft//mods//"+ID0);
					Path p4 = Paths.get(System.getProperty("user.home")+"//TechnicWorld//"+settings.getPack()+"//Minecraft//mods//"+ID0+".disable");

					try {
						Files.move(p3, p4);
						vwP.nieaktywny();
						System.out.println(vwP.active);
						vwP.deaktywuj();;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						erroralert(e);
					}
				}
				
				t.consume(); // consume event -> no further propagation
		    
		    	}
		    };


		view.addEventHandler(MouseEvent.MOUSE_CLICKED, paneOnMouseClicked);
		size0 += 77;

	}
	
	
	
	
	
	
	
	
	String mcver = "1.12.2";
	String forgever = "1.12.2-14.23.5.2838";
	String line = null;
	@FXML
	private void play(ActionEvent event) throws JSONException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (!isLocked()) {
			lock(true);
			consolePane.setVisible(true);
			atDownloadMods = false;
			atLogin = false;
			atRunMinecraft = true;
        	progresspane.setVisible(true);
      		progress.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            download.setText("Trwa uruchamianie minecraft...");
			int sett = (int) slider.getValue();
		  	String settt = Integer.toString(sett);
		  	System.out.println(sett);
		  	settings.setRam(settt);
		  	settings.saveSettings();
		  	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Double memsize = (double) ((Long) memorySize.invoke(os)/1024/1024/1024);
			String javaPath = Paths.get(System.getProperty("java.home"), "bin", OS.getCURRENT() == OS.WINDOWS ? "java.exe" : "java").toString();
			console.appendText("============================================[| SPECYFIKACJA KOMPUTERA |]============================================\r\n" + 
		  			"Uruchomiono minecraft o "+dtf.format(now)+"\r\n" + 
		  			"Uruchomiono paczkê: "+settings.getPack()+" v"+settings.getPackVersion()+"\r\n" +
		  			"Przypisano ramu: "+settings.getRam()+"GB\r\n" + 
		  			"Ramu w komputerze: "+(memsize+1)+"GB\r\n" + 
		  			"Iloœæ rdzeni procesora: "+Runtime.getRuntime().availableProcessors()+"\r\n" + 
		  			"Architektura os: "+System.getProperty("os.arch")+"\r\n" +
		  			"System operacyjny: "+System.getProperty("os.name")+"\r\n" + 
		  			"Œcie¿ka folderu minecraft: "+System.getProperty("user.home")+"/TechnicWorld/"+settings.getPack()+"/Minecraft"+"\r\n" + 
		  			"Œcie¿ka javy: "+javaPath+"\r\n" + 
		  			"==================================================[| MINECRAFT |]=================================================\r\n");
			try {
				
		        Runnable updatethread = new Runnable() {
		            public void run() {


		        

						 LaunchTask task = new LaunchTaskBuilder()
									
									.setCachesDir(System.getProperty("user.home")+"/TechnicWorld/"+settings.getPack()+"/Minecraft/mc-files") //Directory to cache stuff in, copies caches from .minecraft (and verifies)
									
									//.setMinecraftVersion("1.7.10") //Set vanilla version
									//OR
									.setForgeVersion(mcver, forgever) //Minecraftforge version
									
									.setInstanceDir(System.getProperty("user.home")+"/TechnicWorld/"+settings.getPack()+"/Minecraft") //Minecraft directory
									
									.setUsername(nick) //Username for offline
									.setOffline() //Offline mode
									
									.build(); //Build LaunchTask
							        Runnable updatethread = new Runnable() {
							            public void run() {
												Double a = (double) 0;
												while (a<100) {
													a = task.getCompletedPercentage();
							                          Platform.runLater(new Runnable() {;
							                          
						                              @Override
						                              public void run() {
						                            	  //System.out.println(task.getCompletedPercentage()/100);
						                            	progresspane.setVisible(true);
						                          		progress.setProgress(task.getCompletedPercentage()/100);
						                                download.setText("Trwa uruchamianie minecraft...");
						                                
						                              }
						                          });
												}
							            }};
									    new Thread(updatethread).
		
									    start();
									LaunchSpec spec = task.getSpec();
									spec.getJvmArgs().add("-mx"+settings.getRam()+"G");
									spec.getJvmArgs().add("-XX:InitiatingHeapOccupancyPercent=10");
									spec.getJvmArgs().add("-XX:AllocatePrefetchStyle=1");
									spec.getJvmArgs().add("-XX:+UseSuperWord");
									spec.getJvmArgs().add("-XX:+OptimizeFill");
									spec.getJvmArgs().add("-XX:LoopUnrollMin=4");
									spec.getJvmArgs().add("-XX:LoopMaxUnroll=16");
									spec.getJvmArgs().add("-XX:+UseLoopPredicate");
									spec.getJvmArgs().add("-XX:+RangeCheckElimination");
									spec.getJvmArgs().add("-XX:+CMSCleanOnEnter");
									spec.getJvmArgs().add("-XX:+EliminateLocks");
									spec.getJvmArgs().add("-XX:+DoEscapeAnalysis");
									spec.getJvmArgs().add("-XX:+TieredCompilation");
									spec.getJvmArgs().add("-XX:+UseCodeCacheFlushing");
									spec.getJvmArgs().add("-XX:+UseFastJNIAccessors");
									spec.getJvmArgs().add("-XX:+CMSScavengeBeforeRemark");
									spec.getJvmArgs().add("-XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses");
									spec.getJvmArgs().add("-XX:+ScavengeBeforeFullGC");
									spec.getJvmArgs().add("-XX:+AlwaysPreTouch");
									spec.getJvmArgs().add("-XX:+UseFastAccessorMethods");
									spec.getJvmArgs().add("-XX:+UnlockExperimentalVMOptions");
									spec.getJvmArgs().add("-XX:G1HeapWastePercent=10");
									spec.getJvmArgs().add("-XX:G1MaxNewSizePercent=10");
									spec.getJvmArgs().add("-XX:G1HeapRegionSize=32M");
									spec.getJvmArgs().add("-XX:G1NewSizePercent=10");
									spec.getJvmArgs().add("-XX:MaxGCPauseMillis=100");
									spec.getJvmArgs().add("-XX:+OptimizeStringConcat");
									spec.getJvmArgs().add("-XX:+UseParNewGC");
									spec.getJvmArgs().add("-XX:+UseNUMA");
									spec.getJvmArgs().add("-XX:+AlwaysTenure");
									spec.getJvmArgs().add("-XX:+UseCompressedOops");
									spec.getJvmArgs().add("-XX:G1NewSizePercent=10");
									spec.getJvmArgs().add("-XX:G1ReservePercent=10");
									spec.getJvmArgs().add("-XX:+UseConcMarkSweepGC");
									spec.getJvmArgs().add("-XX:+CMSClassUnloadingEnabled");
									spec.getJvmArgs().add("-XX:SurvivorRatio=2");
									spec.getJvmArgs().add("-XX:+DisableExplicitGC");
									spec.getJvmArgs().add("-Dfml.readTimeout=120");
									if (settings.getPack().equals("skyblock")) {
										spec.getLaunchArgs().add("--server technicworld.pl --port 25565");
									} else if(settings.getPack().equals("survival")) {
										spec.getLaunchArgs().add("--server technicworld.pl --port 25566");
									}
									System.out.println(spec.getLaunchArgs());
									System.out.println(spec.getJvmArgs());

									//System.out.println(Paths.get(System.getProperty("java.home"), "bin", OS.getCURRENT() == OS.WINDOWS ? "java.exe" : "java"));
									if (task.getCompletedPercentage() == 100) {
							
										atDownloadMods = false;
										atLogin = false;
										atRunMinecraft = true;
										javafx.application.Platform.runLater( () -> consolePane.setVisible(true) );
							      		javafx.application.Platform.runLater( () -> progresspane.setVisible(true) );
							      		javafx.application.Platform.runLater( () -> progress.setProgress(ProgressBar.INDETERMINATE_PROGRESS) );
										javafx.application.Platform.runLater( () -> download.setText("Uruchomiono minecraft!") );

								     BufferedReader reader = new BufferedReader(new InputStreamReader(spec.run(Paths.get(System.getProperty("java.home"), "bin", OS.getCURRENT() == OS.WINDOWS ? "java.exe" : "java")).getInputStream()));
								        if(!consoleToggle.isSelected()) {
								            console.appendText("\r\n===============================================[| URUCHOMIONO |]===============================================");
											DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
											LocalDateTime now = LocalDateTime.now();
											File theDir = new File(System.getProperty("user.home")+"//TechnicWorld//MinecraftLogs");
											if (!theDir.exists()) {
												theDir.mkdir();
											}
								            String filePath = System.getProperty("user.home")+"//TechnicWorld//MinecraftLogs//minecraft-"+settings.getPack()+"_v"+settings.getPackVersion()+"_"+dtf.format(now)+".txt";

								           
								            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
								                writer.write(console.getText());
								            } catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												erroralert(e);
											}
								            	try {
													Thread.sleep(4000);
												} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
													erroralert(e);
												}
								            	settings.saveSettings();
										      Platform.exit();
										      System.exit(0);
								        }
								            try {
												while ((line = reader.readLine()) != null) {
												    //System.out.println(line);
													javafx.application.Platform.runLater( () -> console.appendText(line+"\r\n") );
												}
												
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												erroralert(e);
											}
								            try {
												reader.close();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												erroralert(e);
											}
								            File theDir = new File(System.getProperty("user.home")+"//TechnicWorld//MinecraftLogs");
								            
									   	     // if the directory does not exist, create it
									   	     if (!theDir.exists()) {
									   	         boolean result = false;
									   	
									   	         try{
									   	             theDir.mkdirs();
									   	             result = true;
									   	         } 
									   	         catch(SecurityException se){
									   	        	erroralert(se);
									   	             //handle it
									   	         }        
									   	         if(result) {    
									   	             System.out.println("DIR created");  
									   	         }
									   	     }
								            consolePane.setVisible(false);
								            console.appendText("\r\n================================================[| ZAKOÑCZENIE |]================================================");
											DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
											LocalDateTime now = LocalDateTime.now();
								            String filePath = System.getProperty("user.home")+"//TechnicWorld//MinecraftLogs//minecraft-"+settings.getPack()+"_v"+settings.getPackVersion()+"_"+dtf.format(now)+".txt";

								           
								            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
								                writer.write(console.getText());
								            } catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												erroralert(e);
											}
								            settings.saveSettings();
								            System.out.println("MC stopped");
								            lock(false);
											atDownloadMods = false;
											atLogin = false;
											atRunMinecraft = false;
											javafx.application.Platform.runLater( () -> consolePane.setVisible(false) );
								      		javafx.application.Platform.runLater( () -> progresspane.setVisible(false) );
								      		javafx.application.Platform.runLater( () -> progress.setProgress(ProgressBar.INDETERMINATE_PROGRESS) );
											javafx.application.Platform.runLater( () -> download.setText("") );
									}
		            }};
			    new Thread(updatethread).

			    start();
				
			} catch(Exception e) {
				e.printStackTrace();
				erroralert(e);
			}
		}	
	}
	   public static void setUpStreamGobbler(final InputStream is, final PrintStream ps) {
		      final InputStreamReader streamReader = new InputStreamReader(is);
		      new Thread(new Runnable() {
		         public void run() {
		            BufferedReader br = new BufferedReader(streamReader);
		            String line = null;
		            try {
		               while ((line = br.readLine()) != null) {
		                  ps.println("process stream: " + line);
		               }
		            } catch (IOException e) {
		               e.printStackTrace();
		               erroralert(e);
		            } finally {
		               try {
		                  br.close();
		               } catch (IOException e) {
		                  e.printStackTrace();
		                  erroralert(e);
		               }
		            }
		         }
		      }).start();
		   }
	@FXML
	private void shop(ActionEvent event) {
		menu.setVisible(false);
	}
	@FXML
	private void news(ActionEvent event) {
		menu.setVisible(false);
	}
	// LOGIN //
	private String successs;
	private String usernic;
	private String uuid;
	@FXML
	private void logout(ActionEvent event) {
		if(!isLocked()) {
			try {
				System.out.println("https://technicworld.pl/index.php?api=logout&token="+settings.getToken());
				JSONObject json = readJsonFromUrl("https://technicworld.pl/index.php?api=logout&token="+settings.getToken());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				erroralert(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				erroralert(e);
			}
			atLogin = true;
			lock(true);
			mainPane.setVisible(true);
			title.setTitle("TechnicWorld launcher");
			menubtn.setVisible(false);
			menu.setVisible(false);
			settings.setToken("loggedout");
			alert("Zosta³eœ wylogowany!", "");
		}
	}
	//PACKS//
	@FXML
	private void skyb(ActionEvent event) throws JSONException, IOException {
			settings.setPack("skyblock");
			settings.saveSettings();
			modsList.clear();
			modsListInDir.clear();
			addonsList.clear();
			jsonSize = 0;
			mods = 0;
		  title.setTitle("TechnicWorld launcher - "+settings.getPack());
			modsPath = settings.workingDir+"//"+settings.getPack()+"//Minecraft//mods";
			urlModsPath = "https://technicworld.pl/api/modpack-"+settings.getPack();
			urlAddonsList = "https://technicworld.pl/api/addons_"+settings.getPack()+".json";
			urlModsList = "https://technicworld.pl/api/mods-"+settings.getPack()+".json";
			urlPackVersion = settings.getPackVersion();
	        Runnable updatethread = new Runnable() {
	            public void run() {
	           try {

				modpack();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				erroralert(e);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				erroralert(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				erroralert(e);
			}
	        }
	        
	    };
	    new Thread(updatethread).

	    start();
	    mainPane.setVisible(false);
	    mainPacks.setVisible(false);
	}
	@FXML
	private void survi(ActionEvent event) throws JSONException, IOException {
		settings.setPack("survival");
		settings.saveSettings();
		modsList.clear();
		modsListInDir.clear();
		addonsList.clear();
		jsonSize = 0;
		mods = 0;
		  title.setTitle("TechnicWorld launcher - "+settings.getPack());
			modsPath = settings.workingDir+"//"+settings.getPack()+"//Minecraft//mods";
			urlModsPath = "https://technicworld.pl/api/modpack-"+settings.getPack();
			urlAddonsList = "https://technicworld.pl/api/addons_"+settings.getPack()+".json";
			urlModsList = "https://technicworld.pl/api/mods-"+settings.getPack()+".json";
			urlPackVersion = settings.getPackVersion();
	        Runnable updatethread = new Runnable() {
	            public void run() {
	           try {

				modpack();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	        
	    };
	    new Thread(updatethread).

	    start();
	    mainPacks.setVisible(false);
	    mainPane.setVisible(false);
	}
	@FXML
	private void loginbtn(ActionEvent event) throws JSONException, IOException {
		boolean success = checklogin(nickfield.getText().trim(), passwordfield.getText().trim());


		if (success) {
	        	  String pack = settings.getPack();
	        	  System.out.println(pack);
	        	  if (pack.equals("survival") | pack.equals("skyblock")) {
	        		  mainPane.setVisible(false);
	        		  atLogin = false;
	        		  title.setTitle("TechnicWorld launcher - "+settings.getPack());
	      			modsPath = settings.workingDir+"//"+settings.getPack()+"//Minecraft//mods";
	    			urlModsPath = "https://technicworld.pl/api/modpack-"+settings.getPack();
	    			urlAddonsList = "https://technicworld.pl/api/addons_"+settings.getPack()+".json";
	    			urlModsList = "https://technicworld.pl/api/mods-"+settings.getPack()+".json";
	    			urlPackVersion = settings.getPackVersion();
	        		  menubtn.setVisible(true);
	        	        Runnable updatethread = new Runnable() {
	        	            public void run() {
	        	           try {

	        				modpack();
	        			} catch (JSONException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        				erroralert(e);
	        			} catch (NoSuchAlgorithmException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        				erroralert(e);
	        			} catch (IOException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        				erroralert(e);
	        			}
	        	        }
	        	        
	        	    };
	        	    new Thread(updatethread).

	        	    start();
	        	  } else {
	        		  mainPacks.setVisible(true);
	        	  }
		} else {
			
		}
	}
	 private boolean checklogin(String user, String pass) throws JSONException, IOException {
		 	resetColors();
		 	//System.out.println("https://technicworld.pl/index.php?api=login&login="+user+"&pass="+pass);
		 	JSONObject json = readJsonFromUrlPost("https://technicworld.pl/index.php?api=login", "login="+user+"&pass="+pass);
		    System.out.println(json.toString());
		    //System.out.println(json.get("info"));
		    if (!json.isNull("info")) {
		    	loginlabel.setText("Zalogowano poprawnie!");
		    	//System.out.println(json.get("success"));
		    	successs = (String) (json.get("success"));
		    	usernic = (String) (json.get("username"));
		    	nick = (String) (json.get("username"));
		    	uuid = (String) (json.get("uuid"));
		    	System.out.println("USERNAME: "+json.get("username"));
		    	  settings.setToken(json.getString("success"));
		    	  settings.saveSettings();
		    	menubtn.setText(nick);
		    	return true;
		    } 
		    else {
		    	System.out.println(json.get("error"));
		    	loginlabel.setText((String) json.get("msg"));
		    	if (((String) json.get("msg")).equals("Wype³nij pole z loginem!")) {
		    		nickfield.setUnFocusColor(Color.RED);
		    	} else if (((String) json.get("msg")).equals("Wype³nij pole z has³em!")) {
		    		passwordfield.setUnFocusColor(Color.RED);
		    	} else {
		    		nickfield.setUnFocusColor(Color.RED);
		    		passwordfield.setUnFocusColor(Color.RED);
		    	}
		    	return false;
		    }
			
	 
		
	 }
	 private void resetColors() {
		 Color normal = new Color(0.77, 0.77, 0.77, 1);
		 nickfield.setUnFocusColor(normal);
		 passwordfield.setUnFocusColor(normal);
	 }
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }

		  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		      String jsonText = readAll(rd);
		      System.out.println(jsonText);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
			private static HttpURLConnection con;

		  public static JSONObject readJsonFromUrlPost(String url, String urlParameters) throws IOException, JSONException {

		        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		        try {

		            URL myurl = new URL(url);
		            con = (HttpURLConnection) myurl.openConnection();

		            con.setDoOutput(true);
		            con.setRequestMethod("POST");
		            con.setRequestProperty("User-Agent", "Java client");
		            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
		                wr.write(postData);
		            }

		            StringBuilder content;

		            try (BufferedReader in = new BufferedReader(
		                    new InputStreamReader(con.getInputStream()))) {

		                String line;
		                content = new StringBuilder();

		                while ((line = in.readLine()) != null) {
		                    content.append(line);
		                    content.append(System.lineSeparator());
		                }
		            }

				      JSONObject json = new JSONObject(content.toString());
				      return json;

		        } finally {
		            
		            con.disconnect();
		        }
			  }
		  @FXML
			 public void checkd() throws IOException {

				 
				 		  String token = settings.getToken();
				 		  System.out.println("https://technicworld.pl/index.php?api=check&token="+token);
						  JSONObject json = readJsonFromUrl("https://technicworld.pl/index.php?api=check&token="+token);
						  if(json.get("token").equals("succ")) {
							  		  nick = (String) (json.get("username"));
						        	  String pack = settings.getPack();
						        	  System.out.println(pack);
						        	  if (pack.equals("survival") | pack.equals("skyblock")) {
						        		  mainPane.setVisible(false);
						        		  atLogin = false;
						        		  title.setTitle("TechnicWorld launcher - "+settings.getPack());
						      			modsPath = settings.workingDir+"//"+settings.getPack()+"//Minecraft//mods";
						    			urlModsPath = "https://technicworld.pl/api/modpack-"+settings.getPack();
						    			urlAddonsList = "https://technicworld.pl/api/addons_"+settings.getPack()+".json";
						    			urlModsList = "https://technicworld.pl/api/mods-"+settings.getPack()+".json";
						    			urlPackVersion = settings.getPackVersion();
						    			
						    			menubtn.setText(nick);
						        		  menubtn.setVisible(true);
						        	        Runnable updatethread = new Runnable() {
						        	            public void run() {
						        	           try {

						        				modpack();
						        			} catch (JSONException e) {
						        				// TODO Auto-generated catch block
						        				e.printStackTrace();
						        				erroralert(e);
						        			} catch (NoSuchAlgorithmException e) {
						        				// TODO Auto-generated catch block
						        				e.printStackTrace();
						        				erroralert(e);
						        			} catch (IOException e) {
						        				// TODO Auto-generated catch block
						        				e.printStackTrace();
						        				erroralert(e);
						        			}
						        	        }
						        	        
						        	    };
						        	    new Thread(updatethread).

						        	    start();
						        	  } else {
						        		  mainPacks.setVisible(true);
						        	  }
						        } else {
						        	mainPane.setVisible(true);
						        	if (!settings.getToken().equals("loggedout") & !settings.getToken().equals("-1")) {
						        		alert("Zosta³eœ wylogowany!", "");
						        	}
						        	
						}
			}
//ERRORS//
		  
		  
		  public static void erroralert(Exception ex) {
			  Alert alert = new Alert(AlertType.ERROR);
			  alert.setTitle("Wyst¹pi³ b³¹d w aplikacji!");
			  alert.setHeaderText("Je¿eli b³¹d nadal wystêpuje po restarcie aplikacji skontaktuj siê z administratorem!");
			  alert.setContentText("Mo¿na nas znaleŸæ na www.technicworld.pl oraz na ts3: technicworld.pl");


			  // Create expandable Exception.
			  StringWriter sw = new StringWriter();
			  PrintWriter pw = new PrintWriter(sw);
			  ex.printStackTrace(pw);
			  String exceptionText = sw.toString();

			  Label label = new Label("B³¹d:");

			  TextArea textArea = new TextArea(exceptionText);
			  textArea.setEditable(false);
			  textArea.setWrapText(true);

			  textArea.setMaxWidth(Double.MAX_VALUE);
			  textArea.setMaxHeight(Double.MAX_VALUE);
			  GridPane.setVgrow(textArea, Priority.ALWAYS);
			  GridPane.setHgrow(textArea, Priority.ALWAYS);

			  GridPane expContent = new GridPane();
			  expContent.setMaxWidth(Double.MAX_VALUE);
			  expContent.add(label, 0, 0);
			  expContent.add(textArea, 0, 1);
			  // Set expandable Exception into the dialog pane.
			  alert.getDialogPane().setExpandableContent(expContent);
			  alert.getDialogPane().setExpanded(true);
			  alert.showAndWait();
		  }
		  private void alert(String title, String content) {
			  try {
			    BoxBlur boxblur = new BoxBlur(3,3,3);
			    anchorMainPane.setEffect(boxblur);
			    JFXDialogLayout dialogContent = new JFXDialogLayout();
			    
			    dialogContent.setHeading(new Text(title));
			    
			    dialogContent.setBody(new Text(content));
			    
			    JFXButton close = new JFXButton("Okej");
			    close.setTextFill(Color.WHITE);
			    close.setStyle("-fx-background-color: #292b2c;");
			    
			    dialogContent.setActions(close);
			    
			    JFXDialog dialog = new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.CENTER);

			    close.setOnAction(actionEvent -> {
			    	dialog.close();
		        });

			    dialog.setOnDialogClosed(closeEvent -> {
			    	anchorMainPane.setEffect(null);
			   });
			    dialog.autosize();
			    dialog.show();
			    
			  } catch(Exception e) {
				  e.printStackTrace();
				  erroralert(e);
			  }
		    }
		  private void updatealert(String version, String whatsnew) {
			    BoxBlur boxblur = new BoxBlur(3,3,3);
			    anchorMainPane.setEffect(boxblur);
			    JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
			    jfxDialogLayout.setHeading(new Text("Aktualizacja v "+version));
			    jfxDialogLayout.setBody(new Text(whatsnew));
			    JFXDialog dialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
			    JFXButton okay = new JFXButton("Pobiorê póŸniej");
			    JFXButton openWeb = new JFXButton("Otwórz stronê pobierania");
			    okay.setPrefWidth(110);
			    okay.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");
			    okay.setButtonType(JFXButton.ButtonType.RAISED);
			    okay.setOnAction(event -> {
			    	dialog.close();
			    });
			    dialog.setOnDialogClosed(closeEvent -> {
			    	anchorMainPane.setEffect(null);
			   });
			    openWeb.setPrefWidth(190);
			    openWeb.setStyle("-fx-background-color:  #00A65A; -fx-text-fill: white;");
			    openWeb.setButtonType(JFXButton.ButtonType.RAISED);
			    openWeb.setOnAction(event -> {
			    	openBrowser("https://technicworld.pl/?site=paczka");
			    	Platform.exit();
				    System.exit(0);
			    });

			    HBox hBox = new HBox(5);
			    hBox.getChildren().addAll(openWeb, okay);
			    jfxDialogLayout.setActions(hBox);
			    dialog.show();
		  }
		  public void openBrowser(String URL) {
				String url = URL;

		        if(Desktop.isDesktopSupported()){
		            Desktop desktop = Desktop.getDesktop();
		            try {
		                desktop.browse(new URI(url));
		            } catch (IOException | URISyntaxException e) {
		                ((Throwable) e).printStackTrace();
		                erroralert(e);
		            }
		        }else{
		            Runtime runtime = Runtime.getRuntime();
		            try {
		                runtime.exec("xdg-open " + url);
		            } catch (IOException e) {
		                e.printStackTrace();
		                erroralert(e);
		            }
		        }
			}
		  //MODY //

			@FXML
			
			public void Procent() {
				progress.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
			}

			
			@SuppressWarnings("finally")
			public boolean modpack() throws JSONException, IOException, NoSuchAlgorithmException {
				lock(true);
				atDownloadMods = false;
		        // update progress bar
		        Platform.runLater(new Runnable() {
		        	
		            @Override
		            public void run() {
			          	progresspane.setVisible(true);
			          	progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
			        	download.setText("Sprawdzam mody...");
		            }
		        });
				try {
					getModsJson();
					if(startCheckingAndDownloadMods(urlModsPath, modsPath) == true) {
						try {
							if(!checkMods(urlModsPath, modsPath)) {
								System.out.println("Wyst¹pi³ nieznany problem!");
							}
							downloadConfigs();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							erroralert(e);
						}
					} else {
						System.out.println("Wyst¹pi³ nieznany problem!");
					}
				
				} finally {
					lock(false);
					atDownloadMods = false;
			        // update progress bar
			        Platform.runLater(new Runnable() {;

			            @Override
			            public void run() {
				          	progresspane.setVisible(false);
				          	progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
				        	download.setText("Zakoñczono!");
			            }
			            
			        });
			        return true;
				}
			}
			
			private boolean getModsJson() throws JSONException, IOException {
				JSONObject json = readJsonFromUrl(urlModsList);
				jsonSize = json.getJSONArray("mods").length();
				urlPackVersion = json.getString("version");
			    for (int i = 0; i < jsonSize; i++) {
			        JSONObject jsonObject = json.getJSONArray("mods").getJSONObject(i);
			        modsListClass arrayMods = new modsListClass();
			        arrayMods.setId(i);
			        arrayMods.setName(jsonObject.get("name").toString());
			        arrayMods.setAddon(jsonObject.get("addon").toString());
			        arrayMods.setHash(jsonObject.get("md5").toString().toLowerCase());
			        modsList.add(arrayMods);
			        mods+=1;
			    }
				return true;
			}
			
			private String getModHash(String mod) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
				System.out.println(mod);
				File folder = new File(mod);
		        if (!folder.exists()) {
		        	return "false";
		        }
		        FileInputStream fis = new FileInputStream(mod);
			    MessageDigest md = MessageDigest.getInstance("MD5");
			    String digest = getDigest(fis, md, 2048);
			    fis.close();
			    return digest.toLowerCase();
			     
			}
			@SuppressWarnings("finally")
			private boolean checkMods(String modsUrl, String modspath) throws IOException {
				
				lock(true);
				atDownloadMods = false;
		        // update progress bar
		        Platform.runLater(new Runnable() {;
		        	
		            @Override
		            public void run() {
			          	progresspane.setVisible(true);
			          	progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
			        	download.setText("Sprawdzam mody...");
		            }
		        });
				try {
					File folder = new File(modspath);
				    File[] listOfFiles = folder.listFiles();
				  
				    String modsStr = "";
					for (modsListClass mods : modsList) {
						modsStr+=mods.getName();
						if (mods.isAddon()) {
							modsStr+=mods.getName()+".disable";
						}
						if(mods.isAddon()) {
							String modStr = modspath+"/"+mods.getName().replace("|", "//");
							File mod = new File(modStr);
							String modStrAddon = modspath+"/"+mods.getName().replace("|", "//")+".disable";
							File modAddon = new File(modStrAddon);
							if (!modAddon.exists() && !mod.exists()) {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							} else {
								try {
									if (!getModHash(modStrAddon).contains(mods.getHash().toLowerCase()) && !getModHash(modStr).contains(mods.getHash().toLowerCase())) {
										downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
									}
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								}
							}
						} else {
							String modStr = modspath+"/"+mods.getName().replace("|", "//");
							File mod = new File(modStr);
							if (mod.exists()) {
								try {
									if (!getModHash(modStr).contains(mods.getHash().toLowerCase())) {
										downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
									}
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								}
							} else {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							}
						}
					}
				    for (int i = 0; i < listOfFiles.length; i++) {
				    	 if (listOfFiles[i].isFile()) {
							 if(!modsStr.contains(listOfFiles[i].getName()) == true) {
								 System.out.println(listOfFiles[i].getName()+", usuwanie...");
								 Path pat = Paths.get(modspath+"//"+listOfFiles[i].getName());
				            	 Files.delete(pat);
				            	 System.out.println("Usuniêto!");
				    		 }
				         }
				    }
				} finally {
					return true;
				}
			}
			
			@SuppressWarnings("unused")
			private void downloadConfigs() throws IOException {
				if (!settings.getPackVersion().contains(urlPackVersion)) {
			            lock(true);
			            atDownloadMods = false;
			            // update progress bar
			            Platform.runLater(new Runnable() {;
			
			                @Override
			                public void run() {
				          	progresspane.setVisible(true);
				          	progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
				        	download.setText("Pobieram: konfiguracja (config)");
			                  
			                }
			            });
						System.out.println(settings.getPackVersion()+"::"+urlPackVersion);
		  	            URL url = new URL("https://technicworld.pl/api/config-"+settings.getPack()+".zip");
		  	            File p1 = new File(settings.workingDir+"//"+settings.getPack()+"//Minecraft");
				  	    unpackArchive(url, p1);
						settings.setPackVersion(urlPackVersion);
						settings.saveSettings();
				} else {
					Path path = Paths.get(settings.workingDir+"//"+settings.getPack()+"//Minecraft//config");
					if (Files.notExists(path)) {
			            lock(true);
			            atDownloadMods = false;
			            // update progress bar
			            Platform.runLater(new Runnable() {;
			
			                @Override
			                public void run() {
				          	progresspane.setVisible(true);
				          	progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
				        	download.setText("Pobieram: konfiguracja (config)");
			                  
			                }
			            });
						System.out.println(settings.getPackVersion()+"::"+urlPackVersion);
		  	            URL url = new URL("https://technicworld.pl/api/config-"+settings.getPack()+".zip");
		  	            File p1 = new File(settings.workingDir+"//"+settings.getPack()+"//Minecraft");
				  	    unpackArchive(url, p1);
						settings.setPackVersion(urlPackVersion);
						settings.saveSettings();
					}
				}
					
			}
			
		    public static File unpackArchive(URL url, File targetDir) throws IOException {
		        if (!targetDir.exists()) {
		            targetDir.mkdirs();
		        }
		        InputStream in = new BufferedInputStream(url.openStream(), 1024);
		        // make sure we get the actual file
		        File zip = File.createTempFile("arc", ".zip", targetDir);
		        OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
		        copyInputStream(in, out);
		        out.close();
		        return unpackArchive(zip, targetDir);
		    }
		    @SuppressWarnings("resource")
			public static File unpackArchive(File theFile, File targetDir) throws IOException {
		        if (!theFile.exists()) {
		            throw new IOException(theFile.getAbsolutePath() + " nie istn");
		        }
		        if (!buildDirectory(targetDir)) {
		            throw new IOException("Could not create directory: " + targetDir);
		        }
		        ZipFile zipFile = new ZipFile(theFile);
		        for (Enumeration<?> entries = zipFile.entries(); entries.hasMoreElements();) {
		            ZipEntry entry = (ZipEntry) entries.nextElement();
		            File file = new File(targetDir, File.separator + entry.getName());
		            if (!buildDirectory(file.getParentFile())) {
		                throw new IOException("Could not create directory: " + file.getParentFile());
		            }
		            if (!entry.isDirectory()) {
		                copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
		            } else {
		                if (!buildDirectory(file)) {
		                    throw new IOException("Could not create directory: " + file);
		                }
		            }
		        }
		        zipFile.close();
		        return theFile;
		    }

		    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
		        byte[] buffer = new byte[1024];
		        int len = in.read(buffer);
		        while (len >= 0) {
		            out.write(buffer, 0, len);
		            len = in.read(buffer);
		        }
		        in.close();
		        out.close();
		    }

		    public static boolean buildDirectory(File file) {
		        return file.exists() || file.mkdirs();
		    }
			
			private void downloadMod(URL url, String path, String modname, File modsPath, File modsPath2, Boolean addon) {
				if (addon) {
					path +=".disable";
				}
				 try  {
					 if (!modsPath2.exists()) {
				         System.out.println("Tworzê folder: " + modsPath2.getName());
				         boolean result = false;
				
				         try{
				        	 modsPath2.mkdirs();
				             result = true;
				         } 
				         catch(SecurityException se){
				        	 erroralert(se);
				         }        
				         if(result) {    
				             System.out.println("stworzono!");  
				         }
				     }
					 File pathic = new File((modsPath2+"//1.12.2"));
					 System.out.println(pathic);
					 if (!pathic.exists()) {
				         System.out.println("Tworzê folder: " + modsPath2.getName());
				         boolean result = false;
				
				         try{
				        	 pathic.mkdirs();
				             result = true;
				         } 
				         catch(SecurityException se){
				        	 erroralert(se);
				         }        
				         if(result) {    
				             System.out.println("stworzono!");  
				         }
				     }
					 if (!modsPath.exists()) {
				         System.out.println("Tworzê folder: " + modsPath.getName());
				         boolean result = false;
				
				         try{
				        	 modsPath.mkdirs();
				             result = true;
				         } 
				         catch(SecurityException se){
				        	 erroralert(se);
				         }        
				         if(result) {    
				             System.out.println("Stworzono!");  
				         }
				     }
					 System.out.println("Pobieram mod: "+modname+" ...");
		             HttpURLConnection httpsConnection = (HttpURLConnection) (url.openConnection());
		             long completeFileSize = httpsConnection.getContentLength();
		             java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpsConnection.getInputStream());
		             java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
		             java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		             byte[] data = new byte[1024];
		             long downloadedFileSize = 0;
		             int x = 0;
		             while ((x = in.read(data, 0, 1024)) >= 0) {
		                 downloadedFileSize += x;
		                 // calculate progress
		                 final double currentProgress = ((((double)downloadedFileSize) / ((double)completeFileSize)) * 100d);
		                 lock(true);
		                 atDownloadMods = true;
		                 // update progress bar
		                 Platform.runLater(new Runnable() {;

		                     @Override
		                     public void run() {
				          	progresspane.setVisible(true);
				          	progress.setProgress(currentProgress/100);
				        	download.setText("Pobieram: "+modname.replaceAll("1.7.10|", ""));
		                     }
		                 });
		                 bout.write(data, 0, x);
		             }
		             bout.close();
		             in.close();
		             System.out.println("Pobrano!");
		         } catch (FileNotFoundException e) {
		       	  	 System.out.println(e);
		       	  erroralert(e);
		         } catch (IOException e) {
		        	 System.out.println(e);
		        	 erroralert(e);
		         }
			}
			
			@SuppressWarnings("finally")
			private boolean startCheckingAndDownloadMods(String modsUrl, String modspath) throws MalformedURLException {
				try {
					for (modsListClass mods : modsList) {
						if(mods.isAddon()) {
							String modStr = modspath+"/"+mods.getName().replace("|", "//");
							File mod = new File(modStr);
							String modStrAddon = modspath+"/"+mods.getName().replace("|", "//")+".disable";
							File modAddon = new File(modStrAddon);
							if (!modAddon.exists() && !mod.exists()) {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							} else {
								try {
									if (!getModHash(modStrAddon).contains(mods.getHash().toLowerCase()) && !getModHash(modStr).contains(mods.getHash().toLowerCase())) {
										downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
									}
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								}
							}
						} else {
							String modStr = modspath+"/"+mods.getName().replace("|", "//");
							File mod = new File(modStr);
							if (mod.exists()) {
								try {
									if (!getModHash(modStr).contains(mods.getHash().toLowerCase())) {
										downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
									}
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									erroralert(e);
								}
							} else {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							}
						}
					}
				} finally {
					return true;
				}
			}
			public String getDigest(InputStream is, MessageDigest md, int byteArraySize)
					throws NoSuchAlgorithmException, IOException {
						md.reset();
						byte[] bytes = new byte[byteArraySize];
						int numBytes;
						while ((numBytes = is.read(bytes)) != -1) {
							md.update(bytes, 0, numBytes);
						}
						byte[] digest = md.digest();
						String result = new String(Hex.encodeHex(digest)).toUpperCase();
						return result;
				}
}

