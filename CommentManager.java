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

public class CommentManager {
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
	
	public void createComment(Comment newComment){
		String create = "INSERT INTO Comment (id, comment, date, username, movieId)"
				+ " VALUES (?,?,?,?,?)";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(create);
			statement.setInt(1, newComment.getId());
			statement.setString(2, newComment.getComment());
			statement.setDate(3, newComment.getDate());
			statement.setString(4, newComment.getUsername());
			statement.setInt(5, newComment.getMovieId());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<Comment> readAllComments(){
		List<Comment> allComments = new ArrayList<Comment>();
		String readAll = "SELECT * FROM Comment";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				String comment = results.getString("comment");
				Date date = results.getDate("date");
				String username = results.getString("username");
				int movieId = results.getInt("movieId");

				Comment c = new Comment(id, comment, date, username, movieId);
				allComments.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allComments;
	}
	
	public List<Comment> readAllCommentsForUsername(String username){
		List<Comment> allComments = new ArrayList<Comment>();
		String readAll = "SELECT * FROM Comment WHERE username=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				String comment = results.getString("comment");
				Date date = results.getDate("date");
				int movieId = results.getInt("movieId");

				Comment c = new Comment(id, comment, date, username, movieId);
				allComments.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allComments;
	}
	
	public List<Comment> readAllCommentsForMovie(int movieId){
		List<Comment> allComments = new ArrayList<Comment>();
		String readAll = "SELECT * FROM Comment WHERE movieId=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			statement.setInt(1, movieId);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				String comment = results.getString("comment");
				Date date = results.getDate("date");
				String username = results.getString("username");

				Comment c = new Comment(id, comment, date, username, movieId);
				allComments.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allComments;
	}
	
	public Comment readCommentForId(int commentId){
		
		String readAll = "SELECT * FROM Comment WHERE id=?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			statement.setInt(1, commentId);
			ResultSet results = statement.executeQuery();
			if(results.next()) {
				int id = results.getInt("id");
				String comment = results.getString("comment");
				Date date = results.getDate("date");
				String username = results.getString("username");
				int movieId = results.getInt("movieId");

				Comment c = new Comment(id, comment, date, username, movieId);
				return c;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return null;
	}
	
	public void updateComment(int commentId, String newComment){
		String update = "UPDATE Comment SET comment=?,date= now() WHERE id = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(update);
			
			statement.setString(1, newComment);
			statement.setInt(2, commentId);

			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public void deleteComment(int commentId){
		String delete = "DELETE FROM Comment WHERE id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setInt(1, commentId);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
