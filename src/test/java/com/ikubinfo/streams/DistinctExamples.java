package com.ikubinfo.streams;

import com.ikubinfo.streams.model.StudentAndSubject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class DistinctExamples {

    private final List<StudentAndSubject> studentAndSubjects = List.of(
            new StudentAndSubject(1L,"John","Male",1L,"math","Ela",16,8.5),
            new StudentAndSubject(2L,"Tea","Female",1L,"math","Ela",17,9.0),
            new StudentAndSubject(2L,"Tea","Female",2L,"physical","Albana",17,7.2),
            new StudentAndSubject(3L,"Hans","Male",1L,"math","Ela",19,8.0),
            new StudentAndSubject(3L,"Hans","Male",2L,"physical","Albana",19,9.3),
            new StudentAndSubject(4L,"Ajs","Male",2L,"math","Ela",20,5.6)
    );

    @Test
    public void distinctAndSort() {
        List<Integer> numbers = List.of(1, 1, 3, 3, 4, 4, 2, 2, 5, 5, 7, 7, 8, 8, 6, 6, 9, 9, 9, 9, 9);
        List<Integer> distinctList = numbers.stream().distinct().toList();
        assertThat(distinctList).hasSize(9);
        System.out.println("Distinct list "+ distinctList);

        List<Integer> sorted = distinctList.stream().sorted().toList();
        System.out.println("Sorted list " + sorted);

        sorted.stream().skip(6)
                .findFirst()
                .ifPresent(System.out::println);
    }

    @Test
    public void mapAndFilter()  {

//        List<String> names = List.of("Hana","Amarda","Hans","Bob");
        List<String> names = List.of("Amard","Hans","Han","Bob");
        String s = names
                .stream()
                .filter(y -> y.endsWith("a"))
                .map(x->x.toUpperCase())
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElse("Name not found");
//        assertThat(s).isEqualTo("HANA");
        assertThat(s).isEqualTo("Name not found");



        List<String> upperCaseNames = new ArrayList<>();
        for (String name : names){
            if (name.endsWith("a")){
                String upperCaseName = name.toUpperCase();
                upperCaseNames.add(upperCaseName);
            }
        }
        Collections.sort(upperCaseNames,Comparator.reverseOrder());
        if (!upperCaseNames.isEmpty()){
            assertThat(upperCaseNames.get(0)).isEqualTo("HANA");
        }else {
            System.out.println("Name not found");
        }
    }

    @Test
    public void dropWhilAndTakeWhileExample() {
        List<Integer> list = List.of(4, 4, 4, 5, 6, 7, 7, 8, 9, 10);

        log.info("drop while");
        list.stream()
                .dropWhile(number->number<=5)
                .toList()
                .forEach(System.out::println);

        log.info("take while");
        list.stream()
                .takeWhile(number->number<=5)
                .toList()
                .forEach(System.out::println);

    }

    @Test
    public void evenAndOddNumbers() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0,
                        Collectors.toList()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(x -> x.getKey() ? "even" : "odd"
                                ,Map.Entry::getValue))
                .entrySet()
                .forEach(System.out::println);
    }

    @Test
    public void joinStrings() {
        List<String> list = List.of("a","b","c","d");
        String string = list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.joining(", ", "[", "]"),
                        String::toString));

        assertEquals("[a, b, c, d]",string);
    }

    @Test
    public void reduceExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int result = numbers.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * n)
                .peek(System.out::println)
                .reduce(0, Integer::sum);

        assertEquals(165,result);
    }

    @Test
    public void zipExample() {
        List<String> names = studentAndSubjects.stream()
                .map(StudentAndSubject::getStudentName)
                .distinct()
                .toList();
        log.info("----------names---------");
        names.forEach(System.out::println);

        List<Integer> ages = studentAndSubjects.stream()
                .map(StudentAndSubject::getStudentAge)
                .distinct()
                .toList();
        log.info("--------ages----------");
        ages.forEach(System.out::println);

        log.info("-----concat name and age---------");
        IntStream.range(0, Math.min(names.size(), ages.size()))
                .mapToObj(i -> names.get(i) + " -> " + ages.get(i))
                .peek(System.out::println)
                .toList();
    }

    @Test
    public void findSubjectWithHighestNumberOfStudents() {
        log.info("List of students: ");
        studentAndSubjects.forEach(System.out::println);

        String subject = studentAndSubjects.stream()
                .collect(Collectors.groupingBy(StudentAndSubject::getSubjectName,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        assertEquals("math", subject);

//        String key = studentAndSubjects.stream()
//                .collect(Collectors.groupingBy(StudentAndSubject::getSubjectName))
//                .entrySet()
//                .stream()
//                .collect(Collectors.toMap(Map.Entry::getKey,
//                        entry -> entry.getValue().size()))
//                .entrySet().stream()
//                .max(Map.Entry.comparingByValue()).get()
//                .getKey();




    }

    @Test
    public void findStudentNamesForEachSubject() {
        log.info("List of students: ");
        studentAndSubjects.forEach(System.out::println);

        studentAndSubjects.stream()
                .collect(Collectors.groupingBy(StudentAndSubject::getSubjectName,
                        Collectors.mapping(a -> a.getStudentName(),
                                Collectors.toList())))
                .entrySet()
                .forEach(System.out::println);
    }

    @Test
    public void findBestStudentsForEachSubject() {
        log.info("List of students: ");
        studentAndSubjects.forEach(System.out::println);

        studentAndSubjects.stream()
                .collect(Collectors.groupingBy(StudentAndSubject::getSubjectName,
                        Collectors.maxBy(Comparator.comparing(StudentAndSubject::getMark))))
                .entrySet()
                .forEach(System.out::println);
    }

    @Test
    public void findAverageAgeOfStudentsForEachSubject() {
        log.info("List of students: ");
        studentAndSubjects.forEach(System.out::println);

        studentAndSubjects.stream()
                .collect(Collectors.groupingBy(StudentAndSubject::getSubjectName,
                                Collectors.averagingInt(StudentAndSubject::getStudentAge)))
                .entrySet()
                .forEach(System.out::println);


    }
}
