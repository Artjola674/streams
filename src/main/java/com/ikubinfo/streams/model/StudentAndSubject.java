package com.ikubinfo.streams.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentAndSubject {

    private Long studentId;
    private String studentName;
    private String studentGender;
    private Long subjectId;
    private String subjectName;
    private String subjectTeacher;
    private Integer studentAge;
    private Double mark;

    public StudentAndSubject(Long studentId, String studentName, String studentGender, Long subjectId, String subjectName, String subjectTeacher) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentGender = studentGender;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectTeacher = subjectTeacher;
    }
}
