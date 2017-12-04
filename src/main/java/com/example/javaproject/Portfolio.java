package com.example.javaproject;

public class Portfolio {

    private String name;
    private double netAssetValue;

    public Portfolio(String name, double netAssetValue){
        this.name = name;
        this.netAssetValue = netAssetValue;
    }

    public String getName(){
        return this.name;
    }

    public double getNetAssetValue(){
        return this.netAssetValue;
    }
}
