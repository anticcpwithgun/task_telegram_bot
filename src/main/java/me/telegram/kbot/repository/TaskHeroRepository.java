package me.telegram.kbot.repository;

import me.telegram.kbot.model.TaskHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author freealways
 * @date 2020/7/30 21:00
 */

public interface TaskHeroRepository extends JpaRepository<TaskHero,String>,PagingAndSortingRepository<TaskHero,String> {
    @Modifying
    @Query(value = "update status = status from TaskHero where taskId = taskId",nativeQuery = true)
    int updateStatusByTaskId(String taskId,Integer status);
    List<TaskHero> findByHeroIdAndStatus(String heroId, Integer status);
    List<TaskHero> findByTaskIdAndStatus(String taskId, Integer status);
    List<TaskHero> findByHeroIdAndStatusAndType(String heroId, Integer status, Integer type);
    TaskHero findByTaskIdAndHeroId(String taskId,String heroId);
}
