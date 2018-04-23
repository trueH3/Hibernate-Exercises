package com.szymon.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.joining;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Student.surnameWithK",
                query = "SELECT s FROM Student s WHERE s.surname LIKE :letter"
        )
})
@Table(name = "STUDENTS")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "computer_id", unique = true)
    private Computer computer;

    @ManyToOne
    @JoinColumn(name = "adress_id")
    private Adress adress;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "STUDENTS_TO_COURSES",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> courses;

    public Student() {

    }

    public Student(String name, String surname, LocalDate dateOfBirth, Computer computer, Adress adress, Set<Course> courses) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.computer = computer;
        this.adress = adress;
        this.courses = courses;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
        final StringBuffer sb = new StringBuffer("Student{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append(", computer=").append(computer);
        sb.append(", adress=").append(adress);
        sb.append(", courses=").append(courses.stream().map(Course::getName).collect(joining(", ")));
        sb.append('}');
        return sb.toString();
    }
}
