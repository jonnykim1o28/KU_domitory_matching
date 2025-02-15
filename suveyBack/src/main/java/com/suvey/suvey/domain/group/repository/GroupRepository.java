package com.suvey.suvey.domain.group.repository;

import com.suvey.suvey.domain.group.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, String> {

    public GroupEntity findByNum(Integer num);
    public List<GroupEntity> findByTitle(String title);

}
