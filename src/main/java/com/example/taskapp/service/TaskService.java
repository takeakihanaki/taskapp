package com.example.taskapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// ===== Spring関連 =====
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ===== 作成済みクラス =====
import com.example.taskapp.entity.Task;
import com.example.taskapp.repository.TaskRepository;

/**
 * =====================================================
 * TaskService
 * -----------------------------------------------------
 * ・ビジネスロジック層
 * ・Controllerから呼ばれる
 * ・Repositoryを利用してDB操作を行う
 *
 * ★ MVCの「M」に近い位置
 * =====================================================
 */
@Service // ← Spring管理対象にする
@Transactional // ← メソッドをトランザクション管理する
public class TaskService {

    /**
     * Repositoryを依存性注入（DI）する
     * finalにすることで不変にする
     */
    private final TaskRepository taskRepository;

    /**
     * コンストラクタインジェクション
     * Springが自動でRepositoryを注入する
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // =====================================================
    // タスク保存
    // =====================================================
    /**
     * タスクをDBへ保存する
     *
     * @param task 保存したいタスク
     * @return 保存後のタスク
     */
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // =====================================================
    // 全件取得
    // =====================================================
    /**
     * 全タスクを取得
     */
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    // =====================================================
    // ID検索
    // =====================================================
    /**
     * IDでタスク検索
     */
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    // =====================================================
    // 日付指定検索（カレンダー用）
    // =====================================================
    /**
     * 指定した日付のタスクを時刻順で取得
     */
    public List<Task> findByDate(LocalDate date) {
        return taskRepository.findByTaskDateOrderByTaskTimeAsc(date);
    }

    // =====================================================
    // 削除
    // =====================================================
    /**
     * 指定IDのタスク削除
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}