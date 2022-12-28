package com.example.cmpt381_a3;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NodePropertiesView extends VBox implements IModelListener{
    private HBox headerBox;
    private Label headerLabel;
    private TextField stateNameField;

    private AppController controller;
    private InteractionModel iModel;

    public NodePropertiesView(){
        headerBox = new HBox();
        headerBox.setStyle("-fx-background-color: #85898d; -fx-alignment: center");
        headerBox.setMinHeight(50);
        headerLabel = new Label("State");
        headerLabel.setStyle("-fx-font-size: 20");
        headerBox.getChildren().add(headerLabel);

        Label stateName = new Label("State Name:");
        stateNameField = new TextField("Default");
        stateNameField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)){
                this.sendEnterKey();
            }
        });

        this.setMinWidth(200);
        this.getChildren().addAll(headerBox,stateName,stateNameField);
        this.setPadding(new Insets(5,5,5,5));
        this.setSpacing(2);
    }

    private void sendEnterKey() {
        controller.handleEnterKey(stateNameField.getText());
    }

    @Override
    public void iModelChanged() {
        if (iModel.getSelectedNode() instanceof SMStateNode){
            stateNameField.setText(((SMStateNode) iModel.getSelectedNode()).getStateName());
        }

    }

    public void setController(AppController controller) {
        this.controller = controller;
    }
    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }
}
