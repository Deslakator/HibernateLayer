package ru.desl.service;

import ru.desl.DAO.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDAOService<T> implements DAO<T> {

    public final SessionFactory sessionFactory;

    protected AbstractDAOService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(T item) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        }
    }

    @Override
    public void update(T item) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        }
    }

    @Override
    public void delete(T item) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
        }
    }

}
