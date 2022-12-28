package com.example.cmpt381_a3;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class AppController {
    private SMModel model;
    private InteractionModel iModel;
    private Scene scene;

    private double prevX, prevY;
    private SMStateNode startNode, endNode;
    private double dX, dY;

    public void handleEnterKey(String stateName) {
        if (iModel.getSelectedNode() != null){
           iModel.setStateName(stateName);
//            System.out.println("Enter key press handled");
        }

    }

    public void handleUpdateClick(String event, String context, String sideEffects) {
        iModel.updateTransitionData(event,context,sideEffects);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void handleDeleteKey() {
        if (iModel.getSelectedNode() != null){
            if (iModel.getSelectedNode() instanceof SMTransitionLink){
                model.deleteTransitionLink((SMTransitionLink) iModel.getSelectedNode());
            }
            if (iModel.getSelectedNode() instanceof SMStateNode){
                model.deleteSMStateNode((SMStateNode) iModel.getSelectedNode());
            }
            iModel.unselectNode();
        }
    }


    enum State {READY, PREPARE_CREATE,DRAGGING}
    State currentState = State.READY;

    public AppController(){}

    public void handlePressed(MouseEvent event) {
        // mouse analysis
        double modifiedX = event.getX() + iModel.getScrollX();
        double modifiedY = event.getY() + iModel.getScrollY();



        switch (iModel.getCursorState()){
            case POINTER -> {
                switch (currentState){
                    case READY -> {
                        if (model.hitSMStateNode(modifiedX, modifiedY))
                        {
                            SMStateNode node = model.whichHit(modifiedX, modifiedY);
                            iModel.setSelectedNode(node);
                            prevX = modifiedX;
                            prevY = modifiedY;
                            currentState = State.DRAGGING;
                        }
                        else if (model.hitSMTransitionBox(modifiedX,modifiedY)){
                            SMItem node = model.whichTransitionHIt(modifiedX, modifiedY);
                            iModel.setSelectedNode(node);
                            prevX =modifiedX;
                            prevY = modifiedY;
                            currentState = State.DRAGGING;
                        }
                        else {
                            currentState = State.PREPARE_CREATE;
                        }
                    }
                }
            }
            case LINK -> {
                switch (currentState){
                    case READY -> {
                        if (model.hitSMStateNode(modifiedX,modifiedY)){
                            iModel.setStartX(modifiedX);
                            iModel.setStartY(modifiedY);
                            startNode = model.whichHit(modifiedX,modifiedY);
                            currentState = State.DRAGGING;
                        }
                        else{
                            currentState = State.READY;
                        }
                    }
                }
            }
            case MOVE -> {
                        prevX = event.getX();
                        prevY = event.getY();
                }
            }
        }

    public void handleDragged(MouseEvent event) {
        double modifiedX = event.getX() + iModel.getScrollX();
        double modifiedY = event.getY() + iModel.getScrollY();

//        System.out.println("ScrollX: "+iModel.getScrollX()+" ScrollY: "+ iModel.getScrollY());
//        System.out.println("dX: "+iModel.getdX()+" dY: "+ iModel.getdY());
//        System.out.println("MouseX: "+event.getX()+" MouseY: "+ event.getY());
//        System.out.println("ModifiedX: "+modifiedX+" ModifiedY: "+ modifiedY+"\n");
        switch (iModel.getCursorState()){
            case POINTER -> {
                switch (currentState){
                    case PREPARE_CREATE -> {
                        currentState = State.READY;

                    }
                    case DRAGGING -> {
                        dX = modifiedX - prevX;
                        dY = modifiedY - prevY;
                        prevX = modifiedX;
                        prevY = modifiedY;
                        if (iModel.getSelectedNode() instanceof SMStateNode){
                            model.moveSMStateNode(iModel.getSelectedNode(),dX,dY);
                        }
                        else if (iModel.getSelectedNode() instanceof SMTransitionLink){
                            model.moveTransitionBox(iModel.getSelectedNode(),dX,dY);
                        }
                    }
                }
            }
            case LINK -> {
                switch (currentState){
                    case DRAGGING -> {
                        iModel.setEndX(modifiedX);
                        iModel.setEndY(modifiedY);
                        iModel.updateTemporaryLine();
                    }
                }
            }
            case MOVE -> {
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                prevX = event.getX();
                prevY = event.getY();
                iModel.moveScrollRegion(dX,dY);

            }
        }

    }

    public void handleReleased(MouseEvent event) {
        double modifiedX = event.getX() + iModel.getScrollX();
        double modifiedY = event.getY() + iModel.getScrollY();
        switch (iModel.getCursorState()){
            case POINTER -> {
                switch (currentState){
                    case PREPARE_CREATE -> {
                        model.addSMStateNode(modifiedX,modifiedY);

                        // for making box selected after creation
                        iModel.setSelectedNode(model.whichHit(modifiedX,modifiedY));
                        currentState = State.READY;
                    }
                    case DRAGGING -> {
//                        iModel.unselectNode(); // or else the nodes are not selected to name State
                        currentState = State.READY;
                    }
                }
            }
            case LINK -> {
                switch (currentState){
                    case DRAGGING -> {
                        if (model.hitSMStateNode(modifiedX,modifiedY)){
                            iModel.setEndX(modifiedX);
                            iModel.setEndY(modifiedY);
                            endNode = model.whichHit(modifiedX,modifiedY);

                            // passing start and end nodes to automatically create link from the center of the nodes
                            // I could just pass event x and y to create links from any place in the node
                            model.addSMTransitionLink(startNode,endNode);
//                            startNode.addTranstionData() may use this to delete the transtion box

                            // for making box selected after creation
                            iModel.setSelectedNode(model.whichTransitionHIt((startNode.getMidX()+endNode.getMidX())/2,
                                    (startNode.getMidY()+endNode.getMidY())/2));



                        }
                        // to remove any temporary lines which are not on the nodes
                        iModel.setStartX(-1);
                        iModel.setStartY(-1);
                        iModel.setEndX(-1);
                        iModel.setEndY(-1);
                        iModel.updateTemporaryLine();

                        currentState = State.READY;
                    }
                }

            }
            case MOVE -> {
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                iModel.moveScrollRegion(dX,dY);
            }
        }

    }


    public void setModel(SMModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;

    }
    public void setCursor(String cursor) {
        if (cursor.equals("Default")){
            iModel.setCursorPointer();
        }
        else if (cursor.equals("Move")){
            iModel.setCursorMove();
        }
        else if (cursor.equals("Crosshair")){
            iModel.setCursorLink();
        }


    }
}
