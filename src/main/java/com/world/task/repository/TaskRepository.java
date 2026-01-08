package com.world.task.repository;


import com.world.task.enums.TaskStatus;
import com.world.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, String> {

    List<TaskModel> findByStatus(TaskStatus status);

    List<TaskModel> findByAssignedTo_UserId(String userId);
}
