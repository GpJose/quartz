package test.quartz.quartz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.quartz.quartz.model.QuartzEntity;

public interface QuartzRepository extends JpaRepository<QuartzEntity,Long> {

}
