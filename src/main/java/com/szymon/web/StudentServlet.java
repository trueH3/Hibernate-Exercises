package com.szymon.web;

import com.szymon.dao.AdressDao;
import com.szymon.dao.ComputerDao;
import com.szymon.dao.CourseDao;
import com.szymon.dao.StudentDao;
import com.szymon.model.Adress;
import com.szymon.model.Computer;
import com.szymon.model.Course;
import com.szymon.model.Student;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(StudentServlet.class);

    @Inject
    private StudentDao studentDao;

    @Inject
    private ComputerDao computerDao;

    @Inject
    private AdressDao adressDao;

    @Inject
    private CourseDao courseDao;

    @Override
    public void init() throws ServletException {
        super.init();

        // Test data
        Computer c1 = new Computer("x386", "DOS");
        computerDao.save(c1);

        Computer c2 = new Computer("Pentium_III", "Windows_95");
        computerDao.save(c2);

        Computer c3 = new Computer("Amiga_500", "noSystem");
        computerDao.save(c3);

        Adress a1 = new Adress("Kazimierza Wlk", "Brodnica");
        adressDao.save(a1);

        Adress a2 = new Adress("Kołobrzeska", "Gdańsk");
        adressDao.save(a2);

        Course co1 = new Course("Norwegian");
        courseDao.save(co1);

        Course co2 = new Course("German");
        courseDao.save(co2);

        Set<Course> student1Course = new HashSet<>();
        student1Course.add(co1);
        student1Course.add(co2);

        Set<Course> student2Course = new HashSet<>();
        student2Course.add(co2);



        studentDao.save(new Student("Michal", "Kowalski", LocalDate.of(1988, 4, 21), c1, a1, student1Course));
        studentDao.save(new Student("Marek", "Zmuda", LocalDate.of(2000, 12, 30), c2, a2, student2Course));

        LOG.info("System time zone is: {}", ZoneId.systemDefault());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final String action = req.getParameter("action");
        LOG.info("Requested action: {}", action);
        if (action == null || action.isEmpty()) {
            resp.getWriter().write("Empty action parameter.");
            return;
        }

        if (action.equals("findAll")) {
            findAll(req, resp);
        } else if (action.equals("add")) {
            addStudent(req, resp);
        } else if (action.equals("delete")) {
            deleteStudent(req, resp);
        } else if (action.equals("update")) {
            updateStudent(req, resp);
        } else if(action.equals("addToCourse")){
            addStudentToCourse(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }
    }

    private void addStudentToCourse(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Updating Student with id = {}", id);

        final Student existingStudent = studentDao.findById(id);
        if (existingStudent == null) {
            LOG.info("No Student found for id = {}, nothing to be updated", id);
        } else {
            final Long courseId = Long.parseLong(req.getParameter("courseId"));
            final Course course = courseDao.findById(courseId);

            Set<Course> courses = new HashSet<>();
            courses.add(course);
            courses.addAll(existingStudent.getCourses());

            existingStudent.setCourses(courses);

            studentDao.update(existingStudent);
            LOG.info("Student object updated: {}", existingStudent);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Updating Student with id = {}", id);

        final Student existingStudent = studentDao.findById(id);
        if (existingStudent == null) {
            LOG.info("No Student found for id = {}, nothing to be updated", id);
        } else {
            existingStudent.setName(req.getParameter("name"));
            existingStudent.setSurname(req.getParameter("surname"));
            existingStudent.setDateOfBirth(LocalDate.parse(req.getParameter("dateOfBirth")));

            Long computerId = Long.parseLong(req.getParameter("computerId"));
            Computer matchedComputer = computerDao.findById(computerId);
            existingStudent.setComputer(matchedComputer);

            Long adressId = Long.parseLong(req.getParameter("adressId"));
            Adress matchedAdress = adressDao.findById(adressId);
            existingStudent.setAdress(matchedAdress);

            studentDao.update(existingStudent);
            LOG.info("Student object updated: {}", existingStudent);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final Student p = new Student();
        p.setName(req.getParameter("name"));
        p.setSurname(req.getParameter("surname"));
        p.setDateOfBirth(LocalDate.parse(req.getParameter("dateOfBirth")));

        Long computerId = Long.parseLong(req.getParameter("computerId"));
        Computer matchedComputer = computerDao.findById(computerId);
        p.setComputer(matchedComputer);

        Long adressId = Long.parseLong(req.getParameter("adressId"));
        Adress matchedAdress = adressDao.findById(adressId);
        p.setAdress(matchedAdress);

        studentDao.save(p);
        LOG.info("Saved a new Student object: {}", p);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Removing Student with id = {}", id);

        studentDao.delete(id);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Student> result = studentDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Student p : result) {
            resp.getWriter().write(p.toString() + "\n");
        }
    }
}

