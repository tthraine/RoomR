package models;

import java.util.Date;

import play.data.validation.Required;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import siena.Table;

@Table("comments")
public class Comment extends Model {
	@Id(Generator.AUTO_INCREMENT)
	public Long id;

	@Required
	public String author;

	@Required
	public Date postedAt;

	@Required
	@Max(10000)
	public String content;

	@Required
	public Post post;

	public Comment(Post post, String author, String content) {
		this.post = post;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
	}

	public static Query<Comment> all() {
		return Model.all(Comment.class);
	}

	@Override
	public String toString() {
		return content;
	}
}