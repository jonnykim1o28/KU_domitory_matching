package com.suvey.suvey.domain.group.controller;

import com.suvey.suvey.domain.group.dto.GroupDTO;
import com.suvey.suvey.domain.group.entity.GroupEntity;
import com.suvey.suvey.domain.group.service.GroupService;
import com.suvey.suvey.domain.post.entity.PostEntity;
import com.suvey.suvey.domain.post.service.PostService;
import com.suvey.suvey.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suvey/group")
public class GroupController {


    private final GroupService groupService;

    private final PostService postService;


    @GetMapping
    public ResponseEntity<?> retrieveAll() {
        List<GroupEntity> groupEntities = groupService.getAllGroup();
        List<GroupDTO> groupDTOS = groupEntities.stream().map(GroupDTO::new).collect(Collectors.toList());
        ResponseDTO<GroupDTO> responseDTO = ResponseDTO.<GroupDTO>builder().data(groupDTOS).build();
        return ResponseEntity.ok().body(responseDTO);
    }


    //Todo 권한에 따른 게시판 표출 logic 추가
    // ex) 권한이 부여된 사용자에게만 보이는 게시판
    @GetMapping("/detail")
    public ResponseEntity<?> retrieveByGroupNum(@RequestParam Integer num, @RequestParam(defaultValue = "0") Integer page) {

        GroupDTO groupDTO = groupService.getGroupByNum(num, page);
        return ResponseEntity.ok().body(groupDTO);
    }


    /*
     *  Todo
     *  1. GroupController에서 post(link)와 note에 접근하는게 과연 맞는지
     *  2. 어차피 front에서 query문으로 위치를 찾아낼 텐데 valid case에서 ResponseEntity를 반환하는게 맞는지
     * */
    @GetMapping("/search")
    public ResponseEntity<?> searchQuery(@RequestParam String field, @RequestParam String query) {

        //  Todo 각각의 option은 여러가지 검색결과를 가질 수 있다. 카카오톡의 대화검색을 생각하면서 만들자
        //  따라서 return을 할 때 ResponseDTO 의 data에 저장해서 return 하자.
        switch (field) {
            case "title":
                List<GroupEntity> groupEntities = groupService.getGroupByTitle(query);
                if (!groupEntities.isEmpty()) {
                    List<GroupDTO> groupDTOs = groupEntities.stream().map(GroupDTO::new).collect(Collectors.toList());
                    ResponseDTO responseDTO = ResponseDTO.<GroupDTO>builder().data(groupDTOs).build();
                    return ResponseEntity.ok().body(responseDTO);
                }

                //글 내용으로 바꾸자
//            case "note":
//                List<PostEntity> notePostEntites = postService.getPostByNote(query);
//                if (!notePostEntites.isEmpty()) {
//                    List<PostDTO> postDTOs = notePostEntites.stream().map(PostDTO::new).collect(Collectors.toList());
//                    ResponseDTO responseDTO = ResponseDTO.<PostDTO>builder().data(postDTOs).build();
//                    return ResponseEntity.ok().body(responseDTO);
//                }

            default:
                ResponseDTO responseDTO = ResponseDTO.builder().error("This query doesn't valid").build();
                return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    //관리자 권한
    @PutMapping
    public ResponseEntity<?> updateTitle(@RequestBody final GroupDTO groupDTO) {
        GroupEntity group = GroupDTO.GroupDTOToEntity(groupDTO);
        if (groupService.getGroupOptionalById(group.getId()).isPresent()) {
            GroupEntity updatedGroup = groupService.updateTitle(group);
            GroupDTO responseGroupDTO = new GroupDTO(updatedGroup);
            return ResponseEntity.ok().body(responseGroupDTO);
        }
        return ResponseEntity.badRequest().body("update title is failed");
    }


    //관리자 권한
//    @DeleteMapping
//    public ResponseEntity<?> deleteGroup(@RequestBody final GroupDTO groupDTO) {
//        GroupEntity group = GroupDTO.GroupDTOToEntity(groupDTO);
//
//    }


    //관리자 권한
    @PostMapping
    public void createGroup(@AuthenticationPrincipal String userId, @RequestBody GroupDTO groupDTO) {
        GroupEntity group = GroupDTO.GroupDTOToEntity(groupDTO);
        group.setTitle("");
        groupService.saveGroup(group);
    }

    @PostMapping("/1")
    public void createGroupTest(@AuthenticationPrincipal String userId) {
        for (int i = 0; i < 10; i++) {
            GroupEntity group = new GroupEntity();
            group.setTitle("자유게시판" + i);
            group.setNum(3000 + i);

            groupService.saveGroup(group);
            for (int j = 0; j < 10000; j++) {
                PostEntity post = new PostEntity();
                post.setDate(LocalDateTime.now());
                post.setPostTitle("자유게시판 " + i + "의 게시글 제목 : " + j);
                post.setPostContent("게시글 내용 : " + j);
                post.setGroup(group);
                postService.save(post);
            }


        }
    }

}
