package ru.desl.service;

import ru.desl.entity.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ProjectService extends AbstractDAOService<Project> {

    public ProjectService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Project> getAll() {
        final String selectSql = "select * from project";

        List<Project> projectCollection;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            projectCollection = session.createNativeQuery(selectSql, Project.class).getResultList();

            transaction.commit();
        }
        return projectCollection;
    }

    @Override
    public Project getById(long id) {
        final String selectSql = "select * from project where id = :id";
        Project project;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            project = session.createNativeQuery(selectSql, Project.class).setParameter("id", id).getSingleResult();
            transaction.commit();
        }
        return project;
    }
}
