package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import useful.Mensagens;

public class Matricula {

	private IntegerProperty id = new SimpleIntegerProperty();
	private IntegerProperty idCurso = new SimpleIntegerProperty();
	private IntegerProperty idAluno = new SimpleIntegerProperty();
	private StringProperty dados = new SimpleStringProperty("");
	
	private Aluno aluno = new Aluno();
	private Curso curso = new Curso();
	
	static DecimalFormat df = new DecimalFormat("0.00");
	
	public static ArrayList<Matricula> filtraPorAluno(Connection conn, Aluno a){
		ArrayList<Matricula> lista = new ArrayList<Matricula>();
		try{
			String sql = "select curso.nome as curso from curso, matricula "
					+ "where matricula.id_curso = curso.id "
					+ "and curso.ativo = 'S' "
					+ "and matricula.id_aluno = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Matricula m = new Matricula();
				m.setDados(rs.getString("curso"));
				lista.add(m);
			}
		if(lista.isEmpty()){
	    	Mensagens.msgErro("Erro","Aluno não está matriculado em nenhum Curso");
			return lista;
		}
		} catch (Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	public static ArrayList<Matricula> filtraPorCurso(Connection conn, Curso c){
		ArrayList<Matricula> lista = new ArrayList<Matricula>();
		try{
			String sql = "select aluno.nome as aluno, "
					+ "aluno.idade as idade, "
					+ "aluno.sexo as sexo, "
					+ "cidade.nome as cidade, "
					+ "cidade.uf as uf "
					+ "from aluno, matricula, cidade "
					+ "where matricula.id_aluno = aluno.id "
					+ "and cidade.id = aluno.cidade "
					+ "and aluno.ativo = 'S' "
					+ "and matricula.id_curso = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Matricula m = new Matricula();
				String dados = rs.getString("aluno")+" ("+(rs.getString("sexo").equals("Masc")?"Masculino":"Feminino")+", "+rs.getString("idade")+" anos) - "+rs.getString("cidade") + " ("+rs.getString("uf")+")";
				m.setDados(dados);
				lista.add(m);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(lista.isEmpty()){
    		Mensagens.msgErro("Erro","Não existem alunos matriculados nesse Curso");
			return lista;
		}
		return lista;
	}

	public boolean insere(Connection conn){
		try {
			String sql = "insert into matricula (id_Aluno, id_Curso) values (?,?)";  //deixe espaço em branco entre a aspa e o parenteses
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, getAluno().getId());
			ps.setInt(2, getCurso().getId());
			ps.executeUpdate();		
			return true;
		} catch (Exception e) {
    		Mensagens.msgErro("Matrícula", "ERRO: "+e.getMessage());
			return false;
		}
	}
	
//INICIO CONSULTAS DO TRABALHO
	public static ArrayList<Matricula> ConsultaPorUF(Connection conn, String uf){
		ArrayList<Matricula> lista = new ArrayList<Matricula>();
		try{
			String sql = "select aluno.nome as aluno, "
					+ "curso.nome as curso, "
					+ "cidade.nome as cidade "
					+ "from aluno, matricula, curso, cidade "
					+ "where matricula.id_aluno = aluno.id "
					+ "and matricula.id_curso = curso.id "
					+ "and aluno.cidade = cidade.id "
					+ "and aluno.ativo = 'S' "
					+ "and curso.ativo = 'S' "
					+ "and cidade.uf = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uf);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Matricula m = new Matricula();
				String dados = rs.getString("aluno")+" - "+rs.getString("curso") +" ("+rs.getString("cidade")+")";
				m.setDados(dados);
				lista.add(m);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(lista.isEmpty()){
    		Mensagens.msgErro("Erro","Não existem alunos matriculados nesse UF");
			return lista;
		}
		return lista;
	}
	
	public static ArrayList<Matricula> ConsultaPorSexo(Connection conn, String sexo){
		ArrayList<Matricula> lista = new ArrayList<Matricula>();
		try{
			String sql = "select aluno.nome as aluno, "
					+ "curso.nome as curso "
					+ "from aluno, matricula, curso "
					+ "where matricula.id_aluno = aluno.id "
					+ "and matricula.id_curso = curso.id "
					+ "and aluno.ativo = 'S' "
					+ "and curso.ativo = 'S' "
					+ "and aluno.sexo = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, sexo);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Matricula m = new Matricula();
				String dados = rs.getString("curso");
				m.setDados(dados);
				if(!temMatricula(lista, m)){
					lista.add(m);			
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(lista.isEmpty()){
			Mensagens.msgErro("Erro", "Não existem alunos matriculados com sexo "+(sexo.equals("Masc")?"Masculino":"Feminino"));
		}
		return lista;
	}
	
	private static boolean temMatricula(ArrayList<Matricula> lista, Matricula m){
		for (Matricula mat : lista) {
			if(mat.getDados().equals(m.getDados())){
				return true;
			}		
		}
		return false;
	}
	
	public static ArrayList<Matricula> ConsultaPorCurso(Connection conn, Curso c){
		ArrayList<Matricula> lista = new ArrayList<Matricula>();
		double soma = 0;
		double media = 0;
		int contMasc = 0;
		int contFemi = 0;
		
		try{
			String sql = "select aluno.nome as aluno, "
					+ "aluno.idade as idade, "
					+ "aluno.sexo as sexo, "
					+ "cidade.nome as cidade, "
					+ "cidade.uf as uf "
					+ "from aluno, matricula, cidade "
					+ "where matricula.id_aluno = aluno.id "
					+ "and cidade.id = aluno.cidade "
					+ "and aluno.ativo = 'S' "
					+ "and matricula.id_curso = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Matricula m = new Matricula();
				String dados = rs.getString("aluno")+" ("+(rs.getString("sexo").equals("Masc")?"Masculino":"Feminino")+", "+rs.getString("idade")+" anos) - "+rs.getString("cidade") + " ("+rs.getString("uf")+")";
				m.setDados(dados);
				lista.add(m);
				
				soma = Integer.parseInt(rs.getString("idade"))+soma;
				
				if(rs.getString("sexo").equals("Masc"))
					contMasc++;
				else
					contFemi++;
			}
			media = soma/lista.size();
		} catch (Exception e){
			e.printStackTrace();
		}
		if(lista.isEmpty()){
			Mensagens.msgAviso("Vazio", "Não existe alunos matriculados nesse curso");
			return lista;
		}
		Mensagens.msgInformacao("Consulta", "Neste curso estão matriculados: "+contMasc+" homens e "+contFemi+" mulheres.\nA média de idade dos é: "+media);
		return lista;
	}
//FIM CONSULTAS DO TRABALHO	
	
	public IntegerProperty idProperty() {
		return this.id;
	}
	
	public int getId() {
		return this.idProperty().get();
	}
	
	public void setId(int id) {
		this.idProperty().set(id);
	}
	
	public IntegerProperty idCursoProperty() {
		return this.idCurso;
	}
	
	public int getIdCurso() {
		return this.idCursoProperty().get();
	}
	
	public void setIdCurso(int idCurso) {
		this.idCursoProperty().set(idCurso);
	}
	
	public IntegerProperty idAlunoProperty() {
		return this.idAluno;
	}
	
	public int getIdAluno() {
		return this.idAlunoProperty().get();
	}
	
	public void setIdAluno(int idAluno) {
		this.idAlunoProperty().set(idAluno);
	}
	
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public StringProperty dadosProperty() {
		return this.dados;
	}
	public  String getDados() {
		return this.dadosProperty().get();
	}
	public void setDados(String dados) {
		this.dadosProperty().set(dados);
	}
}
