/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.rest.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import com.purber.rest.api.Common.ActionCode;
import com.purber.rest.dto.OrderDto;



public interface OrderService {

    @GET
    @Wrapped(element = "OrderList")
    @Path(Common.ORDER_PATH)
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
    @Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
    public Collection<OrderDto> list(@QueryParam("uid") int uid);
    
    @DELETE
    @Wrapped(element = "OrderList")
    @Path(Common.ORDER_PATH)
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
    @Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
    public Response clean(@QueryParam("uid") int uid);
    

    @GET
    @Path(Common.ORDER_PATH + "{id}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public OrderDto get(@PathParam("id") int id,@QueryParam("wait_time") int wait_time, @QueryParam("version") int version);

    @POST
    @Path(Common.ORDER_PATH)
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public OrderDto add(OrderDto order);

    @DELETE
    @Path(Common.ORDER_PATH + "{id}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response delete(@PathParam("id") int id);
    
    @PUT
    @Path(Common.ORDER_PATH + "{id}" + "/" + "{action}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public OrderDto update(@PathParam("id") int id,@PathParam("action") 
    ActionCode action,@QueryParam("vid") int vendor_id);    

}
