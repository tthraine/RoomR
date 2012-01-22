package models;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import play.data.validation.Required;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;

@Table("tags")
public class Tag extends Model implements Comparable<Tag> {
	@Id(Generator.AUTO_INCREMENT)
	public Long id;

	@Required
	public String name;

	private Tag(String name) {
		this.name = name;
	}

	public static Query<Tag> all() {
		return Model.all(Tag.class);
	}

	public String toString() {
		return name;
	}

	public int compareTo(Tag otherTag) {
		return name.compareTo(otherTag.name);
	}

	public static Tag findOrCreateByName(String name) {
		Tag tag = all().filter("name", name).get();

		if (tag == null) {
			tag = new Tag(name);
			tag.insert();
		}
		return tag;
	}

	public static List<Map> getCloud() {
		return Collections.emptyList();
		// List<Map> result = Tag
		// .find("select new map(t.name as tag, count(p.id) as pound) from Post p join p.tags as t group by t.name order by t.name")
		// .fetch();
		// return result;
	}
}