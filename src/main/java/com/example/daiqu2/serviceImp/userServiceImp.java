package com.example.daiqu2.serviceImp;

import com.example.daiqu2.data.mibaoData;
import com.example.daiqu2.data.userData;
import com.example.daiqu2.entity.mibaoTable;
import com.example.daiqu2.entity.userTable;
import com.example.daiqu2.repository.mibaoRepository;
import com.example.daiqu2.repository.userRepository;
import com.example.daiqu2.service.userService;
import com.example.daiqu2.tool.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class userServiceImp implements userService {
    @Autowired
    userRepository uRepository;
    @Autowired
    mibaoRepository mbRepository;

    //插入新的用户数据（注册使用）
    @Override
    public int insertUser(userData user) {
        userTable uTable = new userTable();
        if (userExit(user)) {
            return 0;
        }
        uTable.setIdentity(user.getIdentity());
        uTable.setName(user.getName());
        uTable.setPhone(user.getPhone());
        uTable.setIntroduce(user.getIntroduce());
        uTable.setPwd(user.getPwd());
        uTable.setSex(user.getSex());
        uRepository.save(uTable);
        return 1;
    }

    //判断用户是否已经存在
    @Override
    public boolean userExit(userData user) {
        userTable uTable = uRepository.findByPhone(user.getPhone());

        if (uTable == null) {
            return false;
        }
        return true;
    }

    //删除用户数据
    public boolean deleteUser(userData user) {
        if (!userExit(user)) {
            System.out.println("用户不存在");
            return false;
        }
        uRepository.deleteByPhone(user.getPhone());
        return true;
    }

    //用户登录（4种状态：登录成功111，密码错误110，用户不存在000，其他报错101）
    public String loginUser(userData user) {
        try {
            if (!userExit(user)) {
                //System.out.println("用户不存在");
                return "000";
            }
            userTable uTable = uRepository.findByPhoneAndPwd(user.getPhone(), user.getPwd());
            if (uTable == null) {
                // System.out.println("密码错误");
                return "110";
            } else {
                //System.out.println("登陆成功");
                return "111";
            }
        } catch (Exception e) {
            //System.out.println("其他错误");
            return "101";
        }
    }

    //显示解密后的信息
    public userTable viewData(userData uData) {
        userTable uTable = uRepository.findByPhoneAndPwd(AES.encrypt(uData.getPhone())
                , AES.encrypt(uData.getPwd()));
        return uTable;
    }

    //判断密保是否存在，用户不存在返回000.没有密保返回200，有密保1没有密保2返回220，有密保2没有密保1返回202，存在密保返回222
    @Override
    public String mibaoExit(mibaoData mbData) {
        userData uData = new userData();
        uData.setPhone(mbData.getPhone());
        if (!userExit(uData)) {
            return "000";
        } else {
            mibaoTable mbTable = mbRepository.findByPhone(mbData.getPhone());
            String mibao1 = mbTable.getMibao1();
            String mibao2 = mbTable.getMibao2();
            if (mibao1 == null || mibao1.length() == 0) {
                if (mibao2 == null || mibao2.length() == 0) {
                    return "200";
                } else {
                    return "202";
                }
            } else {
                if (mibao2 == null || mibao2.length() == 0) {
                    return "220";
                } else {
                    return "222";
                }
            }

        }
    }

    @Override
    public String insertMibao(mibaoData mbData) {
        mibaoTable mbTable = new mibaoTable();
        try {
            mbTable.setPhone(mbData.getPhone());
            mbTable.setMibao1(mbData.getMibao1());
            mbTable.setAnswer1(mbData.getAnswer1());
            mbTable.setMibao2(mbData.getMibao2());
            mbTable.setAnswer2(mbData.getAnswer2());
            mbRepository.save(mbTable);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    @Override
    public String mibaoQuestion(mibaoData mbData) {
        String state = mibaoExit(mbData);
        String phone = mbData.getPhone();
        mibaoTable mbTable = mbRepository.findByPhone(phone);
        if (state.equals("220")) {
            return mbTable.getMibao1();
        } else if (state.equals("202")) {
            return mbTable.getMibao2();
        } else {
            return mbTable.getMibao1() + "/" + mbTable.getMibao2();
        }
    }

    //返回fg_e说明更新操作出问题（数据库部分），fg_1说明更新成功，fg_0说明密保答案错误。(存在密保)
    @Override
    public String updateForgetPwd(mibaoData mbData) {
        String phone = mbData.getPhone();
        String newPwd = mbData.getPwd();
        mibaoTable mbTable = mbRepository.findByPhone(phone);
        if (mbData.getAnswer1().equals(mbTable.getAnswer1())
                || mbData.getAnswer2().equals(mbTable.getAnswer2())) {
            try {
                uRepository.updatePwdByPhone(phone, newPwd);
            } catch (Exception e) {
                return "fg_e";
            }
            return "fg_1";
        } else {
            return "fg_0";
        }
    }

    //返回fg_e说明更新操作出问题（数据库部分），fg_1说明更新成功，fg_0说明密保答案错误。(不存在密保)
    @Override
    public String updateForgetPwd(userData uData) {
        String phone = uData.getPhone();
        String newPwd = uData.getPwd();
        try {
            uRepository.updatePwdByPhone(phone, newPwd);
        } catch (Exception e) {
            e.printStackTrace();
            return "fg_e";
        }
        return "fg_1";

    }
}
