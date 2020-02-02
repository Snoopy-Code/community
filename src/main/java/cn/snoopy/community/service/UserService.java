package cn.snoopy.community.service;

import cn.snoopy.community.mapper.UserMapper;
import cn.snoopy.community.model.User;
import cn.snoopy.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void CreateOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountidEqualTo(user.getAccountid());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0){
        //插入
          user.setGmtcreate(System.currentTimeMillis());
          user.setGmtmodified(user.getGmtcreate());
          userMapper.insert(user);
      }else {
          //更新
          User dbUser = users.get(0);
          User updateUser = new User();
          updateUser.setAvatarurl(user.getAvatarurl());
          updateUser.setGmtmodified(System.currentTimeMillis());
          updateUser.setName(user.getName());
          updateUser.setToken(user.getToken());
          UserExample example = new UserExample();
          example.createCriteria().andIdEqualTo(dbUser.getId());
          userMapper.updateByExampleSelective(updateUser,example);
//          userMapper.update(dbUser);
      }
    }
}
