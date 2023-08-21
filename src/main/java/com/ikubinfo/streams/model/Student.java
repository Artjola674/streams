package com.ikubinfo.streams.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Student {

    private Long id;
    private String name;

    private String gender;

    private List<Subject> subjects;

}
