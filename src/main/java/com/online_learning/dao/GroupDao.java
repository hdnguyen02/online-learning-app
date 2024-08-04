package com.online_learning.dao;

import com.online_learning.entity.Group;
import com.online_learning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupDao extends JpaRepository<Group, Long> {
    List<Group> findByOwner(User user);

    @Query("SELECT g FROM Group g WHERE CONCAT(g.name, ' ', g.description) LIKE %?1%")
    List<Group> searchGlobal(String searchTerm);
    @Query("SELECT g FROM Group g WHERE g.isPublic = true")
    List<Group> getGlobal();

}
