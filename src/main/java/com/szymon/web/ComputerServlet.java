package com.szymon.web;

import com.szymon.dao.ComputerDao;
import com.szymon.model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/computer")
public class ComputerServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(ComputerServlet.class);

    @Inject
    ComputerDao computerDao;

    @Override
    public void init() throws ServletException {
        super.init();

        computerDao.save(new Computer("x386", "DOS"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        LOG.info("Requested action {}", action);
        if (action == null || action.isEmpty()) {
            resp.getWriter().write("Empty action parameter");
            return;  // return statement stop execution method right here
        }

        if (action.equals("findAll")) {
            findAll(req, resp);
        } else if (action.equals("add")) {
            addComputer(req, resp);
        } else if (action.equals("delete")) {
            deleteComputer(req, resp);
        } else if (action.equals("update")) {
            updateComputer(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }
    }

    private void updateComputer(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Updating Computer with id = {}", id);

        final Computer existingComputer = computerDao.findById(id);
        if (existingComputer == null) {
            LOG.info("No Computer found for id = {}, nothing to be updated", id);
        } else {
            existingComputer.setName(req.getParameter("name"));
            existingComputer.setOperatingSystem(req.getParameter("operatingSystem"));

            computerDao.update(existingComputer);
            LOG.info("Computer object updated: {}", existingComputer);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void addComputer(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final Computer computer = new Computer();
        computer.setName(req.getParameter("name"));
        computer.setOperatingSystem(req.getParameter("operatingSystem"));

        computerDao.save(computer);
        LOG.info("Saved a new Computer object: {}", computer);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void deleteComputer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Removing Computer with id = {}", id);

        computerDao.delete(id);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Computer> result = computerDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Computer computer : result) {
            resp.getWriter().write(computer.toString() + "\n");
        }
    }

}
