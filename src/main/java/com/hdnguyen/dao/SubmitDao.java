package com.hdnguyen.dao;

import com.hdnguyen.entity.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitDao extends JpaRepository<Submit, Long> {
}
