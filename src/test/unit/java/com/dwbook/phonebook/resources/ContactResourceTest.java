package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.dao.ContactDAO;
import com.dwbook.phonebook.representations.Contact;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.server.Response;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import java.net.URI;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactResourceTest {
    private static final ContactDAO dao = mock(ContactDAO.class);
    private static final Contact JOHN_DOE = new Contact(1, "John", "Doe", "+48511300004");

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ContactResource(dao))
            .build();

    @Test
    public void getContact_should_return_contact_with_given_id() {
        when(dao.getContactById(JOHN_DOE.getId())).thenReturn(JOHN_DOE);

        ClientResponse response = requestJohnDoe();

        assertThat("Response status is 200 OK", response.getStatus(), equalTo(Response.SC_OK));
        assertThat("Response contains contact", response.getEntity(Contact.class), equalTo(JOHN_DOE));
    }

    @Test
    public void getContact_should_return_not_found_when_contact_does_not_exist() {
        when(dao.getContactById(JOHN_DOE.getId())).thenReturn(null);

        ClientResponse response = requestJohnDoe();

        assertThat("Response status is 404 Not Found", response.getStatus(), equalTo(Response.SC_NOT_FOUND));
    }

    @Test
    public void createContact_should_create_contact() {
        ClientResponse response = resources.client().resource("/contacts")
                .type(MediaType.APPLICATION_JSON)
                .entity(JOHN_DOE)
                .post(ClientResponse.class);

        int createdId = verify(dao).createContact(JOHN_DOE.getFirstName(),
                                  JOHN_DOE.getLastName(),
                                  JOHN_DOE.getPhone());
        assertThat("Response Location contain URI for created contact",
                   response.getHeaders().get("Location").get(0), equalTo("/contacts/" + createdId));
        assertThat("Response status is 201 CREATED",
                   response.getStatus(), equalTo(Response.SC_CREATED));
    }

    @Test
    public void updateContact_should_update_contact() {
        ClientResponse response = resources.client().resource("/contacts/" + JOHN_DOE.getId())
                .type(MediaType.APPLICATION_JSON)
                .entity(JOHN_DOE)
                .put(ClientResponse.class);

        verify(dao).updateContact(JOHN_DOE.getId(),
                                  JOHN_DOE.getFirstName(),
                                  JOHN_DOE.getLastName(),
                                  JOHN_DOE.getPhone());
        assertThat(response.getEntity(Contact.class), equalTo(JOHN_DOE));
        assertThat("Response status is 200 OK", response.getStatus(), equalTo(Response.SC_OK));
    }

    @Test
    public void deleteContact_should_delete_contact() {
        ClientResponse response = resources.client().resource("/contacts/" + JOHN_DOE.getId())
                .delete(ClientResponse.class);

        verify(dao).deleteContact(JOHN_DOE.getId());
        assertThat("Response status is 204 NO CONTENT", response.getStatus(), equalTo(Response.SC_NO_CONTENT));
    }

    private ClientResponse requestJohnDoe() {
        return resources.client().resource("/contacts/" + JOHN_DOE.getId())
                .get(ClientResponse.class);
    }
}
