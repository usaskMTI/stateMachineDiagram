package com.example.cmpt381_a3;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

public class ToolPalette extends VBox {
    private Button pointerBtn;
    private Button moveBtn;
    private Button linkBtn;

    private InteractionModel iModel;
    private Scene mainScene;
    private AppController controller;
    private final int normalSize = 43;
    private final int extendSize = 48;



    public ToolPalette(){
        pointerBtn = new Button();
        pointerBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("pointer_img.png"))));
        pointerBtn.setCursor(Cursor.DEFAULT);
        pointerBtn.setStyle("-fx-background-color: #20b1aa");

        pointerBtn.setMinSize(extendSize,extendSize);
        pointerBtn.setPrefSize(extendSize,extendSize);
        pointerBtn.setMaxSize(extendSize,extendSize);

        linkBtn = new Button();
        linkBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("link_img.PNG"))));
        linkBtn.setCursor(Cursor.CROSSHAIR);
        linkBtn.setStyle("-fx-background-color: #20b1aa");

        linkBtn.setMinSize(normalSize,normalSize);
        linkBtn.setPrefSize(normalSize,normalSize);
        linkBtn.setMaxSize(normalSize,normalSize);

        moveBtn = new Button();
        moveBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("move_img.PNG"))));
        moveBtn.setCursor(Cursor.MOVE);
        moveBtn.setStyle("-fx-background-color: #20b1aa");

        moveBtn.setMinSize(normalSize,normalSize);
        moveBtn.setPrefSize(normalSize,normalSize);
        moveBtn.setMaxSize(normalSize,normalSize);


        pointerBtn.setOnAction(e-> {
            mainScene.setCursor(Cursor.DEFAULT);
            controller.setCursor("Default");
            handleButtonSize("Default");
        });


        moveBtn.setOnAction(e-> {
            mainScene.setCursor(Cursor.MOVE);
            controller.setCursor("Move");
            handleButtonSize("Move");

        });


        linkBtn.setOnAction(e-> {
            mainScene.setCursor(Cursor.CROSSHAIR);
            controller.setCursor("Crosshair");
            handleButtonSize("Crosshair");
        });

        this.setMinWidth(50);
        this.setSpacing(7);
//        this.getChildren().addAll(pointerBtn);
        this.setPadding(new Insets(0,0,0,1));
        this.getChildren().addAll(pointerBtn,moveBtn,linkBtn);

    }

    private void handleButtonSize(String cursorName) {
//        pointerBtn.resize(10,10);
        if (cursorName.equals("Default")){
            pointerBtn.setMinSize(extendSize,extendSize);
            pointerBtn.setPrefSize(extendSize,extendSize);
            pointerBtn.setMaxSize(extendSize,extendSize);

            linkBtn.setMinSize(normalSize,normalSize);
            linkBtn.setPrefSize(normalSize,normalSize);
            linkBtn.setMaxSize(normalSize,normalSize);

            moveBtn.setMinSize(normalSize,normalSize);
            moveBtn.setPrefSize(normalSize,normalSize);
            moveBtn.setMaxSize(normalSize,normalSize);
        }
        else if (cursorName.equals("Move")){
            pointerBtn.setMinSize(normalSize,normalSize);
            pointerBtn.setPrefSize(normalSize,normalSize);
            pointerBtn.setMaxSize(normalSize,normalSize);

            linkBtn.setMinSize(normalSize,normalSize);
            linkBtn.setPrefSize(normalSize,normalSize);
            linkBtn.setMaxSize(normalSize,normalSize);

            moveBtn.setMinSize(extendSize,extendSize);
            moveBtn.setPrefSize(extendSize,extendSize);
            moveBtn.setMaxSize(extendSize,extendSize);
        }
        else if (cursorName.equals("Crosshair")){
            pointerBtn.setMinSize(normalSize,normalSize);
            pointerBtn.setPrefSize(normalSize,normalSize);
            pointerBtn.setMaxSize(normalSize,normalSize);

            linkBtn.setMinSize(extendSize,extendSize);
            linkBtn.setPrefSize(extendSize,extendSize);
            linkBtn.setMaxSize(extendSize,extendSize);

            moveBtn.setMinSize(normalSize,normalSize);
            moveBtn.setPrefSize(normalSize,normalSize);
            moveBtn.setMaxSize(normalSize,normalSize);
        }
    }

    public void passScene(Scene mainScene) {
        this.mainScene = mainScene;

    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    public void setController(AppController controller) {
        this.controller = controller;
    }

}
