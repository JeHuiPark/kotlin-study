package com.example.todo

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskService(private val taskRepository: TaskRepository) {

    fun getAll(): Iterable<Task> = taskRepository.findAll()

    fun save(task: Task) = taskRepository.save(task)

    fun delete(id: Long):Boolean {
        val found = taskRepository.existsById(id)
        if (found) {
            taskRepository.deleteById(id)
        }
        return found
    }
}