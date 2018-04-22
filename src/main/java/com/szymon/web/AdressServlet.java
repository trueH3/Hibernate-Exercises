package com.szymon.web;

import com.szymon.dao.AdressDao;
import com.szymon.model.Adress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.util.List;

@WebServlet( urlPatterns = "/adress")
public class AdressServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(AdressServlet.class);

    @Inject
    AdressDao adressDao;

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
            addAdress(req, resp);
        } else if (action.equals("delete")) {
            deleteAdress(req, resp);
        } else if (action.equals("update")) {
            updateAdress(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }
    }

    private void updateAdress (HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Updating Adress with id = {}", id);

        final Adress existingAdress = adressDao.findById(id);
        if (existingAdress == null) {
            LOG.info("No Adress found for id = {}, nothing to be updated", id);
        } else {
            existingAdress.setStreet(req.getParameter("street"));
            existingAdress.setCity(req.getParameter("city"));

            adressDao.update(existingAdress);
            LOG.info("Adress object updated: {}", existingAdress);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void addAdress(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final Adress adress = new Adress();
        adress.setStreet(req.getParameter("street"));
        adress.setCity(req.getParameter("city"));

        adressDao.save(adress);
        LOG.info("Saved a new Adress object: {}", adress);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void deleteAdress(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Removing Adress with id = {}", id);

        adressDao.delete(id);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Adress> result = adressDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Adress adress : result) {
            resp.getWriter().write(adress.toString() + "\n");
        }
    }
}
