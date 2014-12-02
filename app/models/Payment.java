package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by SCC-140 on 2014-11-21.
 */
@Entity
public class Payment extends Model {

    @Id
    @GeneratedValue
    public Long paymentId;

    public int payment;

    public DateTime date;

    public int totalLitres;

    public int price;

    @ManyToOne
    @JoinColumn(name="farmer_id")
    public Farmer farmer;

    public static Finder<Long, Payment> find = new Finder<Long, Payment>(Long.class, Payment.class);

    public static List<Payment> getAllPaymentsFarmer(Long farmerId) {
        List<Payment> paymentList = find.where()
                            .eq("farmer_id", farmerId).findList();
        return paymentList;
    }

    
}
