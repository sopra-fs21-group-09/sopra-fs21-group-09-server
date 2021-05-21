package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("moduleRepository")
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query(value="select case when count(module_id)> 0 then true else false end from USERS_MODULES um where um.module_id = (:moduleId)",nativeQuery = true)
    boolean ModuleHasUser(@Param("moduleId") Long moduleId);

}
