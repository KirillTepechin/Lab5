import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        Connector.initSessionFactory();
        SessionFactory sessionFactory = Connector.getSessionFactory();
    }
}
