package com.example.cmpt381_a3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class DiagramView extends StackPane implements ModelListener, IModelListener{

    private final int ARR_SIZE = 10;
    private Canvas myCanvas;
    private GraphicsContext gc;

    private SMModel model;
    private InteractionModel iModel;

    private final double transparency = 0.3;

    public DiagramView(){
        myCanvas = new Canvas(1600,1600);
        gc = myCanvas.getGraphicsContext2D();
        this.setMinSize(800,800);
        this.setPrefSize(800,800);
        this.setMaxSize(1600,1600);
        this.setStyle("-fx-background-color: #88cee9");


        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());

//        gc.setFill(Color.SKYBLUE.darker());
        gc.setFill(Color.rgb(128,194,222,0.5));
        gc.fillRect(0,0,myCanvas.getWidth()/2,myCanvas.getHeight()/2);
        this.widthProperty().addListener(((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >=800 && newVal.doubleValue() <=1600){
                iModel.setViewWidth(newVal.doubleValue());
                resizeChange();
            }
        }));
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >=800 && newVal.doubleValue() <=1600){
                iModel.setViewHeight(newVal.doubleValue());
                resizeChange();
            }
        });

        this.getChildren().add(myCanvas);
    }

    private void resizeChange() {
        myCanvas.setWidth(iModel.getViewWidth());
        myCanvas.setHeight(iModel.getViewHeight());
        draw();
    }

    private void draw() {
//        gc.clearRect(0-iModel.getScrollX(),0-iModel.getScrollY(),iModel.getWorldWidth(),iModel.getWorldHeight());
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0,0,iModel.getWorldWidth()*2,iModel.getWorldHeight()*2);
        drawOverView();
        if (iModel.getCursorState() == InteractionModel.CursorState.MOVE) {

            gc.translate(iModel.getdX(), iModel.getdY());

        }
        // drawing the link lines
        gc.save();
        model.getSMTransitionLinksList().forEach(link -> {

            // the lines
            gc.setLineWidth(1);

            if (link.getStartNode().equals(link.getEndNode())){
                drawArrow(gc,link.getX1(),link.getY1(),link.getXTransit(),link.getYTransit());
                drawArrow(gc,link.getMidX(),link.getMidY(),link.getX2(),link.getY2());

                double r =Math.sqrt(Math.pow(Math.abs(link.getXTransit()-link.getX2()),2)+Math.pow(Math.abs(link.getYTransit()-link.getY2()),2))/2;
                System.out.println(r);
                gc.strokeOval((link.getXTransit()+link.getX2())/2 -r,(link.getYTransit()+link.getY2())/2 -r,r*2,r*2);

                gc.setFill(Color.LIGHTYELLOW);
                gc.fillRoundRect(link.getX(),link.getY(), link.getWidth(), link.getHeight(),10,10);
                gc.setLineWidth(2);
                gc.setStroke(Color.BLACK);
                gc.strokeRoundRect(link.getX(),link.getY(), link.getWidth(), link.getHeight(),10,10);
            }
            else {
                drawArrow(gc,link.getX1(),link.getY1(),link.getXTransit(),link.getYTransit());
                drawArrow(gc,link.getMidX(),link.getMidY(),link.getX2(),link.getY2());
//                gc.strokeLine(link.getX1(),link.getY1(),link.getXTransit(),link.getYTransit());
//                gc.strokeLine(link.getMidX(),link.getMidY(),link.getX2(),link.getY2());

                // the transition box
//                gc.setFill(Color.LIGHTYELLOW);
                gc.setFill(Color.rgb(255,255,224,0.95));

                gc.fillRoundRect(link.getX(),link.getY(), link.getWidth(), link.getHeight(),10,10);
                gc.setLineWidth(2);
                gc.setStroke(Color.BLACK);
                gc.strokeRoundRect(link.getX(),link.getY(), link.getWidth(), link.getHeight(),10,10);
            }


            String transitionInfo;
            transitionInfo = "·Event:\n"+link.getEvent()+"\n·Context:\n"+link.getContext()+"\n·Side Effects:\n"+link.getSideEffect();
            gc.setLineWidth(0.5);
            gc.setStroke(Color.BLACK);
            gc.strokeText(transitionInfo, link.getX()+25, link.getY()+40);
        });
        gc.restore();
        // end for drawing links

        // drawing the state nodes
        gc.save();
        model.getSMStateNodesList().forEach(sNode -> {
//            System.out.println("Node Location ("+ sNode.getMidX()+","+sNode.getMidY()+")");
            gc.setFill(Color.YELLOW);
            gc.fillRect(sNode.getX(),sNode.getY(), sNode.getWidth(), sNode.getHeight());
            gc.setLineWidth(2);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(sNode.getX(),sNode.getY(), sNode.getWidth(), sNode.getHeight());

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(0.75);
            gc.strokeText(sNode.getStateName(), sNode.getX()+sNode.getTranslateMidX()-22, sNode.getY()+sNode.getTranslateMidY()+5);

        });
        gc.restore();


        // seperated to handle iModel selected node which now can be both SNode and STransitionBox
        SMItem selectedNode = iModel.getSelectedNode();
        if (selectedNode != null){
            gc.save();
            gc.setLineWidth(2);
            gc.setStroke(Color.RED);
            if (selectedNode instanceof SMStateNode){
                gc.strokeRect(selectedNode.getX(),selectedNode.getY(), selectedNode.getWidth(), selectedNode.getHeight());
            }
            else {
                gc.strokeRoundRect(selectedNode.getX(),selectedNode.getY(), selectedNode.getWidth(), selectedNode.getHeight(),10,10);
            }

            gc.restore();
        }

        // during the transition link line
        if (iModel.getCursorState() == InteractionModel.CursorState.LINK)
        {
            gc.save();
            gc.setLineWidth(3);
            gc.strokeLine(iModel.getStartX(),iModel.getStartY(),iModel.getEndX(),iModel.getEndY());
            gc.restore();
        }
        drawOverViewItems();
    }
    private void drawOverView() {
        gc.save();
//        gc.setFill(Color.SKYBLUE.darker());
        gc.setFill(Color.rgb(128,194,222,0.8));
//        rgb(136, 206, 233)
//        gc.fillRect(iModel.getScrollX()+iModel.getScrollX()/2,iModel.getScrollY()+iModel.getScrollY()/2,
//                iModel.getViewWidth()/2,iModel.getViewHeight()/2);
        gc.fillRect(iModel.getScrollX()+iModel.getScrollX()/(1600/iModel.getViewWidth()),iModel.getScrollY()+iModel.getScrollY()/(1600/iModel.getViewHeight()),
                iModel.getViewWidth()/(1600/iModel.getViewWidth()),iModel.getViewHeight()/(1600/iModel.getViewHeight()));

//        System.out.println("scroll values: ("+iModel.getScrollX()+iModel.getScrollX()/(1600/iModel.getViewWidth())+" "+
//                iModel.getScrollY()+iModel.getScrollY()/(1600/iModel.getViewHeight())+")");
//        System.out.println("view width: "+iModel.getViewWidth()+" view height:" +iModel.getViewHeight()+"\n");

        gc.restore();
    }

    public void drawOverViewItems(){
        gc.save();
        gc.translate(iModel.getScrollX(),iModel.getScrollY());
        gc.scale(0.25,0.25);
//        gc.scale(iModel.getViewWidth()/(2*iModel.worldWidth),iModel.getViewHeight()/(2*iModel.worldHeight));

        model.getSMTransitionLinksList().forEach(link -> {

            // the lines
            gc.setLineWidth(1);

            if (link.getStartNode().equals(link.getEndNode())){

                gc.strokeLine(link.getX1(),link.getY1(),link.getXTransit(),link.getYTransit());
                gc.strokeLine(link.getMidX(),link.getMidY(),link.getX2(),link.getY2());

                double r =Math.sqrt(Math.pow(Math.abs(link.getXTransit()-link.getX2()),2)+Math.pow(Math.abs(link.getYTransit()-link.getY2()),2))/2;
//                System.out.println(r);
                gc.strokeOval((link.getXTransit()+link.getX2())/2 -r,(link.getYTransit()+link.getY2())/2 -r,r*2,r*2);

                gc.setFill(Color.rgb(255,255,224,transparency));
//                rgb(255,255,102)
                gc.fillRect(link.getX(),link.getY(), link.getWidth(), link.getHeight());
                gc.setStroke(Color.rgb(0,0,0,transparency));
                gc.strokeRect(link.getX(),link.getY(), link.getWidth(), link.getHeight());
            }
            else {
                gc.strokeLine(link.getX1(),link.getY1(),link.getXTransit(),link.getYTransit());
                gc.strokeLine(link.getMidX(),link.getMidY(),link.getX2(),link.getY2());

                // the transition box
                gc.setFill(Color.rgb(255,255,224,transparency));
                gc.fillRect(link.getX(),link.getY(), link.getWidth(), link.getHeight());
                gc.setStroke(Color.rgb(0,0,0,transparency));
                gc.strokeRect(link.getX(),link.getY(), link.getWidth(), link.getHeight());
            }


            String transitionInfo;
            transitionInfo = "·Event:\n"+link.getEvent()+"\n·Context:\n"+link.getContext()+"\n·Side Effects:\n"+link.getSideEffect();
            gc.setLineWidth(0.5);
            gc.setStroke(Color.BLACK);
            gc.strokeText(transitionInfo, link.getX()+25, link.getY()+40);
        });

        model.getSMStateNodesList().forEach( sNode -> {
            gc.setFill(Color.rgb(255,255,102,transparency));
            gc.fillRect(sNode.getX(),sNode.getY(), sNode.getWidth(), sNode.getHeight());
            gc.setStroke(Color.BLACK.brighter());
            gc.strokeRect(sNode.getX(),sNode.getY(), sNode.getWidth(), sNode.getHeight());

            gc.setFill(Color.rgb(0,0,0,transparency));
            gc.setLineWidth(0.75);
            gc.strokeText(sNode.getStateName(), sNode.getX()+sNode.getTranslateMidX()-22, sNode.getY()+sNode.getTranslateMidY()+5);
        });
        gc.restore();
    }

    public void setModel(SMModel newModel) {
        model = newModel;
    }
    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    public void iModelChanged() {
        draw();

    }

    @Override
    public void modelChanged() {
        draw();

    }


    void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        // copied and modified from stackoverflow

        x1 -= iModel.getScrollX();
        x2 -= iModel.getScrollX();
        y1 -= iModel.getScrollY();
        y2 -= iModel.getScrollY();
        gc.save();
        gc.setFill(Color.BLACK);

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);
        gc.fillPolygon(new double[]{len, len - ARR_SIZE, len - ARR_SIZE, len}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0},
                4);
        gc.restore();
    }
    public void setController(AppController controller) {
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);
    }
}
