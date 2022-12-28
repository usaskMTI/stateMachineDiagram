package com.example.cmpt381_a3;

public abstract class SMItem {
    double x,y;
    double midX, midY;
    double width, height;
    double translateMidX, translateMidY;

    public SMItem(double x, double y, double width, double height){
        this.midX = x;
        this.midY = y;
        this.width = width;
        this.height = height;
        this.translateMidX = width/2;
        this.translateMidY = height/2;

        this.x = x - translateMidX;
        this.y = y - translateMidY;

    }

    abstract boolean contains(double x, double y);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMidX() {
        return midX;
    }

    public double getMidY() {
        return midY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    abstract void move(double dx, double dy);

    public double getTranslateMidX(){
        return translateMidX;
    }

    public double getTranslateMidY(){
        return translateMidY;
    }

//    public abstract boolean equals(SMItem targetSMItem);
    public abstract void setStateName(String stateName);
}
