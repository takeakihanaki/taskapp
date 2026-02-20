package com.example.taskapp.repository;

import java.time.LocalDate;
import java.util.List;

// ===== Spring Data JPA関連 =====
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ===== 作成したEntity =====
import com.example.taskapp.entity.Task;

/**
 * =====================================================
 * TaskRepository
 * -----------------------------------------------------
 * ・Taskエンティティ専用のDBアクセス層
 * ・JpaRepositoryを継承することで
 *   CRUD操作が自動で使えるようになる
 *
 * 【ジェネリクスの意味】
 * JpaRepository<Task, Long>
 *
 * Task → 対象エンティティ
 * Long → 主キーの型
 * =====================================================
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * =====================================================
     * 指定した日付のタスク一覧を取得する
     * -----------------------------------------------------
     * メソッド名から自動でSQLを生成してくれる
     *
     * SELECT * FROM tasks
     * WHERE task_date = ?
     * ORDER BY task_time ASC;
     *
     * というSQLが内部で生成される
     * =====================================================
     */
    List<Task> findByTaskDateOrderByTaskTimeAsc(LocalDate taskDate);

}