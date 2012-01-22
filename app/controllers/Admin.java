package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			// User user = User.all().filter("email",
			// Security.connected()).get();
			User user = new User("test", "test", "testTest");
			renderArgs.put("user", user.fullname);
		}
	}

	public static void index() {
		render();
	}
}