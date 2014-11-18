package models;

import play.db.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by SCC-140 on 2014-11-18.
 */
@Entity
public class Farmer extends Model {

    @Id
    @GeneratedValue
    public Long farmerId;

    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    @Constraints.Required
    public int accountNumber;

    public String sex;

    public String phoneNumber;

    @ManyToOne
    @JoinColumn(name="cooperative_id")
    public Cooperative cooperative;

    public static Finder<Long, Farmer> find = new Finder<Long, Farmer>(Long.class, Farmer.class);
}
