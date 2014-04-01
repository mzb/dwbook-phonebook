package com.dwbook.phonebook.dao.mappers;

import com.dwbook.phonebook.representations.Contact;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements ResultSetMapper<Contact> {
    @Override
    public Contact map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
        return  new Contact(rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("phone"));
    }
}
