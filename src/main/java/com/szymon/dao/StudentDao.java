package com.szymon.dao;

import com.szymon.model.Student;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class StudentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Long save(Student s) {
        entityManager.persist(s);
        return s.getId();
    }

    public Student update(Student s) {
        return entityManager.merge(s);
    }

    public void delete(Long id) {
        final Student s = entityManager.find(Student.class, id);
        if (s != null) {
            entityManager.remove(s);
        }
    }

    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> findAll() {
        final Query query = entityManager.createQuery("SELECT s FROM Student s");

        return query.getResultList();
    }

    public List<String> findAllSurnamesNativeQuery() {
        final Query query = entityManager.createNativeQuery("SELECT `surname` FROM STUDENTS");
        return query.getResultList();
    }

    public List<String> findAllSurnamesQuery() {
        final Query query = entityManager.createQuery("SELECT s.surname FROM Student s");
        return query.getResultList();
    }

    public BigInteger findNumberOfStudentsNativeQuery() {
        final Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM `STUDENTS`");
        return (BigInteger) query.getSingleResult();
    }

    public Long findNumberOfStudentsQuery() {
        final Query query = entityManager.createQuery("SELECT COUNT(c.id) FROM Student c");
        return (Long) query.getSingleResult();
    }



    public List<Student> findStudentWithSurnameStartsWithK(String letter) {
        final Query query = entityManager.createQuery("SELECT s FROM Student s WHERE s.surname LIKE :letter");
        query.setParameter("letter", letter);
        return query.getResultList();
    }

    public List<Student>findStudentWithSurnameStartsWithKUsingNamedQuery (String letter) {
        final  Query query = entityManager.createNamedQuery("Student.surnameWithK");
        query.setParameter("letter", letter);
        return query.getResultList();
    }

}