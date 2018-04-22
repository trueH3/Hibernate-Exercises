package com.szymon.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
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

    public Student() {

    }

    public Student(String name, String surname, LocalDate dateOfBirth, Computer computer, Adress adress) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.computer = computer;
        this.adress = adress;
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
        sb.append('}');
        return sb.toString();
    }
}
