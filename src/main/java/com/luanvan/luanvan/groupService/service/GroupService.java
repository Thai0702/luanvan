package com.luanvan.luanvan.groupService.service;



import com.luanvan.luanvan.accountservice.model.Account;
import com.luanvan.luanvan.accountservice.repository.AccountRepository;
import com.luanvan.luanvan.accountservice.service.AccountDetailService;
import com.luanvan.luanvan.accountservice.wrapper.StudentAccountDetail;
import com.luanvan.luanvan.groupService.model.Group;
import com.luanvan.luanvan.groupService.model.GroupMember;
import com.luanvan.luanvan.groupService.model.Student;
import com.luanvan.luanvan.groupService.repository.GroupMemberRepository;
import com.luanvan.luanvan.groupService.repository.GroupRepository;
import com.luanvan.luanvan.groupService.repository.StudentRepository;
import com.luanvan.luanvan.groupService.wrapper.GroupInfo;
import com.luanvan.luanvan.groupService.wrapper.GroupMemberInfo;
import com.luanvan.luanvan.groupService.wrapper.MemberInfo;
import com.luanvan.luanvan.securityService.model.RegisterRequest;
import com.luanvan.luanvan.subjectclassservice.model.SubjectClass;
import com.luanvan.luanvan.subjectclassservice.repository.SubjectClassReponsitory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@Transactional
public class GroupService {
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;
    private StudentRepository studentRepository;
    private SubjectClassReponsitory subjectClassReponsitory;
    private AccountDetailService accountDetailService;


