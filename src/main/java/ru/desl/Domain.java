package ru.desl;

import ru.desl.conf.HibernateConf;
import ru.desl.entity.Address;
import ru.desl.entity.Employee;
import ru.desl.entity.Project;
import org.hibernate.SessionFactory;
import ru.desl.service.AddressService;
import ru.desl.service.EmployeeService;
import ru.desl.service.ProjectService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Domain {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateConf.getSessionFactory();

        AddressService addressService = new AddressService(sessionFactory);
        EmployeeService employeeService = new EmployeeService(sessionFactory);
        ProjectService projectService = new ProjectService(sessionFactory);

        Domain domain = new Domain(addressService, employeeService, projectService);

        domain.writeObjectsToDB();
        domain.readObjectsFromDB();

        sessionFactory.close();

    }


    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public Domain(AddressService addressService, EmployeeService employeeService, ProjectService projectService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    private void writeObjectsToDB() {
        Address address = new Address();
        address.setCity("Голубое, Мелодия Леса");
        address.setPostcode("122549");

        //addressService.add(address); -- Т.к. включен режим каскада CascadeType.ALL

        Employee employee = new Employee();
        employee.setName("Daniil Lukianov");
        employee.setAddress(address);
        employeeService.add(employee);

        Project projectJDBC = new Project();
        projectJDBC.setTitle("JDBC project");
        projectService.add(projectJDBC);

        Project projectHibernate = new Project();
        projectHibernate.setTitle("Hibernate project");
        projectService.add(projectHibernate);

        Set<Project> projects = new HashSet<>();
        projects.add(projectHibernate);
        employee.setProjects(projects);
        employeeService.update(employee);
    }

    private void readObjectsFromDB() {
        System.out.println();
        System.out.println(projectService.getById(1));
        System.out.println();
        addressService.getById(1);
        System.out.println();
        employeeService.getById(1);
        System.out.println();
        System.out.println();
        System.out.println();
        employeeService.getAll().forEach(System.out::println);
        projectService.getAll().forEach(System.out::println);
        addressService.getAll().forEach(System.out::println);
        System.out.println();
        System.out.println("---- Посмотрим проекты по employees");
        System.out.println();
        employeeService.setCloseConnectionAfterSelect(false);
        List<Employee> employees = employeeService.getAll();
        employees.forEach( e -> {
            StringBuilder builder = new StringBuilder(e.getName());
            builder.append("\n");
            builder.append(e.getAddress());
            builder.append("\n");
            builder.append(e.getProjects());
            builder.append("\n");
            System.out.println(builder.toString());
        });
        employeeService.closeSession();
    }
}
