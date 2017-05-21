package com.pipi.service.adminSerivce;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.common.logaop.MyLog;
import com.pipi.entity.admin.Role;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.adminIService.IUserService;
import com.pipi.util.DSUtil;
import com.pipi.util.Ufn;
import com.pipi.vo.UserRoleVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.pipi.dao.idao.adminIDao.IUserDao;
import com.pipi.service.BaseService;

import java.util.*;

/**
 * Created by yahto on 07/05/2017.
 */
@Service
public class UserService extends BaseService implements IUserService {
    @Resource
    IUserDao userDao;

    @Override
    public void queryUserForLogin(String loginName, String password, HttpServletRequest request) {
        if (loginName == null || loginName.length() == 0) {
            throw new BusinessException("用户名不能为空");
        }
        if (password == null || password.length() == 0) {
            throw new BusinessException("密码不能为空");
        }
        String hql = "from User u where u.isDelete=0 and loginName='" + loginName + "'";
        List<User> list = (List<User>) baseDao.getObjectListByNativeHql(hql);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("该用户不存在");
        } else {
            if (!password.equals(list.get(0).getPassword())) {
                throw new BusinessException("密码错误，请重试");
            } else {
                User user = list.get(0);
                List<Integer> roleIdList = (List<Integer>) queryListByNavtiveSql("select FK_ROLE_ID from t_user_role where FK_USER_ID=" + user.getId());
                if (CollectionUtils.isEmpty(roleIdList))
                    throw new BusinessException("该用户未分配角色，暂时不能访问本系统！");
                user.setRoles(new HashSet<Integer>(roleIdList));
                Set<Integer> roles = user.getRoles();
                List<Integer> privList = (List<Integer>) queryListByNavtiveSql("select PRIV_ID from t_role_priv where FK_ROLE_ID in (" + Ufn.join(roles.toArray()) + ")");
                Set<Integer> privs = new HashSet<Integer>(privList);
                user.setPrivs(privs);
                request.getSession().setAttribute(SystemConstant.CURRENT_USER, user);
            }
        }
    }

    @Override
    @MyLog(operationName = "批量删除客户", operationType = "delete")
    public void deleteUserByIds(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("未指定要删除的用户");
        }
        String hql = "update User u set u.isDelete=1 where u.id in (" + DSUtil.parseIntegerArr(ids) + ")";
        updateByHql(hql);
    }

    @Override
    public void updateUser(User user, Integer[] roleIds) {
        if (user == null) {
            throw new BusinessException("未指定要修改的用户");
        }
        User existUser = (User) queryObjectByID(User.class,user.getId());
        if (existUser == null){
            throw new BusinessException("要修改的用户不存在,请重试");
        }
        existUser.setLoginName(user.getLoginName());
        existUser.setUserName(user.getUserName());
        existUser.setPassword(user.getPassword());
        update(existUser);
        if (roleIds != null && roleIds.length != 0) {
            String hql = "delete UserRole ur where ur.user.id = " + user.getId();
            updateByHql(hql);
            List<String> sqls = new ArrayList<String>();
            for (Integer roleId : roleIds) {
                String tempSql = "insert into T_USER_ROLE (FK_ROLE_ID,FK_USER_ID) values("
                        + roleId + "," + user.getId() + ")";
                sqls.add(tempSql);
            }
            batchExecuteNativeSql(sqls);
        }else {
            throw new BusinessException("未指定用户角色");
        }
    }

    @Override
    public User queryUserById(Integer id) {
        return (User) userDao.getObjectByID(User.class, id);
    }

    @Override
    public List<UserRoleVo> queryAllUsers() {
        List<User> userList = (List<User>) queryAll(User.class);
        List<UserRoleVo> list = new ArrayList<UserRoleVo>();
        Iterator iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            UserRoleVo userRoleVo = new UserRoleVo();
            userRoleVo.setUser(user);
            String sql = "select FK_ROLE_ID from T_USER_ROLE ur where FK_USER_ID = " + user.getId();
            List<Object> userRole = (List<Object>) queryListByNavtiveSql(sql);
            String hql = "from Role r where r.isDelete=0 and r.id in ("
                    + DSUtil.parseObjectList(userRole) + ")";
            List<Role> roleList = (List<Role>) queryObjectList(hql);
            userRoleVo.setRoleList(roleList);
            list.add(userRoleVo);
        }
        return list;
    }

    @Override
    public void addUser(User user, Integer[] roleIds) {
        if (user == null){
            throw new BusinessException("未接收到用户信息");
        }
        if (roleIds == null || roleIds.length ==0){
            throw new BusinessException("未指定用户的角色");
        }
        String tempSql = "select * from T_USER  where LOGIN_NAME = '" + user.getLoginName() +"'";
        Object existUser = baseDao.getObjectByNativeSql2(tempSql);
        if (existUser !=null){
            throw new BusinessException("该登录名已经被使用，请重试");
        }
        Integer userId = (Integer) save(user);
        List<String> stringList = new ArrayList<String>();
        for (Integer id : roleIds) {
            String sql = "insert into T_USER_ROLE (FK_ROLE_ID,FK_USER_ID) values(" + id + "," + userId +")";
            stringList.add(sql);
        }
        batchExecuteNativeSql(stringList);
    }
}
