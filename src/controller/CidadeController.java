package controller;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Cidade;
import useful.Conexao;
import useful.Mensagens;


public class CidadeController {
	
    @FXML private TableView<Cidade> tablecidade;
    @FXML private TableColumn<Cidade, String> colNome;
    @FXML private TableColumn<Cidade, String> colUf;
    @FXML private TextField txtnome;
    @FXML private TextField txtFiltro;
    @FXML private ComboBox<String> comboUf;
    
    ArrayList<String> estados = new ArrayList<>();
    ArrayList<Cidade> cidades = new ArrayList<Cidade>();
    private Connection conn = Conexao.conn();
    
    @FXML
    private void atualizaTblCidade(){
  	  	cidades = Cidade.listarTodas(conn, null);
       	tablecidade.setItems(FXCollections.observableArrayList(cidades));
    }
    
    @FXML
    public void clicaTbl(){ 	//Seleciona na tabela e preenche os campos
    	Cidade selecionado = tablecidade.getSelectionModel().getSelectedItem();
    	txtnome.setText(selecionado.getNome());
    	comboUf.getSelectionModel().select(selecionado.getUf());
    }
    
    @FXML
    public void insere(){
    	try{
    		if(comboUf.getValue() == null || txtnome.getText().isEmpty())
    			throw new NullPointerException();
    		Cidade c = new Cidade();
    		c.setNome(txtnome.getText());
    		c.setUf(comboUf.getSelectionModel().getSelectedItem());
    		c.insere(conn);
    	System.out.println("CIDADE CADSATRADA COM SUCESSO");
	  	atualizaTblCidade();
     	}catch (NullPointerException e){
     		Mensagens.msgErro("Cadastro", "ERRO: Preencha todos os campos!");
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    @FXML
    public void exclui(){
    	try{
    		if(Mensagens.msgEscolha("Atenção, Excluir Cidade","Você deseja excluir essa Cidade?")){
    			Cidade sel = tablecidade.getSelectionModel().getSelectedItem();
	    		sel.exclui(conn);
	    		atualizaTblCidade();
    		}
    	}catch(Exception e){
    		Mensagens.msgErro("Excluir", "ERRO: Selecione uma cidade na tabela!");
    	}
    }
    
    @FXML
    public void altera(){
    	try{
	    	Cidade sel = tablecidade.getSelectionModel().getSelectedItem();
	    	sel.setNome(txtnome.getText());
	    	sel.setUf(comboUf.getSelectionModel().getSelectedItem());
	    	sel.altera(conn);
	    	atualizaTblCidade();
    	}catch(Exception e){
    		Mensagens.msgErro("Alterar", "ERRO: Selecione uma cidade na tabela!");
    	}
    }
    
    @FXML
    public void filtrar(){
    	String filtro = txtFiltro.getText();
    	if(filtro.equals(""))
    		filtro=null;
    	cidades = Cidade.listarTodas(conn, filtro);
    	tablecidade.setItems(FXCollections.observableArrayList(cidades));
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
    public void initialize(){
    	populaEstados();
    	comboUf.setItems(FXCollections.observableArrayList(estados));
    	
 	  	colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());  
  	  	colUf.setCellValueFactory(cellData -> cellData.getValue().ufProperty());  	  	
  	  	atualizaTblCidade();
    }
}
