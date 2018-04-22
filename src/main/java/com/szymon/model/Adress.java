package com.szymon.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static java.util.stream.Collectors.joining;

@Entity
@Table(name = "ADRESSES")
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street", length = 24)
    @NotNull
    private String street;

    @Column(name = "city", length = 24)
    @NotNull
    private String city;

    @OneToMany(mappedBy = "adress", fetch = FetchType.LAZY)
    private Set<Student> students;

    public Adress() {
    }

    public Adress(String street, String city) {
        this.street = street;
        this.city = city;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Adress{");
        sb.append("id=").append(id);
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
