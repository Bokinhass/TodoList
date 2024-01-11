package br.com.curso.TodolistApplication.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);
    /*
    Other option for validation task update for your user
    TaskModel findByIdAndIdUser(UUID id, UUID idUser);
     */
}
