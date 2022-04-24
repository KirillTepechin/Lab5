import logic.AppointmentLogic;
import logic.DepartmentLogic;
import logic.EmployeeLogic;
import logic.PositionLogic;
import org.hibernate.Transaction;
import queryDTO.HandBookDTO;
import model.Appointment;
import model.Employee;
import model.Position;
import org.hibernate.Session;
import queryDTO.MainFormDTO;
import queryDTO.PeriodDTO;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    Session session;

    public ConsoleUI(Session session) {
        this.session = session;
    }

    public void start() {
        mainForm();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] params = input.split(" ", 2);
        switch (params[0]) {
            case ("handbook") -> printHandBook();
            case ("manual") -> printManual();
            case ("bye") -> session.close();
            case ("period") -> printPeriod();
            case ("employee") -> doEmployeeAction(params[1]);
            case ("department") -> doDepartmentAction(params[1]);
            case ("position") -> doPositionAction(params[1]);
            case ("appointment") -> doAppointmentAction(params[1]);
            case ("free") -> executeFreeSqlQuery();
        }
    }

    public void printHandBook() {
        String sql = "select surname as Фамилия, cast(sum(salary+individual_surcharges*salary) as integer) " + "as Зарплата_за_месяц from " + Employee.class.getSimpleName() + ", " + Position.class.getSimpleName() + ", " + Appointment.class.getSimpleName() + " where position_id=position.id and employee.id=appointment.employee_id" + " and employee.id=appointment.employee_id group by surname";

        var query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Handbook--");
        for (Object[] obj : queryResultRows) {
            HandBookDTO handBookDTO = new HandBookDTO();
            handBookDTO.setSurname((String) obj[0]);
            handBookDTO.setSalary((Integer) obj[1]);
            System.out.println(handBookDTO.toString());
        }
        System.out.println("Enter something to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void mainForm() {
        String sql = "SELECT \n" + "\tConcat (employee.surname,' ', employee.name, ' ', employee.patronymic) as ФИО," + " \n" + "\tposition.name as Должность,\n" + "\tdepartment.name as \"Отдел\"\n" + "FROM\n" + "   employee\n" + "INNER JOIN\n" + "    position\n" + "    ON \n" + "        position.id = position_id\n" + "INNER JOIN\n" + "    appointment\n" + "    ON \n" + "        employee_id = employee.id \n" + "INNER JOIN\n" + "" + "    department\n" + "    ON \n" + "        appointment.department_id = department.id\n";
        var query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Main Form--");
        for (Object[] obj : queryResultRows) {
            MainFormDTO mainFormDTO = new MainFormDTO();
            mainFormDTO.setFIO((String) obj[0]);
            mainFormDTO.setPosition((String) obj[1]);
            mainFormDTO.setDepartment((String) obj[2]);
            System.out.println(mainFormDTO.toString());
        }
        System.out.println();
    }

    public void printPeriod() {
        String sql = "select \temployee.surname as Фамилия,\n" + "\t\tappointment.appointment_date as \"Дата назначения\"," + "\n" + "\t\tappointment.dismissal_date as \"Дата увольнения\",\n" + "\t\tdepartment.name as \"Отдел\"\n" + "from employee , appointment , department\n" + "where employee.id=appointment.employee_id " + "and appointment.department_id=department.id and appointment.appointment_date>'01.05.2016'" + " and appointment.dismissal_date<'01.05.2017'\n";
        var query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Period Form--");
        for (Object[] obj : queryResultRows) {
            PeriodDTO periodDTO = new PeriodDTO();
            periodDTO.setSurname((String) obj[0]);
            periodDTO.setAppointmentDate((Date) obj[1]);
            periodDTO.setDismissalDate((Date) obj[2]);
            periodDTO.setDepartment((String) obj[3]);
            System.out.println(periodDTO.toString());
        }
        System.out.println("Enter something to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void printManual() {
        System.out.println("--Manual--");
        System.out.println("Hello, user!");
        System.out.println("Enter 'handbook' to print handbook form");
        System.out.println("Enter 'period' to print period form");
        System.out.println("Enter 'manual' to print manual");
        System.out.println("If you want to work with entities enter 'entity create/read/update/delete'");
        System.out.println("If you work with 'employee' you can also use 'employee set_position'");
        System.out.println("You can execute sql query by entering 'free'(Only for advanced users)");
        System.out.println("If you want to leave session enter 'bye'");
    }

    public void doEmployeeAction(String action) {
        EmployeeLogic logic = new EmployeeLogic(session);
        if ("create".equals(action)) {
            System.out.println("Enter 'surname name patronymic individual_surcharges'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 4);
            logic.createEmployee(attributes[0], attributes[1], attributes[2], Float.parseFloat(attributes[3]));
        }
        if ("read".equals(action)) {
            var list = logic.readEmployees();
            list.forEach(System.out::println);
            System.out.println("Enter something to continue");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        if ("update".equals(action)) {
            System.out.println("Enter 'id surname name patronymic individual_surcharges'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 5);
            logic.updateEmployee(Integer.parseInt(attributes[0]), attributes[1], attributes[2], attributes[3], Float.parseFloat(attributes[4]));
        }
        if ("delete".equals(action)) {
            System.out.println("Enter 'id'");
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.nextLine();
            logic.deleteEmployee(Integer.parseInt(input1));
        }
        if ("set_position".equals(action)) {
            System.out.println("Enter 'employee_id position_id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 2);
            logic.addPosition(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]));
        }
    }

    public void doDepartmentAction(String action) {
        DepartmentLogic logic = new DepartmentLogic(session);
        if ("create".equals(action)) {
            System.out.println("Enter 'name number_of_employees phone_number'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 3);
            logic.createDepartment(attributes[0], Integer.parseInt(attributes[1]), attributes[2]);
        }
        if ("read".equals(action)) {
            var list = logic.readDepartment();
            list.forEach(System.out::println);
            System.out.println("Enter something to continue");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        if ("update".equals(action)) {
            System.out.println("Enter 'id number_of_employees phone_number'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 4);
            logic.updateDepartment(Integer.parseInt(attributes[0]), attributes[1], Integer.parseInt(attributes[2]), attributes[3]);
        }
        if ("delete".equals(action)) {
            System.out.println("Enter 'id'");
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.nextLine();
            logic.deleteDepartment(Integer.parseInt(input1));
        }
    }

    public void doPositionAction(String action) {
        PositionLogic logic = new PositionLogic(session);
        if ("create".equals(action)) {
            System.out.println("Enter 'name salary number_of_employees'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 3);
            logic.createPosition(attributes[0], Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]));
        }
        if ("read".equals(action)) {
            var list = logic.readPosition();
            list.forEach(System.out::println);
            System.out.println("Enter something to continue");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        if ("update".equals(action)) {
            System.out.println("Enter 'id name salary number_of_employees'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 4);
            logic.updatePosition(Integer.parseInt(attributes[0]), attributes[1], Integer.parseInt(attributes[2]), Integer.parseInt(attributes[3]));
        }
        if ("delete".equals(action)) {
            System.out.println("Enter 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deletePosition(Integer.parseInt(input));
        }
    }

    public void doAppointmentAction(String action) {
        AppointmentLogic logic = new AppointmentLogic(session);
        if ("create".equals(action)) {
            System.out.println("Enter 'dep_id emp_id app_date dis_date'");
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.nextLine();
            String[] attributes = input1.split(" ", 4);
            logic.createAppointment(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]), Date.valueOf(attributes[2]), Date.valueOf(attributes[3]));
        }
        if ("read".equals(action)) {
            var list = logic.readAppointment();
            list.forEach(System.out::println);
            System.out.println("Enter something to continue");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        if ("update".equals(action)) {
            System.out.println("Enter 'id dep_id emp_id app_date dis_date'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 5);
            logic.updateAppointment(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]), Date.valueOf(attributes[3]), Date.valueOf(attributes[4]));
        }
        if ("delete".equals(action)) {
            System.out.println("Enter 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteAppointment(Integer.parseInt(input));
        }

    }
    public void executeFreeSqlQuery(){
        Scanner scanner = new Scanner(System.in);
        String sql = scanner.nextLine();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
    }
}

