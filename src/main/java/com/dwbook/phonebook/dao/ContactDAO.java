package com.dwbook.phonebook.dao;

import com.dwbook.phonebook.dao.mappers.ContactMapper;
import com.dwbook.phonebook.representations.Contact;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface ContactDAO {
    @Mapper(ContactMapper.class)
    @SqlQuery("SELECT * FROM  contact WHERE id = :id")
    public Contact getContactById(@Bind("id") int id);

    @Mapper(ContactMapper.class)
    @SqlUpdate("INSERT INTO contact (id, firstName, lastName, phone) VALUES (NULL, :firstName, :lastName, :phone)")
    @GetGeneratedKeys
    public int createContact(@Bind("firstName") String firstName,
                              @Bind("lastName") String lastName,
                              @Bind("phone") String phone);

    @Mapper(ContactMapper.class)
    @SqlUpdate("UPDATE contact SET " +
                       "firstName = :firstName, " +
                       "lastName = :lastName, " +
                       "phone = :phone " +
                       "WHERE id = :id")
    void updateContact(@Bind("id") int id,
                       @Bind("firstName") String firstName,
                       @Bind("lastName") String lastName,
                       @Bind("phone") String phone);

    @Mapper(ContactMapper.class)
    @SqlUpdate("DELETE FROM contact WHERE id = :id")
    void deleteContact(@Bind("id") int id);
}
