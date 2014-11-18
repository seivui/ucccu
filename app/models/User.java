package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by SCC-140 on 2014-11-18.
 */

@Entity
public class User extends Model {

    @Id
    public String email;

    public String role;

    public String password;

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
                .eq("password", password).findUnique();
    }

}
