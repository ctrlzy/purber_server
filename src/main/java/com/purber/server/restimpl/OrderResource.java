/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.server.restimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.purber.rest.api.Common;
import com.purber.rest.api.Common.ActionCode;
import com.purber.rest.api.OrderService;
import com.purber.rest.dto.ErrorInfoDto;
import com.purber.rest.dto.OrderDto;


@Path("v1")
public class OrderResource implements OrderService {
    
    private Logger LOG = Logger.getLogger(OrderResource.class);
    
    private static Collection<OrderDto> orderList = new ArrayList<OrderDto>();
    private static Map<Integer, OrderDto> orderMap = new HashMap<Integer, OrderDto>();
    private static Map<Integer, OrderDto> orderMapBySub = new HashMap<Integer, OrderDto>();
    private static int currentId = 1;
    
    @Override
    public Collection<OrderDto> list( int uid ) {
        
        if ( uid != 0 )
        {
            //OrderDto order= poll(uid, wait_time);
            Collection<OrderDto> result = new ArrayList<OrderDto>();
            for(OrderDto order:orderList)
            {
                if (order.getCustomer_id() == uid )
                {
                    result.add(order);
                }                
            }
            //if (order!=null) orderList.add(order);
            return result;
                        
        }else
        {        
            return orderList;
        }
    }

    @Override
    public OrderDto get(int id,int wait_time, int version) {
        LOG.info("get id = " + id);
        
        OrderDto order = orderMap.get(id);
        
        if (order == null)
        {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND)
                    .entity(new ErrorInfoDto("No such order.")).build());
        }
        
        do{
            if ( order.getVersion() > version )
            { 
                return order;
            }
            else if ( wait_time > 0)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                wait_time--;
            }
            
        }while( wait_time >0);        

        throw new WebApplicationException(Response.status(Status.NOT_MODIFIED)
                .entity(new ErrorInfoDto("No update.")).build());
        
    }
    
    @Override
    public OrderDto add(OrderDto order) {
        
        try {
            LOG.info("add body = " + IOUtils.toString(req.getInputStream()));
            LOG.info("add l = " + req.getContentLength());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        if ( order.getCustomer_id() == 0 )
        {
            order.setStatus(Common.ORDER_STATUS_REJECTED);
        }else
        {
            order.setStatus(Common.ORDER_STATUS_ACCEPTED);
        }
        
        order.setId(assignId());
        order.updateVesion();        
        
       
        if (orderMap.containsKey(order.getId()))
        {
            throw new WebApplicationException(Response.status(Status.CONFLICT).build());                    
        }
        
        orderMap.put(order.getId(), order);
        orderList.add(order);
        
        return order;
    }

/*    @Override
    public OrderDto add(OrderDto order) {
        order.setId(assignId());
        order.setStatus(2);
        if (orderMap.containsKey(order.getId()))
        {
            throw new WebApplicationException(Response.status(Status.CONFLICT).build());                    
        }
        
        orderMap.put(order.getId(), order);
        orderList.add(order);               
        
        return order;
    }*/

    @Override
    public Response delete(int id) {
        
        LOG.info("delete id = " + id);
        
        OrderDto order = orderMap.get(id);
        
        if (order == null)
        {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND)
                    .entity(new ErrorInfoDto("No such order.")).build());
        }
        
        orderMap.remove(id);
        orderList.remove(order);
        return null;
    }

   @Context
   private HttpServletRequest req;
   
    
    @Override
    public OrderDto update(int id, 
            ActionCode action,
            int vendor_id) {
        LOG.info("update id = " + id);
        LOG.info("update action = " + action);
        try {
            LOG.info("update body = " + IOUtils.toString(req.getInputStream()));
            LOG.info("update l = " + req.getContentLength());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        OrderDto order = orderMap.get(id);
        
        if (order == null)
        {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND)
                    .entity(new ErrorInfoDto("No such order.")).build());
        }
        
        LOG.info("vid = " + vendor_id);
        order.setStatus(action.ordinal());
        order.updateVesion();
        if (vendor_id!=0)order.setVendor_id(vendor_id);
        
        
        switch(action)
        {
        case confirm:
            if (order.getVendor_id()!=0)
            {
               orderMapBySub.put(order.getCustomer_id(), order);
            }
            break;
        case accept:
        case initial:
        case book:
        case cancel:
        
        case reject:            

        case complete:
        case close:

            
            
            break;
        default:
            throw new WebApplicationException("unknown action:"+action);              
        }
                
        return order;
    }
    
    @Override
    public Response clean(int uid) {
        
        orderList.clear();
        orderMap.clear();       
        
        return Response.noContent().build();
    }

    private int assignId()
    {
        return currentId++;
    }

}
