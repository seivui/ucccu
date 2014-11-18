package controllers;

import com.avaje.ebean.Ebean;
import models.Cooperative;
import models.MUnion;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addCooperative;
import views.html.cooperative;
import views.html.editCooperative;

import java.util.List;

import static play.data.Form.form;

/**
 * Created by SCC-140 on 2014-11-18.
 */
public class CooperativeController extends Controller {

    @Transactional
    public Result cooperativeIndex() {
        //Get list of all cooperatives
        List<Cooperative> cooperativeList = models.Cooperative.find.all();
        List<MUnion> unionList = MUnion.find.all();
        /*
        for (Cooperative cooperative : cooperativeRepository.findAll()) {
            cooperativeList.add(cooperative);
        }

        //Get list of all unions
        List<MUnion> unionList = new ArrayList<MUnion>();
        for (MUnion u : unionRepository.findAll()) {
            unionList.add(u);
        }
        */

        return ok(cooperative.render(cooperativeList, unionList));
    }

    @Transactional
    public Result addCooperativeIndex() {
        //Get list of all unions
        List<MUnion> unionList = MUnion.find.all();

        /*
        for (MUnion union : unionRepository.findAll()) {
            unionList.add(union);
        }
        */

        return ok(addCooperative.render(unionList));
    }

    @Transactional
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
    public Result deleteCooperative(Long cooperativeId) {
        Cooperative cooperative;
        cooperative = Cooperative.find.byId(cooperativeId);

        //cooperativeRepository.delete(cooperative);
        Ebean.delete(cooperative);

        return redirect(routes.CooperativeController.cooperativeIndex());
    }

    @Transactional
    public Result getCooperative(Long cooperativeId) {
        Cooperative cooperative;
        cooperative = Cooperative.find.byId(cooperativeId);

        //Get list of all unions
        List<MUnion> unionList = MUnion.find.all();
        /*
        for (MUnion u : unionRepository.findAll()) {
            unionList.add(u);
        }
        */

        return ok(editCooperative.render(cooperative, unionList));
    }

    @Transactional
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
