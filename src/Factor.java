import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Factor {
    public static final String COST = "price";
    public static final String PROFIT = "sud";
    public  static final String CO = "counter";
    private List<Product> goods;
    private float price;
    private float sud;
    private float disscount;
    private int counter;

    public void setCounter(int e){
        counter = e;
    }
    public int getCounter(){
        return counter;
    }

    public List<Product> getGoods(){
        return goods;
    }

    public Factor(){
        goods = new ArrayList<>();
        price = 0;
        disscount = 0;
        sud = 0;
    }

    public void setSud(float sud) {
        this.sud = sud;
    }

    String getForSave(){
        String str = "";
                            //price,sud,diss,n(goods)
        str += String.format("%s %s %s %s ",getPrice(),getSud(),getDisscount(),goods.size());

        for (Product good : goods) {
            str += good.getCounter() + " ";
        }

        return str.trim();
    }
    static Factor extract(List<Product> products,String[] attrs){
        float price = Float.valueOf(attrs[0]);
        float pro = Float.valueOf(attrs[1]);
        float dis = Float.valueOf(attrs[2]);
        int n = Integer.valueOf(attrs[3]);
        ArrayList<Product> goods = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int index = Integer.valueOf(attrs[i+4]);
            goods.add(products.get(index-1));
        }
        Factor factor = new Factor();
        factor.goods = goods;
        factor.price = price;
        factor.sud = pro;
        factor.disscount = dis;
        return factor;
    }

    boolean canAdd(Product product,float number){
        return number<=product.getNumber();
    }

    boolean add(Product product,float number,boolean foreAdd){
        if(foreAdd){
            goods.add(product);
            product.setNumber(product.getNumber()-number);
            addPrice((number*product.getPrice()));
            addSud(number * product.getProfit());
            return true;
        }else if(canAdd(product,number)){
            goods.add(product);
            product.setNumber(product.getNumber()-number);
            addPrice((number)*product.getPrice());
            addSud(number*product.getProfit());
            return true;
        }else
            return false;
    }

    boolean add(Product product,float number){
        return add(product,number,false);
    }

    void remove(Product p,float n){
        if(!goods.contains(p))
            return;
        goods.remove(p);
        addPrice(-(n*p.getPrice()));
        addSud(-(n*p.getProfit()));
    }

    void setDisscount(float disscount) {
        this.disscount += disscount;
    }

    public float getPrice(){
        return  price;
    }
    public float getSud(){
        return sud;
    }
    float getDisscount(){
        return disscount;
    }
    float getCost(){
        return price - disscount;
    }

    void addPrice(float price){
        this.price += price;
    }
    void addSud(float s){
        sud+=s;
    }
    void setPrice(float pr){
        price = pr;
    }
}
