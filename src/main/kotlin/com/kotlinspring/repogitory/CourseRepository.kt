package com.kotlinspring.repogitory

import com.kotlinspring.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int>{
}