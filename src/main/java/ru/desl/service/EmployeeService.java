package ru.desl.service;

import ru.desl.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeService extends AbstractDAOService<Employee> {

    public EmployeeService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private boolean closeConnectionAfterSelect = true;

    private Session currentSession;

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeCollection;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        query.select(root);
        employeeCollection = session.createQuery(query).getResultList();

        transaction.commit();
        if (closeConnectionAfterSelect) session.close();
        else currentSession = session;
        return employeeCollection;
    }

    public boolean isCloseConnectionAfterSelect() {
        return closeConnectionAfterSelect;
    }

    public void setCloseConnectionAfterSelect(boolean closeConnectionAfterSelect) {
        this.closeConnectionAfterSelect = closeConnectionAfterSelect;
    }

    public void closeSession() {
        closeConnectionAfterSelect = true;
        if (currentSession != null) currentSession.close();
    }

    @Override
    public Employee getById(long id) {
        Employee employee;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            query.select(root).where(builder.equal(root.get("id"), id));
            employee = session.createQuery(query).getSingleResult();
            transaction.commit();
        }
        return employee;
    }
}
