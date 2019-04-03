import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Button add;
    private Button show;
    private Button addFucktor;
    private Button showFucktor;
    private ArrayList<Factor> factors = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private static final String FILE_ADDRESS = "P.txt";
    ///******************************************
    private void save() throws IOException {
        OutputStreamWriter osw = new FileWriter(FILE_ADDRESS);
        for (Product p : products){
            String str = p.getForSave();
            osw.write(str);
        }
        osw.close();
    }
    private void read() throws IOException {
        File file = new File(FILE_ADDRESS);
        if(!file.exists())
            return;
        InputStreamReader isr = new FileReader(FILE_ADDRESS);
        StringBuilder txtB = new StringBuilder();
        int chr;
        while ((chr = isr.read())!=-1){
            txtB.append((char) chr);
        }

        chr = 1;
        String tx = txtB.toString();
        String[] lines = tx.split("\n");
        for (String line : lines) {
            String[] attr = line.split(" ");
            if(attr.length!=6 && attr.length != 4)
                continue;
            products.add(Product.Extract(attr));
            products.get(chr-1).setCounter(chr);
            chr++;
        }
    }
    private void fsave() throws IOException {
        OutputStreamWriter osw = new FileWriter("f.tx");
        for (Factor factor : factors) {
            osw.write(factor.getForSave() + "\n");
        }
        osw.close();
    }
    private void fread() throws IOException {
        if (!new File("f.tx").exists())
            return;
        InputStreamReader isr = new FileReader("f.tx");
        int ch;
        StringBuilder stringBuilder = new StringBuilder();
        while ((ch=isr.read())!=-1)
            stringBuilder.append((char) ch);
        String [] lines = stringBuilder.toString().trim().split("\n");
        for (String line : lines) {
            String[] attr = line.split(" ");
            if(attr.length < 3)
                continue;
            factors.add(Factor.extract(products, attr));
        }
        isr.close();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        products = new ArrayList<>();
        read();
        fread();

        stage.getIcons().addAll(new Image("assets\\store.png"));
        stage.setTitle("Store");
        stage.setResizable(false);

        Image image;
        ImageView imageView;
        stage.setOnCloseRequest(e->{
            try {
                save();
                fsave();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        int width = 60,height = 60;

        // row 1
        add = new Button();
        add.setOnAction(e-> {
            Product tmp = ADD.display();
            if(tmp != null) {
                products.add(tmp);
            }
        });
        image = new Image(getClass().getResourceAsStream("assets\\add.png"));
        imageView = new ImageView(image);
        imageView.setFitWidth(height);
        imageView.setFitHeight(width);
        add.setGraphic(imageView);

        show = new Button();
        show.setOnAction(e-> SHOW.display(products));
        image = new Image(getClass().getResourceAsStream("assets\\show.png"));
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        show.setGraphic(imageView);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(60,60,10,60));
        hBox.setSpacing(20);
        hBox.getChildren().addAll(add,show);

        // row = 2
        addFucktor = new Button();
        image = new Image(getClass().getResourceAsStream("assets\\addFactor.png"));
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        addFucktor.setGraphic(imageView);
        addFucktor.setOnAction(e->{
            Factor factor = MakeFactor.display(products);
            if(factor != null && factor.getCost() != 0)
                factors.add(factor);
        });

        showFucktor = new Button();
        image = new Image(getClass().getResourceAsStream("assets\\showFactors.png"));
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        showFucktor.setGraphic(imageView);
        showFucktor.setOnAction(e->{
            ShowFucktors.display(factors);
        });

        HBox hBox1 = new HBox();
        hBox1.setSpacing(20);
        hBox1.setPadding(new Insets(0,60,60,60));
        hBox1.getChildren().addAll(addFucktor,showFucktor);
        hBox1.setAlignment(Pos.CENTER);


        //main layout
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox,hBox1);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().addAll("Main_CSS.css");
        stage.setScene(scene);
        stage.show();

    }

}
