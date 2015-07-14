/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.server.management;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.purber.common.entities.Order;

public class OrderManager {


    private static final OrderManager _instance = new OrderManager();
    

    private OrderManager() {
    }

    public static OrderManager getInstance() {
        return _instance;
    }
    
    public Order accept(Order order)
    {
        return add(order);
    }
    
    public Order getById(int id)
    {
        return get(id);
    }
    
    public Order cancel(int id)
    {
        return remove(get(id));
    }
    
    private Order add(Order order)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("purber");  
        EntityManager em = factory.createEntityManager();
        
        try{
        em.getTransaction().begin();  
        em.persist(order); 
        em.getTransaction().commit();
        }catch(Exception e)
        {
            e.printStackTrace();
            if(em.getTransaction().isActive())
            {
                em.getTransaction().rollback();
            }
            return null;
        }finally
        {
            em.close();
            factory.close();            
        }        
        
        return order;
    }
    
    private Order remove(Order order)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("purber");  
        EntityManager em = factory.createEntityManager();
        
        
        try{
        em.getTransaction().begin();  
        em.remove(order); 
        em.getTransaction().commit();
        }catch(Exception e)
        {
            e.printStackTrace();
            if(em.getTransaction().isActive())
            {
                em.getTransaction().rollback();
            }
            return order;
        }finally
        {
            em.close();
            factory.close();            
        }
        return null;
    }
    
    private Order get(int id)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("purber");  
        EntityManager em = factory.createEntityManager();
        Order order = null;
        
        try{
        em.getTransaction().begin();  
        order = em.find(Order.class, id); 
        em.getTransaction().commit();
        }catch(Exception e)
        {
            e.printStackTrace();
            if(em.getTransaction().isActive())
            {
                em.getTransaction().rollback();
            }
            return null;
        }finally
        {
            em.close();
            factory.close();            
        }
        return order;
    }
    
    private Order update(Order order)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("purber");  
        EntityManager em = factory.createEntityManager();
        
        Order od = null;
        
        
        try{
        em.getTransaction().begin();
        
        if (em.contains(order))
        {
            od =  em.merge(order);
        }    

        em.getTransaction().commit();
        }catch(Exception e)
        {
            e.printStackTrace();
            if(em.getTransaction().isActive())
            {
                em.getTransaction().rollback();
            }
            
        }finally
        {
            em.close();
            factory.close();            
        }
        
        return od;
        
    }     

}
