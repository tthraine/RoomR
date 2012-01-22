package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Comment extends Model {
	@Required
	public String author;

	@Required
	public Date postedAt;

	@Lob
	@Required
	@MaxSize(10000)
	public String content;

	@ManyToOne
	@Required
	public Post post;

	public Comment(Post post, String author, String content) {
		this.post = post;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
	}

	@Override
	public String toString() {
		return content;
	}
}