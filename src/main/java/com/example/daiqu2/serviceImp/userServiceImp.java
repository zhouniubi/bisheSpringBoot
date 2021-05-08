package com.example.daiqu2.serviceImp;

import com.example.daiqu2.data.mibaoData;
import com.example.daiqu2.data.userData;
import com.example.daiqu2.entity.mibaoTable;
import com.example.daiqu2.entity.userTable;
import com.example.daiqu2.repository.mibaoRepository;
import com.example.daiqu2.repository.userRepository;
import com.example.daiqu2.service.userService;
import com.example.daiqu2.tool.AES;
import com.example.daiqu2.tool.returnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class userServiceImp implements userService {
    @Autowired
    userRepository uRepository;
    @Autowired
    mibaoRepository mbRepository;

    //插入新的用户数据（注册使用）
    @Override
    public String insertUser(userData user) {
        userTable uTable = new userTable();
        if (userExit(user)) {
            return returnState.insert_fail;
        }
        uTable.setIdentity(user.getIdentity());
        uTable.setName(user.getName());
        uTable.setPhone(user.getPhone());
        uTable.setIntroduce("null");
        uTable.setPwd(user.getPwd());
        uTable.setSex(user.getSex());
        //默认初始头像为“000”
        uTable.setPic("000");
        uRepository.save(uTable);
        return returnState.insert_success;
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
                return returnState.user_not_exit;
            }
            userTable uTable = uRepository.findByPhoneAndPwd(user.getPhone(), user.getPwd());
            if (uTable == null) {
                return returnState.error_pwd;
            } else {
                return returnState.load_success;
            }
        } catch (Exception e) {
            return returnState.error_special;
        }
    }
    public String loadUser(userData user){
        switch(loginUser(user)){
            case returnState.load_success:
                return returnState.load_success;//"登录成功"
            case returnState.error_pwd:
                return returnState.error_pwd;//"密码错误"
            case returnState.user_not_exit:
                return returnState.user_not_exit;//"用户不存在";
            default:
                return returnState.error_special;
        }
    }

    @Override
    public List<String> mibaoExitList(mibaoData mbData) {
        List<String> list = new ArrayList<>();
        switch (mibaoExit(mbData)) {
            case returnState.user_not_exit:
                list.add(returnState.user_not_exit);
                list.add("##");
                return list;//用户不存在
            case returnState.mibao_not_exit:
                list.add(returnState.mibao_not_exit);
                list.add("#");
                return list;//密保不存在
            case returnState.mibao2_not_exit:
                list.add(returnState.mibao2_not_exit);
                list.add(mibaoQuestion(mbData));
                return list;//密保2不存在
            case returnState.mibao1_not_exit:
                list.add(returnState.mibao1_not_exit);
                list.add(mibaoQuestion(mbData));
                return list;//密保1不存在
            default:
                list.add(returnState.mibao_exit);
                list.add(mibaoQuestion(mbData));
                return list;//密保存在
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
            return returnState.user_not_exit;
        } else {
            mibaoTable mbTable = mbRepository.findByPhone(mbData.getPhone());
            if (null == mbTable) {
                return returnState.mibao_not_exit;
            }
            String mibao1 = mbTable.getMibao1();
            String mibao2 = mbTable.getMibao2();
            if (null == mibao1 || mibao1.length() == 0) {
                if (null == mibao2 || mibao2.length() == 0) {
                    return returnState.mibao_not_exit;
                } else {
                    return returnState.mibao1_not_exit;
                }
            } else {
                if (null == mibao2 || mibao2.length() == 0) {
                    return returnState.mibao2_not_exit;
                } else {
                    return returnState.mibao_exit;
                }
            }

        }
    }

    @Override
    public String insertMibao(mibaoData mbData) {
        mibaoTable mbTable = new mibaoTable();
        mbTable.setPhone(mbData.getPhone());
        if(mibaoExit(mbData).equals(returnState.mibao_not_exit)){
            if ("".equals(mbTable.getMibao1()) && !"".equals(mbTable.getMibao2())) {
                mbTable.setMibao1(null);
                mbTable.setAnswer1(null);
                mbTable.setMibao2(mbData.getMibao2());
                mbTable.setAnswer2(mbData.getAnswer2());
            } else if (!"".equals(mbTable.getMibao1()) && "".equals(mbTable.getMibao2())) {
                mbTable.setMibao1(mbData.getMibao1());
                mbTable.setAnswer1(mbData.getAnswer1());
                mbTable.setMibao2(null);
                mbTable.setAnswer2(null);
            } else {
                mbTable.setMibao1(mbData.getMibao1());
                mbTable.setAnswer1(mbData.getAnswer1());
                mbTable.setMibao2(mbData.getMibao2());
                mbTable.setAnswer2(mbData.getAnswer2());
            }
            mbRepository.save(mbTable);
        }else{
            if ("".equals(mbTable.getMibao1()) && !"".equals(mbTable.getMibao2())) {
                mbTable.setMibao1(null);
                mbTable.setAnswer1(null);
                mbTable.setMibao2(mbData.getMibao2());
                mbTable.setAnswer2(mbData.getAnswer2());
            } else if (!"".equals(mbTable.getMibao1()) && "".equals(mbTable.getMibao2())) {
                mbTable.setMibao1(mbData.getMibao1());
                mbTable.setAnswer1(mbData.getAnswer1());
                mbTable.setMibao2(null);
                mbTable.setAnswer2(null);
            } else {
                mbTable.setMibao1(mbData.getMibao1());
                mbTable.setAnswer1(mbData.getAnswer1());
                mbTable.setMibao2(mbData.getMibao2());
                mbTable.setAnswer2(mbData.getAnswer2());
            }
            mbRepository.updateMibao(mbTable.getMibao1(),mbTable.getAnswer1(),mbTable.getMibao2(),mbTable.getAnswer2(),mbTable.getPhone());
        }
        return "1";
    }

    @Override
    public String mibaoQuestion(mibaoData mbData) {
        String state = mibaoExit(mbData);
        String phone = mbData.getPhone();
        mibaoTable mbTable = mbRepository.findByPhone(phone);
        if (state.equals(returnState.mibao2_not_exit)) {
            return mbTable.getMibao1();
        } else if (state.equals(returnState.mibao1_not_exit)) {
            return mbTable.getMibao2();
        } else {
            return mbTable.getMibao1() + "/" + mbTable.getMibao2();
        }
    }

    @Override
    public mibaoTable findMibao(mibaoData mibao) {
        String state = mibaoExit(mibao);
        mibaoTable mbTable = new mibaoTable();
        switch (state) {
            case returnState.mibao_not_exit:
                mbTable.setMibao1("null");
                mbTable.setAnswer1("null");
                mbTable.setMibao2("null");
                mbTable.setAnswer2("null");
                return mbTable;
            case returnState.mibao2_not_exit:
                mbTable = mbRepository.findByPhone(mibao.getPhone());
                mbTable.setMibao2("null");
                mbTable.setAnswer2("null");
                return mbTable;
            case returnState.mibao1_not_exit:
                mbTable = mbRepository.findByPhone(mibao.getPhone());
                mbTable.setMibao1("null");
                mbTable.setAnswer1("null");
                return mbTable;
            default:
                return mbRepository.findByPhone(mibao.getPhone());
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

    @Override
    public userTable findUserByPhone(userData user) {

        return uRepository.findByPhone(user.getPhone());
    }

    @Override
    public String updateUser(userData uData) {
        try {
            uRepository.updateUserByPhone(uData.getPhone(), uData.getName(),
                    uData.getSex(), uData.getIdentity(), uData.getPic(), uData.getIntroduce());
        } catch (Exception e) {
            e.printStackTrace();
            return "db_error";
        }
        return "11";
    }

}
