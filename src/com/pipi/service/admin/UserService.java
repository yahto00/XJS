package com.pipi.service.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.admin.User;
import com.pipi.util.DSUtil;
import com.pipi.util.Ufn;
import org.springframework.stereotype.Service;

import com.pipi.dao.admin.IUserDao;
import com.pipi.service.BaseService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yahto on 07/05/2017.
 */
@Service
public class UserService extends BaseService implements IUserService {
    @Resource
    IUserDao userDao;

    @Override
    public void queryUserForLogin(String loginName, String password, HttpServletRequest request) {
        if (loginName == null || loginName.length() == 0){
            throw new BusinessException("用户名不能为空");
        }
        if (password == null || password.length() == 0){
            throw new BusinessException("密码不能为空");
        }
        String hql = "from User u where u.isDelete=0 and loginName='" + loginName + "'";
        List<User> list = (List<User>) baseDao.getObjectListByNativeHql(hql);
        if (list == null || list.size() == 0) {
            throw new BusinessException("该用户不存在");
        } else {
            if (!password.equals(list.get(0).getPassword())) {
                throw new BusinessException("密码错误，请重试");
            } else {
                User user = list.get(0);
//                List<Integer> roleIdList = (List<Integer>) queryListByNavtiveSql("select FK_ROLE_ID from t_user_role where FK_USER_ID=" + user.getId());
//                if (roleIdList == null || roleIdList.size() == 0)
//                    throw new BusinessException("该用户未分配角色，暂时不能访问本系统！");
//                list.get(0).setRoles(new HashSet<Integer>(roleIdList));
//				Set<Integer> roles = new HashSet<Integer>();
//				roles.add(3);
//				user.setRoles(roles);
//
//				List<Integer> privList = (List<Integer>) queryListByNavtiveSql("select PRIV_ID from t_role_priv where FK_ROLE_ID in (" + Ufn.join(roles.toArray()) + ")");
//				Set<Integer> privs = new HashSet<Integer>(privList);
//				privs.add(0);
//				user.setPrivs(privs);
                request.getSession().setAttribute(SystemConstant.CURRENT_USER, user);
            }
        }
    }

    @Override
    public void deleteUserByIds(Integer[] ids) {
        if (ids == null || ids.length == 0){
            throw new BusinessException("未指定要删除的用户");
        }
        String hql = "update User u set u.isDelete=1 where u.id in (" + DSUtil.parseIntegerArr(ids) + ")";
        updateByHql(hql);
    }
}
