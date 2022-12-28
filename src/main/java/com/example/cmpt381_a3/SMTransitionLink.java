package com.example.cmpt381_a3;

import java.util.ArrayList;
import java.util.List;

public class SMTransitionLink extends SMItem {
    private double x1,y1,x2,y2, xTransit, yTransit;
    private String event;
    private String context;
    private String sideEffect;

    private SMStateNode startNode;
    private SMStateNode endNode;

    public SMStateNode getStartNode() {
        return startNode;
    }

    public SMStateNode getEndNode() {
        return endNode;
    }

    public SMTransitionLink(double nx1, double ny1, double nx2, double ny2) {
        super((nx1 + nx2)/2,(ny1 + ny2)/2, 150,160);

        x1 = nx1;
        y1 = ny1;

        // midpoint
        xTransit = (nx1 + nx2) / 2;
        yTransit = (ny1 + ny2) / 2;
        x2 = nx2;
        y2 = ny2;

        event = "No Event";
        context = "No Context";
        sideEffect = "No Side Effects";
    }

    @Override
    public boolean contains(double cx, double cy){
        if ((cx >= super.x) && (cx <= super.x+super.width)){
            if ((cy >= super.y) && (cy <= super.y+super.height)){
                return true;
            }
        }
        return false;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;

        this.midX += dx;
        this.midY += dy;
    }

    @Override
    public void setStateName(String stateName) {

    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public double getXTransit() {
        return xTransit;
    }

    public double getYTransit() {
        return yTransit;
    }

    public void update() {
        x1 = startNode.getMidX();
        y1 = startNode.getMidY();
        x2 = endNode.getMidX();
        y2 = endNode.getMidY();
        xTransit = super.getMidX();
        yTransit = super.getMidY();

        List<Double> result;
        result = nearPointBoundaryAdvanced(startNode,this, super.getMidX(), super.getMidY());
        xTransit = result.get(0);
        yTransit = result.get(1);
        result = nearPointBoundaryAdvanced(this,endNode, x2, y2);
        x2 = result.get(0);
        y2 = result.get(1);

    }
    private List<Double> nearPointBoundaryAdvanced(SMItem startNode, SMItem endNode, double x2, double y2){
        double gradient = (endNode.getMidY()-startNode.getMidY())/(endNode.getMidX()-startNode.getMidX());
        double threshold = endNode.getHeight()/endNode.getWidth();
//        System.out.println(gradient);
        if (gradient >= 0){
            if (startNode.getMidX() <= endNode.getMidX()){
                if (gradient <= threshold){
//                    System.out.println(gradient);
                    x2 = x2 - endNode.getWidth()/2;
                    y2 = gradient *(-endNode.getWidth()/2) + endNode.getMidY();
                }
                else if (gradient > threshold){
//                    System.out.println(gradient);
                    y2 = y2 - endNode.getHeight()/2;
                    x2 = (-endNode.getHeight()/2)/gradient + endNode.getMidX();
                }
            }
            else if (startNode.getMidX() > endNode.getMidX()){
                if (gradient <=threshold){
                    x2 = x2 + endNode.getWidth()/2;
                    y2 = gradient *(endNode.getWidth()/2) + endNode.getMidY();
                }
                else if (gradient >threshold){
                    y2 = y2 + endNode.getHeight()/2;
                    x2 = (endNode.getHeight()/2)/gradient + endNode.getMidX();
                }
            }
        }
        else if (gradient < 0){
            if (startNode.getMidX() <= endNode.getMidX()){
                if (gradient >= -threshold){
                    x2 = x2 - endNode.getWidth()/2;
                    y2 = gradient * (-endNode.getWidth()/2) +endNode.getMidY();
                }
                else if (gradient < -threshold){
                    y2 = y2 + endNode.getHeight()/2;
                    x2 = (endNode.getHeight()/2)/gradient + endNode.getMidX();
                }
            }
            else if (startNode.getMidX() > endNode.getMidX()){
                if (gradient >= -threshold){
                    x2 = x2 + endNode.getWidth()/2;
                    y2 = gradient* (endNode.getWidth()/2) + endNode.getMidY();
                }
                else if (gradient < -threshold){
                    y2 = y2 - endNode.getHeight()/2;
                    x2 = (-endNode.getHeight()/2)/gradient + endNode.getMidX();
                }
            }
        }
        List<Double> result = new ArrayList<>();
        result.add(x2);
        result.add(y2);
        return result;
    }
//    private List<Double> nearPointBoundary2(){
//        System.out.println(gradient);
//        if (gradient >= 0){
//            if (startNode.getMidX() <= endNode.getMidX()){
//                if (gradient <= 0.5){
////                    System.out.println(gradient);
//                    x2 = x2 - endNode.getWidth()/2;
//                    y2 = gradient *(-endNode.getWidth()/2) + endNode.getMidY();
//                }
//                else if (gradient > 0.5){
////                    System.out.println(gradient);
//                    y2 = y2 - endNode.getHeight()/2;
//                    x2 = (-endNode.getHeight()/2)/gradient + endNode.getMidX();
//                }
//            }
//            else if (startNode.getMidX() > endNode.getMidX()){
//                if (gradient <=0.5){
//                    x2 = x2 + endNode.getWidth()/2;
//                    y2 = gradient *(endNode.getWidth()/2) + endNode.getMidY();
//                }
//                else if (gradient >0.5){
//                    y2 = y2 + endNode.getHeight()/2;
//                    x2 = (endNode.getHeight()/2)/gradient + endNode.getMidX();
//                }
//            }
//        }
//        else if (gradient < 0){
//            if (startNode.getMidX() <= endNode.getMidX()){
//                if (gradient >= -0.5){
//                    x2 = x2 - endNode.getWidth()/2;
//                    y2 = gradient * (-endNode.getWidth()/2) +endNode.getMidY();
//                }
//                else if (gradient < -0.5){
//                    y2 = y2 + endNode.getHeight()/2;
//                    x2 = (endNode.getHeight()/2)/gradient + endNode.getMidX();
//                }
//            }
//            else if (startNode.getMidX() > endNode.getMidX()){
//                if (gradient >= -0.5){
//                    x2 = x2 + endNode.getWidth()/2;
//                    y2 = gradient* (endNode.getWidth()/2) + endNode.getMidY();
//                }
//                else if (gradient < -0.5){
//                    y2 = y2 - endNode.getHeight()/2;
//                    x2 = (-endNode.getHeight()/2)/gradient + endNode.getMidX();
//                }
//            }
//        }
//        List<Double> result = new ArrayList<>();
//        result.add(x2);
//        result.add(y2);
//        return result;
//    }
    public void setStartNode(SMStateNode startNode) {
        this.startNode = startNode;
    }
    public void setEndNode(SMStateNode endNode) {
        this.endNode = endNode;
    }


    public void init() {
        update();
    }
}
