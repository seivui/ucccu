package controllers;

import play.mvc.Security;
import play.mvc.*;
import play.mvc.Http.*;

/**
 * Created by SCC-140 on 2014-11-18.
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context context) {
        return context.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context context) {
        return redirect(routes.Application.login());
    }


}
