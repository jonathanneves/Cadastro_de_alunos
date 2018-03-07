package controller;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Curso;
import model.Matricula;
import useful.Conexao;
import useful.Mensagens;

public class ConsultaController {


    @FXML private ComboBox<String> comboUFConsulta;
    @FXML private ComboBox<Curso> comboCursoConsulta;
    @FXML private TableView<Matricula> tableConsulta;
    @FXML private RadioButton rdMascConsulta;
    @FXML private RadioButton rdFemiConsulta;
    @FXML private TableColumn<Matricula, String> colConsulta;
    
    ArrayList<String> estados = new ArrayList<>();
    ArrayList<Matricula> matriculas = new ArrayList<>();
    private Connection conn = Conexao.conn();
    
	    @FXML
	    public void ConsultarPorUF(){
	    	try{
	    		String uf = comboUFConsulta.getSelectionModel().getSelectedItem();
	    		if(uf==null)
	    			Mensagens.msgErro("Erro", "Selecione um UF");
	    		else{
	    		tableConsulta.setItems(FXCollections.observableArrayList(Matricula.ConsultaPorUF(conn, uf)));
	    		}
	    	}catch(Exception e){
	    		e.getMessage();
	    	}
	    }
	    
	    @FXML
	    public void ConsultarPorSexo(){
	      	if(rdMascConsulta.isSelected())
	    		tableConsulta.setItems(FXCollections.observableArrayList(Matricula.ConsultaPorSexo(conn, "Masc")));
	      	else
	    		tableConsulta.setItems(FXCollections.observableArrayList(Matricula.ConsultaPorSexo(conn, "Femi")));
	    	}	
	    
	    @FXML
	    public void ConsultarPorCurso(){
	    	Curso c = comboCursoConsulta.getSelectionModel().getSelectedItem();
	    	if(c==null)
	    		Mensagens.msgErro("Erro", "Selecione um Curso");
	    	else{
	    		tableConsulta.setItems(FXCollections.observableArrayList(Matricula.ConsultaPorCurso(conn, c)));
	    	}
	    }
	    
	    
	    private void populaEstados(){
	       	estados.add("AC");
	    	estados.add("AL");
	    	estados.add("AP");
	    	estados.add("AM");
	    	estados.add("BA");
	    	estados.add("CE");
	    	estados.add("DF");
	    	estados.add("ES");
	    	estados.add("GO");
	    	estados.add("MA");
	    	estados.add("MT");
	    	estados.add("MS");
	    	estados.add("MG");
	    	estados.add("PA");
	    	estados.add("PB");
	    	estados.add("PR");
	    	estados.add("PE");
	    	estados.add("PI");
	    	estados.add("RJ");
	    	estados.add("RN");
	    	estados.add("RO");
	    	estados.add("RR");
	    	estados.add("RS");
	    	estados.add("SC");
	    	estados.add("SP");
	    	estados.add("SE");
	    	estados.add("TO");
	    }
	    @FXML
	    public void atualizaCombo(){
	       	comboCursoConsulta.setItems(FXCollections.observableArrayList(Curso.listarTodas(conn, null)));
	    }
	    
	    @FXML
	    public void initialize(){
	    	populaEstados();
	    	atualizaCombo();
	    	rdMascConsulta.setSelected(true);
	    	comboUFConsulta.setItems(FXCollections.observableArrayList(estados));
	    	colConsulta.setCellValueFactory(cellData -> cellData.getValue().dadosProperty());  
	    }
    }

