package models;

import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by SCC-140 on 2014-11-19.
 */
@Entity
public class FailedMilkTransaction extends Model {

    @Id
    @GeneratedValue
    public Long transactionId;

    public String reason;

    public DateTime date;

    @ManyToOne
    @JoinColumn(name="farmerId")
    @Constraints.Required
    public Farmer farmer;

    @ManyToOne
    @JoinColumn(name="cooperative_id")
    @Constraints.Required
    public Cooperative cooperative;

    public static Finder<Long, FailedMilkTransaction> find = new Finder<Long, FailedMilkTransaction>(
            Long.class, FailedMilkTransaction.class
    );

    public static List<FailedMilkTransaction> failedTransactionFarmer(Long farmerId) {
        List<FailedMilkTransaction> failedMilkTransactionList = find.where()
                                            .eq("farmer_id", farmerId).findList();
        return failedMilkTransactionList;
    }

    public static List<FailedMilkTransaction> failedTransactionCooperative(Long cooperativeId) {
        List<FailedMilkTransaction> failedMilkTransactionList = find.where()
                                            .eq("cooperative_id", cooperativeId).findList();
        return failedMilkTransactionList;
    }

    public static List<FailedMilkTransaction> periodFailedTransactionFarmer(DateTime start, DateTime end, Long farmerId) {
        List<FailedMilkTransaction> failedMilkTransactionList = find.where()
                                                    .eq("farmer_id", farmerId)
                                                    .between("date", start, end)
                                                    .findList();
        return failedMilkTransactionList;
    }

    public static List<FailedMilkTransaction> periodFailedTransactionCooperative(DateTime start, DateTime end,
                                                                          Long cooperativeId) {
        List<FailedMilkTransaction> failedMilkTransactionList = find.where()
                                                    .eq("cooperative_id", cooperativeId)
                                                    .between("date", start, end)
                                                    .findList();
        return failedMilkTransactionList;
    }

    public static List<FailedMilkTransaction> periodAllFailedTransactions(DateTime start, DateTime end) {
        List<FailedMilkTransaction> failedMilkTransactionList = find.where()
                                                    .between("date", start, end)
                                                    .findList();
        return failedMilkTransactionList;
    }



}
