package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Curso { 

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty ativo = new SimpleStringProperty("");
	
	
	public void insere(Connection conn){
		try {
			String sql = "insert into curso (nome, ativo) values (?,?)";  //deixe espaço em branco entre a aspa e o parenteses
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, "S");
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		public void exclui(Connection conn){
			try {
//				STRING SQL = "DELETE FROM CIDADE WHERE CODIGO=?";  			exclui o dado do banco
				String sql = "update curso set ativo='N' where id=?";	//Vamos apenas desativa-lo do banco
				PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, getId());
					ps.executeUpdate(); 
			} catch (Exception e) {
				e.printStackTrace();
			} 	
		}

		public void altera(Connection conn){
			try{
				String sql = "update curso set nome=? where id=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, getNome());
				ps.setInt(2, getId());
				ps.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();				
			}
		}
		
		public static ArrayList<Curso> listarTodas(Connection conn, String iniciais){
			ArrayList<Curso> lista = new ArrayList<Curso>();
			try {
				String sql = "select * from curso where ativo='S' order by nome";
				if(iniciais!=null)
					sql = "select * from curso where ativo='S' and nome like '%"+iniciais+"%' order by nome";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					Curso c = new Curso();
					c.setId(rs.getInt("id"));
					c.setNome(rs.getString("nome"));
					c.setAtivo(rs.getString("ativo"));
					lista.add(c);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return lista;
		}
		
	public final StringProperty nomeProperty() {
		return this.nome;
	}
	
	public final String getNome() {
		return this.nomeProperty().get();
	}
	
	public final void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}
	
	public final StringProperty ativoProperty() {
		return this.ativo;
	}
	
	public final String getAtivo() {
		return this.ativoProperty().get();
	}
	
	public final void setAtivo(final String ativo) {
		this.ativoProperty().set(ativo);
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}
	

	public final int getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	@Override
	public String toString() {
		return getNome();
	}

	
}
