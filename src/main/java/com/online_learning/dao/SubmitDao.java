package com.online_learning.dao;

import com.online_learning.entity.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitDao extends JpaRepository<Submit, Long> {
}
