import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

 class PrepBtnFactory {
    enum Team {
        AFF,
        NEG
    }

    private Team team;
    private boolean bool = false;
    private int count;
    private int t;
    private DateFormat df = new SimpleDateFormat("mm:ss");
    private Button btn = new Button();
    private Timeline tl = new Timeline();
    private IntegerProperty ip = new SimpleIntegerProperty(0);

     PrepBtnFactory(int t, Team team) {
         this.t = t;
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
        ip.setValue(t);
        count = t;
        btn.prefWidth(20);
        btn.setText(df.format(ip.getValue()*1000));
        btn.getStyleClass().add("prep");
        ChangeListener prepCL = (ChangeListener<Number>) (observable, oldValue, newValue) -> {
            if (newValue.equals(0)) {
                CxTimer.isRunning = !CxTimer.isRunning;
                switch (team) {
                    case AFF:
                        btn.setStyle("-fx-text-fill: white; -fx-background-color: #0050FF;");
                        break;
                    case NEG:
                        btn.setStyle("-fx-text-fill: white; -fx-background-color: red;");
                        break;
                }
            }
            DateFormat df = new SimpleDateFormat("mm:ss");
            btn.setText(df.format((ip.getValue() * 1000)));
        };

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

    void resetValue() {
        ip.setValue(t);
    }

    void changeStartValue(int length) {
         this.stop();
         t = length*60;
         count = length*60;
         ip.setValue(length*60);
         this.make();
    }

    void stop() {
        tl.stop();
    }

     void colorAff() {
         btn.setStyle("-fx-background-color: #152E66; -fx-text-fill: white;");
     }

     void colorNeg() {
         btn.setStyle("-fx-background-color: #7C1A1A; -fx-text-fill: white;");
     }
}
