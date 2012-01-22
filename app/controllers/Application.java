package controllers;

import java.util.List;

import models.Post;
import play.Play;
import play.cache.Cache;
import play.data.validation.Required;
import play.libs.Codec;
import play.libs.Images;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

	@Before
	static void addDefaults() {
		renderArgs.put("blogTitle",
				Play.configuration.getProperty("blog.title"));
		renderArgs.put("blogBaseline",
				Play.configuration.getProperty("blog.baseline"));
	}

	public static void index() {
		Post frontPost = Post.all().order("-postedAt").get();
		List<Post> olderPosts = Post.all().order("-postedAt").fetch(10, 1);
		render(frontPost, olderPosts);
	}

	public static void show(Long id) {
		Post post = Post.all().filter("id", id).get();
		String randomID = Codec.UUID();
		render(post, randomID);
	}

	public static void postComment(Long postId,
			@Required(message = "Author is required") String author,
			@Required(message = "A message is required") String content,
			@Required(message = "Please type the code") String code,
			String randomID) {
		Post post = Post.all().filter("id", postId).get();
		validation.equals(code, Cache.get(randomID)).message(
				"Invalid code. Please type it again");
		if (validation.hasErrors()) {
			render("Application/show.html", post, randomID);
		}
		post.addComment(author, content);
		flash.success("Thanks for posting %s", author);
		Cache.delete(randomID);
		show(postId);
	}

	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#E4EAFD");
		Cache.set(id, code, "10mn");
		renderBinary(captcha);
	}

	public static void listTagged(String tag) {
		List<Post> posts = Post.findTaggedWith(tag);
		render(tag, posts);
	}
}