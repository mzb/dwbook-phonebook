package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.representations.Contact;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.server.Response;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ContactResourceTest {
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ContactResource())
            .build();
    static final Contact JOHN_DOE = new Contact(1, "John", "Doe", "+48511300004");

    @Test
    public void getContact() {
        ClientResponse response = resources.client().resource("/contacts/" + JOHN_DOE.getId())
                .get(ClientResponse.class);

        assertThat("Response status is 200 OK", response.getStatus(), equalTo(Response.SC_OK));
    }

    @Test
    public void createContact() {
        ClientResponse response = resources.client().resource("/contacts")
                .type(MediaType.APPLICATION_JSON)
                .entity(JOHN_DOE)
                .post(ClientResponse.class);

        assertThat("Response status is 201 CREATED", response.getStatus(), equalTo(Response.SC_CREATED));
    }

    @Test
    public void updateContact() {
        ClientResponse response = resources.client().resource("/contacts/" + JOHN_DOE.getId())
                .type(MediaType.APPLICATION_JSON)
                .entity(JOHN_DOE)
                .put(ClientResponse.class);

        assertThat(response.getEntity(Contact.class), equalTo(JOHN_DOE));
        assertThat("Response status is 200 OK", response.getStatus(), equalTo(Response.SC_OK));
    }

    @Test
    public void deleteContact() {
        ClientResponse response = resources.client().resource("/contacts/" + JOHN_DOE.getId())
                .delete(ClientResponse.class);

        assertThat("Response status is 204 NO CONTENT", response.getStatus(), equalTo(Response.SC_NO_CONTENT));
    }
}
