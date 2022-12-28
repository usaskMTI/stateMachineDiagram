package com.example.cmpt381_a3;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class EditorApp extends StackPane {
    private MainUI mainUI;
    private AppController controller;
    public EditorApp(){
        SMModel model = new SMModel();
        InteractionModel iModel = new InteractionModel();
        controller = new AppController();
        mainUI = new MainUI();


        controller.setModel(model);
        controller.setIModel(iModel);

        model.addSubscribers(mainUI);
        iModel.addSubscriber(mainUI);

        mainUI.setController(controller);
        mainUI.setIModel(iModel);
        mainUI.setModel(model);


        this.getChildren().add(mainUI);
    }

    public void passScene(Scene scene) {
        mainUI.passScene(scene);
        controller.setScene(scene);

    }
}
