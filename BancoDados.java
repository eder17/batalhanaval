import java.util.ArrayList;
import java.util.List;

//import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BancoDados {
	
	public void atacar(int x, int y, int senha) {
			
			// chamar funcao atacar da base de dados
			//System.out.println("Atacar em " + x + " " + y);
			
			Connection conn = null;
			PreparedStatement pstm = null;
	
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://10.9.0.95/batalha", "aluno", "123456");
				System.out.println("Conex�o bem sucedida...");
				String sql = "select ATACA(?, ?, ?)";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, senha);
				pstm.setInt(2, x);
				pstm.setInt(3, y);
				pstm.executeQuery();
				pstm.close();
				
	
			} catch (SQLException e) {
				System.err.append("erro conexao banco");
			}
	
			if (conn!=null) {
				try {
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	public void mover(int x, int y, int senha) {
		
		// chama a funcao mover da base da dados
		//System.out.println("Mover em " + x + " " + y);
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = DriverManager.getConnection("jdbc:postgresql://10.9.0.95/batalha", "aluno", "123456");
			System.out.println("Conex�o bem sucedida...");
			String sql = "select MOVE(?, ?, ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, senha);
			pstm.setInt(2, x);
			pstm.setInt(3, y);
			pstm.executeQuery();
			pstm.close();
			

		} catch (SQLException e) {
			System.err.append("erro conexao banco");
			// TODO: handle exception
		}

		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	

	// Atualizar a matriz com sua nova posi��o (e a dos advers�rios)
	public List<Posicao> atualizarMapa(int senha) {
		
		List<Posicao> lista = null;
		

		Connection connServidor = null;
		Connection connLocal = null;
		Connection connData = null;
		PreparedStatement pstm = null, pstm2 = null;
		String funcionou = "Conex�o bem sucedida!!!!!!! ";

		try {
			connServidor = DriverManager.getConnection("jdbc:postgresql://10.9.0.95/batalha", "aluno", "123456");
			System.out.println(funcionou);
			connData = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "123456");
			//System.out.println(funcionou);
			pstm = connData.prepareStatement("SELECT 1 FROM pg_database WHERE datname = 'batalha'");
			ResultSet rsv = pstm.executeQuery();
			if(!rsv.next()) {
				pstm.close();
				pstm = connData.prepareStatement("create database batalha");
				pstm.executeUpdate();
				pstm.close();
				connLocal = DriverManager.getConnection("jdbc:postgresql://localhost/batalha", "postgres", "123456");
				pstm = connLocal.prepareStatement("create table jogo(navio varchar(30), x integer, y integer); create table historico(navio varchar(30), x integer, y integer)");
				pstm.executeUpdate();
				System.out.println("Criando tabelas jogo e historico");
				
			}
			else connLocal = DriverManager.getConnection("jdbc:postgresql://localhost/batalha", "postgres", "123456");
			pstm.close();
			System.out.println(funcionou);
			
			//select  * from historico union select  * from jogo
			//select  * from historico intersect select  * from jogo
			

			pstm = connLocal.prepareStatement("select  * from jogo except select  * from historico");
			ResultSet rsh = pstm.executeQuery();
			while (rsh.next()) {
				String navio = rsh.getString("navio");
				int x = rsh.getInt("x");
				int y = rsh.getInt("y");
				System.out.println("Navio: " + navio + " X " + x + " Y " + y);
				String comando = "insert into historico (navio, x, y) values (?,?,?)";
				pstm2 = connLocal.prepareStatement(comando);
				pstm2.setString(1, navio);
				pstm2.setInt(2, x);
				pstm2.setInt(3, y);
				pstm2.executeUpdate();
				pstm2.close();
				
			}

			pstm = connLocal.prepareStatement("delete from jogo");
			pstm.executeUpdate();
			pstm.close();

			pstm = connServidor.prepareStatement("select * from mapa_batalha(?)");
			pstm.setInt(1, senha); //621165
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String navio = rs.getString("navio");
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				System.out.println("Navio: " + navio + " X " + x + " Y " + y);
				String comando = "insert into jogo (navio, x, y) values (?,?,?)";
				pstm2 = connLocal.prepareStatement(comando);
				pstm2.setString(1, navio);
				pstm2.setInt(2, x);
				pstm2.setInt(3, y);
				pstm2.executeUpdate();
				pstm2.close();
			}
			pstm.close();
			
			lista=new ArrayList <Posicao>(); 
			pstm = connLocal.prepareStatement("select * from jogo");
			rs = pstm.executeQuery();
			while (rs.next()) {
				String navio = rs.getString("navio");
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				System.out.println(navio + x + y);
				lista.add(new Posicao(navio,x,y));
			}
			pstm.close();

			//String query = "insert into jogo (navio , x, y) VALUES (?,?,?)";
			// String query = "delete from jogo where navio = ?";
			// String query = "update jogo set cod = ? where nome = 'teste 1' ";
			// pstm = connLocal.prepareStatement(query);
			// pstm.setString(1, "p�rola negra ");
			// pstm.setInt(2, 123);
			// pstm.setInt(3, 123);
			// pstm.executeUpdate();

			// pstm.close();
		} catch (Exception e) {
			System.err.append("erro conexao banco");
			// TODO: handle exception
		}
		if (connLocal != null) {
			try {
				connLocal.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		
		return lista;
	}
	
	public boolean verificarSenha(String senha) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		boolean result = false;

		try {
			conn = DriverManager.getConnection("jdbc:postgresql://10.9.0.95/batalha", "aluno", "123456");
			System.out.println("Conex�o bem sucedida...");
			pstm = conn.prepareStatement("select * from mapa_batalha(?)");
			pstm.setInt(1, Integer.parseInt(senha)); //
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) result = true;
			else result = false;
			pstm.close();
			
		} catch (SQLException e) {
			System.err.append("erro conexao banco");
		}

		if (conn!=null) {
			try {
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}
	
	public List<Posicao> historico(){
		
		Connection connLocalH = null;
		PreparedStatement hps = null;
		List<Posicao> lista = null;
		try {
			connLocalH = DriverManager.getConnection("jdbc:postgresql://localhost/batalha", "postgres", "123456");
			hps = connLocalH.prepareStatement("select  * from historico");
			ResultSet rsh = hps.executeQuery();
			lista=new ArrayList <Posicao>(); 
			while (rsh.next()) {
				String navio = rsh.getString("navio");
				int x = rsh.getInt("x");
				int y = rsh.getInt("y");
				System.out.println("Navio: " + navio + " X " + x + " Y " + y);
				lista.add(new Posicao(navio,x,y));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(funcionou);
				
		//select  * from historico union select  * from jogo
		//select  * from historico intersect select  * from jogo
		
		return lista;
	}
}


