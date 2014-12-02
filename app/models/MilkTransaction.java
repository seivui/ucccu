package models;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by SCC-140 on 2014-11-18.
 */
@Entity
public class MilkTransaction extends Model {

    @Id
    @GeneratedValue
    public Long transactionId;

    public double amount;

    public double fatContent;

    public double waterContent;

    @Constraints.Required
    public DateTime date;

    @ManyToOne
    @JoinColumn(name="farmer_id")
    @Constraints.Required
    public Farmer farmer;

    @ManyToOne
    @JoinColumn(name="cooperative_id")
    @Constraints.Required
    public Cooperative cooperative;

    public static Finder<Long, MilkTransaction> find = new Finder<Long, MilkTransaction>(Long.class, MilkTransaction.class);

    public static List<MilkTransaction> transactionFarmer(Long farmerId) {
        return find.where()
                .eq("farmer_id", farmerId)
                .findList();
    }

    public static List<MilkTransaction> transactionCooperative(Long cooperativeId) {
        return find.where()
                .eq("cooperative_id", cooperativeId)
                .findList();
    }

    public static List<MilkTransaction> periodTransactionFarmer(DateTime start, DateTime end, Long farmerId) {
        List<MilkTransaction> transactionList = find.where()
                                    .eq("farmer_id", farmerId)
                                    .between("date", start, end)
                                    .findList();
        return transactionList;
    }

    public static List<MilkTransaction> periodTransactionCooperative(DateTime start, DateTime end,
                                                                     Long cooperativeId) {
        List<MilkTransaction> transactionList = find.where()
                                    .eq("cooperative_id", cooperativeId)
                                    .between("date", start, end)
                                    .findList();
        return transactionList;
    }

    public static List<MilkTransaction> periodAlltransactions(DateTime start, DateTime end) {
        List<MilkTransaction> milkTransactionList = find.where()
                                        .between("date", start, end)
                                        .findList();

        return milkTransactionList;
    }

    public static List<MilkTransaction> todayAllDelivery() {
        List<MilkTransaction> milkTransactionList;
        DateTime dateTime = DateTime.now();

        milkTransactionList = find.where()
                                .eq("date", dateTime).findList();
        return milkTransactionList;
    }
}
