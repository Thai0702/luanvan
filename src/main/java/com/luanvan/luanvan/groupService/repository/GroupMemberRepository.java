package com.luanvan.luanvan.groupService.repository;

import com.luanvan.luanvan.groupService.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember,Integer> {
    public List<GroupMember>findAllByGroupId(Integer groupId);
}
