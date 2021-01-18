package com.example.daiqu2.repository;

import com.example.daiqu2.entity.userTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface userRepository extends JpaRepository<userTable,Integer> {
    //根据手机号进行用户存在查询
    userTable findByPhone(String phone);
    //删除用户数据
    void deleteByPhone(String phone);
    //用户手机号与密码匹配查询
    userTable findByPhoneAndPwd(String phone,String pwd);
    //根据手机号更新密码操作
  /*  @Transactional
    @Modifying*/
    @Query(value="update userTable u set u.pwd=?2 where u.phone = ?1",nativeQuery = true)
    void updatePwdByPhone(String phone,String pwd);


}
