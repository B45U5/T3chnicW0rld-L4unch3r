package pl.B4GU5.Controllers;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class addons_dea {

	//Kontroler itemu listy
	String ID0 = "";
    @FXML
	private Text nazwa;
    @FXML
	private Text autor;
    @FXML
	private Text opis;
    @FXML
	private Text diaxy;
    @FXML
	private Text wersja;
    @FXML
	private Pane obrazek;
    @FXML
	private Text typ;
    @FXML
	private Text pobran;
    @FXML
	private Button playBTN;
    @FXML
	private Text kiedy;

	@FXML
	   private void initialize() {
	       nazwa.setText("Wczytujê...");
	   }
	   public void Nazwa(String tekst) {
		   nazwa.setText(tekst);
	   }
	   public void Obrazek(String tekst) {
		   obrazek.setStyle("-fx-background-image: url('"+tekst+"');");
	   }
	   public void ID(String tekst) {
		   ID0 = tekst;
	   }
}
