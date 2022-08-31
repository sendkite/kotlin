package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.service.CourseService
import com.kotlinspring.service.GreetingService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {
        val courseDTO = CourseDTO(null, "learning kotlin and spring", "songyeon")

        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange() // call endpoint
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }


    @Test
    fun getCourses() {

        every { courseServiceMock.retrieveAllCourses() }.returnsMany(
            listOf(courseDTO(id = 1), courseDTO(id = 2, name = "springboot"))
        )

        val courseDTOs = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(2, courseDTOs!!.size)
    }

    @Test
    fun updateCourse() {
        val course = Course(null, "test", "test")

        every { courseServiceMock.updateCourse(any(), any()) } returns courseDTO(id =100, "newTest", "newCategory")

        val updateDTO = CourseDTO(null, "newTest", "newCategory")

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(updateDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("newTest", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {

        every { courseServiceMock.deleteCourse(any()) } just runs

        val deleteCourse = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent
    }
}

