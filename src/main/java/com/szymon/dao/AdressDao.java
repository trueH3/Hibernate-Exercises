package com.szymon.dao;

import com.szymon.model.Adress;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AdressDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Long save(Adress a) {
        entityManager.persist(a);
        return a.getId();
    }

    public Adress update(Adress a) {
        return entityManager.merge(a);
    }

    public void delete(Long id) {
        final Adress a = entityManager.find(Adress.class, id);
        if (a != null) {
            entityManager.remove(a);
        }
    }

    public Adress findById (Long id){
        return entityManager.find(Adress.class, id);
    }

    public List<Adress> findAll(){
        final Query query = entityManager.createQuery("SELECT a FROM Adress a");
                return query.getResultList();
    }


}
