package com.example.taskapp.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// ===== Spring関連 =====
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// ===== 作成済みクラス =====
import com.example.taskapp.entity.Task;
import com.example.taskapp.service.TaskService;

/**
 * ==========================================================
 * TaskController（REST API用）
 * ----------------------------------------------------------
 * ・外部からのHTTPリクエストを受け取る入口
 * ・JSON形式でデータをやり取りする
 * ・Service層を呼び出す役割のみを持つ
 * ==========================================================
 */
@RestController // ← JSONを返すコントローラ
@RequestMapping("/api/tasks") // ← 共通URL
public class TaskController {

    private final TaskService taskService;

    /**
     * コンストラクタインジェクション
     * Springが自動でServiceを注入する
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // =====================================================
    // 全件取得
    // =====================================================
    /**
     * GET http://localhost:8080/api/tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAllTasks();
    }

    // =====================================================
    // ID指定取得
    // =====================================================
    /**
     * GET http://localhost:8080/api/tasks/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {

        Optional<Task> task = taskService.findById(id);

        return task.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // =====================================================
    // 日付指定取得
    // =====================================================
    /**
     * GET http://localhost:8080/api/tasks/date?date=2026-02-20
     */
    @GetMapping("/date")
    public List<Task> getTasksByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return taskService.findByDate(date);
    }

    // =====================================================
    // 新規作成
    // =====================================================
    /**
     * POST http://localhost:8080/api/tasks
     * Body(JSON):
     * {
     *   "taskDate": "2026-02-20",
     *   "taskTime": "14:30",
     *   "content": "会議"
     * }
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // クライアントから誤ってIDが送られてきた場合に上書きされないよう明示的にクリア
        task.setId(null);
        Task saved = taskService.saveTask(task);

        // Locationヘッダに作成したリソースのURIを設定して201 Createdを返す
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    // =====================================================
    // 削除
    // =====================================================
    /**
     * DELETE http://localhost:8080/api/tasks/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            // 削除対象が存在しない場合は404を返す
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
