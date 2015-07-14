/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.server.restimpl;

import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.purber.client.restimpl.OrderRequestImpl;
import com.purber.rest.api.Common;
import com.purber.rest.dto.OrderDto;



public class TestOrderResource {
    public int retId;
    @Test
    public void test()
    {
        OrderRequestImpl req = new OrderRequestImpl();        
        
        StatusType status = req.removeByOid(1);
        
        System.out.println(status);
        
        status = req.removeByOid(1);
        
        System.out.println(status);
        
        OrderDto order = new OrderDto();
        order.setCustomer_id(133);
        
        OrderDto ret = req.book(order);
        
        retId = ret.getId();
        
        System.out.println("order" + retId + " - " + "status:"+ ret.getStatus());
        System.out.println("order" + retId + " - " + "vendor_id:"+ ret.getVendor_id());
        System.out.println("order" + retId + " - " + "version:"+ ret.getVersion());
        
        ret = req.update(ret.getId(), Common.ActionCode.confirm, 99999);
        
        System.out.println("order" + retId + " - " + "status:"+ ret.getStatus());
        System.out.println("order" + retId + " - " + "vendor_id:"+ ret.getVendor_id());
        System.out.println("order" + retId + " - " + "version:"+ ret.getVersion());
        
        ret = req.getByOid(retId);
        
        System.out.println("order" + retId + " - " + "status:"+ ret.getStatus());
        System.out.println("order" + retId + " - " + "vendor_id:"+ ret.getVendor_id());
        System.out.println("order" + retId + " - " + "version:"+ ret.getVersion());
        
        
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                OrderRequestImpl req = new OrderRequestImpl();
                OrderDto ret = req.update(retId, Common.ActionCode.complete, 99999);

            }
        });
        t.start();
        
        ret = req.getByOid(retId,20,ret.getVersion());
        if(ret == null){System.out.println("null ret");}else{
        System.out.println("order" + retId + " - " + "status:"+ ret.getStatus());
        System.out.println("order" + retId + " - " + "vendor_id:"+ ret.getVendor_id());
        System.out.println("order" + retId + " - " + "version:"+ ret.getVersion());}
        
        
        Collection<OrderDto> list = req.getAllOrders();
        
        System.out.println(list.size());
        
        status = req.clean();
        
        System.out.println(status);
        
        list = req.getAllOrders();
        
        System.out.print(list.size());
        
        
    }
    
    //@Test
    public void test1()
    {
        String serverUri = "http://localhost:8080/Purber_Server/rest/v1/orders/";
        Client client = ClientBuilder.newClient();
        client.register(JacksonJsonProvider.class);

        OrderDto order = client.target(serverUri).path("1").request(MediaType.APPLICATION_JSON)
                .get(OrderDto.class);
        
        ObjectMapper mapper = new ObjectMapper();
        
        System.out.println(order);
        
        try {
            String jsonString = mapper.writeValueAsString(order);
            System.err.println(jsonString);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        
        
        
    }
    
    //@Test
    public void test2()
    {
        String serverUri = "http://localhost:8080/purber_server/rest/v1/orders/";
        Client client = ClientBuilder.newClient();
        client.register(JacksonJsonProvider.class);
        //client.register(OrderDto.class);
        
        OrderDto order = new OrderDto();
        order.setCustomer_id(12);
        
        final Response response = client.target(serverUri).
                request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(order));

        OrderDto returnOrder = response.readEntity(OrderDto.class);

        String result = "Get oid=" + returnOrder.getId()+ ",status="+ returnOrder.getStatus();
        
        Collection<OrderDto> list = new OrderRequestImpl().getOrderListByUid(122);
        
        
        
        
        
        for (OrderDto od:list)
        {
            System.out.println(od.getId());
        }
        
        //System.out.println(result);
        
        
        

//        Response response = client.target(serverUri).request(MediaType.APPLICATION_JSON).
//                post(Entity.json(order));
//        
//        OrderDto retOrder = response.readEntity(OrderDto.class);                
//        
//        System.out.println(retOrder);
        
        
    }
    
    
    //@Test
    public void test3()
    {
        String serverUri = "http://localhost:8080/Purber_Server/rest/v1/orders/";
        Client client = ClientBuilder.newClient();
        client.register(JacksonJsonProvider.class);
        //client.register(OrderDto.class);
        
        OrderDto order = new OrderDto();
        order.setCustomer_id(12);
        
        final Response response = client.target(serverUri).path("3/confirm").queryParam("vid", 1234).
                request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(""));

        OrderDto returnOrder = response.readEntity(OrderDto.class);

        String result = "Get oid=" + returnOrder.getId()+ ",status="+ returnOrder.getStatus();
        
        //System.out.println(result);
        
        
        

//        Response response = client.target(serverUri).request(MediaType.APPLICATION_JSON).
//                post(Entity.json(order));
//        
//        OrderDto retOrder = response.readEntity(OrderDto.class);                
//        
//        System.out.println(retOrder);
        
        
    }
    
    

}
