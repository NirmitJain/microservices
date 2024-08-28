package com.eazybytes.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public static final String CORRELATON_ID= "eazyBank-correlation-id";

    public String getCorrelatonId(HttpHeaders requestHeaders){
        if(null != requestHeaders.get(CORRELATON_ID)){
            List<String> requestHeadersList = requestHeaders.get(CORRELATON_ID);
            return requestHeadersList.stream().findFirst().get();
        }
        else {
            return null;
        }
    }

    public ServerWebExchange setRequestHeaders(ServerWebExchange exchange,String name,String value){
        return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange,String correlationId){
        return this.setRequestHeaders(exchange,CORRELATON_ID,correlationId);
    }
}
