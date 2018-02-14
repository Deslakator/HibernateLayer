package ru.desl.conf;

import ru.desl.entity.Address;
import ru.desl.entity.Employee;
import ru.desl.entity.Project;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConf {

    private final static String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private final static String DB_CONNECT_STRING = "jdbc:jtds:sybase://192.168.0.9:4100/Eurasia";
    private final static String DB_USER = "sa";
    private final static String DB_PASSWORD = "IngelheIm";

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.SybaseDialect")
                    .setProperty("hibernate.connection.driver_class", DB_DRIVER)
                    .setProperty("hibernate.connection.url", DB_CONNECT_STRING)
                    .setProperty("hibernate.connection.username", DB_USER)
                    .setProperty("hibernate.connection.password", DB_PASSWORD)
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.hbm2ddl.auto", "none")

                    // Your Mapping Class
                    .addAnnotatedClass(Address.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Project.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

}
