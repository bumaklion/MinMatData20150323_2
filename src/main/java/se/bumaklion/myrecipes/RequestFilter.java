
package main.java.se.bumaklion.myrecipes;


import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.service.UserService;
import main.java.se.bumaklion.myrecipes.util.PasswordHash;
import main.java.se.bumaklion.myrecipes.util.Strings;
import main.java.se.bumaklion.myrecipes.util.logging.BumLogger;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (!isAuthorized((ContainerRequest) containerRequestContext.getRequest()))
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"").entity("Page requires login.").build());
    }

    private boolean isAuthorized(ContainerRequest req) {
        //CORS preflight requests are not authenticated
        if ("OPTIONS".equalsIgnoreCase(req.getMethod()))
            return true;

        // Get the authentication passed in HTTP headers parameters
        String auth = req.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (auth == null)
            return false;

        auth = auth.replaceFirst("[Bb]asic ", "");
        String userColonPass = new String(DatatypeConverter.parseBase64Binary(auth));

        User user = new UserService().getUserByLogin(Strings.getEverythingBefore(userColonPass, ":"));

        if (user == null)
            return false;

        try {
            if (!PasswordHash.validatePassword(Strings.getEverythingAfter(userColonPass, ":"), user.getPasswordHash()))
                return false;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            BumLogger.error(e);
            return false;
        }

        return true;
    }

}

