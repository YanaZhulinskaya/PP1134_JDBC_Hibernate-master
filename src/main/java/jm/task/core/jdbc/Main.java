package jm.task.core.jdbc;


/**
 *  Создание таблицы User(ов)
 *  Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в
 *  консоль ( User с именем – name добавлен в базу данных )
 *  Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 *  Очистка таблицы User(ов)
 *  Удаление таблицы
 */

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    private static final User user1 = new User("Alex", "Ivanov", (byte) 22);
    private static final User user2 = new User("Ivan", "Ivanov", (byte) 34);
    private static final User user3 = new User("Petr", "Petrenko", (byte) 29);
    private static final User user4 = new User("Kirill", "Somov", (byte) 56);

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());


        List<User> strings = userService.getAllUsers();
        for (User s : strings)
            System.out.println(s);

        userService.removeUserById(3);

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeConnection();
    }
}