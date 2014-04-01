package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.dao.ContactDAO;
import com.dwbook.phonebook.representations.Contact;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
    private static final Logger LOG = LoggerFactory.getLogger(ContactResource.class);

    private final ContactDAO dao;

    public ContactResource(ContactDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {
        Contact contact = dao.getContactById(id);
        if (contact == null) {
            return Response.status(404)
                    .build();
        }

        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact) {
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
                                  Contact contact) {
        Contact updated = new Contact(id,
                                      contact.getFirstName(),
                                      contact.getLastName(),
                                      contact.getPhone());
        return Response
                .ok(updated)
                .build();
    }
}
