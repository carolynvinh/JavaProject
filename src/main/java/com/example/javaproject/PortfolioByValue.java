package com.example.javaproject;

public class PortfolioByValue {

    public String name;
    public double marketValue;

    public PortfolioByValue(String name, double marketValue){
        this.name = name;
        this.marketValue = marketValue;
    }

    public String getName(){
        return this.name;
    }

    public double getMarketValue(){
        return this.marketValue;
    }
}
