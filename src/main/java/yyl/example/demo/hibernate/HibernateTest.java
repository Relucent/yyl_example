package yyl.example.demo.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import yyl.example.demo.hibernate.entity.Table1;

public class HibernateTest {
	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure("/yyl/example/demo/hibernate/config/hibernate.cfg.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			session = sessionFactory.openSession();
			{
				Table1 table1 = new Table1();
				table1.setId("1");
				table1.setName("admin");
				table1.setTime(new Date());
				session.save(table1);
			}
			{
				Table1 table1 = (Table1) session.get(Table1.class, "1");
				System.out.println(table1);
			}
		} finally {
			closeQuietly(session);
			closeQuietly(sessionFactory);
		}

	}

	private static void closeQuietly(Session session) {
		try {
			session.close();
		} catch (Exception e) {
			//ignore
		}
	}

	private static void closeQuietly(SessionFactory sessionFactory) {
		try {
			sessionFactory.close();
		} catch (Exception e) {
			//ignore
		}
	}
}
