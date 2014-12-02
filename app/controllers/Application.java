package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;

import models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage a database of computers
 */
public class Application extends Controller {
    
    @Security.Authenticated(Secured.class)
    public static Result index() {

        User user = User.find.byId(request().username());
        Cooperative cooperative;
        List<Farmer> farmerList;
        List<MilkTransaction> milkTransactionList = MilkTransaction.find.all();
        if (Cooperative.findCooperativeByEmail(user.email) != null) {
            cooperative = Cooperative.findCooperativeByEmail(user.email);
        }
        else {
            cooperative = new Cooperative();
        }

        if (Farmer.getFarmersInCooperative(cooperative.cooperativeId) != null) {
            farmerList = Farmer.getFarmersInCooperative(cooperative.cooperativeId);
        }
        else {
            farmerList = new ArrayList<Farmer>();
        }

        return ok(index.render(user, milkTransactionList));
    }

    public static Result login() {
        return ok(login.render(form(Login.class))
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        }
        else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(routes.Application.index());
        }
    }

    public static class Login {
        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    public static Result logout() {
        session().clear();
        flash("Success", "You have been logged out");
        return redirect(routes.Application.login());
    }




    

}
            
