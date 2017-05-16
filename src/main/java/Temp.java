import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Temp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }


    private static final Integer STARTTIME = 1000;
    private Timeline timeline;
    private Label label = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);


    @Override
    public void start(Stage primaryStage) {
//        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Policy Timer");
//        primaryStage.setAlwaysOnTop(true);
        Group root = new Group();
        Scene scene = new Scene(root); // dimensions of window
        try {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        HBox hbox = new HBox();
        label.textProperty().bind(timeSeconds.asString()); // Bind the label text property to the timeSeconds property
        VBox btns = new VBox();
        btns.setSpacing(10);
        Button reset = new Button("reset");
        reset.getStyleClass().add("reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // somehow stop other event threads?
                label.setTextFill(Color.GREEN);
            }
        });

        VBox labelBox = new VBox();
        btns.getChildren().addAll(buttonFactory(8, "C"), buttonFactory(5, "R"), buttonFactory(3, "CX"));
        btns.setAlignment(Pos.TOP_CENTER);
        label.setAlignment(Pos.TOP_CENTER);
        labelBox.getChildren().addAll(label, reset);
        hbox.getChildren().addAll(btns, labelBox);
        root.getChildren().addAll(hbox);
        primaryStage.setResizable(false); // rm size changer
        primaryStage.setY(0); // top of screen
        primaryStage.setX(0); // left corner
        primaryStage.setScene(scene);
//        primaryStage.setOpacity(.8);
        primaryStage.show();
    }

    private Button buttonFactory(double t, String name){
//        final IntegerProperty timeProp = new SimpleIntegerProperty(time);
        Button button1 = new Button();
        button1.setText(name);
        final int time = (int)t*60;
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                label.setTextFill(Color.BLACK);
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds.set(time);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(time),
                                new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
                timeline.setOnFinished(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent args) {
                        label.setTextFill(Color.RED);
//                        label.setText("done"); // broken
                    }
                });
            }
        });
        return button1;
    }
}