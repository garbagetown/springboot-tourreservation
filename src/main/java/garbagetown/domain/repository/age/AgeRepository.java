package garbagetown.domain.repository.age;

import garbagetown.domain.model.Age;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by garbagetown on 10/25/15.
 */
public interface AgeRepository extends JpaRepository<Age, String> {
}
