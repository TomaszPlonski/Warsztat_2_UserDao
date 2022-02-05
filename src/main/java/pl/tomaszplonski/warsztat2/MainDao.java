package pl.tomaszplonski.warsztat2;

public class MainDao {

    public static void main(String[] args) {
        User test = new User("testName","test@email.com","haslotest");

        UserDao.createUser(test);

    }
}
