package com.szymon.web;

import com.szymon.dao.StudentDao;
import com.szymon.model.Student;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@WebServlet(urlPatterns = "/query")
public class QueryServlet extends HttpServlet {

    @Inject
    StudentDao studentDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().write("List of surnames using nativeQuery \n");
        List<String> studentSurnames = studentDao.findAllSurnamesNativeQuery();

        for (String surname : studentSurnames) {
            resp.getWriter().write(surname + "\n");
        }

        resp.getWriter().write("\n");

        resp.getWriter().write("List of surnames using Query \n");
        List<String> studentSurnames2 = studentDao.findAllSurnamesQuery();

        for (String surname : studentSurnames2) {
            resp.getWriter().write(surname + "\n");
        }

        resp.getWriter().write("\n");

        resp.getWriter().write("number of students using nativeQuery \n");
        BigInteger numberOfStudents = studentDao.findNumberOfStudentsNativeQuery();
        resp.getWriter().write(numberOfStudents.toString() + "\n");

        resp.getWriter().write("\n");

        resp.getWriter().write("number of students using Query \n");
        Long numberOfStudents2 = studentDao.findNumberOfStudentsQuery();
        resp.getWriter().write(numberOfStudents2.toString() + "\n");

        resp.getWriter().write("\n");
        resp.getWriter().write("All records of students that surname starts with K using Query \n");
        List<Student> studentsWithSurnameStartK=studentDao.findStudentWithSurnameStartsWithK("K%");
        for (Student student : studentsWithSurnameStartK) {
            resp.getWriter().write(student + "\n");
        }

        resp.getWriter().write("\n");
        resp.getWriter().write("All records of students that surname starts with K using NamedQuery \n");
        List<Student> studentsWithSurnameStartK2=studentDao.findStudentWithSurnameStartsWithKUsingNamedQuery("K%");
        for (Student student : studentsWithSurnameStartK2) {
            resp.getWriter().write(student + "\n");
        }
    }
}
