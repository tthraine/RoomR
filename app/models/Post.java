package models;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import play.data.validation.Required;
import siena.Filter;
import siena.Generator;
import siena.Id;
import siena.Max;
import siena.Model;
import siena.Query;
import siena.Table;
import siena.embed.Embedded;

@Table("posts")
public class Post extends Model {
	@Id(Generator.AUTO_INCREMENT)
	public Long id;

	@Required
	public String title;

	@Required
	public Date postedAt;

	@Required
	@Max(10000)
	public String content;

	@Required
	public User author;

	@Filter("post")
	public Query<Comment> comments;

	@Embedded
	public Set<Tag> tags;

	public Post(User author, String title, String content) {
		this.tags = new TreeSet<Tag>();
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}

	public static Query<Post> all() {
		return Model.all(Post.class);
	}

	public Post addComment(String author, String content) {
		Comment newComment = new Comment(this, author, content);
		newComment.insert();
		return this;
	}

	public Post previous() {
		return all().filter("postedAt <", postedAt).order("-postedAt").get();
	}

	public Post next() {
		return all().filter("postedAt >", postedAt).order("postedAt").get();
	}

	public Post tagItWith(String name) {
		tags.add(Tag.findOrCreateByName(name));
		return this;
	}

	public static List<Post> findTaggedWith(String tag) {
		return Collections.emptyList();
		// return find(
		// "select distinct p from Post p join p.tags as t where t.name = ?",
		// tag).fetch();
	}

	public static List<Post> findTaggedWith(String... tags) {
		return Collections.emptyList();
		// return Post
		// .find("select distinct p from Post p join p.tags as t where t.name in (:tags) group by p.id, p.author, p.title, p.content, p.postedAt having count(t.id) = :size")
		// .bind("tags", tags).bind("size", tags.length).fetch();
	}

	@Override
	public String toString() {
		return title;
	}
}