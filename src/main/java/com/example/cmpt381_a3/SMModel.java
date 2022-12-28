package com.example.cmpt381_a3;

import java.util.ArrayList;
import java.util.List;

public class SMModel {
    private List<ModelListener> subscribers;
    private List<SMStateNode> SMStateNodesList;
    private List<SMTransitionLink> SMTransitionLinksList;


    public SMModel(){
        subscribers = new ArrayList<>();
        SMStateNodesList = new ArrayList<>();
        SMTransitionLinksList = new ArrayList<>();

    }

    public void addSMStateNode(double x, double y){
        SMStateNode newNode = new SMStateNode(x,y, 150,75);
        this.SMStateNodesList.add(newNode);
        notifySubscribers();
    }
    public void moveSMStateNode(SMItem node, double dx, double dy){
        node.move(dx,dy);

        updateLinks();
        notifySubscribers();
    }

    private void updateLinks() {
        getSMTransitionLinksList().forEach(link -> {
            link.update();
        });
    }

    public List<SMStateNode> getSMStateNodesList() {
        return this.SMStateNodesList;
    }
    public List<SMTransitionLink> getSMTransitionLinksList(){return this.SMTransitionLinksList;}

    public void addSubscribers(ModelListener subs){
        this.subscribers.add(subs);
    }
    public void notifySubscribers(){
        subscribers.forEach(s -> s.modelChanged());

    }

    public boolean hitSMStateNode(double x, double y)
    {
        for (SMStateNode sNode : SMStateNodesList)
        {
            if (sNode.contains(x,y))
                return true;
        }
        return false;
    }
    public boolean hitSMTransitionBox(double x, double y)
    {
        for (SMItem sNode : SMTransitionLinksList)
        {
            if (sNode.contains(x,y))
                return true;
        }
        return false;
    }

    public SMStateNode whichHit(double x,double y){
        for (SMStateNode sNode : SMStateNodesList)
        {
            if (sNode.contains(x,y))
                return sNode;
        }
        return null;
    }


    public void addSMTransitionLink(SMStateNode startNode, SMStateNode endNode) {
        SMTransitionLink newLink = new SMTransitionLink(startNode.getMidX(),startNode.getMidY(),endNode.getMidX(),endNode.getMidY());
        newLink.setStartNode(startNode);
        newLink.setEndNode(endNode);
        newLink.init();
//        System.out.println("Start Node from link"+startNode.getX()+" "+startNode.getY());
//        System.out.println("End Node from link"+endNode.getX()+" "+endNode.getY());
        SMTransitionLinksList.add(newLink);
        notifySubscribers();
    }

    public SMItem whichTransitionHIt(double x, double y) {
        for (SMItem sNode : SMTransitionLinksList)
        {
            if (sNode.contains(x,y))
                return sNode;
        }
        return null;
    }

    public void moveTransitionBox(SMItem selectedNode, double dX, double dY) {
        selectedNode.move(dX,dY);
        updateLinks();
        notifySubscribers();
    }

    public void deleteTransitionLink(SMTransitionLink selectedNode) {
        SMTransitionLinksList.removeIf(link -> link.equals(selectedNode));
        notifySubscribers();
    }

    public void deleteSMStateNode(SMStateNode selectedNode) {
        SMStateNodesList.removeIf(sNode -> sNode.equals(selectedNode));
        SMTransitionLinksList.removeIf(links -> {
            return selectedNode.contains(links.getX1(), links.getY1()) || selectedNode.contains(links.getX2(),links.getY2());
        });
        notifySubscribers();
    }
}
