package navegacao;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class MainController {

	@FXML
	public void chamaTela2(){
		try{
			System.out.println("TELA 2");
			BorderPane tela = (BorderPane)FXMLLoader.load(getClass().getResource("Tela01.fxml"));
			Main.scene.setRoot(tela);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	public void chamaTela3(){
		try{
			System.out.println("TELA 3");
			BorderPane tela = (BorderPane)FXMLLoader.load(getClass().getResource("Tela02.fxml"));
			Main.scene.setRoot(tela);    //aplica a tela como a scene principal
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
