import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private final String PRODUCTS_FILE_ADDRESS;
    private final String FACTORS_FILE_ADDRESS;

    private List<Product> products;
    private List<Factor> factors;


    DataBase(List<Product> products,List<Factor> factors,String pAddress,String fAddress){
        this.factors = factors;
        this.products = products;
        PRODUCTS_FILE_ADDRESS = pAddress;
        FACTORS_FILE_ADDRESS = fAddress;
    }

    DataBase(List<Product> products,List<Factor> factors){
        this(products,factors,"P.TX","F.TX");
    }

    void save() throws IOException {
        OutputStreamWriter osw = new FileWriter(PRODUCTS_FILE_ADDRESS);
        for (Product p : products){
            String str = p.getForSave();
            osw.write(str);
        }
        osw.close();
    }
    void read() throws IOException {
        File file = new File(PRODUCTS_FILE_ADDRESS);
        if(!file.exists())
            return;
        InputStreamReader isr = new FileReader(PRODUCTS_FILE_ADDRESS);
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
    void fsave() throws IOException {
        OutputStreamWriter osw = new FileWriter(FACTORS_FILE_ADDRESS);
        for (Factor factor : factors) {
            osw.write(factor.getForSave() + "\n");
        }
        osw.close();
    }
    void fread() throws IOException {
        if (!new File(FACTORS_FILE_ADDRESS).exists())
            return;
        InputStreamReader isr = new FileReader(FACTORS_FILE_ADDRESS);
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
}
