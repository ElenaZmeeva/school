package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }
    @Test
    public void  testDefaultMessage() throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/",String.class));
    }

    @Test
    public void  testGetStudent() throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student",String.class))
                .isNotNull();
    }
    @Test
    public void testPostStudent() throws Exception{
        Student student= new Student();
        student.setId(4L);
        student.setName("Lorry");
        student.setAge(12);

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:"+port+"/student",student, String.class))
                .isNotNull();
    }
    @Test
    public void testDeleteStudent()throws Exception{
       ResponseEntity<Void> responseDELETE= restTemplate.exchange("/student/", HttpMethod.DELETE, null, Void.class);
    }

    @Test
    public void testPutStudent() throws Exception{
        Student student= new Student();
        student.setId(4L);
        student.setName("Lorry");
        student.setAge(12);
        Student newStudent =new Student();
        student.setId(2L);
        student.setName("Karl");
        student.setAge(10);

        HttpEntity<Student> studentHttpEntity= new HttpEntity<Student>(newStudent);
       ResponseEntity<Student> responsePut=restTemplate.exchange("/student", HttpMethod.PUT, studentHttpEntity, Student.class);
    }

}
