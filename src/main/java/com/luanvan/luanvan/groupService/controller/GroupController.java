package com.luanvan.luanvan.groupService.controller;



import com.luanvan.luanvan.accountservice.model.Account;
import com.luanvan.luanvan.groupService.model.Group;
import com.luanvan.luanvan.groupService.model.GroupMember;
import com.luanvan.luanvan.groupService.model.Student;
import com.luanvan.luanvan.groupService.repository.GroupMemberRepository;
import com.luanvan.luanvan.groupService.service.GroupService;
import com.luanvan.luanvan.groupService.wrapper.GroupInfo;
import com.luanvan.luanvan.groupService.wrapper.GroupMemberInfo;
import com.luanvan.luanvan.groupService.wrapper.MemberInfo;
import com.luanvan.luanvan.securityService.model.RegisterRequest;
import com.luanvan.luanvan.securityService.service.AuthenticationService;
import com.luanvan.luanvan.subjectclassservice.model.SubjectClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
public class GroupController {
    private final GroupMemberRepository groupMemberRepository;
    GroupService groupService;
    AuthenticationService authenticationService;

    public GroupController(GroupService groupService, AuthenticationService authenticationService, GroupMemberRepository groupMemberRepository) {
        this.groupService = groupService;
        this.authenticationService = authenticationService;
        this.groupMemberRepository = groupMemberRepository;
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
//    //Them 1 thanh vien vào nhóm
//    @PostMapping("/api/class/group/add-member/{classId}/{groupId}/{accountId}")
//    public ResponseEntity<String>addOneMemberIntoGroup(@PathVariable Integer classId,@PathVariable Integer groupId,@PathVariable Integer accountId){
//        return new ResponseEntity<>(HttpStatus.OK );
//    }
@PostMapping("/api/class/group/add-member/{classId}/{groupId}/{accountId}")
public ResponseEntity<String> addOneMemberIntoGroup(@PathVariable Integer classId,
                                                    @PathVariable Integer groupId,
                                                    @PathVariable Integer accountId) {
    try {
        // Kiểm tra xem thành viên đã tồn tại trong nhóm chưa
        if (groupMemberRepository.existsByGroupIdAndMemberId(groupId, accountId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Member already exists in the group.");
        }
        // Tạo một đối tượng GroupMember mới
        GroupMember groupMember = new GroupMember(groupId, accountId);

        // Lưu đối tượng GroupMember vào cơ sở dữ liệu
        groupMemberRepository.save(groupMember);

        // Trả về mã trạng thái HTTP 200 OK và thông báo thành công
        return ResponseEntity.ok("Member added successfully to the group.");
    } catch (Exception e) {
        // Nếu có lỗi xảy ra, trả về mã trạng thái HTTP 500 Internal Server Error và thông báo lỗi
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add member to the group.");
    }
}
    //lay danh sach lop da join
    @GetMapping("/api/api-gv/user/{userId}/joined-class")
    public ResponseEntity<?>getJoinedClassOfUser(@PathVariable Integer userId){
        return groupService.findJoinedClassById(userId);
    }

    //Lay danh sach nhóm của lớp do
    @GetMapping("/api-gv/classId/group-list/{classId}")
    public ResponseEntity<List<Group>>getGroupListOfClass(@PathVariable Integer classId){
        return groupService.findGroupListByClassId(classId);
    }
    // xoa sinh vien ra khoi liststudent
    @DeleteMapping("/api-gv/class/delete/student-list/{classId}/{studentId}")
    public ResponseEntity<String> deleteStudentByClassIdAndStudentId(@PathVariable int classId, @PathVariable int studentId) {
        try {
            groupService.deleteSVByClassIdAndStudentId(classId, studentId);
            return ResponseEntity.ok("Đã xóa sinh viên có classId là " + classId + " và studentId là " + studentId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình xóa");
        }
    }
    // xóa sinh vien khoi group 
    @DeleteMapping("/{groupId}/members/{memberId}")
    public void removeMemberFromGroup(@PathVariable int groupId, @PathVariable int memberId) {
        groupService.removeMemberFromGroup(groupId, memberId);
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
//    //cho sinh vien join group
//    @PostMapping("/api/class/{classId}/group/{groupId}/join-group")
//    public ResponseEntity<?>studentJoinGroup(@PathVariable Integer classId,@PathVariable Integer groupId,@RequestHeader(value = "Authorization")String token){
//        int accountId=authenticationService.getUserIdFromToken(token);
//        if(accountId!=0){
//            return groupService.studentJoinGroup(accountId,classId,groupId);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
@PostMapping("/api/class/{classId}/group/{groupId}/join-group")
public ResponseEntity<?> studentJoinGroup(@PathVariable Integer classId, @PathVariable Integer groupId, @RequestHeader(value = "Authorization") String token) {
    int accountId = authenticationService.getUserIdFromToken(token);
    if (accountId != 0) {
        // Kiểm tra xem thành viên đã có trong nhóm hay chưa
        if (groupService.isMemberAlreadyInGroup(accountId, groupId)) {
            // Nếu thành viên đã có trong nhóm, trả về lỗi
            return new ResponseEntity<>("Thành viên đã tồn tại trong nhóm", HttpStatus.BAD_REQUEST);
        }

        // Nếu thành viên chưa tồn tại trong nhóm, thêm vào nhóm
        return groupService.studentJoinGroup(accountId, classId, groupId);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
    //lay danh sach sinh vien lop trong group kem ten nhom
    @GetMapping("/api/class/student-group-sorted/{classId}")
    public ResponseEntity<?>getSortedByGroupList(@PathVariable Integer classId){

        return new ResponseEntity<List<GroupMemberInfo>>(groupService.findSortedByGroup(classId),HttpStatus.OK);
    }
    //join sinh vien vao class
    @PostMapping("/api/join-class/form")
    public ResponseEntity<String> joinClassByInviteCode(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody SubjectClass inviteCode) {
        int accountId = authenticationService.getUserIdFromToken(token);
        return (ResponseEntity<String>) groupService.joinClassByInviteCode(inviteCode.getInviteCode(), accountId);
    }
    @GetMapping("/api/class/{classId}/group/{groupId}/students")
    public ResponseEntity<?> getStudentsByGroupInClass(@PathVariable Integer classId, @PathVariable Integer groupId) {
        List<GroupMemberInfo> students = groupService.findStudentsByGroupInClass(classId, groupId);
        if (students.isEmpty()) {
            return new ResponseEntity<>("No students found for this group in the specified class", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<GroupMemberInfo>>(students, HttpStatus.OK);
    }

    //  delete member in group

}
