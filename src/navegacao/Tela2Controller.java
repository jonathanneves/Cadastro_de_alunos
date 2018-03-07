package navegacao;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class Tela2Controller {

		@FXML
		public void chamaHome(){
			try{
				System.out.println("HOME");
				BorderPane tela = (BorderPane)FXMLLoader.load(getClass().getResource("TelaHome.fxml"));
				Main.scene.setRoot(tela);
			} catch (IOException e){
				e.printStackTrace();
			}
		}
}
	

