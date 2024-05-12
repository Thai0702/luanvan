package com.luanvan.luanvan.groupService.controller;



import com.luanvan.luanvan.groupService.model.Group;
import com.luanvan.luanvan.groupService.service.GroupService;
import com.luanvan.luanvan.groupService.wrapper.GroupInfo;
import com.luanvan.luanvan.groupService.wrapper.GroupMemberInfo;
import com.luanvan.luanvan.groupService.wrapper.MemberInfo;
import com.luanvan.luanvan.securityService.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
public class GroupController {
    GroupService groupService;
    AuthenticationService authenticationService;

    public GroupController(GroupService groupService, AuthenticationService authenticationService) {
        this.groupService = groupService;
        this.authenticationService = authenticationService;
    }

    //tạo nhóm bằng danh sách
    @PostMapping("/api/class/create-groups")
    public ResponseEntity<String>createGroupFromList(@RequestBody List<GroupInfo> groupInfoList){
        return groupService.createGroupForClass(groupInfoList);
        //return new ResponseEntity<String>(groupService.createGroupForClass(groupInfoList),HttpStatus.OK);
    }
    //thêm thành viên theo danh sách
    @PostMapping("/api/class/add-member")
    public ResponseEntity<String>addMemberIntoGroup(@RequestBody List<MemberInfo> memberList){
        return groupService.addGroupMemberFromList(memberList);
    }
    //cập nhật leader
    //tam thoi tkhong dung dc
    @PutMapping("/api/class/{classId}/group/{groupId}/set-leader/{userId}")
    public ResponseEntity<String>updateGroupLeader(@PathVariable Integer classId,@PathVariable Integer groupId,@PathVariable Integer userId){
        return groupService.updateGroupLeader(classId,groupId,userId);
    }
    //tao mot group
    @PostMapping("/api/class/create-a-group")
    public ResponseEntity<String>createSingleGroup(@RequestBody GroupInfo groupInfo/*,@RequestHeader(value = "Authorization")String requestToken*/){
        //groupInfo.setLeaderId(authenticationService.getUserIdFromToken(requestToken));
        return groupService.createSingleGroup(groupInfo);
    }
    //Them 1 thanh vien vào nhóm
    @PostMapping("/api/class/{classId}/group/{groupId}/add-member/{accountId}")
    public ResponseEntity<String>addOneMemberIntoGroup(@PathVariable Integer classId,@PathVariable Integer groupId,@PathVariable Integer accountId){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //lay danh sach lop da join
    @GetMapping("/api/user/{userId}/joined-class")
    public ResponseEntity<?>getJoinedClassOfUser(@PathVariable Integer userId){
        return groupService.findJoinedClassById(userId);
    }

    //Lay danh sach nhóm của lớp do
    @GetMapping("/api/class/{classId}/group-list")
    public ResponseEntity<List<Group>>getGroupListOfClass(@PathVariable Integer classId){
        return groupService.findGroupListByClassId(classId);
    }

    // xoa sinh vien ra khoi liststudent
    @DeleteMapping("/api/class/student-list/{classId}/{studentId}")
    public ResponseEntity<String> deleteStudentByClassIdAndStudentId(@PathVariable int classId, @PathVariable int studentId) {
        try {
            groupService.deleteSVByClassIdAndStudentId(classId, studentId);
            return ResponseEntity.ok("Đã xóa sinh viên có classId là " + classId + " và studentId là " + studentId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình xóa");
        }
    }

    @GetMapping("/api-test/random/{classId}")
    public ResponseEntity<?>testRandomGroup(@PathVariable Integer classId){
        groupService.assignStudentsToRandomGroups(classId,4,4);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // xoa nhom
    @DeleteMapping("/api/group/{groupId}")
    public ResponseEntity<String> deleteGroupById(@PathVariable Integer groupId) {
        try {
            groupService.deleteGroupById(groupId);
            return ResponseEntity.ok("Đã xóa nhóm có id là " + groupId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình xóa nhóm");
        }
    }
    //sua nhom
    @PutMapping("/api/group/{groupId}")
    public ResponseEntity<String> updateGroup(@PathVariable Integer id, @RequestBody Group group) {
        try {
            Group gr = groupService.updateGroup(id, group);
            return ResponseEntity.ok("Đã sửa nhóm có id là " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình sửa");
        }
    }
    //cho sinh vien join group
    @PostMapping("/api/class/{classId}/group/{groupId}/join-group")
    public ResponseEntity<?>studentJoinGroup(@PathVariable Integer classId,@PathVariable Integer groupId,@RequestHeader(value = "Authorization")String token){
        int accountId=authenticationService.getUserIdFromToken(token);
        if(accountId!=0){
            return groupService.studentJoinGroup(accountId,classId,groupId);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //lay danh sach lop kem ten nhom
    @GetMapping("/api/class/{classId}/student-group-sorted")
    public ResponseEntity<?>getSortedByGroupList(@PathVariable Integer classId){
        return new ResponseEntity<List<GroupMemberInfo>>(groupService.findSortedByGroup(classId),HttpStatus.OK);
    }
}
