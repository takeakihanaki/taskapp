package com.example.taskapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.taskapp.entity.Task;
import com.example.taskapp.repository.TaskRepository;

/**
 * =====================================================
 * ViewController
 * -----------------------------------------------------
 * ・画面表示専用のController
 * ・カレンダー表示、編集画面遷移などを担当
 * =====================================================
 */
@Controller
public class ViewController {

    private final TaskRepository taskRepository;

    public ViewController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * =====================================================
     * 編集画面表示
     * -----------------------------------------------------
     * タスクIDをもとにDBからTaskを取得し、編集画面に渡す
     * URL例: /tasks/edit/3
     * =====================================================
     */
    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));

        model.addAttribute("task", task); // Thymeleafで利用できるようにModelに渡す
        return "edit-task"; // src/main/resources/templates/edit-task.html
    }
}