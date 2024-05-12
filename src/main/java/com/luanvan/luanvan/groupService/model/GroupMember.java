package com.luanvan.luanvan.groupService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_member")
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_index")
    private int memberIndex;
    @Column(name="group_id")
    private int groupId;
    @Column(name = "member_id")
    private int memberId;

    public GroupMember(int groupId, int memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }
}
