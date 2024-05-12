package com.luanvan.luanvan.groupService.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo {
    private int classId;
    private String groupName;
    private int leaderId;
}
