package controllers;

import com.avaje.ebean.Ebean;
import models.Cooperative;
import models.MUnion;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;

import java.util.List;

import static play.data.Form.form;

/**
 * Created by SCC-140 on 2014-11-18.
 */
public class CooperativeController extends Controller {

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result cooperativeIndex() {
        //Get list of all cooperatives
        List<Cooperative> cooperativeList = models.Cooperative.find.all();
        List<MUnion> unionList = MUnion.find.all();

        User user = User.find.byId(request().username());

        if (user.role.equals("cooperative") || user.role.equals("union")) {
            return ok(cooperative.render(cooperativeList, unionList, user));
        }
        else {
            return ok(restrictedPage.render());
        }
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result addCooperativeIndex() {
        //Get list of all unions
        List<MUnion> unionList = MUnion.find.all();

        User user = User.find.byId(request().username());

        if (user.role.equals("cooperative") || user.role.equals("union")) {
            return ok(addCooperative.render(unionList, user));
        }
        else {
            return ok(restrictedPage.render());
        }
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result addCooperative() {

        Cooperative cooperative = new Cooperative();
        MUnion union;

        DynamicForm requestData = Form.form().bindFromRequest();

        cooperative.cooperativeName = requestData.get("inputCooperativeName");
        cooperative.contactPerson = requestData.get("inputContactPerson");
        cooperative.phoneNumber = requestData.get("inputPhoneNumber");
        cooperative.email = requestData.get("inputEmail");

        //Get union that cooperative will belong to
        Long unionId = Long.valueOf(requestData.get("inputUnion"));
        union = MUnion.find.byId(unionId);

        cooperative.union = union;

        //cooperativeRepository.save(cooperative);
        Ebean.save(cooperative);

        return redirect(routes.CooperativeController.cooperativeIndex());
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result deleteCooperative(Long cooperativeId) {
        Cooperative cooperative;
        cooperative = Cooperative.find.byId(cooperativeId);

        //cooperativeRepository.delete(cooperative);
        Ebean.delete(cooperative);

        return redirect(routes.CooperativeController.cooperativeIndex());
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result getCooperative(Long cooperativeId) {
        Cooperative cooperative;
        cooperative = Cooperative.find.byId(cooperativeId);
        User user = User.find.byId(request().username());

        //Get list of all unions
        List<MUnion> unionList = MUnion.find.all();

        if (user.role.equals("cooperative") || user.role.equals("union")){
            return ok(editCooperative.render(cooperative, unionList, user));
        }
        else {
            return ok(restrictedPage.render());
        }
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result editCooperative(Long cooperativeId) {
        DynamicForm requestData = form().bindFromRequest();

        MUnion union;

        Cooperative cooperative;
        cooperative = Cooperative.find.byId(cooperativeId);

        cooperative.cooperativeName = requestData.get("inputCooperativeName");
        cooperative.contactPerson = requestData.get("inputContactPerson");
        cooperative.phoneNumber = requestData.get("inputPhoneNumber");
        cooperative.email = requestData.get("inputEmail");

        //Get union that cooperative will belong to
        Long uid = Long.valueOf(requestData.get("inputUnion"));
        union = MUnion.find.byId(uid);


        cooperative.union = union;

        //cooperativeRepository.save(cooperative);
        Ebean.save(cooperative);

        return redirect(routes.CooperativeController.cooperativeIndex());
    }

}
