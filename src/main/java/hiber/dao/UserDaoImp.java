package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public User getUserByCar(String model, int series) {
      String hql = "FROM User u LEFT JOIN FETCH u.car i where i.model = :model and i.series = :series";
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.getSingleResult();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("from User");
      return query.getResultList();
   }

   @Override
   public void clean() {
      Session session = sessionFactory.getCurrentSession();
      session.createQuery("delete from User").executeUpdate();
      session.createQuery("delete from Car").executeUpdate();
   }

   @Override
   public void removedUserById(long id) {
      Session session = sessionFactory.getCurrentSession();
      session.delete(session.load(User.class, id));
   }
}
