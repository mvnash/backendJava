package be.vinci.api;

import be.vinci.api.filters.Authorize;
import be.vinci.domain.TexteADactylographier;
import be.vinci.domain.User;
import be.vinci.services.Json;
import be.vinci.services.TextService;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Path("texts")
public class TexteADactylographierResource {

    private TextService textService = new TextService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TexteADactylographier> getAllTexts(@DefaultValue("") @QueryParam("level") String level) {
        return textService.getAllTexts("");
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TexteADactylographier getOne(@PathParam("id") int id) {
        TexteADactylographier textFound = textService.getOne(id);
        if (textFound == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        }
        return textFound;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public TexteADactylographier createOne(TexteADactylographier texteADactylographier, @Context ContainerRequest request) {
        User authentificatedUser = (User) request.getProperty("user");
        System.out.println("A new text is added by " + authentificatedUser.getLogin() );
        if (texteADactylographier == null || texteADactylographier.getContent() == null || texteADactylographier.getLevel().isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info")
                            .type("text/plain").build());
        }
        return textService.createOne(texteADactylographier);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public TexteADactylographier deleteOne(@PathParam("id") int id) {
        if (id == 0) // default value of an integer => has not been initialized
        {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                            .type("text/plain").build());
        }
        TexteADactylographier textToDelete = textService.deleteOne(id);
        if (textToDelete == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        }
        return textToDelete;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public TexteADactylographier updateOne(TexteADactylographier text, @PathParam("id") int id) {
        if (id == 0 || text == null || text.getContent() == null || text.getLevel().isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info")
                            .type("text/plain").build());
        }
        TexteADactylographier textToUpdate = textService.updateOne(text, id);
        if (textToUpdate == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        }
        return textToUpdate;
    }
}
