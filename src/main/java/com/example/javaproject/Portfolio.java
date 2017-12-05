package com.example.javaproject;
import java.util.*;

public class Portfolio {

    private String name;
    private ArrayList<Stock> stocks;

    public Portfolio(String name){
        this.name = name;
        this.stocks = new ArrayList<Stock>();;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Stock> getPositions(){
        return this.stocks;
    }

    public void addPosition(Stock stock){
        this.stocks.add(stock);
    }

    public Stock findPosition(String ticker){
        Stock x = null;
        for (Stock stock : this.stocks) {
            if ((stock.getName()).equals(ticker)) {
                x = stock;
            }
        }
        return x;
    }

    public void deletePosition(Stock stock){
        this.stocks.remove(stock);
    }

}

