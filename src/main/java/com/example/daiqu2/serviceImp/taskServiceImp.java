package com.example.daiqu2.serviceImp;

import com.example.daiqu2.data.taskData;
import com.example.daiqu2.data.taskDataWithName;
import com.example.daiqu2.entity.taskTable;
import com.example.daiqu2.repository.taskRepository;
import com.example.daiqu2.repository.userRepository;
import com.example.daiqu2.service.taskService;
import com.example.daiqu2.tool.codeBuilder;
import com.example.daiqu2.tool.postPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class taskServiceImp implements taskService {

    @Autowired
    private taskRepository tkRepository;
    @Autowired
    private userRepository uRepository;

    //创建任务：01：图片上传异常；00：数据库存储错误；11：成功。
    @Override
    public String createTask(taskData data, MultipartFile file) {
        taskTable tkTable = new taskTable();
        String pic_name = "";
        postPicture post = new postPicture();
        Date d = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf2.format(d);
        if (file != null) {
            //图片上传异常
            if (!post.upLoad(file)) {
                return "01";
            } else {
                pic_name = post.file_name;
            }
        }
        try {
            //自动生成任务码并进行存在校验
            String taskCode = codeBuilder.getTaskCode(6);
            while (tkRepository.findByTaskCode(taskCode) != null) {
                taskCode = codeBuilder.getTaskCode(6);
            }
            tkTable.setTaskCode(taskCode);
            tkTable.setAccepterPhone("null");
            tkTable.setGetPlace(data.getGetPlace());
            tkTable.setInfomation(data.getInfomation());
            tkTable.setMoney(data.getMoney());
            tkTable.setNeedTime(data.getNeedTime());
            tkTable.setPostPlace(data.getPostPlace());
            tkTable.setPublisherPhone(data.getPublisherPhone());
            tkTable.setPic(pic_name);
            tkTable.setState("00");
            tkTable.setTitle(data.getTitle());
            tkTable.setType(data.getType());
            tkTable.setTime(time);
            tkTable.setTime2("null");
            tkRepository.save(tkTable);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return "00";
        }
        return "11";
    }

    @Override
    public List<taskTable> findTaskByPublisherPhone(String phone) {
        List<taskTable> list = tkRepository.findAllByPublisherPhone(phone);
        if (list == null || list.size() == 0) {
            list.add(new taskTable());
            return list;
        } else {
            Collections.reverse(list);
            return list;
        }
    }

    @Override
    public List<taskDataWithName> findTaskWithNameByPublisherPhone(String phone) {
        List<taskTable> list = tkRepository.findAllByPublisherPhone(phone);
        List<taskDataWithName> list1 = new ArrayList<>();
        if (list == null || list.size() == 0) {
            list1.add(new taskDataWithName());
            return list1;
        } else {
            for (com.example.daiqu2.entity.taskTable taskTable : list) {
                taskDataWithName taskName = new taskDataWithName();
                taskName.setId(taskTable.getId());
                taskName.setAccepterPhone(taskTable.getAccepterPhone());
                taskName.setGetPlace(taskTable.getGetPlace());
                taskName.setInfomation(taskTable.getInfomation());
                taskName.setMoney(taskTable.getMoney());
                taskName.setNeedTime(taskTable.getNeedTime());
                taskName.setPic(taskTable.getPic());
                taskName.setPostPlace(taskTable.getPostPlace());
                taskName.setState(taskTable.getState());
                taskName.setTaskCode(taskTable.getTaskCode());
                taskName.setPublisherPhone(taskTable.getPublisherPhone());
                taskName.setTime(taskTable.getTime());
                taskName.setTime2(taskTable.getTime2());
                taskName.setTitle(taskTable.getTitle());
                taskName.setType(taskTable.getType());
                taskName.setPublisherName(uRepository.findByPhone(taskTable.getPublisherPhone()).getName());
                if (taskTable.getAccepterPhone().equals("null")) {
                    taskName.setAccepterName("null");
                } else {
                    taskName.setAccepterName(uRepository.findByPhone(taskTable.getAccepterPhone()).getName());
                }
                list1.add(taskName);
            }
        }
        Collections.reverse(list1);
        return list1;
    }

    @Override
    public String findPicByCode(String code) {
        String pic_name = tkRepository.findByTaskCode(code).getPic();
        if (pic_name.equals("")) {
            return "00";
        } else {
            return pic_name;
        }
    }

    @Override
    public String updateTaskByCode(taskData data, MultipartFile file) {
        taskTable table = new taskTable();
        String pic_name ="";
        postPicture post = new postPicture();
        table.setId(Integer.valueOf(data.getId()));
        table.setTitle(data.getTitle());
        table.setNeedTime(data.getNeedTime());
        table.setMoney(data.getMoney());
        table.setGetPlace(data.getGetPlace());
        table.setPostPlace(data.getPostPlace());
        table.setInfomation(data.getInfomation());
        table.setTaskCode(data.getTaskCode());
        table.setPublisherPhone(data.getPublisherPhone());
        table.setAccepterPhone(data.getAccepterPhone());
        table.setTime(data.getTime());
        table.setTime2(data.getTime2());
        table.setType(data.getType());
        table.setState(data.getState());
        if (file != null) {
            //图片上传
            if (post.upLoad(file)) {
                pic_name = post.file_name;
            }
        }else{
            if(!data.getPic().equals("")){
                pic_name = data.getPic();
            }
        }
        table.setPic(pic_name);
        tkRepository.save(table);
        return "11";
    }

    @Override
    public String deleteTaskByCode(taskData data) {
        tkRepository.deleteByTaskCode(data.getTaskCode());
        return "1";
    }

    @Override
    public String updateStateByCode(taskData data) {
        tkRepository.updateStateByCode(data.getTaskCode(),data.getState());
        return "1";
    }

    @Override
    public String updateStateAndNameByCode(taskData data) {
        //String state = "01";
        Date d = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String js_time = sdf2.format(d);
        tkRepository.updateStateAndNameByCode(data.getTaskCode(),data.getState(),js_time,data.getAccepterPhone());
        return "1";
    }

    public List<taskDataWithName> findTaskByState(taskData data){
        List<taskTable> list = tkRepository.findAllByState(data.getState());
        List<taskDataWithName> list1 = new ArrayList<>();
        if(list==null||list.size()==0){
            list1.add(new taskDataWithName());
            return list1;
        }else{
            for (taskTable taskTable:list){
                taskDataWithName taskName = new taskDataWithName();
                taskName.setId(taskTable.getId());
                taskName.setAccepterPhone(taskTable.getAccepterPhone());
                taskName.setGetPlace(taskTable.getGetPlace());
                taskName.setInfomation(taskTable.getInfomation());
                taskName.setMoney(taskTable.getMoney());
                taskName.setNeedTime(taskTable.getNeedTime());
                taskName.setPic(taskTable.getPic());
                taskName.setPostPlace(taskTable.getPostPlace());
                taskName.setState(taskTable.getState());
                taskName.setTaskCode(taskTable.getTaskCode());
                taskName.setPublisherPhone(taskTable.getPublisherPhone());
                taskName.setTime(taskTable.getTime());
                taskName.setTime2(taskTable.getTime2());
                taskName.setTitle(taskTable.getTitle());
                taskName.setType(taskTable.getType());
                taskName.setPublisherName(uRepository.findByPhone(taskTable.getPublisherPhone()).getName());
                if (taskTable.getAccepterPhone().equals("null")) {
                    taskName.setAccepterName("null");
                } else {
                    taskName.setAccepterName(uRepository.findByPhone(taskTable.getAccepterPhone()).getName());
                }
                list1.add(taskName);
            }
            Collections.reverse(list1);
            return list1;
        }
    }
    public List<taskDataWithName> findTaskByStateAndAceepter(taskData data){
        List<taskTable> list = tkRepository.findAllByStateAndAccepterPhone(data.getState(),data.getAccepterPhone());
        List<taskDataWithName> list1 = new ArrayList<>();
        if(list==null||list.size()==0){
            list1.add(new taskDataWithName());
            return list1;
        }else{
            for (taskTable taskTable:list){
                taskDataWithName taskName = new taskDataWithName();
                taskName.setId(taskTable.getId());
                taskName.setAccepterPhone(taskTable.getAccepterPhone());
                taskName.setGetPlace(taskTable.getGetPlace());
                taskName.setInfomation(taskTable.getInfomation());
                taskName.setMoney(taskTable.getMoney());
                taskName.setNeedTime(taskTable.getNeedTime());
                taskName.setPic(taskTable.getPic());
                taskName.setPostPlace(taskTable.getPostPlace());
                taskName.setState(taskTable.getState());
                taskName.setTaskCode(taskTable.getTaskCode());
                taskName.setPublisherPhone(taskTable.getPublisherPhone());
                taskName.setTime(taskTable.getTime());
                taskName.setTime2(taskTable.getTime2());
                taskName.setTitle(taskTable.getTitle());
                taskName.setType(taskTable.getType());
                taskName.setPublisherName(uRepository.findByPhone(taskTable.getPublisherPhone()).getName());
                if (taskTable.getAccepterPhone().equals("null")) {
                    taskName.setAccepterName("null");
                } else {
                    taskName.setAccepterName(uRepository.findByPhone(taskTable.getAccepterPhone()).getName());
                }
                list1.add(taskName);
            }
            Collections.reverse(list1);
            return list1;
        }
    }
}
