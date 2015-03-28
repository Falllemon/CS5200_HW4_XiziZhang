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

public class MovieManager {
	
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
	
	public void createMovie(Movie newMovie){
		String create = "INSERT INTO Movie (id, title, posterImage, releaseDate)"
				+ " VALUES (?,?,?,?)";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(create);
			statement.setInt(1, newMovie.getId());
			statement.setString(2, newMovie.getTitle());
			statement.setString(3, newMovie.getPosterImage());
			statement.setDate(4, newMovie.getReleaseDate());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<Movie> readAllMovies(){
		List<Movie> allMovies = new ArrayList<Movie>();
		String readAll = "SELECT * FROM Movie";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(readAll);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int id = results.getInt("id");
				String title = results.getString("title");
				String posterImage = results.getString("posterImage");
				Date releaseDate = results.getDate("releaseDate");

				Movie movie = new Movie(id, title, posterImage, releaseDate);
				allMovies.add(movie);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return allMovies;
	}
	
	public Movie readMovie(int movieId){
		String read = "SELECT * FROM Movie WHERE id = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(read);
			statement.setInt(1, movieId);
			ResultSet results = statement.executeQuery();
			if(results.next()) {
				int id = results.getInt("id");
				String title = results.getString("title");
				String posterImage = results.getString("posterImage");
				Date releaseDate = results.getDate("releaseDate");
				Movie movie = new Movie(id, title, posterImage, releaseDate);
				return movie;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;
	}
	
	public void updateMovie(int movieId, Movie movie){
		String update = "UPDATE Movie SET id=?,title=?,posterImage=?,releaseDate=? WHERE id = ?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(update);
			int id = movie.getId();
			String title = movie.getTitle();
			String posterImage = movie.getPosterImage();
			Date releaseDate = movie.getReleaseDate();
			statement.setInt(1, id);
			statement.setString(2, title);
			statement.setString(3, posterImage);
			statement.setDate(4, releaseDate);
			statement.setInt(5, movieId);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public void deleteMovie(int movieId){
		String delete = "DELETE FROM Movie WHERE id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setInt(1, movieId);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
}
