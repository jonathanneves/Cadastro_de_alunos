package controller;

import java.sql.Connection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Curso;
import useful.Conexao;
import useful.Mensagens;

public class CursoController {


    @FXML private TableView<Curso> tablecurso;
    @FXML private TableColumn<Curso, String> colCurso;
    @FXML private TextField txtCurso;
    @FXML private TextField txtFiltroCurso;

    ArrayList<Curso> cursos = new ArrayList<Curso>();
    private Connection conn = Conexao.conn();
    
    @FXML
    private void atualizaTblCurso(){
  	  	cursos = Curso.listarTodas(conn, null);
       	tablecurso.setItems(FXCollections.observableArrayList(cursos));
    }
    
    @FXML
    public void clicaTblCurso(){ 	//Seleciona na tabela e preenche os campos
    	Curso selecionado = tablecurso.getSelectionModel().getSelectedItem();
    	txtCurso.setText(selecionado.getNome());
    }
    
    @FXML
    public void insereCurso(){
    	try{
    		if(txtCurso.getText().isEmpty())
    			throw new NullPointerException();
    		Curso cr = new Curso();
    		cr.setNome(txtCurso.getText());
    		cr.insere(conn);
    		atualizaTblCurso();
    		System.out.println("CURSO CADSATRADO COM SUCESSO");
     	}catch (NullPointerException e){
     		Mensagens.msgErro("Cadastro", "ERRO: Preencha todos os campos!");
		}catch (Exception e){
			e.printStackTrace();
		}
    }
    
    @FXML
    public void excluiCurso(){
    	try{
    		if(Mensagens.msgEscolha("Atenção, Excluir Curso","Você deseja excluir esse Curso?")){
    			Curso sel = tablecurso.getSelectionModel().getSelectedItem();
	    		sel.exclui(conn);
	    		atualizaTblCurso();
    		}
    	}catch(Exception e){
    		Mensagens.msgErro("Excluir", "ERRO: Selecione um curso na tabela!");
    	}
    }
    
    @FXML
    public void alteraCurso(){
    	try{
	    	Curso sel = tablecurso.getSelectionModel().getSelectedItem();
	    	sel.setNome(txtCurso.getText());
	    	sel.altera(conn);
	    	atualizaTblCurso();
    	}catch(Exception e){
    		Mensagens.msgErro("Alterar", "ERRO: Selecione um curso na tabela!");
    	}
    }
    
    @FXML
    public void filtrarCurso(){
    	String filtro = txtFiltroCurso.getText();
    	if(filtro.equals(""))
    		filtro=null;
    	cursos = Curso.listarTodas(conn, filtro);
    	tablecurso.setItems(FXCollections.observableArrayList(cursos));
    }
    
    @FXML
    public void initialize(){
    	colCurso.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());  
    	atualizaTblCurso();
    }
}
