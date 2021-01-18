package com.example.daiqu2.repository;

import com.example.daiqu2.entity.mibaoTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface mibaoRepository extends JpaRepository<mibaoTable,Integer> {
    //更具手机号进行密保查询
    mibaoTable findByPhone(String phone);
}
