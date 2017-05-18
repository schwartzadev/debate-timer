import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    int t;
    private DateFormat df = new SimpleDateFormat("mm:ss");
    private Button btn = new Button();
    private Timeline tl = new Timeline();

     public IntegerProperty ipProperty() {
         return ip;
     }

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
//         this.recolor();
     }

    Button make() {
        ip.setValue(t);
        btn.prefWidth(20);
        btn.setText(df.format(ip.getValue()*1000));
        btn.getStyleClass().add("prep");
        ChangeListener prepCL = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.equals(0)) {
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
            }};

        ip.addListener(prepCL);
        btn.setOnAction((event) -> {
            if (ip.getValue() == t || bool) { // make if timeline is not running
                ip.set(t);
                tl.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(t),
                                new KeyValue(ip, 0)));
                bool = false;
                tl.play();
            } else {
                t = ip.intValue();
                tl.stop();
                bool = true;
            }
        });
        return btn;
    }

    void resetValue() {
        ip.setValue(t);
    }

    void stop() {
        tl.stop();
    }

    void recolor() {
        switch (team) {
            case AFF:
                btn.setStyle("-fx-background-color: #152E66; -fx-text-fill: white;");
                break;
            case NEG:
                btn.setStyle("-fx-background-color: #7C1A1A; -fx-text-fill: white;");
                break;
        }
    }
     void colorAff() {
         btn.setStyle("-fx-background-color: #152E66; -fx-text-fill: white;");
     }

     void colorNeg() {
         btn.setStyle("-fx-background-color: #7C1A1A; -fx-text-fill: white;");
     }

     void setIpVal(int val) {
         ip.setValue(val);
     }

     public void setT(int t) {
         this.t = t;
     }

     public void setBool(boolean bool) {
         this.bool = bool;
     }
}
