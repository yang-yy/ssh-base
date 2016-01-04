package testCgLib;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2015/1/7.
 */
public class Test {
    public static void main(String[] args) {

        BeanFactory factory = new ClassPathXmlApplicationContext("classpath:testCgLib/application-Test.xml");
        UserManagerImpl userManager = (UserManagerImpl) factory.getBean("userManager");
        System.out.println(userManager.getNum());
       // userManager.addUser("张三", "123");
//      userManager.delUser(1);
//      userManager.modifyUser(1, "李四", "abc");
    }
}
