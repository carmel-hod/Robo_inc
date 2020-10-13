package robo.inc.autoclickers;

import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import robo.inc.Game_Master;
import robo.inc.R;

public class Autoclicker_base {
    protected String picture;
    protected double gatherSpeed;
    protected String description;
    protected double price;
    protected double priceGrowthRate;
    protected int amountOwned;
    protected String name;
    protected Type type;
    protected int id;
    protected boolean active;

    public Type getType(){
        return type;
    }

    public int getId() {
        return id;
    }



    public String toJson(){
        String json = "\t\t\t{\n" +
                "\t\t\t\t\"id\":\"" + id + "\",\n" +
                "\t\t\t\t\"name\":\"" + name + "\",\n" +
                "\t\t\t\t\"cost\":\"" + price +"\",\n" +
                "\t\t\t\t\"materiel\":\"" + 0 +"\",\n" +
                "\t\t\t\t\"photo\":\"" + picture +"\",\n" +
                "\t\t\t\t\"description\":\"" + description +"\",\n" +
                "\t\t\t\t\"gatherRate\":\"" + gatherSpeed +"\",\n" +
                "\t\t\t\t\"active\":\"" + active +"\",\n" +
                "\t\t\t\t\"priceGrowthRate\":\"" + priceGrowthRate +"\",\n" +
                "\t\t\t\t\"amountOwned\":\"" + amountOwned +"\"\n" +
                "\t\t\t}";
        return json;
    }

    public Autoclicker_base(int id, Boolean active, String picture, double gatherSpeed, String description, double price, double priceGrowthRate, int amountOwned, String name, Type type) {
        this.picture = picture;
        this.gatherSpeed = gatherSpeed;
        this.description = description;
        this.price = price;
        this.priceGrowthRate = priceGrowthRate;
        this.amountOwned = amountOwned;
        this.name = name;
        this.id = id;
        this.active = active;
        this.type = type;
    }

    public Autoclicker_base(JSONObject data, Type type) throws JSONException {
        this.picture = data.getString("photo");
        this.gatherSpeed = data.getDouble("gatherRate");
        this.description = data.getString("description");
        this.price = data.getDouble("cost");
        this.priceGrowthRate = data.getDouble("priceGrowthRate");
        this.amountOwned = data.getInt("amountOwned");
        this.name = data.getString( "name");
        this.type = type;
        this.active = data.getBoolean("active");
        this.id = data.getInt("id");
    }

    public String getPicture() {
        return picture;
    }

    public double getGatherSpeed() {
        return gatherSpeed;
    }

    public void setGatherSpeed(double gatherSpeed) {
        this.gatherSpeed = gatherSpeed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceGrowthRate() {
        return priceGrowthRate;
    }

    public void setPriceGrowthRate(double priceGrowthRate) {
        this.priceGrowthRate = priceGrowthRate;
    }

    public int getAmountOwned() {
        return amountOwned;
    }

    public void setAmountOwned(int amountOwned) {
        this.amountOwned = amountOwned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    static void makeLayout() {

    }
}
