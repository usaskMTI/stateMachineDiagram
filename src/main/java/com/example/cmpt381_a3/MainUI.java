package com.example.cmpt381_a3;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class MainUI extends BorderPane implements ModelListener, IModelListener {
    private DiagramView myCanvas;
    private ToolPalette myPalette;
    private NodePropertiesView myNodePropertyView;
    private LinkPropertiesView myLinkPropertyView;
    private Scene myScene;


    private AppController controller;
    private SMModel model;
    private InteractionModel iModel;

    public MainUI(){
        myCanvas = new DiagramView();
        myPalette = new ToolPalette();
        myNodePropertyView = new NodePropertiesView();
        myLinkPropertyView = new LinkPropertiesView();

        myPalette.toFront();
        this.setLeft(myPalette);
        this.setCenter(myCanvas);

        this.setRight(myNodePropertyView);


    }

    public void switchEditView(){
        if (iModel.getSelectedNode() instanceof SMTransitionLink){
            this.setRight(myLinkPropertyView);
        }
        else {
            this.setRight(myNodePropertyView);
        }
    }

    public void passScene(Scene mainScene){
        myPalette.passScene(mainScene);

        this.myScene = mainScene;
        myScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DELETE){
                controller.handleDeleteKey();
            }

        });

    }

    @Override
    public void iModelChanged() {
        switchEditView();
        myCanvas.iModelChanged();
        myNodePropertyView.iModelChanged();
        myLinkPropertyView.iModelChanged();
    }

    @Override
    public void modelChanged() {
        myCanvas.modelChanged();
    }

    public void setController(AppController controller) {
        this.controller = controller;
        this.myCanvas.setController(controller);
        this.myPalette.setController(controller);
        this.myNodePropertyView.setController(controller);
        this.myLinkPropertyView.setController(controller);
    }
    public void setModel(SMModel model) {
        this.model = model;
        this.myCanvas.setModel(model);
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
        this.myCanvas.setIModel(iModel);
        this.myPalette.setIModel(iModel);
        this.myNodePropertyView.setIModel(iModel);
        this.myLinkPropertyView.setIModel(iModel);
    }


}
