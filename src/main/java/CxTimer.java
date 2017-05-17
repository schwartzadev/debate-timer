import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private IntegerProperty affPrep = new SimpleIntegerProperty(0);
    private IntegerProperty negPrep = new SimpleIntegerProperty(0);
    private Timeline tl = new Timeline();
    private boolean bool = false;
    private int count;


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
        timeSeconds.addListener(timerCL); // add listener to time var for label

        Button reset = new Button("reset");
        reset.getStyleClass().add("reset");
        reset.setOnAction((event) -> {
            try {
                timeline.stop();
            } catch (NullPointerException npe) {
                // do nothing -- should only happen is timeline DNE
            }
                label.setStyle("-fx-text-fill: black; -fx-background-color: white;");
                timeSeconds.setValue(0);
            });
        label.setText("00:00"); // init label with text
        label.setAlignment(Pos.TOP_CENTER);

        VBox btns = new VBox(buttonFactory(8, "C"), buttonFactory(5, "R"), buttonFactory(3, "CX")); // box for the buttons
        btns.setSpacing(10);
        btns.setAlignment(Pos.TOP_CENTER);

        HBox prep = new HBox(prepFactory(300, affPrep), prepFactory(300, negPrep));
        prep.setSpacing(20);

        VBox labelBox = new VBox(label, reset, prep); // box for label, reset button
        HBox hbox = new HBox(btns, labelBox); // box for the boxes

        root.getChildren().addAll(hbox);

        // build window
        primaryStage.setResizable(false); // rm size changer
        primaryStage.setY(0); // top of screen
        primaryStage.setX(0); // left corner
        primaryStage.setScene(scene);
        primaryStage.setOpacity(.9); // slightly transparent
        primaryStage.show();
    }

     private ChangeListener timerCL = new ChangeListener<Number>() { // make lambda
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (newValue.equals(30)) {
                label.setStyle("-fx-text-fill: red;"); // make text red for last 30 seconds
            } else if (newValue.equals(0)) {
                label.setStyle("-fx-text-fill: white; -fx-background-color: red;");
            }
            DateFormat df = new SimpleDateFormat("mm:ss");
            label.setText(df.format((timeSeconds.getValue() * 1000)));
        }};

    private Button prepFactory(int t, IntegerProperty ip){
        count = t;
        DateFormat df = new SimpleDateFormat("mm:ss");
        Button btn = new Button();
        final int time = t;
        ip.setValue(time);
        btn.setText(df.format(ip.getValue()*1000));

        ChangeListener prepCL = new ChangeListener<Number>() { // make lambda
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.equals(30)) {
                    btn.setStyle("-fx-text-fill: red;"); // make text red for last 30 seconds
                } else if (newValue.equals(0)) {
                    btn.setStyle("-fx-text-fill: white; -fx-background-color: red;");
                }
                DateFormat df = new SimpleDateFormat("mm:ss");
                btn.setText(df.format((ip.getValue() * 1000)));
            }};

        ip.addListener(prepCL);
        btn.setOnAction((event) -> {
            if (ip.getValue() == t || bool) { // make if timeline is not running
                ip.set(count);
                tl.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(count),
                                new KeyValue(ip, 0)));
                bool = false;
                tl.play();
            } else {
                count = ip.intValue();
                tl.stop();
                bool = true;
            }
        });
        return btn;
    }

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
