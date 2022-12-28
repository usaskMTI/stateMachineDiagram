package com.example.cmpt381_a3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        EditorApp myUI = new EditorApp();
        Scene scene = new Scene(myUI, 1050, 800);

        myUI.passScene(scene);
        stage.setTitle("State Machine Editor");
        stage.setScene(scene);
        stage.show();

        myUI.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}