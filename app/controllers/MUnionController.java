package controllers;

import com.avaje.ebean.Ebean;
import models.MUnion;
import play.data.DynamicForm;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addUnion;
import views.html.editUnion;
import views.html.union;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

/**
 * Created by SCC-140 on 2014-11-18.
 */
public class MUnionController extends Controller {

    @Transactional
    public Result unionIndex() {

        List<MUnion> unionList = MUnion.find.all();

        return ok(union.render(unionList));
    }

    @Transactional
    public Result addUnionIndex() {
        return ok(addUnion.render());
    }

    @Transactional
    public Result getUnion(Long unionId) {
        MUnion union = MUnion.find.byId(unionId);

        //union = mUnionRepository.findOne(unionId);

        return ok(editUnion.render(union));
    }

    @Transactional
    public Result addUnion() {
        MUnion nUnion = new MUnion();

        DynamicForm requestData = form().bindFromRequest();

        nUnion.unionName = requestData.get("inputUnionName");
        nUnion.contactPerson = requestData.get("inputContactPerson");
        nUnion.phoneNumber = requestData.get("inputPhoneNumber");
        nUnion.email = requestData.get("inputEmail");
        nUnion.district = requestData.get("inputDistrict");
        nUnion.address = requestData.get("inputAddress");

        //mUnionRepository.save(nUnion);
        Ebean.save(nUnion);

        return redirect(routes.MUnionController.unionIndex());
    }

    @Transactional
    public Result deleteUnion(Long unionId) {

        //mUnionRepository.delete(unionId);

        MUnion union = MUnion.find.byId(unionId);
        Ebean.delete(union);

        return redirect(routes.MUnionController.unionIndex());
    }

    @Transactional
    public Result editUnion(Long unionId) {

        DynamicForm requestData = form().bindFromRequest();

        MUnion union = MUnion.find.byId(unionId);

        //union = mUnionRepository.findOne(unionId);

        union.unionName = requestData.get("inputUnionName");
        union.contactPerson = requestData.get("inputContactPerson");
        union.phoneNumber = requestData.get("inputPhoneNumber");
        union.email = requestData.get("inputEmail");
        union.district = requestData.get("inputDistrict");
        union.address = requestData.get("inputAddress");

        //mUnionRepository.save(union);
        Ebean.save(union);
        return redirect(routes.MUnionController.unionIndex());
    }


}
