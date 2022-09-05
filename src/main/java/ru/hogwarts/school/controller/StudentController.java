package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{age}")
    public ResponseEntity<Collection<Student>> getAllStudentsByAge (@PathVariable int age){
       if(age>0){
           return ResponseEntity.ok (studentService.studentsByAge(age));
       }
        return ResponseEntity.ok (Collections.emptyList());
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getByAgeBetween (@RequestParam int min,
                                                                @RequestParam int max){
        return ResponseEntity.ok(studentService.findByAgeBetween(min,max));
    }


    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo (@PathVariable Long id){
        Student student= studentService.readStudent(id);
        if (student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent( @RequestBody Student student){
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student student1= studentService.updateStudent(student);
        if(student1==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent (@PathVariable Long id){
         studentService.deleteStudent(id);
         return ResponseEntity.ok().build();
    }


}
