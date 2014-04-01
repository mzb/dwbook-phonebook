package com.dwbook.phonebook;

import com.dwbook.phonebook.dao.ContactDAO;
import com.dwbook.phonebook.resources.ContactResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<PhonebookConfiguration> {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> bootstrap) {

    }

    @Override
    public void run(PhonebookConfiguration config, Environment env) throws Exception {
        final DBIFactory dbiFactory = new DBIFactory();
        final DBI jdbi = dbiFactory.build(env, config.getDatabase(), "mysql");

        env.jersey().register(new ContactResource(jdbi.onDemand(ContactDAO.class)));
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
