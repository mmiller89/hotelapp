package edu.wgu.d387_sample_code.repository;


import edu.wgu.d387_sample_code.entity.AdditionEntity;
import org.springframework.data.repository.CrudRepository;


public interface AdditionRepository extends CrudRepository<AdditionEntity, Long> {

    public AdditionEntity findEntityByName(String name);

}