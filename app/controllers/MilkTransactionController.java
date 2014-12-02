package controllers;

import akka.dispatch.sysmsg.Failed;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;

import java.util.List;

/**
 * Created by SCC-140 on 2014-11-25.
 */

@Security.Authenticated(Secured.class)
public class MilkTransactionController extends Controller {

    @Transactional
    public Result milkTransactionIndex() {
        User user = User.find.byId(request().username());
        List<MilkTransaction> milkTransactionList = MilkTransaction.find.all();

        if (user.role.equals("cooperative") || user.role.equals("union")) {
            return ok(index.render(user, milkTransactionList));
        }
        else {
            return ok(restrictedPage.render());
        }
    }

    @Transactional
    public Result addMilkTransactionIndex() {
        User user = User.find.byId(request().username());
        List<Farmer> farmerList = Farmer.find.all();

        if (user.role.equals("cooperative") || user.role.equals("union")) {
            return ok(addMilkTransaction.render(farmerList, user));
        }
        else {
            return ok(restrictedPage.render());
        }
    }

    @Transactional
    public Result addMilkTransaction() {
        MilkTransaction milkTransaction = new MilkTransaction();
        Cooperative cooperative;
        Farmer farmer;
        DateTime dateTime;
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        DynamicForm requestData = Form.form().bindFromRequest();

        milkTransaction.amount = Double.parseDouble(requestData.get("inputAmount"));
        milkTransaction.fatContent = Double.parseDouble(requestData.get("inputFatContent"));
        milkTransaction.waterContent = Double.parseDouble(requestData.get("inputWaterContent"));

        // Parse date time
        dateTime = dateTimeFormatter.parseDateTime(requestData.get("inputDateTime"));
        milkTransaction.date = dateTime;

        // Add farmer to transaction
        Long farmerId = Long.valueOf(requestData.get("inputFarmer"));
        farmer = Farmer.find.byId(farmerId);
        milkTransaction.farmer = farmer;

        // Add cooperative to transaction
        cooperative = farmer.cooperative;
        milkTransaction.cooperative = cooperative;

        Ebean.save(milkTransaction);

        return redirect(routes.MilkTransactionController.milkTransactionIndex());
    }

    @Transactional
    public Result addFailedMilkTransactionIndex() {
        User user = User.find.byId(request().username());
        List<Farmer> farmerList = Farmer.find.all();

        if (user.role.equals("cooperative") || user.role.equals("union")) {
            return ok(addFailedMilkTransaction.render(farmerList, user));
        }
        else {
            return ok(restrictedPage.render());
        }
    }

    @Transactional
    public Result addFailedMilkTransaction() {
        FailedMilkTransaction failedMilkTransaction = new FailedMilkTransaction();
        Cooperative cooperative;
        Farmer farmer;
        DateTime dateTime;
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        DynamicForm requestData = Form.form().bindFromRequest();

        // Add frmer to failed transaction
        Long farmerId = Long.valueOf(requestData.get("inputFarmer"));
        farmer = Farmer.find.byId(farmerId);
        failedMilkTransaction.farmer = farmer;

        // Add cooperative to failed transaction
        cooperative = farmer.cooperative;
        failedMilkTransaction.cooperative = cooperative;

        failedMilkTransaction.reason = requestData.get("inputReason");

        // Parse date time
        dateTime = dateTimeFormatter.parseDateTime(requestData.get("inputDateTime"));
        failedMilkTransaction.date = dateTime;

        Ebean.save(failedMilkTransaction);

        return redirect(routes.MilkTransactionController.milkTransactionIndex());
    }

    @Transactional
    public Result deleteMilkTransaction(Long transactionId) {
        MilkTransaction milkTransaction;
        milkTransaction = MilkTransaction.find.byId(transactionId);

        Ebean.delete(milkTransaction);

        return redirect(routes.MilkTransactionController.milkTransactionIndex());
    }

    @Transactional
    public Result deleteFailedMilkTransaction(Long transactionId) {
        FailedMilkTransaction failedMilkTransaction;
        failedMilkTransaction = FailedMilkTransaction.find.byId(transactionId);

        Ebean.delete(failedMilkTransaction);

        return redirect(routes.MilkTransactionController.milkTransactionIndex());
    }

