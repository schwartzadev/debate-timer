import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


class PrepFactory {
    private VBox container;
    private PrepBtnFactory aff;
    private PrepBtnFactory neg;
    private HBox prepBtns;
    private Button prepReset;
    private TextField textField;
    private int length;
    PrepFactory(int length) {
        this.length = length;
        aff = new PrepBtnFactory(length, PrepBtnFactory.Team.AFF);
        neg = new PrepBtnFactory(length, PrepBtnFactory.Team.NEG);
        prepReset = resetFactory();
        textField = new TextField();
        prepBtns = new HBox(aff.make(), neg.make());
        prepBtns.setSpacing(30);
        prepBtns.setStyle("-fx-padding: 15 0 0 0;");
        prepBtns.setAlignment(Pos.BOTTOM_CENTER);
        prepReset.setStyle("-fx-padding: 0 0 0 73;");
        prepReset.setOnAction((event) -> {
            try {
                // stop both tls for the buttons
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                // do nothing -- should only happen is timeline DNE
            }
//            aff.recolor(); // fix formatting for both TODO fix .recolor()
            aff.colorAff(); // fix formatting for both
            neg.colorNeg();
            aff.stop(); // stop both timelines
            neg.stop();
            aff.resetValue(); // reset number for both
            neg.resetValue();
        });
        container = new VBox(prepBtns, prepReset, textField);
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) { // remove non-numbers
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (textField.getText().length() > 2) { // max 2 digits
                    String s = textField.getText().substring(0, 2);
                    textField.setText(s);
                }
                try {
                    int num = Integer.parseInt(textField.getText());
                    System.out.println(num);
                    aff.changeStartVaule(num);
                    neg.changeStartVaule(num);
                } catch (NumberFormatException nfe) {
                    System.out.println("parsing empty string...");
                }
            }
        });
    }

    private Button resetFactory() {
        Button reset = new Button("reset");
        reset.getStyleClass().add("reset");
        return reset;
    }

    public VBox getContainer() {
        return container;
    }

}
