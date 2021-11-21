package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;
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
      Session session = sessionFactory.getCurrentSession();
      String hql = "From Car where model = :model and series = :series";
      Query query = session.createQuery(hql);
      query.setParameter("model", model);
      query.setParameter("series", series);
      Car car = (Car) query.getResultList().get(0);
      return session.load(User.class, car.getId());
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
