package controller;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Aluno;
import model.Cidade;
import useful.Conexao;
import useful.Mensagens;

public class AlunoController {

    @FXML private RadioButton rdFemi;
    @FXML private RadioButton rdMasc;
    @FXML private TextField txtNomeAluno;
    @FXML private TextField txtidade;
    @FXML private TextField txtFiltroAluno;
    @FXML private ComboBox<Cidade>  comboCidade;
    @FXML private TableView<Aluno> tablealuno;
    @FXML private TableColumn<Aluno, String> colNomeAluno;
    @FXML private TableColumn<Aluno, String> colUfAluno;
    @FXML private TableColumn<Aluno, Number> colIdade;
    @FXML private TableColumn<Aluno, String> colSexo;
    @FXML private TableColumn<Aluno, String> colCidade;
    
    ArrayList<Aluno> alunos = new ArrayList<Aluno>(); 	
    ArrayList<Cidade> cidades = new ArrayList<Cidade>();
    private Connection conn = Conexao.conn();
    
	@FXML 
    private void atualizaTblAluno(){
    	alunos = Aluno.listarTodas(conn, null);
    	tablealuno.setItems(FXCollections.observableArrayList(alunos));
    }
    
    @FXML
    public void clicaTblAluno(){ 	//Seleciona na tabela e preenche os campos
    	Aluno selecionado = tablealuno.getSelectionModel().getSelectedItem();
    	txtNomeAluno.setText(selecionado.getNome());
    	if(selecionado.getSexo().equals("Masc"))
    		rdMasc.setSelected(true);
    	else
    		rdFemi.setSelected(false);
    	txtidade.setText(selecionado.getIdade()+"");
    	comboCidade.getSelectionModel().select(selecionado.getCidade());
    }
    
    @FXML
    public void insereAluno(){
    	try{
    		if(txtNomeAluno.getText().isEmpty() || comboCidade.getValue() == null || txtidade.getText().isEmpty()  || (!rdMasc.isSelected() && !rdFemi.isSelected()))
    			throw new NullPointerException();
    		Aluno a = new Aluno();
    		a.setNome(txtNomeAluno.getText());
    		a.setSexo(rdMasc.isSelected()?"Masc":"Femi");	//if internario
    		a.setIdade(Integer.parseInt(txtidade.getText()));
    		a.setCidade(comboCidade.getSelectionModel().getSelectedItem());
    		a.insere(conn);
	    	System.out.println("ALUNO CADSATRADO COM SUCESSO");
	  	  	atualizaTblAluno();
    	}catch(NullPointerException e){
    		Mensagens.msgErro("Cadastro", "ERRO: Preencha todos os campos!");
		}catch (Exception e){
			e.printStackTrace();
		}
    }
    
    @FXML
    public void alteraAluno(){
    	try{
	    	Aluno sel = tablealuno.getSelectionModel().getSelectedItem();
	    	sel.setNome(txtNomeAluno.getText());
	    	sel.setSexo(rdMasc.isSelected()?"Masc":"Femi");
	    	sel.setIdade(Integer.parseInt(txtidade.getText()));
	    	sel.setCidade(comboCidade.getSelectionModel().getSelectedItem());
	    	sel.altera(conn);
	    	atualizaTblAluno();
    	}catch(Exception e){
    		Mensagens.msgErro("Alterar", "ERRO: Selecione um aluno na tabela!");
    	}
    }
    
    @FXML
    public void excluiAluno(){
    	try{
    		if(Mensagens.msgEscolha("Atenção, Excluir Aluno","Você deseja excluir esse aluno?")){
    			Aluno sel = tablealuno.getSelectionModel().getSelectedItem();
    			sel.exclui(conn);
    			atualizaTblAluno();
    		}
    	}catch(Exception e){
    		Mensagens.msgErro("Excluir", "ERRO: Selecione um aluno na tabela!");
    	}
    }
    
    @FXML
    public void filtrarAluno(){
    	String filtro = txtFiltroAluno.getText();
    	if(filtro.equals(""))
    		filtro=null;
    	alunos = Aluno.listarTodas(conn, filtro);
    	tablealuno.setItems(FXCollections.observableArrayList(alunos));
    }
    
    @FXML
    public void atualizaCombo(){
     	comboCidade.setItems(FXCollections.observableArrayList( Cidade.listarTodas(conn, null)));
    }
    
    @FXML
    public void initialize(){	
      	atualizaCombo();
      	colNomeAluno.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());  
  	  	colSexo.setCellValueFactory(cellData -> cellData.getValue().sexoProperty()); 
  	  	colIdade.setCellValueFactory(cellData -> cellData.getValue().idadeProperty());  
  	  	colCidade.setCellValueFactory(cellData -> cellData.getValue().getCidade().nomeProperty());  //.getCidade() pegar o nome da cidade 
  		colUfAluno.setCellValueFactory(cellData -> cellData.getValue().getCidade().ufProperty());  //.getCidade() pegar o uf 
  		atualizaTblAluno();
    }
}
