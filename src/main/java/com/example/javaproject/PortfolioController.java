package com.example.javaproject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

@RestController
public class PortfolioController {

    private static Map<String, Portfolio> map = new ConcurrentHashMap<>();

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/portfolios")
    public Set<String> getPortfolios(){
        return map.keySet();
    }

    @RequestMapping(method = RequestMethod.GET, value ="/api/v1/portfolios/{portfolioName}")
    public ArrayList<Stock> fetchPositions(@PathVariable("portfolioName") String portfolioName) throws Exception {
        if(map.containsKey(portfolioName)){
            return map.get(portfolioName).getPositions();
        } else {
            throw new Exception("The portfolio you are searching for does not exist");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/v1/portfolios/net-asset-value")
    public ArrayList<PortfolioByValue> netAssetValue(){
        ArrayList<PortfolioByValue> portfolioList = new ArrayList<PortfolioByValue>();
        map.forEach((k, v) -> {
            int sum = v.getPositions().stream().mapToInt(stock -> (int) stock.getValue()).sum();
            PortfolioByValue portfolioAndValue = new PortfolioByValue(k, sum);
            portfolioList.add(portfolioAndValue);
        });
        return portfolioList;
    };

    @RequestMapping(method = RequestMethod.PUT, path = "/api/v1/portfolios/{portfolioName}")
    public void createPortfolio(@PathVariable("portfolioName") String portfolioName) throws Exception {
        if(map.containsKey(portfolioName)){
            throw new Exception("The portfolio you entered already exists. Please select a new name.");
        } else {
            Portfolio portfolio = new Portfolio(portfolioName);
            map.put(portfolioName, portfolio);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/portfolios/{portfolioName}/{ticker}")
    public void insertPosition(@PathVariable("portfolioName") String portfolioName,
                               @PathVariable("ticker") String ticker,
                               @RequestParam(value="marketValue", required=true) double marketValue) throws Exception {
        if(map.containsKey(portfolioName)){
            map.get(portfolioName).addPosition(new Stock(ticker, marketValue));
        } else {
            throw new Exception("The portfolio you are trying to add a position to does not exist. Please try again");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/portfolios/{portfolioName}/{ticker}")
    public void updatePosition(@PathVariable("portfolioName") String portfolioName,
                               @PathVariable("ticker") String ticker,
                               @RequestParam(value="marketValue", required=true) double marketValue) throws Exception {
        if(map.containsKey(portfolioName) && map.get(portfolioName).findPosition(ticker) != null){
            map.get(portfolioName).findPosition(ticker).setValue(marketValue);
        } else {
            throw new Exception("The portfolio or the position you entered does not exist. Please try again.");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/v1/portfolios/{portfolioName}")
    public void deletePortfolio(@PathVariable("portfolioName") String portfolioName) throws Exception {
        if(map.containsKey(portfolioName)){
            map.remove(portfolioName);
        } else {
            throw new Exception("The portfolio you are trying to delete does not exist. Please try again.");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/v1/portfolios/{portfolioName}/{ticker}")
    public void deletePosition(@PathVariable("portfolioName") String portfolioName,
                               @PathVariable("ticker") String ticker) throws Exception {
        if(map.containsKey(portfolioName) && map.get(portfolioName).findPosition(ticker) != null){
            Stock stock = map.get(portfolioName).findPosition(ticker);
            map.get(portfolioName).deletePosition(stock);
        } else {
            throw new Exception("The portfolio or the position you entered does not exist. Please try again.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/**")
    public void errorHandler() throws Exception {
        throw new Exception("The url you entered does not exist. Please check it and try again.");
    }

}

