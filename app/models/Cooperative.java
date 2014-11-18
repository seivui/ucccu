package models;

import play.db.ebean.Model;
import play.data.validation.Constraints;
import javax.persistence.*;

/**
 * Created by SCC-140 on 2014-11-18.
 */

@Entity
public class Cooperative extends Model {

    @Id
    @GeneratedValue
    public Long cooperativeId;

    @Constraints.Required
    public String cooperativeName;

    public String contactPerson;

    public String phoneNumber;

    public String address;

    @Constraints.Email
    public String email;

    @ManyToOne
    @JoinColumn(name="union_id")
    public MUnion union;

    public static Finder<Long, Cooperative> find = new Finder<Long, Cooperative>(Long.class, Cooperative.class);
}
