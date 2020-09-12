package me.telegram.kbot.repository;

import me.telegram.kbot.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author freealways
 * @date 2020/7/30 20:48
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, String>,PagingAndSortingRepository<Task, String> {
    Task findByTaskId(String taskId);
    List<Task> findByTaskIdIn(List<String> taskIdList);
    Page<Task> findByLevelAndStatusIn(Integer level,List<Integer> statusList, Pageable pageable);
}
