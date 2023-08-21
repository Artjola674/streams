package com.ikubinfo.streams;

import com.ikubinfo.streams.model.Student;
import com.ikubinfo.streams.model.StudentAndSubject;
import com.ikubinfo.streams.model.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class FlatMapExamples {

    @Test
    public void firstExample() {
        List<List<Integer>> listOfLists = List.of(
                List.of(1,2),
                List.of(3,4),
                List.of(5,6));

        List<Integer> list = listOfLists.stream()
                .flatMap(Collection::stream)
                .toList();
        listOfLists.forEach(System.out::println);
        list.forEach(System.out::println);

        assertEquals(list.size(), listOfLists.size() * 2);

    }

    @Test
    public void secondExample() {
        List<StudentAndSubject> studentAndSubjects = List.of(
                new StudentAndSubject(1L,"John","Male",1L,"math","Ela"),
                new StudentAndSubject(2L,"Tea","Female",1L,"math","Ela"),
                new StudentAndSubject(2L,"Tea","Female",2L,"physical","Albana"),
                new StudentAndSubject(3L,"Hans","Male",1L,"math","Ela"),
                new StudentAndSubject(3L,"Hans","Male",2L,"physical","Albana"),
                new StudentAndSubject(4L,"Ajs","Male",2L,"math","Ela")
                );
        log.info("List of students and subjects: ");
        studentAndSubjects.forEach(System.out::println);


        Map<Long, Map<String, Map<String, List<StudentAndSubject>>>> students = studentAndSubjects.stream()
                .collect(Collectors.groupingBy(StudentAndSubject::getStudentId,
                        Collectors.groupingBy(StudentAndSubject::getStudentName,
                                Collectors.groupingBy(StudentAndSubject::getStudentGender))));

        List<Student> studentList =
                students.entrySet()
                        .stream().map(a1 -> {
                            Long studentId = a1.getKey();
                            return a1.getValue()
                                    .entrySet()
                                    .stream().map(a2 -> {
                                        String studentName = a2.getKey();
                                        return a2.getValue()
                                                .entrySet()
                                                .stream()
                                                .map(a3 -> {
                                                    String studentGender = a3.getKey();
                                                    return new Student(studentId, studentName, studentGender,
                                                            a3.getValue().stream()
                                                                    .map(a4 -> new Subject(a4.getSubjectId(), a4.getSubjectName(),
                                                                            a4.getSubjectTeacher()))
                                                                    .toList());
                                                })
                                                .toList();
                                    })
                                    .flatMap(Collection::stream)
                                    .toList();
                        })
                        .flatMap(Collection::stream)
                        .toList();

        log.info("List with students: ");
        studentList.forEach(System.out::println);



        log.info("-----------------reverse--------------------");

        List<StudentAndSubject> studentAndSubjects1 = studentList.stream()
                .map(a1 ->
                        a1.getSubjects().stream()
                                .map(a2 -> new StudentAndSubject(a1.getId(), a1.getName(), a1.getGender(),
                                        a2.getId(), a2.getName(), a2.getTeacher()))
                                .toList()
                )
                .flatMap(a3 -> a3.stream())
                .peek(a4-> a4.setStudentGender(a4.getStudentGender().toUpperCase()))
                .peek(System.out::println)
                .toList();

        log.info("--------------- get list of subjects from list of students--------------");

        List<Subject> subjects = studentList.stream()
                .flatMap(o -> o.getSubjects().stream())
                .distinct()
                .peek(System.out::println)
                .toList();

    }

}
