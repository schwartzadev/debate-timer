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


    private static final Integer STARTTIME = 100;
    private Timeline timeline;
    private Label label = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Policy Timer");
        Group root = new Group();
//        Scene scene = new Scene(root, 600, 600); // dimensions of window
        Scene scene = new Scene(root); // dimensions of window
        try {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        HBox hbox = new HBox();


        // Bind the label text property to the timeSeconds property
        label.textProperty().bind(timeSeconds.asString());


        VBox btns = new VBox();
        btns.setSpacing(10);
        Button btnC = buttonFactory(8, "C");
        Button btnR = buttonFactory(5, "R");
        Button btnCX = buttonFactory(3, "CX");

        btns.getChildren().addAll(btnC, btnR, btnCX);
        btns.setAlignment(Pos.TOP_CENTER);
        label.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btns, label);
        root.getChildren().addAll(hbox);
        primaryStage.setScene(scene);
        primaryStage.setOpacity(.8);
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