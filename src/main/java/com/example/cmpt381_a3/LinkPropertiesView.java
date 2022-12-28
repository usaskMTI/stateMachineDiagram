package com.example.cmpt381_a3;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LinkPropertiesView extends VBox implements IModelListener{
    private AppController controller;
    private InteractionModel iModel;

    private HBox headerBox;
    private Label headerLabel;
    private TextField eventField;
    private TextArea contextField;
    private TextArea sideEffectField;
    private Button updateBtn;

    public LinkPropertiesView(){
        headerBox = new HBox();
        headerBox.setStyle("-fx-background-color: #85898d; -fx-alignment: center");
        headerBox.setMinHeight(50);
        headerLabel = new Label("Transition");
        headerLabel.setStyle("-fx-font-size: 20");
        headerBox.getChildren().add(headerLabel);


        Label eventLabel = new Label("Event:");
        eventField = new TextField("No Event");

        Label contextLabel = new Label("Context:");
        contextField = new TextArea("No Context");
        contextLabel.setAlignment(Pos.TOP_CENTER);
        contextField.setPrefSize(180,200);

        Label sideEffectLabel = new Label("Side Effects");
        sideEffectField = new TextArea("No Side Effects");
        sideEffectField.setPrefSize(180,200);
        sideEffectLabel.setAlignment(Pos.TOP_LEFT);

        updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> sentUpdateClick());




        this.setMinWidth(200);
        this.getChildren().addAll(headerBox,eventLabel, eventField, contextLabel,contextField,sideEffectLabel,sideEffectField,updateBtn);

        this.setPadding(new Insets(5,5,5,5));
        this.setSpacing(2);
    }

    private void sentUpdateClick() {
        controller.handleUpdateClick(eventField.getText(),contextField.getText(), sideEffectField.getText());
    }

    @Override
    public void iModelChanged() {
        if (iModel.getSelectedNode()!= null){
            if (iModel.getSelectedNode() instanceof SMTransitionLink){
                eventField.setText(((SMTransitionLink) iModel.getSelectedNode()).getEvent());
                contextField.setText(((SMTransitionLink) iModel.getSelectedNode()).getContext());
                sideEffectField.setText(((SMTransitionLink) iModel.getSelectedNode()).getSideEffect());
            }
        }
    }

    public void setController(AppController controller) {
        this.controller = controller;
    }
    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }
}
