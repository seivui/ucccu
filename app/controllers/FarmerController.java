package controllers;

import com.avaje.ebean.Ebean;
import models.Cooperative;
import models.Farmer;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.addFarmer;
import views.html.editFarmer;
import views.html.farmer;

import java.util.List;

import static play.data.Form.form;

/**
 * Created by SCC-140 on 2014-11-18.
 */

@Security.Authenticated(Secured.class)
public class FarmerController extends Controller {

    @Transactional
    public Result farmerIndex() {
        //Get list of all farmers
        List<Farmer> farmerList = Farmer.find.all();
        List<Cooperative> cooperativeList = Cooperative.find.all();
        User user = User.find.byId(request().username());

        return ok(farmer.render(farmerList, cooperativeList, user));
    }

    @Transactional
    public Result addFarmerIndex() {
        //Get list of all cooperatives
        List<Cooperative> cooperativeList = Cooperative.find.all();
        User user = User.find.byId(request().username());

        return ok(addFarmer.render(cooperativeList, user));
    }

    @Transactional
    public Result addFarmer() {
        Farmer farmer = new Farmer();
        Cooperative cooperative;

        DynamicForm requestData = Form.form().bindFromRequest();

        farmer.firstName = requestData.get("inputFirstName");
        farmer.lastName = requestData.get("inputLastName");
        farmer.phoneNumber = requestData.get("inputPhoneNumber");
        farmer.accountNumber = Integer.parseInt(requestData.get("inputAccount"));
        farmer.sex = requestData.get("inputSex");
        farmer.amountCows = Integer.parseInt(requestData.get("inputCows"));

        //Get cooperative that farmer will belong to
        Long cooperativeId = Long.valueOf(requestData.get("inputCooperative"));
        cooperative = Cooperative.find.byId(cooperativeId);

        farmer.cooperative = cooperative;

        //farmerRepository.save(farmer);
        Ebean.save(farmer);

        return redirect(routes.FarmerController.farmerIndex());
    }

    @Transactional
    public Result deleteFarmer(Long farmerId) {
        Farmer farmer;
        farmer = Farmer.find.byId(farmerId);

        //farmerRepository.delete(farmer);
        Ebean.delete(farmer);

        return redirect(routes.FarmerController.farmerIndex());
    }

    @Transactional
    public Result getFarmer(Long farmerId) {
        Farmer farmer;
        farmer = Farmer.find.byId(farmerId);

        List<Cooperative> cooperativeList = Cooperative.find.all();
        User user = User.find.byId(request().username());

        return ok(editFarmer.render(farmer, cooperativeList, user));
    }

    @Transactional
    public Result editFarmer(Long farmerId) {
        DynamicForm requestData = form().bindFromRequest();

        Farmer farmer;
        Cooperative cooperative;

        farmer = Farmer.find.byId(farmerId);

        farmer.firstName = requestData.get("inputFirstName");
        farmer.lastName = requestData.get("inputLastName");
        farmer.phoneNumber = requestData.get("inputPhoneNumber");
        farmer.accountNumber = Integer.parseInt(requestData.get("inputAccount"));
        farmer.sex = requestData.get("inputSex");
        farmer.amountCows = Integer.parseInt(requestData.get("inputCows"));

        //Find cooperative to add to farmer
        Long cid = Long.valueOf(requestData.get("inputCooperative"));
        cooperative = Cooperative.find.byId(cid);

        farmer.cooperative = cooperative;

        //farmerRepository.save(farmer);

        Ebean.save(farmer);
        return redirect(routes.FarmerController.farmerIndex());
    }
}
