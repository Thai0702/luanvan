package com.luanvan.luanvan.groupService.repository;

import com.luanvan.luanvan.groupService.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    List<Group> findAllByClassId(Integer classId);
<<<<<<< HEAD
    List<Group> findGroupByGroupName(String groupName);
=======

    Group findGroupByGroupName(String groupName);

    List<Group> findGroupByGroupName(String groupName);

>>>>>>> fa387723dd136da103348c7ada2dda5accc30e1e
    Optional<Group> findByGroupIdAndClassId(Integer groupId, Integer classId);  // New method to find group by groupId and classId
    boolean existsByGroupNameAndClassId(String groupName, Integer classId);
}
