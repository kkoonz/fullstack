package demo.helloworld.repository;

import demo.helloworld.entity.HelloJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloJPARepository extends JpaRepository<HelloJPAEntity, Integer> {

}
