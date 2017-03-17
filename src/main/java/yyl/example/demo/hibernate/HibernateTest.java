package yyl.example.demo.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.AssociationType;
import org.hibernate.type.Type;

import yyl.example.demo.hibernate.entity.Table1;

public class HibernateTest {

	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure("/yyl/example/demo/hibernate/config/hibernate.cfg.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			test1(sessionFactory);
			test2(sessionFactory);
			test3(sessionFactory);
		} finally {
			closeQuietly(session);
			closeQuietly(sessionFactory);
		}

	}

	private static void test1(SessionFactory sessionFactory) {
		System.out.println("--------------------------------------------------");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session = sessionFactory.openSession();
			Table1 table1 = new Table1();
			table1.setId("1");
			table1.setName("admin");
			table1.setTime(new Date());
			session.save(table1);
		} finally {
			closeQuietly(session);
		}
	}

	private static void test2(SessionFactory sessionFactory) {
		System.out.println("--------------------------------------------------");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<Table1> records = (List<Table1>) session.createCriteria(Table1.class).list();
			for (Table1 record : records) {
				System.out.println(record);
			}
		} finally {
			closeQuietly(session);
		}
	}

	private static void test3(SessionFactory sessionFactory) {
		System.out.println("--------------------------------------------------");
		ClassMetadata metadata = sessionFactory.getClassMetadata(Table1.class);

		System.out.println("EntityName: " + metadata.getMappedClass());
		System.out.println("IdProperty: " + metadata.getIdentifierPropertyName());

		for (String propertyName : metadata.getPropertyNames()) {

			Type type = metadata.getPropertyType(propertyName);

			System.out.println("Property: " + propertyName);

			System.out.println(" isComponentType: " + type.isComponentType());
			System.out.println(" isAnyType: " + type.isAnyType());
			System.out.println(" isCollection: " + type.isCollectionType());
			System.out.println(" isAssociationType: " + type.isAssociationType());
			if (type instanceof AssociationType) {
				AssociationType associationType = (AssociationType) type;
				String associatedEntityName = associationType.getAssociatedEntityName((SessionFactoryImpl) sessionFactory);
				ClassMetadata associatedClassMetadata = sessionFactory.getClassMetadata(associatedEntityName);

				Class<?> mappedClass = associatedClassMetadata.getMappedClass();
				System.out.println(" associatedClass: " + mappedClass);
			} else {
				System.out.println(" propertyClass: " + type.getReturnedClass());
			}
			System.out.println();
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
