package testCgLib;

import org.aspectj.lang.JoinPoint;

/**
 * Created by Administrator on 2015/1/7.
 */
public class MySecurityManagerImpl {
    public void checkSecurity(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
            }
            if ("张三".equals(args[0])) {
                System.out.println("你没有权限访问");
            }
        }
        //...
        //...
        //System.out.println("进行安全检查！！");
    }
}
