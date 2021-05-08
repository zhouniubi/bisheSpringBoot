package com.example.daiqu2.repository;

import com.example.daiqu2.entity.mibaoTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface mibaoRepository extends JpaRepository<mibaoTable, Integer> {
    //根据手机号进行密保查询
    mibaoTable findByPhone(String phone);

    //更新密保
    @Transactional
    @Modifying
    @Query(value="update mibao_table mb set mb.mibao1 =?1,mb.answer1=?2," +
            "mb.mibao2=?3,mb.answer2=?4 where mb.phone=?5",nativeQuery = true)
    void updateMibao(String qs1, String as1, String qs2, String as2, String phone);
}
