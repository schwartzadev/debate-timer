import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CxTimer extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }


    private Timeline timeline;
    private Label label = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(0);


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CX Timer");
        primaryStage.setAlwaysOnTop(true);
        Group root = new Group();
        Scene scene = new Scene(root); // dimensions of window
        try {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        primaryStage.getIcons().add(new Image("icon.png"));
        HBox hbox = new HBox(); // box for the boxes
        timeSeconds.addListener(changeListener); // add listener to time var for label
        VBox btns = new VBox(); // box for the buttons
        btns.setSpacing(10);
        Button reset = new Button("reset");
        reset.getStyleClass().add("reset");
        reset.setOnAction((event) -> {
                timeline.stop();
                label.setStyle("-fx-text-fill: black; -fx-background-color: white;");
                timeSeconds.setValue(0);
            });

        label.setText("00:00"); // init label with text
        VBox labelBox = new VBox(); // box for label, reset button
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
        primaryStage.setOpacity(.9); // slightly transparent
        primaryStage.show();
    }

     private ChangeListener changeListener = new ChangeListener<Number>() { // make lambda
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (newValue.equals(30)) {
                label.setStyle("-fx-text-fill: red;"); // make text red for last 30 seconds
            } else if (newValue.equals(0)) {
                label.setStyle("-fx-text-fill: white; -fx-background-color: red;");
            }
            DateFormat df = new SimpleDateFormat("mm:ss");
            label.setText(df.format((timeSeconds.getValue() * 1000)));
        }};

    private Button buttonFactory(double t, String name){
        Button button1 = new Button();
        button1.setText(name);
        final int time = (int)t*60;
        button1.setOnAction((event) -> {
            label.setStyle("-fx-text-fill: black; -fx-background-color: white;");
            label.setTextFill(Color.BLACK);
            if (timeline != null) {
                timeline.stop();
            }
            timeSeconds.set(time);
            timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(time),
                            new KeyValue(timeSeconds, 0)));
            timeline.play();
        });
        return button1;
    }
}
