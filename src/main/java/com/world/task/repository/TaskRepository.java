package com.world.task.repository;


import com.world.task.enums.TaskPriority;
import com.world.task.enums.TaskStatus;
import com.world.task.model.TaskModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, String> {

    List<TaskModel> findByStatus(TaskStatus status);

    List<TaskModel> findByAssignedTo_UserId(String userId);

    @Query("""
        SELECT t FROM TaskModel t
        WHERE (:status IS NULL OR t.status = :status)
          AND (:priority IS NULL OR t.priority = :priority)
          AND (:userId IS NULL OR t.assignedTo.userId = :userId)
    """)
    Page<TaskModel> filterTasks(
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            @Param("userId") String userId,
            Pageable pageable
    );
}
