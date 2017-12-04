package com.example.javaproject;

public class Stock{

    private String name;
    private double value;

    public Stock(String name, double value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return this.name;
    }
}