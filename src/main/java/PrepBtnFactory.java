import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

 class prepBtnFactory {
    prepBtnFactory(int t) {
        this.t = t;
    }

    // TODO fix all of these private vars, put into prepFactory
    private boolean bool = false;
    private int count;
    int t;
    private DateFormat df = new SimpleDateFormat("mm:ss");
    private Button btn = new Button();
    private Timeline tl = new Timeline();
    private IntegerProperty ip = new SimpleIntegerProperty(0);
    Button make() {
        ip.setValue(t);
        count = t;
        btn.setText(df.format(ip.getValue()*1000));
        btn.getStyleClass().add("prep");
        ChangeListener prepCL = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.equals(0)) {
//                    btn.setStyle("-fx-text-fill: white; -fx-background-color: red;");
                    btn.setStyle("-fx-text-fill: white; -fx-background-color: #0050FF;");
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

    void resetValue() {
        ip.setValue(t);
    }

    void colorAff() {
        btn.setStyle("-fx-background-color: #152E66; -fx-text-fill: white;");
    }

    void colorNeg() {
        btn.setStyle("-fx-background-color: #7C1A1A; -fx-text-fill: white;");
    }

    void stop() {
        tl.stop();
    }
}
