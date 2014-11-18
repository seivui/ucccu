package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by SCC-140 on 2014-11-18.
 */

@Entity
public class MUnion extends Model {

    @Id
    @GeneratedValue
    public Long unionId;

    @Constraints.Required
    public String unionName;

    public String contactPerson;

    public String district;

    public String address;

    public String phoneNumber;

    @Constraints.Email
    public String email;

    public static Finder<Long, MUnion> find = new Finder<Long, MUnion>(Long.class, MUnion.class);
}
