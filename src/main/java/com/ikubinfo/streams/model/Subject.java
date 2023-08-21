package com.ikubinfo.streams.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Subject {

    private Long id;

    private String name;

    private String teacher;
}
