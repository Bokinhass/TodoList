package br.com.curso.TodolistApplication.task;

import br.com.curso.TodolistApplication.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (
                currentDate.isAfter(taskModel.getStartAt()) ||
                currentDate.isAfter(taskModel.getEndAt()) ||
                taskModel.getStartAt().isAfter(taskModel.getEndAt())
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Start date must be greater than current date and less than end date.");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/get")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        return this.taskRepository.findByIdUser((UUID) idUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

        var task = this.taskRepository.findById(id).orElse(null);
        var idUser = request.getAttribute("idUser");

        if (task == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Task not found.");
        }

        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User can't change this task.");
        }

        Utils.copyNonNullProperties(taskModel, task);

        var taskUpdated = this.taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
    }
}
