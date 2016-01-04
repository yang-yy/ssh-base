package testCgLib;

/**
 * Created by Administrator on 2015/1/7.
 */
public class UserManagerImpl {
     private int num;

    public void addUser(String name, String password) {
        System.out.println("UserManagerImpl.addUser() -- name: " + name);
    }

    public void delUser(int id) {
        System.out.println("UserManagerImpl.delUser() -- id: " + id);
    }

    public void modifyUser(int id, String name, String password) {
        System.out.println("UserManagerImpl.modifyUser() -- id: " + id);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}