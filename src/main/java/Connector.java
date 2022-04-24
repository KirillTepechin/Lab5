import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;

import java.net.ConnectException;


public class Connector {

    private static SessionFactory sessionFactory;

    public static void initSessionFactory(){
        try {
            StandardServiceRegistry std = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(std).getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        }
        catch (Exception exception){
            System.out.println("Ошибка при попытке подсоединения. Проверьте состояние сервера");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
