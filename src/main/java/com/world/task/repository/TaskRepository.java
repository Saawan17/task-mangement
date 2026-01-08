package com.world.task.repository;


import com.world.task.enums.TaskStatus;
import com.world.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    List<TaskModel> findByStatus(TaskStatus status);

    List<TaskModel> findByAssignedTo_Id(Long userId);
}
