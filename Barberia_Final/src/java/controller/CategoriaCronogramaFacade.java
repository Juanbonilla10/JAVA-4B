/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CategoriaCronograma;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author soporte
 */
@Stateless
public class CategoriaCronogramaFacade extends AbstractFacade<CategoriaCronograma> {

    @PersistenceContext(unitName = "Barberia_FinalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaCronogramaFacade() {
        super(CategoriaCronograma.class);
    }
    
}
