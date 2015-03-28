package HW4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CastManager {
	public Connection getConnection() {
		Connection connection = null;
		
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/XiziZhang");
			connection = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createCast(Cast newCast){
		String create = "INSERT INTO Cast (id, movieId, actorId, characterName)"
				+ " VALUES (?,?,?,?)";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(create);
			statement.setInt(1, newCast.getId());
			statement.setInt(2, newCast.getMovieId());
			statement.setInt(3, newCast.getActorId());
			statement.setString(4, newCast.getCharacterName());
			
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<Cast> readAllCast(){
		List<Cast> allCasts = new ArrayList<Cast>();
		String readAll = "SELECT * FROM Cast";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				int movieId = results.getInt("movieId");
				int actorId = results.getInt("actorId");
				String characterName = results.getString("characterName");

				Cast cast = new Cast(id, movieId, actorId, characterName);
				allCasts.add(cast);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allCasts;
	}
	
	public List<Cast> readAllCastForActor(int actorId){
		List<Cast> allCasts = new ArrayList<Cast>();
		String readAll = "SELECT * FROM Cast WHERE actorId=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			statement.setInt(1, actorId);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				int movieId = results.getInt("movieId");
				actorId = results.getInt("actorId");
				String characterName = results.getString("characterName");
				
				Cast cast = new Cast(id, movieId, actorId, characterName);
				allCasts.add(cast);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allCasts;
	}
	
	public List<Cast> readAllCastForMovie(int movieId){
		List<Cast> allCasts = new ArrayList<Cast>();
		String readAll = "SELECT * FROM Cast WHERE movieId=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			statement.setInt(1, movieId);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				movieId = results.getInt("movieId");
				int actorId = results.getInt("actorId");
				String characterName = results.getString("characterName");
				
				Cast cast = new Cast(id, movieId, actorId, characterName);
				allCasts.add(cast);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allCasts;
	}
	
	public Cast readCastForId(int castId){
		String readAll = "SELECT * FROM Cast WHERE id=?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			statement.setInt(1, castId);
			ResultSet results = statement.executeQuery();
			if(results.next()) {
				int id = results.getInt("id");
				int movieId = results.getInt("movieId");
				int actorId = results.getInt("actorId");
				String characterName = results.getString("characterName");

				Cast cast = new Cast(id, movieId, actorId, characterName);
				return cast;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return null;
	}
	
	public void updateCast(int castId, Cast newCast){
		String update = "UPDATE Cast SET id=?,movieId=?,actorId=?,characterName=? WHERE id = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(update);
			
			statement.setInt(1, newCast.getId());
			statement.setInt(2, newCast.getMovieId());
			statement.setInt(3, newCast.getActorId());
			statement.setString(4, newCast.getCharacterName());
			statement.setInt(5, castId);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public void deleteCast(int castId){
		String delete = "DELETE FROM Cast WHERE id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setInt(1, castId);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
