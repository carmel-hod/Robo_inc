package robo.inc;

import android.os.Handler;
import android.os.Looper;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import robo.inc.autoclickers.Autoclicker_base;
import robo.inc.autoclickers.Type;

public class Game_Master {
    public List<Autoclicker_base> build = new ArrayList<Autoclicker_base>();
    public List<Autoclicker_base> mining = new ArrayList<Autoclicker_base>();
    public List<Autoclicker_base> science = new ArrayList<Autoclicker_base>();
    public double money = 0;
    public double materials = 0;
    public double scince_points = 0;
    public double  robots = 0;
    public double hype = 0;
    public int clicker_rate = 1;
    public int manufacture_cost = 2;
    public int sellage_Amount = 1;
    public int robot_cost = 1;
    public long time = 0;
    public int current_path_1 = 0;
    public int current_path_2 = 0;
    public int current_path_3 = 0;
    public JSONObject lab;


    static private Game_Master singletone = null;

    private Game_Master() {
    }

    static public Game_Master getInstance() throws IOException {
        if (singletone == null){
            singletone = new Game_Master();
            final Handler hundleGMCreate = new Handler(Looper.getMainLooper()   );
            hundleGMCreate.postDelayed(
                    new Runnable(){
                        public void run(){
                            try {
                                singletone.loadFromJson();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 2);

        }
        return singletone;
    }

    public String toJson(){
        StringBuilder json = new StringBuilder("{\n" +
                "\t\"gm\": {\n" +
                "\t\t\"resources\" : {\n" +
                "\t\t\t\"money\" : "+money+",\n" +
                "\t\t\t\"robots\" : "+robots+",\n" +
                "\t\t\t\"hype\" : "+hype+",\n" +
                "\t\t\t\"materials\" : "+materials+",\n" +
                "\t\t\t\"science\" : "+scince_points+",\n" +
                "\t\t\t\"date\" : "+time+",\n" +
                "\t\t\t\"current_path_1\" : "+current_path_1+",\n" +
                "\t\t\t\"current_path_2\" : "+current_path_2+",\n" +
                "\t\t\t\"current_path_3\" : "+current_path_3+",\n" +
                "\t\t\t\"clicker_rate\" : "+clicker_rate+",\n" +
                "\t\t\t\"manufacture_cost\" : "+manufacture_cost+",\n"+
                "\t\t\t\"robot_cost\" : "+robot_cost+"\n" +
        "\t\t}," +
                "\n\t\"autocliker\":{\n" +
                "\t\t\"mining\":[\n");
        for(Autoclicker_base  auto: mining){
            if(auto.getId() != 0){
                json.append(",\n");
            }
            json.append(auto.toJson());
        }
        json.append("\n\t\t],\n" + "\t\t\"build\":[\n");
        for(Autoclicker_base  auto: build){
            if(auto.getId() != 0){
                json.append(",\n");
            }
            json.append(auto.toJson());
        }
        json.append("\n\t\t],\n" + "\t\t\"science\":[\n");
        for(Autoclicker_base  auto: science){
            if(auto.getId() != 0) {
                json.append(",\n");
            }
            json.append(auto.toJson());
        }
        json.append("\n\t\t]\n" + "\t}\n}\n}\n");
        return json.toString();
    }

    private void loadFromJson() throws IOException {
        String jsonString = (MainActivity.getInstance().loadFile("Auto.json"));
        //String jsonStringLab = (MainActivity.getInstance().loadFile("lab.json"));
        JSONObject jb;
        try {
            //lab = new JSONObject(jsonStringLab).getJSONObject("lab");
            lab =  new JSONObject(loadJSON(R.raw.lab)).getJSONObject("lab");
            jb = new JSONObject(jsonString).getJSONObject("gm");
            JSONObject resources = jb.getJSONObject("resources");
            time = resources.getLong("date");

            loadAutoclickers(jb.getJSONObject("autocliker"));

            money = resources.getDouble("money");
            hype = resources.getDouble("hype");
            materials = resources.getDouble("materials");
            scince_points = resources.getDouble("science");
            robots = resources.getDouble("robots");
            current_path_1 = resources.getInt("current_path_1");
            current_path_2 = resources.getInt("current_path_2");
            current_path_3 = resources.getInt("current_path_3");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        offline();
    }


    private void offline() {
        int robotsMade  = 0;
        long offlineTime = (System.currentTimeMillis() / 1000L)- time;
        for(Autoclicker_base auto : mining) {
            materials += auto.getGatherSpeed()*auto.getAmountOwned() * offlineTime;
        }
        for(Autoclicker_base auto : science) {
            scince_points += auto.getGatherSpeed()*auto.getAmountOwned() * offlineTime;
        }
        for(Autoclicker_base auto : build) {
            robotsMade += auto.getGatherSpeed()*auto.getAmountOwned() * offlineTime;
        }
        if((robotsMade*2) <= materials){
            robots += robotsMade;
            materials -= robotsMade*2;
        }
        else{
            robots+=((robotsMade*2) - materials)/2;
            materials%=2;
        }
    }

    private void loadAutoclickers(JSONObject jb) {
        try {
            JSONArray miningJSON = jb.getJSONArray("mining");
            JSONArray buildJSON = jb.getJSONArray("build");
            JSONArray scienceJSON = jb.getJSONArray("science");


            for(int i=0; i < miningJSON.length(); i++){
                Autoclicker_base autoclicker = new Autoclicker_base(miningJSON.getJSONObject(i), Type.MINING);
                mining.add(autoclicker);
            }

            for(int i=0; i < buildJSON.length(); i++){
                Autoclicker_base autoclicker = new Autoclicker_base( buildJSON.getJSONObject(i), Type.BUILD);
                build.add(autoclicker);
            }

            for(int i=0; i < scienceJSON.length(); i++) {
                Autoclicker_base autoclicker = new Autoclicker_base(scienceJSON.getJSONObject(i), Type.SCIENCE);
                science.add(autoclicker);
            }

            int x = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String loadJSON(int jsonFile) {
        InputStream is = App.getContext().getResources().openRawResource(jsonFile);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }
    public boolean gatherMatrials() {
        materials+=clicker_rate;
        return true;
    }

    public boolean makeRobot() {
        double manufacture_amount;
        if (materials < manufacture_cost){
            return false;
        }
        if(materials < manufacture_cost*clicker_rate){
            manufacture_amount = (int) materials / manufacture_cost;
            materials -= manufacture_amount * manufacture_cost;
            robots  += manufacture_amount;
            return true;
        }
        else{
            materials-=manufacture_cost*clicker_rate;
            robots+=clicker_rate;
        }

        return true;
    }
    public boolean advertise() {
        hype+=clicker_rate;
        return true;
    }
    public boolean reserch() {
        scince_points+=clicker_rate;
        return true;
    }

    public boolean sellRobots() {
        if(robots == 0 || hype < 1) {
            return false;
        }
        if(robots < sellage_Amount){
            money += robots * robot_cost;
            robots = 0;
            return true;
        }
        robots-=sellage_Amount;
        money += sellage_Amount * robot_cost;
        return true;
    }

    public void buy1(Type type, int id) {
        Autoclicker_base auto = null;
        switch (type) {
            case BUILD:
                auto = build.get(id);
                break;
            case MINING:
                auto = mining.get(id);
                break;
            case SCIENCE:
                auto = science.get(id);

        }
        double price = auto.getPrice();
        if (money >= price) {
            auto.setAmountOwned(auto.getAmountOwned() + 1);
            auto.setPrice(price * (1+auto.getPriceGrowthRate()));
            money -= price;
        }
    }

    public void buy10(Type type, int id){
        Autoclicker_base auto = null;
        switch (type){
            case BUILD:
                auto = build.get(id);
                break;
            case MINING:
                auto = mining.get(id);
                break;
            case SCIENCE:
                auto = science.get(id);
        }
        double price = auto.getPrice();
        double price10 = price;
        for(int i=1; i <= 10; i++){
            price10 += price*(Math.pow(1 + auto.getPriceGrowthRate(), i));
        }

        if(money >= price10){
            auto.setAmountOwned(auto.getAmountOwned()+10);
            auto.setPrice(price * Math.pow(1+auto.getPriceGrowthRate(), 11));
            money-= price10;
        }
    }
    public void lab(JSONObject upgrade) throws JSONException {
        switch(upgrade.getInt("effectType")) {
            case 0 :
                autoclicker_upgrade(upgrade);
                return;

            case 1 :
                autoclicker_class_upgrade(upgrade);
                return;

            case 2 :
                clicker_upgrade(upgrade);
                return;

            case 3 :
                sale_increase(upgrade);
        }
    }
    public void clicker_upgrade(JSONObject upgrade) throws JSONException {
        clicker_rate += upgrade.getInt("increase");
    }
    public void sale_increase(JSONObject upgrade) throws JSONException {
        manufacture_cost += upgrade.getInt("matIncrease");
        robot_cost += upgrade.getInt("priceIncrease");
    }
    public void autoclicker_upgrade(JSONObject upgrade) throws JSONException {
        double  increase;
        switch(upgrade.getInt("class")) {
            case 0 :
                increase =mining.get(upgrade.getInt("autoId")).getGatherSpeed() +
                        upgrade.getDouble("increase");

                mining.get(upgrade.getInt("autoId")).setGatherSpeed(increase);
                break;

            case 1 :
                increase =build.get(upgrade.getInt("autoId")).getGatherSpeed() +
                        upgrade.getDouble("increase");

                build.get(upgrade.getInt("autoId")).setGatherSpeed(increase);
                break;

            case 2 :
                increase =science.get(upgrade.getInt("autoId")).getGatherSpeed() +
                        upgrade.getDouble("increase");

                science.get(upgrade.getInt("autoId")).setGatherSpeed(increase);
                break;
        }
    }
    public void autoclicker_class_upgrade(JSONObject upgrade) throws JSONException {
        double  new_value;
        switch(upgrade.getInt("class")) {
            case 0 :
                for (Autoclicker_base auto : mining){
                    new_value = auto.getGatherSpeed()*upgrade.getDouble("new_value");
                    auto.setGatherSpeed(new_value);
                }
                break;
            case 1 :
                for (Autoclicker_base auto : build){
                    new_value = auto.getGatherSpeed()*upgrade.getDouble("new_value");
                    auto.setGatherSpeed(new_value);
                }
                break;
            case 2 :
                for (Autoclicker_base auto : science){
                    new_value = auto.getGatherSpeed()*upgrade.getDouble("new_value");
                    auto.setGatherSpeed(new_value);
                }
                break;
        }
    }
}
