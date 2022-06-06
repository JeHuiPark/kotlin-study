package com.example.todo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Task(
    @Id @GeneratedValue val id: Long,
    val description: String,)
