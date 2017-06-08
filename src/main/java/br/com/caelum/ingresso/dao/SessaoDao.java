package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Sessao;

@Repository
public class SessaoDao {

    @PersistenceContext
    private EntityManager manager;

    public Sessao findOne(Integer id) {

        return manager.find(Sessao.class, id);
    }
    
    public void save(Sessao sessao) {
        manager.persist(sessao);
    }
    
    public List<Sessao> findAll() {
        return manager.createQuery("select s from Sessao s", Sessao.class).getResultList();
    }

    public void delete(Integer id) {
        manager.remove(findOne(id));
    }
}