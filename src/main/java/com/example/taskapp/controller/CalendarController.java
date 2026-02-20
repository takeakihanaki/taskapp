package com.example.taskapp.controller;

// ===== Java標準 =====
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ===== Spring関連 =====
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskapp.repository.TaskRepository;

/**
 * =====================================================
 * Calendar API
 * -----------------------------------------------------
 * ・FullCalendar専用のイベント取得API
 * ・Taskエンティティをカレンダー用JSONに変換して返す
 *
 * 【返却形式（例）】
 * [
 *   { "title": "会議", "start": "2026-02-20T14:30:00" }
 * ]
 *
 * =====================================================
 */
@RestController
public class CalendarController {

    private final TaskRepository taskRepository;

    public CalendarController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/calendar")
    public List<Map<String, Object>> getCalendarEvents() {

        /**
         * =====================================================
         * stream()とは？
         * -----------------------------------------------------
         * Listを「1件ずつ処理する流れ」に変換する仕組み
         *
         * map()で
         * Task → カレンダー用Map
         * に変換している
         * =====================================================
         */
        return taskRepository.findAll().stream().map(task -> {

            Map<String, Object> event = new HashMap<>();

            // ID
            event.put("id", task.getId());
            
            // カレンダーに表示するタイトル
            event.put("title", task.getContent());
            
            
            // 日付 + 時刻を ISO形式に結合
            event.put(
                "start",
                task.getTaskDate() + "T" + task.getTaskTime()
            );

            return event;

        }).toList();
    }
}