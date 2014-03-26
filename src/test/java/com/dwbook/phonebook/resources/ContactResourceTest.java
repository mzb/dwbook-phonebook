package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.representations.Contact;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.server.Response;
import org.junit.ClassRule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ContactResourceTest {
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ContactResource())
            .build();
    static final Contact JOHN_DOE = new Contact(1, "John", "Doe", "+48511300004");

    @Test
    public void getContact() {
        assertThat(resources.client().resource("/contacts/1").get(Contact.class))
                .isEqualTo(JOHN_DOE);

        assertThat(resources.client().resource("/contacts/1").get(ClientResponse.class).getStatus())
                .isEqualTo(Response.SC_OK);
    }

    @Test
    public void createContact() {
        assertThat(resources.client().resource("/contacts")
                .queryParam("firstName", "John")
                .queryParam("lastName", "Doe")
                .queryParam("phone", "+48511300004")
                .post(ClientResponse.class).getStatus())
                .isEqualTo(Response.SC_CREATED);
    }

    // FIXME: Fails because queryParam is not the way to pass data to PUT/POST request
    @Test
    public void updateContact() {
        assertThat(resources.client().resource("/contacts/1")
                .queryParam("firstName", "John")
                .queryParam("lastName", "Doe")
                .queryParam("phone", "+48511300004")
                .put(Contact.class))
                .isEqualTo(JOHN_DOE);
    }

    @Test
    public void deleteContact() {
        assertThat(resources.client().resource("/contacts/100").delete(ClientResponse.class).getStatus())
                .isEqualTo(Response.SC_NO_CONTENT);
    }
}
