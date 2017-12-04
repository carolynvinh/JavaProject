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

    public static Map<String, ArrayList<Stock>> map = new ConcurrentHashMap<>();

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/portfolios")
    public Set<String> getPortfolios(){
        return map.keySet();
    }

    @RequestMapping(method = RequestMethod.GET, value ="/api/v1/portfolios/{portfolioName}")
    public ArrayList<Stock> fetchPositions(@PathVariable("portfolioName") String portfolioName) throws Exception {
        if(map.containsKey(portfolioName)){
            return map.get(portfolioName);
        } else {
            throw new Exception("The portfolio you are searching for does not exist");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/v1/portfolios/net-asset-value")
    public ArrayList<Portfolio> netAssetValue(){
        ArrayList<Portfolio> portfolioList = new ArrayList<Portfolio>();
        map.forEach((k, v) -> {
            int sum = v.stream().mapToInt(stock -> (int) stock.getValue()).sum();
            Portfolio portfolioAndValue = new Portfolio(k, sum);
            portfolioList.add(portfolioAndValue);
        });
        return portfolioList;
    };

    @RequestMapping(method = RequestMethod.PUT, path = "/api/v1/portfolios/{portfolioName}")
    public Map<String, ArrayList<Stock>> createPortfolio(@PathVariable("portfolioName") String portfolioName) throws Exception {
        if(map.containsKey(portfolioName)){
            throw new Exception("The portfolio you entered already exists. Please select a new name.");
        } else {
            ArrayList<Stock> list = new ArrayList<Stock>();
            map.put(portfolioName, list);
            return map;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/portfolios/{portfolioName}/{ticker}")
    public Map<String, ArrayList<Stock>> insertPosition(@PathVariable("portfolioName") String portfolioName,
                                                        @PathVariable("ticker") String ticker,
                                                        @RequestParam(value="marketValue", required=true) double marketValue) throws Exception {
        if(map.containsKey(portfolioName)){
            Stock stock = new Stock(ticker, marketValue);
            map.get(portfolioName).add(stock);
            return map;
        } else {
            throw new Exception("The portfolio you would like to add a position to does not exist.");
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/portfolios/{portfolioName}/{ticker}")
    public Map<String, ArrayList<Stock>> updatePosition(@PathVariable("portfolioName") String portfolioName,
                                                        @PathVariable("ticker") String ticker,
                                                        @RequestParam(value="marketValue", required=true) double marketValue) throws Exception {
        ArrayList portfolio = map.get(portfolioName);
        boolean changed = false;
        for(int i = 0; i < portfolio.size(); i++){
            Stock position = (Stock) portfolio.get(i);
            if((position.getName()).equals(ticker)){
                position.setValue(marketValue);
                changed = true;
            }
        }
        if(changed){
            return map;
        } else {
            throw new Exception("The position you are searching for does not exist in this portfolio. Please try again.");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/v1/portfolios/{portfolioName}")
    public Map<String, ArrayList<Stock>> deletePortfolio(@PathVariable("portfolioName") String portfolioName) throws Exception {
        if(map.containsKey(portfolioName)){
            map.remove(portfolioName);
            return map;
        } else {
            throw new Exception("The portfolio you are trying to delete does not exist. Please try again");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/v1/portfolios/{portfolioName}/{ticker}")
    public Map<String, ArrayList<Stock>> deletePosition(@PathVariable("portfolioName") String portfolioName,
                                                        @PathVariable("ticker") String ticker) throws Exception {
        if(map.containsKey(portfolioName)){
            ArrayList portfolio = map.get(portfolioName);
            for(int i = 0; i < portfolio.size(); i++){
                Stock position = (Stock) portfolio.get(i);
                if((position.getName()).equals(ticker)){
                    portfolio.remove(position);
                }
            }
            return map;
        } else {
            throw new Exception("The position you are trying to delete does not exist in the portfolio. Please try again.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/**")
    public void errorHandler() throws Exception {
        throw new Exception("The url you entered does not exist. Please check it and try again.");
    }

}

