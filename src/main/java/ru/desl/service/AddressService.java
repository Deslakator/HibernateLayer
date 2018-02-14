package ru.desl.service;

import ru.desl.entity.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQueryApiInspection")
public class AddressService extends AbstractDAOService<Address> {

    public AddressService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Address> getAll() {
        List<Address> addressCollection;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            TypedQuery<Address> query = session.createNamedQuery("get_all_address",Address.class); //.createQuery("from Address", Address.class);
            addressCollection = query.getResultList();
            transaction.commit();
        }
        return addressCollection;
    }

    @Override
    public Address getById(long id) {
        final Address address;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createNamedQuery("get_address_by_id", Address.class);
            query.setParameter("id", id);

            address = (Address) query.getSingleResult();
            transaction.commit();
        }
        return address;
    }
}
