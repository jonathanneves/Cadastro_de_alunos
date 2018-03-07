package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.Aluno;
import model.Cidade;
import model.Curso;
import useful.Conexao;
import useful.Mensagens;

public class MainController {

	@FXML public TabPane tabPane;
	@FXML private MenuItem fechar;
	
	ArrayList<Aluno> alunos = new ArrayList<>();
	ArrayList<Cidade> cidades = new ArrayList<>();
	ArrayList<Curso> cursos = new ArrayList<>();
    private Connection conn = Conexao.conn();
    
	@FXML
	private void fecharTela(){		//Primeiro defina um ID pro botao import ele para o controller e repita codigo
		System.exit(0);
	}			

	@FXML
	private void suporte(){
		Mensagens.msgInformacao("Suporte", "Desenvolvido por: Jonathan Neves. \nEm caso de erro contate meu e-mail: nevesjon98@gmail.com");
	}
	
	@FXML
	private void ajuda(){
		Mensagens.msgInformacao("Ajuda", "Incluir: Preencha todos os campos corretamente e clique em Incluir.  \nAlterar: Selecione um item da tabela, altere os campos e clique em alterar. \nExcluir: Selecione o item da tabela e clique em excluir.");
	}
	
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
	public void abreAluno(){
		abreTab("Cadastro de Aluno", "TelaAluno.fxml");
	}
	
	@FXML
	public void abreCurso(){
		abreTab("Cadastro de Curso", "TelaCurso.fxml");
	}
	
	@FXML
	public void abreCidade(){
		abreTab("Cadastro de Cidade", "TelaCidade.fxml");
	}
	
	@FXML
	public void abreMatricula(){
		abreTab("Matrícula", "TelaMatricula.fxml");
	}
	
	@FXML
	public void abreConsulta(){
		abreTab("Consulta", "TelaConsulta.fxml");
	}
	
	@FXML
	public void exportarAlunos(){
		alunos = Aluno.listarTodas(conn, null);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("aluno.txt", false));
			for(Aluno a : alunos){
				String linha = a.getId()+","+a.getNome()+","+a.getSexo()+","+a.getIdade()+","+a.getAtivo()+","+a.getCidade()+"\n";
				bw.append(linha);
			}
			bw.close();
			Mensagens.msgConfirmacao("Gravação", "Arquivos aluno.txt gravado com sucesso.");
		} catch(Exception e){
     		Mensagens.msgErro("Gravação", "ERRO: Gravação apresenta erros: "+e.getMessage());
		}
	}
	
	@FXML
	public void exportarCursos(){
		cursos = Curso.listarTodas(conn, null);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("curso.txt", false));
			for(Curso c : cursos){
				String linha = c.getId()+","+c.getNome()+","+c.getAtivo()+"\n";
				bw.append(linha);
			}
			bw.close();
			Mensagens.msgConfirmacao("Gravação", "Arquivos curso.txt gravado com sucesso.");
		} catch(Exception e){
     		Mensagens.msgErro("Gravação", "ERRO: Gravação apresenta erros: "+e.getMessage());
		}
	}
	
	@FXML
	public void exportarCidades(){
		cidades = Cidade.listarTodas(conn, null);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("cidade.txt", false));
			for(Cidade c : cidades){
				String linha = c.getId()+","+c.getNome()+","+c.getUf()+","+c.getAtivo()+"\n";
				bw.append(linha);
			}
			bw.close();
			Mensagens.msgConfirmacao("Gravação", "Arquivos cidade.txt gravado com sucesso.");
		} catch(Exception e){
     		Mensagens.msgErro("Gravação", "ERRO: Gravação apresenta erros: "+e.getMessage());
		}
	}
	
	@FXML
	public void exportarTodos(){
		alunos = Aluno.listarTodas(conn, null);
		cidades = Cidade.listarTodas(conn, null);
		cursos =  Curso.listarTodas(conn, null);
		try{
			BufferedWriter bwA = new BufferedWriter(new FileWriter("aluno.txt", false));
			BufferedWriter bwB = new BufferedWriter(new FileWriter("curso.txt", false));
			BufferedWriter bwC = new BufferedWriter(new FileWriter("cidade.txt", false));
			for(Aluno a : alunos){
				String linha = a.getId()+","+a.getNome()+","+a.getSexo()+","+a.getIdade()+","+a.getAtivo()+","+a.getCidade()+"\n";
				bwA.append(linha);
			}
			for(Curso b : cursos){
				String linha = b.getId()+","+b.getNome()+","+b.getAtivo()+"\n";
				bwB.append(linha);
			}
			for(Cidade c : cidades){
				String linha = c.getId()+","+c.getNome()+","+c.getUf()+","+c.getAtivo()+"\n";
				bwC.append(linha);
			}
			bwA.close();
			bwB.close();
			bwC.close();
			Mensagens.msgConfirmacao("Gravação", "Arquivos '.txt' gravados com sucesso.\nAluno.txt - Completo\nCidade.txt - Completo\nCurso.txt - Completo");
		} catch(Exception e){	
     		Mensagens.msgErro("Gravação", "ERRO: Gravação apresenta erros: "+e.getMessage());
			e.printStackTrace();
		}
	}

}