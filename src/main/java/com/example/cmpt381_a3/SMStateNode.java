package com.example.cmpt381_a3;

public class SMStateNode extends SMItem{
    private String stateName;
    public SMStateNode(double nx,double ny, double width, double height){
        super(nx, ny, width, height);
        stateName = "Default";
//        System.out.println("Node Original: "+nx+" "+ny);
//        System.out.println("Node Location: "+x+" "+y);
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;

        this.midX += dx;
        this.midY += dy;
    }

    public String getStateName(){
        return stateName;
    }
    public double getTranslateMidX() {
        return translateMidX;
    }

    public double getTranslateMidY() {
        return translateMidY;
    }


    public void setStateName(String stateName) {
        this.stateName = stateName;
//        System.out.println(stateName);
    }


    public boolean contains(double cx, double cy){
        if ((cx >= x) && (cx <= x+width)){
            if ((cy >= y) && (cy <= y+height)){
                return true;
            }
        }
        return false;
    }
}
