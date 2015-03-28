package HW4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ActorManager {

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
	
	public void createActor(Actor newActor){
		String create = "INSERT INTO Actor (id, firstName, lastName, dateOfBirth)"
				+ " VALUES (?,?,?,?)";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(create);
			statement.setInt(1, newActor.getId());
			statement.setString(2, newActor.getFirstName());
			statement.setString(3, newActor.getLastName());
			statement.setDate(4, newActor.getDateOfBirth());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<Actor> readAllActors(){
		List<Actor> allActors = new ArrayList<Actor>();
		String readAll = "SELECT * FROM Actor";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				Date dateOfBirth = results.getDate("dateOfBirth");

				Actor actor = new Actor(id, firstName, lastName, dateOfBirth);
				allActors.add(actor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allActors;
	}
	
	public Actor readActor(int actorId){
		String read = "SELECT * FROM Actor WHERE id = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(read);
			statement.setInt(1, actorId);
			ResultSet results = statement.executeQuery();
			if(results.next()) {
				int id = results.getInt("id");
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				Date dateOfBirth = results.getDate("dateOfBirth");
				Actor actor = new Actor(id, firstName, lastName, dateOfBirth);
				return actor;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;
	}
	
	public void updateActor(int actorId, Actor actor){
		String update = "UPDATE Actor SET id=?,firstName=?,lastName=?,dateOfBirth=? WHERE id = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(update);
			int id = actor.getId();
			String firstName = actor.getFirstName();
			String lastName = actor.getLastName();
			Date dateOfBirth = actor.getDateOfBirth();
			statement.setInt(1, id);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setDate(4, dateOfBirth);
			statement.setInt(5, actorId);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public void deleteActor(int actorId){
		String delete = "DELETE FROM Actor WHERE id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setInt(1, actorId);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
}
