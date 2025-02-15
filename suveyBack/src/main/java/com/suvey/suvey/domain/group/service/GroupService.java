package com.suvey.suvey.domain.group.service;

import com.suvey.suvey.domain.group.dto.GroupDTO;
import com.suvey.suvey.domain.group.entity.GroupEntity;
import com.suvey.suvey.domain.group.repository.GroupRepository;
import com.suvey.suvey.domain.post.entity.PostEntity;
import com.suvey.suvey.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {


    private final PostService postService;
    private final GroupRepository groupRepository;

    private final int MAX_GROUP_NUM = 3;

    public List<GroupEntity> getAllGroup() {

        return groupRepository.findAll();
    }

    public GroupEntity getGroupById(String id) {
        return groupRepository.findById(id).get();
    }

    public Optional<GroupEntity> getGroupOptionalById(String id) {
        return groupRepository.findById(id);
    }

    public GroupEntity getGroupByNum(Integer num) {
//        Pageable pageable = PageRequest.of(page, 12);
        return groupRepository.findByNum(num);
    }

    public GroupDTO getGroupByNum(Integer num, Integer page){
        GroupEntity group = groupRepository.findByNum(num);

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("date")));
        Page<PostEntity> postEntities = postService.getPosts(group ,pageable);
        int totalPages = postEntities.getTotalPages();


        List<PostEntity> posts = postEntities.getContent();



        // post 갯수가 매우 큰 수 일때 groupRepository.findByNum(num) 을 하면 group을 불러오는데 큰 시간이 소요될 까?
        // 만약 그렇다면 group.title만 가져오는 방법은?

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setTitle(group.getTitle());
        groupDTO.setPosts(posts);
        groupDTO.setTotalPages(totalPages);


        return groupDTO;


    }

    public List<GroupEntity> getGroupByTitle(String title){
        return groupRepository.findByTitle(title);
    }

    public void saveGroup(GroupEntity group) {
        groupRepository.save(group);
    }

    public boolean deleteGroup(GroupEntity group) {
        if (group.getNum() <= MAX_GROUP_NUM) {
            return false;
        }
        groupRepository.delete(group);
        return true;
    }

    public GroupEntity updateTitle(final GroupEntity group) {
        Optional<GroupEntity> original = groupRepository.findById(group.getId());
        /*
        original.ifPresent(gr -> {//왜 postEntites가 []일까..
                    gr.setTitle(group.getTitle());
                    gr.setPostEntities(group.getPostEntities());

                    groupRepository.save(gr);
                }
        );


        //  A collection with casacde ="all-delete-orphan" was no longer referenced by the owning entity instance 에러가 뜸
        //  Collection 에 대한 조작이 발생했을 때 에러가 생긴다.
        //  Hibernate가 기존 리스트에 포함된 엔티티들이 더 이상 참조되지 않는다고 판단하여 삭제를 시도하는데, 이때 문제가 발생할 수 있다.
        //  위와 같이 실행했을 경우, 기존 gr에 종속된 postEntites 가 새로운 postEntites로 교체되면서 기존 것들이 고아 상태가 된다.(맞게 해석된 지는 모르겠음)
        //  아래의 경우엔 컬렉션에 변경이 없기 때문에 문제 없이 실행된다.
        //

        if(original.isPresent()){
            GroupEntity gr = original.get();
            gr.setTitle(group.getTitle());
            groupRepository.save(gr);
        }
         */

        original.ifPresent(gr -> {//왜 postEntites가 []일까..
                    gr.setTitle(group.getTitle());
                    gr.getPostEntities().clear();
                    if (group.getPostEntities() != null) {
                        gr.getPostEntities().addAll(group.getPostEntities());
                    }
                    groupRepository.save(gr);
                }
        );


        return groupRepository.findById(group.getId()).get();
    }

    public Integer findLastGroupNum() {

//        class Comp implements Comparator {
//            public int compare(Object o1, Object o2){
//                if(o1 instanceof Comparable<?> && o2 instanceof Comparable<?>){
//                    Comparable c1 = (Comparable) o1;
//                    Comparable c2 = (Comparable) o2;
//                    return c1.compareTo(c2);
//                }
//                return -1;
//            }
//        }
//        Comp c = new Comp();

        List<GroupEntity> groupEntities = groupRepository.findAll();

//        final Comparator<Integer> comp = (i1, i2) -> i1.compareTo(i2);
//        Integer max = (Integer) groupEntities.stream().map(g -> g.getNum()).max(comp).orElse(MAX_GROUP_NUM);

        // 람다식이 하나일 경우 메서드 참조 사용 가능
        Integer max = groupEntities.stream().map(GroupEntity::getNum).max(Integer::compareTo).orElse(MAX_GROUP_NUM);
        return max;
    }


}