    public Result getMilkTransaction(Long transactionId) {
        MilkTransaction milkTransaction;
        milkTransaction = MilkTransaction.find.byId(transactionId);

        User user = User.find.byId(request().username());

        // Get farmer list
        List<Farmer> farmerList = Farmer.find.all();

        return ok(editMilkTransaction.render(milkTransaction, farmerList, user));

    }

    public Result getFailedMilkTransaction(Long transactionId) {
        FailedMilkTransaction failedMilkTransaction;
        failedMilkTransaction = FailedMilkTransaction.find.byId(transactionId);

        User user = User.find.byId(request().username());

        // Get farmer list
        List<Farmer> farmerList = Farmer.find.all();

        return ok(editFailedMilkTransaction.render(failedMilkTransaction, farmerList, user));
    }

    @Transactional
    public Result editMilkTransaction(Long transactionId) {
        MilkTransaction milkTransaction;
        milkTransaction = MilkTransaction.find.byId(transactionId);
        DynamicForm requestData = Form.form().bindFromRequest();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        milkTransaction.amount = Double.parseDouble(requestData.get("inputAmount"));
        milkTransaction.waterContent = Double.parseDouble(requestData.get("inputWaterContent"));
        milkTransaction.fatContent = Double.parseDouble(requestData.get("inputFatContent"));

        // Find farmer based on input
        Farmer farmer = Farmer.find.byId(Long.valueOf(requestData.get("inputFarmer")));
        milkTransaction.farmer = farmer;

        Cooperative cooperative = farmer.cooperative;
        milkTransaction.cooperative = cooperative;

        DateTime dateTime = dateTimeFormatter.parseDateTime(requestData.get("inputDateTime"));
        milkTransaction.date = dateTime;

        Ebean.save(milkTransaction);

        return redirect(routes.MilkTransactionController.milkTransactionIndex());
    }

    @Transactional
    public Result editFailedMilkTransaction(Long transactionId) {
        FailedMilkTransaction failedMilkTransaction;
        failedMilkTransaction = FailedMilkTransaction.find.byId(transactionId);
        DynamicForm requestData = Form.form().bindFromRequest();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        failedMilkTransaction.reason = requestData.get("inputReason");
        DateTime dateTime = dateTimeFormatter.parseDateTime(requestData.get("inputDateTime"));

        failedMilkTransaction.date = dateTime;

        Farmer farmer = Farmer.find.byId(Long.valueOf(requestData.get("inputFarmer")));
        failedMilkTransaction.farmer = farmer;

        Cooperative cooperative = farmer.cooperative;
        failedMilkTransaction.cooperative = cooperative;

        Ebean.save(failedMilkTransaction);

        return redirect(routes.MilkTransactionController.milkTransactionIndex());
    }

    public Result jsonMilkTransactionList() {
        List<MilkTransaction> milkTransactionList = MilkTransaction.find.all();

        return ok(Json.toJson(milkTransactionList));
    }

    public Result periodMilkTransactions() {
        List<MilkTransaction> milkTransactionList;
        DynamicForm requestData = Form.form().bindFromRequest();
        DateTime start;
        DateTime end;
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        User user = User.find.byId(request().username());

        start = dateTimeFormatter.parseDateTime(requestData.get("inputStartDateTime"));
        end = dateTimeFormatter.parseDateTime(requestData.get("inputEndDateTime"));

        milkTransactionList = MilkTransaction.periodAlltransactions(start, end);

        return ok(index.render(user, milkTransactionList));
    }

    /*
    public Result periodFailedTransactions() {
        List<FailedMilkTransaction> failedMilkTransactionList;
        DynamicForm requestData = Form.form().bindFromRequest();
        DateTime start;
        DateTime end;
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        User user = User.find.byId(request().username());

        start = dateTimeFormatter.parseDateTime(requestData.get("inputStartDateTime"));
        end = dateTimeFormatter.parseDateTime(requestData.get("inputEndDateTime"));

        failedMilkTransactionList = FailedMilkTransaction.periodAllFailedTransactions(start, end);

        return ok(failedMilkTransactionList);
    }
    */

    public Result getAllRejectedMilkToday() {
        User user = User.find.byId(request().username());
        List<FailedMilkTransaction> failedMilkTransactionList;
        failedMilkTransactionList = FailedMilkTransaction.find.all();

        return ok(failedDelivery.render(user, failedMilkTransactionList));

    }

}
