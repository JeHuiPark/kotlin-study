package com.example.todo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(private val taskService: TaskService) {

    @GetMapping
    fun getTasks(): Iterable<Task> = taskService.getAll()

    @PostMapping
    fun create(@RequestBody task: Task): String {
        val result = taskService.save(task)
        return "added task with description ${result.description}"
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        if (taskService.delete(id)) {
            return ResponseEntity.ok("Task with id $id deleted")
        }
        return ResponseEntity
            .status(404)
            .body("Task with id $id not found")
    }
}