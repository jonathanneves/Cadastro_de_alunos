package navegacao_tab;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {

	@FXML public TabPane tabPane;
	
	//verificar se uma tab esta aberta
	private Tab tabAberta(String titulo){
		for (Tab tb : tabPane.getTabs()) {
			if(tb.getText().equals(titulo))
				return tb;
		}
		return null;
	}
	
	//Seleciona uma tab ou uma nova scene
	private void selecionaTab(Tab tab){
		tabPane.getSelectionModel().select(tab);			
	}
	
	private void abreTab(String titulo, String path) {
		try {
			Tab tab = tabAberta(titulo);
			if(tab==null){
				tab = new Tab(titulo);
				tabPane.getTabs().add(tab);
				tab.setContent((Node) FXMLLoader.load(getClass().getResource(path)));
			}
			selecionaTab(tab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void abreJanela1(){
		abreTab("Tela 1", "Tela01.fxml");
	}
	
	@FXML
	public void abreJanela2(){
		abreTab("Tela 2", "Tela02.fxml");
	}
	
	@FXML
	public void abreJanela3(){
		abreTab("Tela 3", "Tela03.fxml");
	}
	
}
