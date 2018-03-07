package controller;

import java.sql.Connection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import model.Aluno;
import model.Curso;
import model.Matricula;
import useful.Conexao;
import useful.Mensagens;

public class MatriculaController {

    @FXML private ComboBox<Aluno> comboAluno;
    @FXML private ComboBox<Curso> comboCurso;
    @FXML private Label labelSinal;
    @FXML private TableView<Matricula> tableMatricula;
    @FXML private TableColumn<Matricula, String> colBusca;
    @FXML private ImageView imgFiltroAluno;
    @FXML private ImageView imgFiltroCurso;
    
    ArrayList<Aluno> alunos = new ArrayList<Aluno>(); 
    ArrayList<Curso> cursos = new ArrayList<Curso>(); 
    ArrayList<Matricula> busca = new ArrayList<Matricula>();  
    private Connection conn = Conexao.conn();
    
    
	    @FXML
	    public void matricular(){
	    	try{
		    	if(comboAluno.getValue() == null || comboCurso.getValue() == null)
		    		throw new NullPointerException();
		    	Matricula m = new Matricula();
		    	m.setAluno(comboAluno.getSelectionModel().getSelectedItem());
		    	m.setCurso(comboCurso.getSelectionModel().getSelectedItem());
		    	if(m.insere(conn));
		    		Mensagens.msgConfirmacao("Matriculado", (m.getAluno().getSexo().equals("Masc")?"Cadastrado com sucesso!":"Cadastrada com sucesso!"));
			}catch (NullPointerException e){
				Mensagens.msgErro("Matrícula", "ERRO: Campo esta vazio, selecione um item!");
			}catch (Exception e){
				e.printStackTrace();
				}
	    }
	    
	    @FXML
	    public void filtraPorAluno(){
	    	Aluno a = comboAluno.getSelectionModel().getSelectedItem();
	    	if(a==null)
	    		Mensagens.msgErro("Erro", "Selecione um aluno");
	    	else{
	    		busca = Matricula.filtraPorAluno(conn, a);
	    		tableMatricula.setItems(FXCollections.observableArrayList(busca));
	    	}	
	    }
	    
	    @FXML
	    public void filtraPorCurso(){
	    	Curso c = comboCurso.getSelectionModel().getSelectedItem();
	    	if(c==null)
	    		Mensagens.msgErro("Erro", "Selecione um aluno");
	    	else{
	    		busca = Matricula.filtraPorCurso(conn, c);
	    		tableMatricula.setItems(FXCollections.observableArrayList(busca));
	    	}	
	    }
	    
	    @FXML
	    public void atualizaCurso(){
	    	comboCurso.setItems(FXCollections.observableArrayList(Curso.listarTodas(conn, null)));
	    }
	    
	    public void atualizaAluno(){
	      	comboAluno.setItems(FXCollections.observableArrayList(Aluno.listarTodas(conn, null)));
	    }
	    
	    @FXML
	    public void initialize(){
	    	atualizaCurso();
	    	atualizaAluno();
	    	colBusca.setCellValueFactory(cellData -> cellData.getValue().dadosProperty());  
	    }
}
