import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


class PrepFactory {
    private VBox container;
    private PrepBtnFactory aff;
    private PrepBtnFactory neg;
    private TextField textField;
    PrepFactory(int length) {
        aff = new PrepBtnFactory(length, PrepBtnFactory.Team.AFF);
        neg = new PrepBtnFactory(length, PrepBtnFactory.Team.NEG);

        textField = makePrepTextField(length); // text input for prep duration

        HBox prepButtons = makePrepButtons(); // aff/neg buttons

        Button prepReset = makePrepReset(); // reset button

        HBox resetAndTF = new HBox(prepReset, textField);
        resetAndTF.setSpacing(50);
        resetAndTF.setPadding(new Insets(5,0,0,0));

        container = new VBox(prepButtons, resetAndTF);
        container.setPadding(new Insets(-10,0,0,0));
    }

    HBox makePrepButtons() {
        HBox prepButtons = new HBox(aff.make(), neg.make());
        prepButtons.setSpacing(30);
        prepButtons.setStyle("-fx-padding: 15 0 0 0;");
        prepButtons.setAlignment(Pos.BOTTOM_CENTER);
        return prepButtons;
    }

    Button makePrepReset() {
        Button prepReset = resetFactory();
        prepReset.setStyle("-fx-padding: 0 0 0 35;");
        prepReset.setOnAction((event) -> {
            aff.colorAff(); // fix formatting for both
            neg.colorNeg();
            aff.stop(); // stop both timelines
            neg.stop();
            aff.resetValue(); // reset number for both
            neg.resetValue();
        });
        return prepReset;
    }

    TextField makePrepTextField(int length) {
        textField = new TextField();
        textField.setMaxSize(30,10);
        textField.setText(Integer.toString(length/60));
        textField.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // remove non-numbers
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            } if (newValue.matches("0*")) { // remove "0", "00", etc.
                textField.setText(newValue.replaceAll("0", ""));
            } if (textField.getText().length() > 2) { // max 2 digits
                String s = textField.getText().substring(0, 2);
                textField.setText(s);
            }
            try {
                int num = Integer.parseInt(textField.getText());
                aff.changeStartValue(num);
                neg.changeStartValue(num);
            } catch (NumberFormatException nfe) {
                // bad input - NaN
            }
        });
        return textField;
    }

    private Button resetFactory() {
        Button reset = new Button("reset");
        reset.getStyleClass().add("reset");
        return reset;
    }

    VBox getContainer() {
        return container;
    }

}
