package br.com.curso.TodolistApplication.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private UUID idUser;

  @Column(length = 50)
  private String title;

  private String description;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private String priority;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public void setTitle(String title) throws Exception {
    if (title.length() > 50) {
      throw new Exception("Title must be has 50 characters.");
    }
    this.title = title;
  }
}
