import com.sun.javafx.scene.control.IntegerField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

public class Factory {

    static Label label (String name, int r, int c){
        Label label = new Label();
        label.setText(name);
        GridPane.setConstraints(label,c,r);
        return label;
    }

    static Button button(String name, int r, int c){
        Button button = new Button(name);
        GridPane.setConstraints(button,c,r);
        return button;
    }
    static Button button(String name){
        return new Button(name);
    }

    static TextField textField(String prompt){
        TextField t = new TextField();
        t.setPromptText(prompt);
        return t;
    }
    static TextField textField(String prompt, int r, int c){
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        GridPane.setConstraints(textField,c,r);
        return textField;
    }
    static TextField textField(String name,int r,int c,int width){
        TextField t = textField(name,r,c);
        t.setMaxWidth(width);
        return t;
    }

    static IntegerField integerField(String name,int r,int c){
        IntegerField integerField = new IntegerField();
        integerField.setPromptText(name);
        GridPane.setConstraints(integerField,c,r);
        return integerField;
    }

    static void changeColor(Label label, Color to){
        label.setTextFill(to);
    }

    static GridPane gridPane(int vgp,int hgp,int lp,int rp,int tp,int bp){
        GridPane gridPane = new GridPane();
        gridPane.setVgap(vgp);
        gridPane.setHgap(hgp);
        gridPane.setPadding(new Insets(tp,rp,bp,lp));
        return gridPane;
    }
}
