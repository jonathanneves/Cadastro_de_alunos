package navegacao;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static Stage stage;
	public static Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaHome.fxml"));		//caminho absoluto da leta que irá abrir
			BorderPane root = (BorderPane) loader.load();				//criar o BorderPane;
			scene = new Scene(root);									//vai linkar o scene (Apague o Scene scene = deixe so scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Troca de Telas");
			primaryStage.show();
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
