import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyLabel extends Label {
//    private boolean click = false;
    private String nextColor;

    void setColor(String c){
        nextColor = c;
    }

    public MyLabel() {
    }

    public MyLabel(String s) {
        super(s);

    }

    public MyLabel(String s, Node node) {
        super(s, node);
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        setStyle("-fx-background-color: #9cd4e6");
    }

    public void deRequestFocus(){
        setStyle("-fx-background-color: dodgerblue");
    }
}
