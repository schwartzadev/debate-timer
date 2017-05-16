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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final Integer STARTTIME = 15;
    private Timeline timeline;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Policy Timer");
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 500);

        // Bind the label1 text property to the timeSeconds property
        label1.textProperty().bind(timeSeconds.asString());
        label1.setStyle("-fx-font-size: 10em;");

        Button button1 = new Button();
        button1.setText("Start Timer");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds.set(STARTTIME);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(STARTTIME+1),
                                new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
            }
        });



        //********************

        Button button2 = new Button();
        button2.setText("Start Timer");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds.set(STARTTIME);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(STARTTIME+1),
                                new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
            }
        });

        //********************


        VBox vb1 = new VBox(20);             // gap between components is 20
        vb1.setAlignment(Pos.CENTER);        // center the components within VBox

        vb1.setPrefWidth(scene.getWidth());
        vb1.getChildren().addAll(button1, label1);
        vb1.setLayoutY(30);

        VBox vb2 = new VBox(20);             // gap between components is 20
        vb2.setAlignment(Pos.CENTER);        // center the components within VBox

        vb2.setPrefWidth(scene.getWidth());
        vb2.getChildren().addAll(button2, label2);
        vb2.setLayoutY(30);



        root.getChildren().addAll(vb1, vb2);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}