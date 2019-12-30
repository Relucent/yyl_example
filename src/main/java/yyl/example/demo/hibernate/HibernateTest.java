package yyl.example.demo.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import yyl.example.demo.hibernate.entity.Table1;

public class HibernateTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("/yyl/example/demo/hibernate/config/hibernate.cfg.xml");
        SessionFactory sessionFactory = null;
        EntityManager entityManager = null;
        try {
            sessionFactory = configuration.buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
            test1(entityManager);
            test2(entityManager);
            test3(entityManager);
        } finally {
            closeQuietly(entityManager);
            closeQuietly(sessionFactory);
        }
    }

    private static void test1(EntityManager entityManager) {
        System.out.println("#test1--------------------------------------------------");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Table1 entity = new Table1();
            entity.setId("1");
            entity.setName("admin");
            entity.setTime(new Date());
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    private static void test2(EntityManager entityManager) {
        System.out.println("#test2--------------------------------------------------");
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Table1> criteria = builder.createQuery(Table1.class);
        Root<Table1> root = criteria.from(Table1.class);
        criteria.select(root);
        Query query = entityManager.createQuery(criteria);
        @SuppressWarnings("unchecked")
        List<Table1> records = (List<Table1>) query.getResultList();
        for (Table1 record : records) {
            System.out.println(record);
        }
    }

    private static void test3(EntityManager entityManager) {
        System.out.println("#test3--------------------------------------------------");
        Metamodel metamodel = entityManager.getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            System.out.println(entityType.getJavaType());
            for (Attribute<?, ?> attribute : entityType.getAttributes()) {
                System.out.println(" Attribute:" + attribute);
            }
            System.out.println();
        }
    }

    private static void closeQuietly(EntityManager entityManager) {
        try {
            entityManager.close();
        } catch (Exception e) {
            // ignore
        }
    }

    private static void closeQuietly(SessionFactory sessionFactory) {
        try {
            sessionFactory.close();
        } catch (Exception e) {
            // ignore
        }
    }
}
