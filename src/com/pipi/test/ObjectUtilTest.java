package com.pipi.test;

import com.pipi.entity.admin.User;
import com.pipi.util.ObjectUtil;
import org.junit.Test;

/**
 * Created by yahto on 21/05/2017.
 */
public class ObjectUtilTest {
    @Test
    public void objectIsEmptyTest(){
        User user = new User();
        user.setLoginName("yahto");
        user.setUserName("yahto");
        user.setPassword("123");
        String[] params = {"userName","password","loginName"};
        System.out.println(ObjectUtil.objectIsEmpty(user,params));
    }
}
