package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cidade {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty uf = new SimpleStringProperty("");
	private StringProperty ativo = new SimpleStringProperty("");
	
	public void insere(Connection conn){
		try {
			String sql = "insert into cidade (nome, uf, ativo) values (?,?,?)";  //deixe espaço em branco entre a aspa e o parenteses
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getUf());
			ps.setString(3, "S");
			//Resolve os ???  com as variaveis na ordem que ele recebe
			ps.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exclui(Connection conn){
		try {
//			STRING SQL = "DELETE FROM CIDADE WHERE ID=?";  			exclui o dado do banco
			String sql = "update cidade set ativo='N' where id=?";	//Vamos apenas desativa-lo do banco
			PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, getId());
				ps.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void altera(Connection conn){
		try{
			String sql = "update cidade set nome=?, uf=? where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getUf());
			ps.setInt(3, getId());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();				
		}
	} 
	
	public static ArrayList<Cidade> listarTodas(Connection conn, String iniciais){
		ArrayList<Cidade> lista = new ArrayList<Cidade>();
		try {
			String sql = "select * from cidade where ativo='S' order by nome";
			if(iniciais!=null){
				sql = "select * from cidade where ativo='S' and nome like '%"+iniciais+"%' order by nome";
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Cidade c = new Cidade();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setUf(rs.getString("uf"));
				c.setAtivo(rs.getString("ativo"));
				lista.add(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	@Override
	public String toString(){
		return getNome()+" ("+getUf()+")";			//Criar o formato de escrita para aparecer no comboBox de Cidade
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
	
	public StringProperty ufProperty() {
		return this.uf;
	}
	
	public String getUf() {
		return this.ufProperty().get();
	}
	
	public void setUf(String uf) {
		this.ufProperty().set(uf);
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
}
