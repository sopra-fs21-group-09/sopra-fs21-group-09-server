package ch.uzh.ifi.hase.soprafs21.repository;


import ch.uzh.ifi.hase.soprafs21.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("groupRepository")
public interface GroupRepository extends JpaRepository<Group, Long> {
}
