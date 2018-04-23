package com.szymon.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static java.util.stream.Collectors.joining;

@Entity
@Table(name = "COURSES")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Student> students;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Course{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", students='").append(
                students != null ? students.stream().map(Student::getSurname).collect(joining(", "))
                        : "").append("\'");
        sb.append('}');
        return sb.toString();
    }
}
