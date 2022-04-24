import org.hibernate.Session;
import org.hibernate.SessionFactory;
import  org.jboss.logging.Logger;

import java.util.logging.Level;
import static java.util.logging.Logger.getLogger;


public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("unused")                                          //
        Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate"); //clear console from hibernate log
        getLogger("org.hibernate").setLevel(Level.OFF);                      //

        Connector.initSessionFactory();
        SessionFactory sessionFactory = Connector.getSessionFactory();
        Session session = sessionFactory.openSession();

        ConsoleUI consoleUI = new ConsoleUI(session);
        consoleUI.printManual();
        while (session.isOpen()){
            consoleUI.start();
        }

        sessionFactory.close();
    }

}