    public GroupService(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, StudentRepository studentRepository, SubjectClassReponsitory subjectClassReponsitory, AccountDetailService accountDetailService) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.studentRepository = studentRepository;
        this.subjectClassReponsitory = subjectClassReponsitory;
        this.accountDetailService = accountDetailService;
    }

    public ResponseEntity<String> createGroupForClass(List<GroupInfo> groupList) {
        for (GroupInfo groupInfo : groupList) {
            Group newGroup = new Group();
            newGroup.setGroupName(groupInfo.getGroupName());
            newGroup.setClassId(groupInfo.getClassId());
            newGroup.setLeaderId(groupInfo.getLeaderId());


            groupRepository.save(newGroup);
        }
        return new ResponseEntity<>("SUCCES", HttpStatus.OK);
    }

    public ResponseEntity<String> addGroupMemberFromList(List<MemberInfo> memberList) {
        for (MemberInfo member : memberList) {
            GroupMember newMember = new GroupMember();
            newMember.setMemberId(member.getAccountId());

            Group groupInfo = groupRepository.findGroupByGroupName(member.getGroupName());
            newMember.setGroupId(groupInfo.getGroupId());


            groupMemberRepository.save(newMember);
        }

        return new ResponseEntity<>("SUCCES", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateGroupLeader(int classId, int groupId, int userId) {
        //groupRepository.updateGroupLeader(userId,classId,groupId);
        return new ResponseEntity<>("SUCCES", HttpStatus.OK);
    }
    // kiem tra trung ten nhom
    public boolean isGroupNameExists(String groupName, Integer classId) {
        return groupRepository.existsByGroupNameAndClassId(groupName,classId);
    }
    public ResponseEntity<String> createSingleGroup(GroupInfo groupInfo) {

        // Kiểm tra xem tên nhóm đã tồn tại hay chưa
        if (isGroupNameExists(groupInfo.getGroupName(),groupInfo.getClassId())) {
            return new ResponseEntity<>("Group name already exists", HttpStatus.BAD_REQUEST);
        }
        Group newGroup = new Group();
        newGroup.setGroupName(groupInfo.getGroupName());
        newGroup.setClassId(groupInfo.getClassId());
        newGroup.setLeaderId(groupInfo.getLeaderId());

        groupRepository.save(newGroup);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    public ResponseEntity<List<Group>> findGroupListByClassId(Integer classId) {
        List<Group> searchResult = new ArrayList<>();
        searchResult = groupRepository.findAllByClassId(classId);
        if (searchResult != null) {
            return new ResponseEntity<>(searchResult, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteSVByClassIdAndStudentId(int classId, int studentId) {
        studentRepository.deleteByClassIdAndStudentId(classId, studentId);
    }
    public void removeMemberFromGroup(int groupId, int memberId) {
        groupMemberRepository.deleteByGroupIdAndMemberId(groupId, memberId);
    }

    public ResponseEntity<String> addMemberIntoGroup(int classId, int groupId, int accountId) {
        GroupMember newMember = new GroupMember(groupId, accountId);
        groupMemberRepository.save(newMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    // kiểm tra join nhóm
    public boolean isMemberAlreadyInGroup(int memberId, int groupId) {
        return groupMemberRepository.existsByGroupIdAndMemberId(groupId, memberId);
    }



    public void assignStudentsToRandomGroups ( int classId, int numberOfGroup, int memberPerGroup){
            List<Student> studentList = studentRepository.getStudentsByClassId(classId);
            for (int i = 1; i <= numberOfGroup; i++) {
                String groupName = "Nhóm " + i;
                Group group = new Group(0, classId, groupName);
                Group newGroup = groupRepository.save(group);
                for (int j = 1; j <= memberPerGroup; j++) {
                    if (studentList.isEmpty()) {
                        break;
                    }
                    int index = randomNumber(studentList.size());
                    Student student = studentList.get(index);
                    GroupMember newMember = new GroupMember(newGroup.getGroupId(), student.getStudentId());
                    groupMemberRepository.save(newMember);
                    studentList.remove(student);
                }
            }
            //return new ResponseEntity<>(HttpStatus.CREATED);
        }


        public static Integer randomNumber ( int max){
            if (max <= 0) {
                return 0;
            }
            Random rand = new Random();
            int randomNumber = rand.nextInt(max);
            return randomNumber;

        }


    public void deleteGroupById(Integer id) {
        groupRepository.deleteById(id);
    }

    public Group updateGroup(Integer groupId, Group group){
        Group gr = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Khong co group voi id:"+ groupId));
        gr.setLeaderId(group.getLeaderId());
        gr.setClassId(group.getClassId());
        gr.setGroupName(group.getGroupName());
        groupRepository.save(gr);
        return gr;
    }

    public ResponseEntity<?>findJoinedClassById(int userId){
        List<Student>joinedList=studentRepository.findByStudentId(userId);
        List<SubjectClass>result=new ArrayList<>();
        for (Student joined:joinedList){
            Optional<SubjectClass> joinedClass =subjectClassReponsitory.findById(joined.getClassId());
            joinedClass.ifPresent(result::add);
        }
        if (result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<SubjectClass>>(result,HttpStatus.OK);
    }
    public ResponseEntity<?> studentJoinGroup(int accountId,int classId,int groupId){
        Optional<SubjectClass> subjectClass=subjectClassReponsitory.findById(classId);
        Optional<Group> group=groupRepository.findById(groupId);
        if(subjectClass.isPresent()&&group.isPresent()){
            List<GroupMember>memberList=groupMemberRepository.findAllByGroupId(group.get().getGroupId());
            if(memberList.size()<subjectClass.get().getMemberPerGroup()){
                GroupMember newMember=new GroupMember(groupId,accountId);
                groupMemberRepository.save(newMember);
                return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
            }
            return new ResponseEntity<String>("GROUP FULL",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    public List<GroupMemberInfo>findSortedByGroup(int classId){
        List<GroupMemberInfo>resultList=new ArrayList<>();
        List<Group>groupList=groupRepository.findAllByClassId(classId);
        for(Group group:groupList){
            List<GroupMember>memberList=groupMemberRepository.findAllByGroupId(group.getGroupId());
            for (GroupMember member:memberList){
                StudentAccountDetail memberDetail=accountDetailService.getStudentAccountDetail(member.getMemberId());
                GroupMemberInfo memberInfo=new GroupMemberInfo(group.getGroupId(),group.getGroupName(),member.getMemberId(), memberDetail.getFullName(), memberDetail.getStudentId());
                resultList.add(memberInfo);
            }
        }
        return resultList;
    }
    // New method to get students by group ID within a specific class
    public List<GroupMemberInfo> findStudentsByGroupInClass(int classId, int groupId) {
        List<GroupMemberInfo> resultList = new ArrayList<>();
        Optional<Group> groupOpt = groupRepository.findByGroupIdAndClassId(groupId, classId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            List<GroupMember> memberList = groupMemberRepository.findAllByGroupId(group.getGroupId());
            for (GroupMember member : memberList) {
                StudentAccountDetail memberDetail = accountDetailService.getStudentAccountDetail(member.getMemberId());
                GroupMemberInfo memberInfo = new GroupMemberInfo(group.getGroupId(), group.getGroupName(), member.getMemberId(), memberDetail.getFullName(), memberDetail.getStudentId());
                resultList.add(memberInfo);
            }
        }
        return resultList;
    }
    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<?> joinClassByInviteCode(@RequestParam String inviteCode, @RequestParam int accountId) {
        Optional<SubjectClass> subjectClassOpt = subjectClassReponsitory.findByInviteCode(inviteCode);
        if (subjectClassOpt.isPresent()) {
            SubjectClass subjectClass = subjectClassOpt.get();
            Optional<Account> accountOpt = accountRepository.findById(accountId);
            if (accountOpt.isEmpty()) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }
            // Check if the account has already joined the class
            boolean alreadyJoined = studentRepository.existsByClassIdAndStudentId(subjectClass.getSubjectClassId(), accountId);
            if (alreadyJoined) {
                return new ResponseEntity<>("Cannot join, account already in class", HttpStatus.BAD_REQUEST);
            }
            Student student = new Student(subjectClass.getSubjectClassId(), accountId);
            studentRepository.save(student);  // Assumption: you have a repository for Student
            return new ResponseEntity<>("Successfully joined the class" +"User"+ accountId,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//Tao invite CODE
    public String createInviteCode() {
        String source = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder output = new StringBuilder(5);//tao ra code co do dai la 5 ky tu VD:5x7Yg
        for (int i = 0; i < 5; i++) {
            int index = (int) (source.length() * Math.random());
            output.append(source.charAt(index));
        }
        return output.toString();
    }
    // get all group
    public List<Group> findAllGroup() {
        return groupRepository.findAll();
    }

}
