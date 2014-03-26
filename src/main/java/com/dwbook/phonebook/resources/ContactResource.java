package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.representations.Contact;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {
    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {
        return Response
                .ok(new Contact(id, "John", "Doe", "+48511300004"))
                .build();
    }

    @POST
    public Response createContact(@FormParam("firstName") String firstName,
                                  @FormParam("lastName") String lastName,
                                  @FormParam("phone") String phone) {
        return Response
                .created(null)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        return Response
                .noContent()
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id,
                                  @FormParam("firstName") String firstName,
                                  @FormParam("lastName") String lastName,
                                  @FormParam("phone") String phone) {
        return Response
                .ok(new Contact(id, firstName, lastName, phone))
                .build();
    }
}
