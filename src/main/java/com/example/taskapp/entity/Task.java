package com.example.taskapp.entity;

// ===== 日付・時刻API =====
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// ===== JPA関連アノテーション =====
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * ==========================================
 * Taskエンティティクラス
 * ------------------------------------------
 * ・DBの tasks テーブルと対応するクラス
 * ・1レコード = 1タスク
 * ・カレンダーに表示する予定情報を保持
 * ==========================================
 */
@Entity // ← このクラスはDBテーブルと紐づくことを示す
@Table(name = "tasks") // ← DBのテーブル名を明示指定
public class Task {

    /**
     * ==========================================
     * 主キーID
     * ------------------------------------------
     * ・各タスクを一意に識別するID
     * ・AUTO INCREMENT
     * ==========================================
     */
    @Id // ← 主キー
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ↑ PostgreSQLのSERIAL型に対応（自動採番）
    private Long id;


    /**
     * ==========================================
     * タスクの日付（年月日）
     * ------------------------------------------
     * ・例：2026-02-20
     * ・カレンダーのキーとなる値
     * ・NULL不可
     * ==========================================
     */
    @Column(nullable = false)
    private LocalDate taskDate;


    /**
     * ==========================================
     * タスクの時刻
     * ------------------------------------------
     * ・例：14:30
     * ・NULL不可
     * ==========================================
     */
    @Column(nullable = false)
    private LocalTime taskTime;


    /**
     * ==========================================
     * タスク内容
     * ------------------------------------------
     * ・例：「会議」「歯医者」など
     * ・最大255文字
     * ・NULL不可
     * ==========================================
     */
    @Column(nullable = false, length = 255)
    private String content;


    /**
     * ==========================================
     * 作成日時
     * ------------------------------------------
     * ・レコードが初めて保存された時刻
     * ・自動セット
     * ==========================================
     */
    private LocalDateTime createdAt;


    /**
     * ==========================================
     * 更新日時
     * ------------------------------------------
     * ・更新されるたびに自動更新
     * ==========================================
     */
    private LocalDateTime updatedAt;


    // ====================================================
    // JPAライフサイクルイベント
    // ====================================================

    /**
     * データが「初回保存」される直前に呼ばれる
     * createdAt / updatedAt を自動設定
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * データが「更新」される直前に呼ばれる
     * updatedAt を自動更新
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    // ====================================================
    // Getter / Setter
    // ====================================================

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public LocalTime getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(LocalTime taskTime) {
        this.taskTime = taskTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}