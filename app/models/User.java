package models;

import play.data.validation.Email;
import play.data.validation.Required;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;

@Table("users")
public class User extends Model {
	@Id(Generator.AUTO_INCREMENT)
	public Long id;

	@Email
	@Required
	public String email;

	@Required
	public String password;
	public String fullname;
	public boolean isAdmin;

	public User(String email, String password, String fullname) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
	}

	public static Query<User> all() {
		return Model.all(User.class);
	}

	public static User connect(String email, String password) {
		return all().filter("email", email).filter("password", password).get();
	}

	@Override
	public String toString() {
		return email;
	}
}