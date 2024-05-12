package com.luanvan.luanvan.groupService.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_list")
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;
    @Column(name = "leader_id")
    private int leaderId;
    @Column(name = "class_id")
    private int classId;
    @Column(name = "group_name")
    private String groupName;

    public Group(int leaderId, int classId, String groupName) {
        this.leaderId = leaderId;
        this.classId = classId;
        this.groupName = groupName;
    }
}
