package com.online_learning.dao;

import com.online_learning.entity.CommonDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommonDeckDao extends JpaRepository<CommonDeck, Long> {

    // lấy dựa vào idGroup
    @Query("SELECT c FROM CommonDeck c WHERE c.group.id = ?1")
    List<CommonDeck> getCommonDeckByIdGroup(long idGroup);
}
