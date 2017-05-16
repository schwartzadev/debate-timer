import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.SimpleDateFormat;

public class Temp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }


    private static final Integer STARTTIME = 100;
    private Timeline timeline;
    private Label label = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TEMP");
        Group root = new Group();
        Scene scene = new Scene(root, 600, 1000);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        // Bind the label text property to the timeSeconds property
        label.textProperty().bind(timeSeconds.asString());
        label.setStyle("-fx-font-size: 10em;");

        vbox.getChildren().addAll(buttonMaker(300, "top"), buttonMaker(50, "mid"), buttonMaker(3, "btm"), label);

        root.getChildren().addAll(vbox);
        primaryStage.setScene(scene);
        primaryStage.setOpacity(.8);
        primaryStage.show();
    }

    private Button buttonMaker(final int time, String name){
//        final IntegerProperty timeProp = new SimpleIntegerProperty(time);
        Button button1 = new Button();
        button1.setText(name);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                label.setTextFill(Color.BLACK);
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds.set(time);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(time+1),
                                new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
                timeline.setOnFinished(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent args) {
                        label.setTextFill(Color.RED);
                        label.setText("done");
                    }
                });
            }
        });
        return button1;
    }
}