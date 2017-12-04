package com.example.javaproject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

@RestController
public class PortfolioController {

    static Map<String, ArrayList<Stock>> map = new ConcurrentHashMap<>();


    @RequestMapping(method = RequestMethod.PUT, path = "/api/v1/portfolios/{portfolioName}")
    public Map<String, ArrayList<Stock>> createPortfolio(@PathVariable("portfolioName") String name){
        ArrayList<Stock> list = new ArrayList<Stock>();
        map.put(name, list);
        return map;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/v1/portfolios/{portfolioName}")
    public Map<String, ArrayList<Stock>> deletePortfolio(@PathVariable("portfolioName") String name){
        map.remove(name);
        return map;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/portfolios/{portfolioName}/{ticker}")
    public Map<String, ArrayList<Stock>> insertPosition(@PathVariable("portfolioName") String name,
                                                   @PathVariable("ticker") String ticker,
                                                   @RequestParam(value="marketValue", required=true) double marketValue){

        Stock stock = new Stock(ticker, marketValue);
        map.get(name).add(stock);

        return map;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/portfolios")
    public Map<String, ArrayList<Stock>> getPortfolios(){
        return map;
    }


}

