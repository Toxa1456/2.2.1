package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      user1.setCar(new Car("Lada", 1234));
      userService.add(user1);
      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      user2.setCar(new Car("Porsche", 12345));
      User user3 = new User("User3", "Lastname3", "user3@mail.ru");
      user3.setCar(new Car("Mercedes", 2828));
      userService.add(user2);
      userService.add(user3);
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));


      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         if (user.getCar() != null) {
            System.out.println("Car = " + user.getCar().toString());
         } else {
            System.out.println("The user does not have a car");
         }
         System.out.println();
      }

      System.out.println(userService.getUserByCar("Porsche", 12345).getFirstName());

      // userService.clean();
      context.close();
   }
}
