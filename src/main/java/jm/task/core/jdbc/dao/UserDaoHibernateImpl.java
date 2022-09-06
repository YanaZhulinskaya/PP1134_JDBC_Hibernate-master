package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection();
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS test.user (Id BIGINT PRIMARY KEY NOT NULL  AUTO_INCREMENT," +
                    "name VARCHAR(30),lastname VARCHAR(30),age TINYINT").executeUpdate();
            transaction.commit();
            System.out.println("Table created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("Drop table if exists test.table").executeUpdate();
            transaction.commit();
            System.out.println("Table remove");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User: " + name + "" + lastName + " add in database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("User" + id + "remove");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            list = session.createQuery(User.class.getSimpleName()).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {

        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            List<User> users = getAllUsers();
            for (User user : users) session.delete(user);
            transaction.commit();
            System.out.println("Table cleaned");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}