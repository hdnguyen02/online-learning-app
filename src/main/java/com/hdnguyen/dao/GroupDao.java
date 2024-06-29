package com.hdnguyen.dao;

import com.hdnguyen.entity.Group;
import com.hdnguyen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupDao extends JpaRepository<Group, Long> {
    List<Group> findByOwner(User user);

    @Query("SELECT g FROM Group g WHERE CONCAT(g.name, ' ', g.description) LIKE %?1%")
    List<Group> searchGlobal(String searchTerm);

}
