package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Aluno { 

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty sexo = new SimpleStringProperty("");
	private IntegerProperty idade = new SimpleIntegerProperty(0);
	private StringProperty ativo = new SimpleStringProperty("");
	private Cidade cidade = new Cidade();
	
	public void insere(Connection conn){
		try {
			String sql = "insert into aluno (nome, sexo, idade, ativo, cidade) values (?,?,?,?,?)";  //deixe espaço em branco entre a aspa e o parenteses
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getSexo());
			ps.setInt(3, getIdade());
			ps.setString(4, "S");
			ps.setInt(5, getCidade().getId());
			ps.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exclui(Connection conn){
		try {
			String sql = "update aluno set ativo='N' where id=?";	//Vamos apenas desativa-lo do banco
			PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, getId());
				ps.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void altera(Connection conn){
		try{
			String sql = "update aluno set nome=?, sexo=?, idade=?, cidade=? where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getSexo());
			ps.setInt(3, getIdade());
			ps.setInt(4, getCidade().getId());
			ps.setInt(5, getId());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();				
		}
	} 
	
	public static ArrayList<Aluno> listarTodas(Connection conn, String iniciais){
		ArrayList<Aluno> lista = new ArrayList<Aluno>();
		try {
			//ALIAS = as  Comando do SQL para da apelido para as colunas
			String sql = "select aluno.id as cdAluno, "
					+ "aluno.nome as nmAluno, idade, sexo, "
					+ "cidade.id as cdCidade, "
					+ "cidade.nome as nmCidade, uf, "
					+ "cidade.ativo as ativoCidade, "
					+ "aluno.ativo as ativoAluno "
					+ "from aluno, cidade "
					+ "where cidade.id = aluno.cidade "
					+ "and aluno.ativo='S' "
					+ "order by aluno.nome";
			if(iniciais!=null){
				sql = "select aluno.id as cdAluno, "
					+ "aluno.nome as nmAluno, idade, sexo, "
					+ "cidade.id as cdCidade, "
					+ "cidade.nome as nmCidade, uf, "
					+ "cidade.ativo as ativoCidade, "
					+ "aluno.ativo as ativoAluno "
					+ "from aluno, cidade "
					+ "where cidade.id = aluno.cidade "
					+ "and aluno.ativo='S' "
					+ "and aluno.nome like '%"+iniciais+"%' "		//%palavra = busca primeira letra  palavra% = ultima letra
					+ "order by aluno.nome";
				}
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Cidade c = new Cidade();
				c.setId(rs.getInt("cdCidade"));
				c.setNome(rs.getString("nmCidade"));
				c.setUf(rs.getString("uf"));
				c.setAtivo(rs.getString("ativoCidade"));
				
				Aluno a = new Aluno();
				a.setId(rs.getInt("cdAluno"));
				a.setNome(rs.getString("nmAluno"));
				a.setSexo(rs.getString("sexo"));
				a.setIdade(rs.getInt("idade"));
				a.setCidade(c);		
				a.setAtivo(rs.getString("ativoAluno"));
				lista.add(a);		
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public IntegerProperty idProperty() {
		return this.id;
	}
	
	public int getId() {
		return this.idProperty().get();
	}
	
	public void setId(int id) {
		this.idProperty().set(id);
	}
	
	public StringProperty nomeProperty() {
		return this.nome;
	}
	
	public String getNome() {
		return this.nomeProperty().get();
	}
	
	public void setNome(String nome) {
		this.nomeProperty().set(nome);
	}
	
	public final IntegerProperty idadeProperty() {
		return this.idade;
	}
	
	public int getIdade() {
		return this.idadeProperty().get();
	}
	
	public void setIdade( int idade) {
		this.idadeProperty().set(idade);
	}
	
	public StringProperty ativoProperty() {
		return this.ativo;
	}
	
	public String getAtivo() {
		return this.ativoProperty().get();
	}
	
	public void setAtivo(String ativo) {
		this.ativoProperty().set(ativo);
	}

	public StringProperty sexoProperty() {
		return this.sexo;
	}

	public String getSexo() {
		return this.sexoProperty().get();
	}

	public void setSexo(String sexo) {
		this.sexoProperty().set(sexo);
	}
	
	@Override
	public String toString() {
		return getNome();
	}
}
