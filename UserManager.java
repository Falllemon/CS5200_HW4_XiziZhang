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

public class UserManager {
	
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
	
	public void createUser(User newUser){
		String create = "INSERT INTO User (username, password, firstName, lastName, email, dateOfBirth)"
				+ " VALUES (?,?,?,?,?,?)";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(create);
			statement.setString(1, newUser.getUsername());
			statement.setString(2, newUser.getPassword());
			statement.setString(3, newUser.getFirstName());
			statement.setString(4, newUser.getLastName());
			statement.setString(5, newUser.getEmail());
			statement.setDate(6, newUser.getDateOfBirth());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<User> readAllUsers(){
		List<User> allUsers = new ArrayList<User>();
		String readAll = "SELECT * FROM User";
		Connection connection = getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				String username = results.getString("username");
				String password = results.getString("password");
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				String email = results.getString("email");
				Date dateOfBirth = results.getDate("dateOfBirth");

				User user = new User(username, password, firstName, lastName, email, dateOfBirth);
				allUsers.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return allUsers;
	}
	
	public User readUser(String username){
		String read = "SELECT * FROM User WHERE username = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(read);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			if(results.next()) {
				String uName = results.getString("username");
				String password = results.getString("password");
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				String email = results.getString("email");
				Date dateOfBirth = results.getDate("dateOfBirth");
				User user = new User(uName, password, firstName, lastName, email, dateOfBirth);
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;
	}
	
	public void updateUser(String username, User user){
		String update = "UPDATE User SET username=?,password=?,firstName=?,lastName=?,email=?,dateOfBirth=? WHERE username = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(update);
			String uName = user.getUsername();
			String password = user.getPassword();
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String email = user.getEmail();
			Date dateOfBirth = user.getDateOfBirth();
			statement.setString(1, uName);
			statement.setString(2, password);
			statement.setString(3, firstName);
			statement.setString(4, lastName);
			statement.setString(5, email);
			statement.setDate(6, dateOfBirth);
			statement.setString(7, username);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}	
	}
	
	public void deleteUser(String username){
		String delete = "DELETE FROM User WHERE username = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, username);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

}
