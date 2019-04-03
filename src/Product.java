import java.util.Objects;

public class Product {
    public static final String DATE_SEPRATOR = "/";
    public static final String
            NAME = "name",
            PRICE = "price",
            BD = "buyDate",
            ED = "expirationDate",
            NUM = "number",
            COUNTER = "counter";
    public static final String PROFIT = "profit";
    private String name;
    private float price;
    private String buyDate;
    private String expirationDate;
    private float number;
    private int counter ;
    private float profit;

    public Product(String n, Float pr, Float nu, Float pro, String b, String e) {
        name = n;
        price = pr;
        number = nu;
        profit = pro;
        buyDate = b;
        expirationDate = e;
    }

    static class Checker{
        private static final String DEFAUL_SEPRATOR = DATE_SEPRATOR;
        static boolean CHECKDATE(String date){
            return CHECKDATE(date,DEFAUL_SEPRATOR);
        }
        static boolean CHECKDATE(String date, String sprator){
            String[] dates = date.split(sprator);
            if(dates.length!=3)
                return false;
            String y = dates[0];
            String m = dates[1];
            String d = dates[2];
            if(y.length() != 4 || m.length()!=2 || d.length()!= 2)
                return false;
            try {
                Integer.valueOf(y);
                Integer.valueOf(m);
                Integer.valueOf(d);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }

    public Product(String name, float price, float num, String buyDate, String expirationDate) {
        this.name = name;
        this.price = price;
        this.number = num;
        this.buyDate = buyDate;
        this.expirationDate = expirationDate;
    }

    public float getProfit(){
        return profit;
    }
    public void setProfit(float s){
        profit = s;
    }

    public void setCounter(int c){
        counter = c;
    }
    public int getCounter(){
        return counter;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getPrice() == product.getPrice() &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getBuyDate(), product.getBuyDate()) &&
                Objects.equals(getExpirationDate(), product.getExpirationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), getBuyDate(), getExpirationDate());
    }

    @Override
    public String toString() {
        return String.format("[%s %sR %sN %s->%s]",getName(),getPrice(),getNumber(),getBuyDate(),getExpirationDate());
    }

    private static final String
            rg = " ",
            rp = "#";
    String getForSave(){
        return String.format("%s %s %s %s %s %s\n", // name price number profit buy expire
                getName().replaceAll(rg,rp),
                getPrice(),
                getNumber(),
                getProfit(),
                getBuyDate(),
                getExpirationDate());
    }

    static Product Extract(String[] attr) {
        if(attr.length == 6){
            return new Product(attr[0].replaceAll(rp, rg),
                    Float.valueOf(attr[1]),
                    Float.valueOf(attr[2]),
                    Float.valueOf(attr[3]),
                    attr[4],
                    attr[5]);
        }
        return new Product(attr[0].replaceAll(rp, rg),
                Float.valueOf(attr[1]),
                Float.valueOf(attr[2]),
                Float.valueOf(attr[3]),"","");
    }

}
