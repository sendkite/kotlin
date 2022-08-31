package com.kotlinspring.util

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course

fun courseEntityList() = listOf(
    Course(null, "Learning how to use Kotlin", "songyeon"),
    Course(null, "Master course for JPA", "hmin"),
    Course(null, "Have fun with Netflix", "bryce")
)

fun courseDTO(
    id: Int? = null,
    name: String = "learning kotlin with spring",
    category: String = "basic",
) = CourseDTO(
    id,
    name,
    category
)