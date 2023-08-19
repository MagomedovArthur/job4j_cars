package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param
     */
    public void delete(int userId) {
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = this.sf.openSession();
        List<User> resultList = new ArrayList<>();
        try {
            Query<User> query = session.createQuery("from User order by id", User.class);
            resultList = query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = this.sf.openSession();
        Optional<User> user = Optional.empty();
        try {
            Query<User> query = session.createQuery(
                    "from User as u where u.id = :fId", User.class);
            query.setParameter("fId", userId);
            user = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = this.sf.openSession();
        List<User> resultList = new ArrayList<>();

        try {
            Query<User> query = session.createQuery(
                    "from User as u where u.login = :fLogin", User.class);
            query.setParameter("fLogin", key);
            resultList = query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = this.sf.openSession();
        Optional<User> userOptional = Optional.empty();
        try {
            Query<User> query = session.createQuery(
                            "from User where login like :fLogin", User.class)
                    .setParameter("fLogin", login);
            userOptional = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOptional;
    }
}