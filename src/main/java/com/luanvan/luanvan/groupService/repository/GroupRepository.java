package com.luanvan.luanvan.groupService.repository;

import com.luanvan.luanvan.groupService.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    List<Group> findAllByClassId(Integer classId);
    Group findGroupByGroupName(String groupName);


}
