import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

 class PrepBtnFactory {
    enum Team {
        AFF,
        NEG
    }

    private String affFormat = "-fx-text-fill: white; -fx-background-color: #0050FF;";
    private String negFormat = "-fx-text-fill: white; -fx-background-color: red;";


    private Team team;
    private boolean isCountdownStarted = false;
    private int count;
    private int time;
    private DateFormat df = new SimpleDateFormat("mm:ss");
    private Button btn = new Button();
    private Timeline timeline = new Timeline();
    private IntegerProperty integerProperty = new SimpleIntegerProperty(0);

     PrepBtnFactory(int t, Team team) {
         this.time = t;
         switch (team) {
             case AFF:
                 btn.setStyle("-fx-background-color: #152E66; -fx-text-fill: white;");
                 break;
             case NEG:
                 btn.setStyle("-fx-background-color: #7C1A1A; -fx-text-fill: white;");
                 break;
         }
     }

    Button make() {
        integerProperty.setValue(time);
        count = time;
        btn.prefWidth(20);
        btn.setText(df.format(integerProperty.getValue()*1000));
        btn.getStyleClass().add("prep");
        ChangeListener prepChangeListener = (ChangeListener<Number>) (observable, oldValue, newValue) -> {
            if (newValue.equals(0)) {
                CxTimer.isRunning = !CxTimer.isRunning;
                switch (team) {
                    case AFF:
                        btn.setStyle(affFormat);
                        break;
                    case NEG:
                        btn.setStyle(negFormat);
                        break;
                }
            }
            DateFormat dateFormat = new SimpleDateFormat("mm:ss");
            btn.setText(dateFormat.format((integerProperty.getValue() * 1000)));
        };

        integerProperty.addListener(prepChangeListener);
        return setButtonClickAction(btn);
    }

    Button setButtonClickAction(Button btn) {
        btn.setOnAction((event) -> {
            if (integerProperty.getValue() == time || isCountdownStarted) { // starts countdown if it hasn't been started
                startCountdown();
            } else { // stop countdown if already started
                stopCountdown();
            }
        });
        return btn;
    }

    void stopCountdown() {
        count = integerProperty.intValue();
        timeline.stop();
        isCountdownStarted = true;
    }

    void startCountdown() {
        integerProperty.set(count);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(count),
                        new KeyValue(integerProperty, 0)));
        isCountdownStarted = false;
        timeline.play();
    }

    void resetValue() {
        integerProperty.setValue(time);
        // todo: make this actually reset the prep buttons
    }

    void changeStartValue(int length) {
         this.stop();
         time = length*60;
         count = length*60;
         integerProperty.setValue(length*60);
         this.make();
    }

    void stop() {
        timeline.stop();
    }

     void colorAff() {
         btn.setStyle("-fx-background-color: #152E66; -fx-text-fill: white;");
     }

     void colorNeg() {
         btn.setStyle("-fx-background-color: #7C1A1A; -fx-text-fill: white;");
     }
}
