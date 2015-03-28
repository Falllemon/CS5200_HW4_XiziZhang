package HW4;

import java.sql.Date;

public class Comment {
	private int Id;
	private String comment;
	private Date date;
	private String username;
	private int movieId;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Comment() {
		super();
	}
	
	public Comment(int id, String comment, Date date, String username,
			int movieId) {
		super();
		Id = id;
		this.comment = comment;
		this.date = date;
		this.username = username;
		this.movieId = movieId;
	}

}
