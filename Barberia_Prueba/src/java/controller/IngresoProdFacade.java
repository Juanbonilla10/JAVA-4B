/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.IngresoProd;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author soporte
 */
@Stateless
public class IngresoProdFacade extends AbstractFacade<IngresoProd> {

    @PersistenceContext(unitName = "Barberia_PruebaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IngresoProdFacade() {
        super(IngresoProd.class);
    }
    
}
