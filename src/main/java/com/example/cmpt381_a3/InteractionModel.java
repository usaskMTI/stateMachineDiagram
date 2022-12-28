package com.example.cmpt381_a3;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {

    private List<IModelListener> subscribers;
    private SMItem selectedNode;
    private Double tempX1, tempY1, tempX2, tempY2;
    private double scrollX, scrollY;

    private double prevScrollX, prevScrollY;
    private double dX, dY;
    private double worldWidth, worldHeight;
    private double viewWidth, viewHeight;

    enum CursorState {POINTER, MOVE, LINK}
    CursorState currentCursor = CursorState.POINTER;

    public InteractionModel()
    {
        subscribers = new ArrayList<>();

        scrollX = 0;
        scrollY = 0;
        prevScrollX = 0;
        prevScrollY = 0;
        viewWidth = 800;
        viewHeight = 800;
        worldWidth = 1600;
        worldHeight = 1600;
    }
    public void setViewWidth(double viewWidth) {
        this.viewWidth = viewWidth;
    }

    public void setViewHeight(double viewHeight) {
        this.viewHeight = viewHeight;
    }



    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public double getdX() {
        return dX;
    }

    public double getdY() {
        return dY;
    }

    public double getWorldWidth() {
        return worldWidth;
    }

    public double getWorldHeight() {
        return worldHeight;
    }

    public double getViewWidth() {
        return viewWidth;
    }

    public double getViewHeight() {
        return viewHeight;
    }




    public void setStateName(String stateName) {
        selectedNode.setStateName(stateName);
        notifySubscriber();
    }
    public void moveScrollRegion(double dX, double dY) {
        prevScrollX = scrollX;
        prevScrollY = scrollY;
        this.dX = dX;
        this.dY = dY;
        scrollX -= dX;
        scrollY -= dY;

        if (scrollX < 0) {
//        System.out.println("dX:"+ dX);
//        System.out.println("X:"+ scrollX+"\n");
            scrollX = 0;
            this.dX = 0;
        }
        else if (scrollX > worldWidth - viewWidth) {
            scrollX = worldWidth - viewWidth;
            this.dX = 0;
        }
        if (scrollY < 0) {
            scrollY = 0;
            this.dY = 0;
        }
        else if (scrollY > worldHeight - viewHeight) {
            scrollY = worldHeight - viewHeight;
            this.dY = 0;
        }
//        System.out.println("dX:"+ dX+" dY:"+ dY);
//        System.out.println("X:"+ scrollX+" Y:"+ scrollY+"\n");
        notifySubscriber();
    }

    public void updateTransitionData(String event, String context, String sideEffects) {
        if (selectedNode instanceof SMTransitionLink){
            ((SMTransitionLink) selectedNode).setEvent(event);
            ((SMTransitionLink) selectedNode).setContext(context);
            ((SMTransitionLink) selectedNode).setSideEffect(sideEffects);
            notifySubscriber();

        }
    }

    public void setStartX(double x) {
        tempX1 = x;
    }
    public void setStartY(double y) {
        tempY1 = y;
    }
    public void setEndX(double x) {
        tempX2 = x;
    }
    public void setEndY(double y) {
        tempY2 = y;
    }

    public double getStartX() {
       return tempX1;
    }
    public double getStartY() {
        return tempY1;
    }
    public double getEndX() {
        return tempX2;
    }
    public double getEndY() {
        return tempY2;
    }


    public void updateTemporaryLine() {
        notifySubscriber();
    }

    public CursorState getCursorState() {
        return currentCursor;
    }
    public void setCursorPointer() {
        currentCursor = CursorState.POINTER;
//        System.out.println("pointer");
    }
    public void setCursorMove() {
//        System.out.println("move");
        currentCursor = CursorState.MOVE;
    }
    public void setCursorLink() {
        currentCursor = CursorState.LINK;
//        System.out.println("link");
    }


    public void addSubscriber(IModelListener sub){
        this.subscribers.add(sub);
    }

    public void notifySubscriber(){
        subscribers.forEach(s -> s.iModelChanged());
    }

    public void setSelectedNode(SMItem focusNode){
        this.selectedNode = focusNode;
        notifySubscriber();
    }


    public void unselectNode(){
        this.selectedNode = null;
        notifySubscriber();
    }
    public SMItem getSelectedNode(){
        return selectedNode;
    }
}
