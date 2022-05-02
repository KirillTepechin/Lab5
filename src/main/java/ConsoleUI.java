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

import javax.persistence.PersistenceException;
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
        try {
            String[] params = input.split(" ", 2);
            switch (params[0]) {
                case ("справочник") -> printHandBook();
                case ("мануал") -> printManual();
                case ("пока") -> session.close();
                case ("период") -> printPeriod();
                case ("employee") -> doEmployeeAction(params[1]);
                case ("department") -> doDepartmentAction(params[1]);
                case ("position") -> doPositionAction(params[1]);
                case ("appointment") -> doAppointmentAction(params[1]);
                case ("запрос") -> executeFreeSqlQuery();
                default -> throw new IllegalArgumentException();
            }
        }
        catch (PersistenceException e){
            System.out.println("Эту сущность нельзя удалить, сначала удалите связанную строку");
        }
        catch (IllegalArgumentException e){
            System.out.println("Ошибка ввода данных");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Вы ввели недостаточное количество аргументов");
        }
        catch (NullPointerException e){
            System.out.println("Запрашивамых данных не существует");
        }
        catch (Exception e){
            System.out.println("Неизвестная ошибка");
        }
    }

    public void printHandBook() {
        String sql = "select surname as Фамилия, cast(sum(salary+individual_surcharges*salary) as real) " + "as Зарплата_за_месяц from " + Employee.class.getSimpleName() + ", " + Position.class.getSimpleName() + ", " + Appointment.class.getSimpleName() + " where position_id=position.id and employee.id=appointment.employee_id" + " and employee.id=appointment.employee_id group by surname";
        var query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Справочник--");
        for (Object[] obj : queryResultRows) {
            HandBookDTO handBookDTO = new HandBookDTO();
            handBookDTO.setSurname((String) obj[0]);
            handBookDTO.setSalary((Float) obj[1]);
            System.out.println(handBookDTO.toString());
        }
        System.out.println("Введите что-нибудь для продолжения");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void mainForm() {
        String sql = "SELECT \n" + "\tConcat (employee.surname,' ', employee.name, ' ', employee.patronymic) as ФИО," + " \n" + "\tposition.name as Должность,\n" + "\tdepartment.name as \"Отдел\"\n" + "FROM\n" + "   employee\n" + "INNER JOIN\n" + "    position\n" + "    ON \n" + "        position.id = position_id\n" + "INNER JOIN\n" + "    appointment\n" + "    ON \n" + "        employee_id = employee.id \n" + "INNER JOIN\n" + "" + "    department\n" + "    ON \n" + "        appointment.department_id = department.id\n";
        var query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Главная форма--");
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
        String sql = "select \temployee.surname as Фамилия,\n" + "\t\tappointment.appointment_date as \"Дата назначения\"," + "\n" + "\t\tappointment.dismissal_date as \"Дата увольнения\",\n" + "\t\tdepartment.name as \"Отдел\"\n" + "from employee , appointment , department\n" + "where employee.id=appointment.employee_id " + "and appointment.department_id=department.id" + " and appointment.dismissal_date<'"+new Date(System.currentTimeMillis()).toString()+"'\n";
        var query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--За период--");
        for (Object[] obj : queryResultRows) {
            PeriodDTO periodDTO = new PeriodDTO();
            periodDTO.setSurname((String) obj[0]);
            periodDTO.setAppointmentDate((Date) obj[1]);
            periodDTO.setDismissalDate((Date) obj[2]);
            periodDTO.setDepartment((String) obj[3]);
            System.out.println(periodDTO.toString());
        }
        System.out.println("Введите что-нибудь для продолжения");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void printManual() {
        System.out.println("--Руководство пользователя--");
        System.out.println("Введите 'справочник' для печати справочника");
        System.out.println("Введите 'период' для печати формы за период");
        System.out.println("Введите 'мануал' для печати руководства пользователя");
        System.out.println("Если вы хотите работать с сущностями введите 'название_сущности create/read/update/delete'");
        System.out.println("При работе с сущностью 'employee' вы так же можете использовать 'employee set_position' для указания должности");
        System.out.println("Вы можете выполнить запрос в базу введя команду 'запрос'(Потребуется пароль)");
        System.out.println("Если хотите выйти, введите 'пока'");
    }

    public void doEmployeeAction(String action) {
        EmployeeLogic logic = new EmployeeLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'фамилия имя отчество индивид.доплаты'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 4);
            logic.createEmployee(attributes[0], attributes[1], attributes[2], Float.parseFloat(attributes[3]));
        }
        else if ("read".equals(action)) {
            var list = logic.readEmployees();
            list.forEach(System.out::println);
            System.out.println("Введите что-нибудь для продолжения");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        else if ("update".equals(action)) {
            System.out.println("Enter 'id фамилия имя отчество индивид.доплаты'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 5);
            logic.updateEmployee(Integer.parseInt(attributes[0]), attributes[1], attributes[2], attributes[3], Float.parseFloat(attributes[4]));
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.nextLine();
            logic.deleteEmployee(Integer.parseInt(input1));
        }
        else if ("set_position".equals(action)) {
            System.out.println("Введите 'id_работника id_должности'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 2);
            logic.addPosition(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]));
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void doDepartmentAction(String action) {
        DepartmentLogic logic = new DepartmentLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'название тел.номер'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 2);
            logic.createDepartment(attributes[0], attributes[1]);
        }
        else if ("read".equals(action)) {
            var list = logic.readDepartment();
            list.forEach(System.out::println);
            System.out.println("Введите что-нибудь для продолжения");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id название тел.номер'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 4);
            logic.updateDepartment(Integer.parseInt(attributes[0]), attributes[1], attributes[3]);
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.nextLine();
            logic.deleteDepartment(Integer.parseInt(input1));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public void doPositionAction(String action) {
        PositionLogic logic = new PositionLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'название зарплата'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 3);
            logic.createPosition(attributes[0], Integer.parseInt(attributes[1]));
        }
        else if ("read".equals(action)) {
            var list = logic.readPosition();
            list.forEach(System.out::println);
            System.out.println("Введите что-нибудь для продолжения");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id название зарплата'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 3);
            logic.updatePosition(Integer.parseInt(attributes[0]), attributes[1], Float.parseFloat(attributes[2]));
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deletePosition(Integer.parseInt(input));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public void doAppointmentAction(String action) {
        AppointmentLogic logic = new AppointmentLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'id_отдела id_работника дата_назнач дата_увол'");
            System.out.println("Паттерн даты 'yyyy-mm-dd'");
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.nextLine();
            String[] attributes = input1.split(" ", 4);
            logic.createAppointment(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]), Date.valueOf(attributes[2]), Date.valueOf(attributes[3]));
        }
        else if ("read".equals(action)) {
            var list = logic.readAppointment();
            list.forEach(System.out::println);
            System.out.println("Введите что-нибудь для продолжения");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id id_отдела id_работника дата_назнач дата_увол'");
            System.out.println("Паттерн даты 'yyyy-mm-dd'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(" ", 5);
            logic.updateAppointment(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]), Date.valueOf(attributes[3]), Date.valueOf(attributes[4]));
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteAppointment(Integer.parseInt(input));
        }else {
            throw new IllegalArgumentException();
        }

    }
    public void executeFreeSqlQuery(){
        System.out.print("Введите пароль:");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if(!answer.equals("123")){
            System.out.println("Неверный пароль");
            return;
        }
        String sql = scanner.nextLine();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
    }
}

