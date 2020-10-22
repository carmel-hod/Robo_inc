package robo.inc;

import android.os.Handler;
import android.os.Looper;

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
    public int money = 0;
    public double matrials = 0;
    public double scince_points = 0;
    public double  robots = 0;
    public double hype = 0;
    public int gathering_rate = 1;
    public int reserch_rate = 1;
    public int manufacture_amount = 1;
    public int manufacture_cost = 2;
    public int sellage_Amount = 1;
    public int robot_cost = 1;
    public long time = 0;

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
                    }, 10);

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
                "\t\t\t\"materials\" : "+matrials+",\n" +
                "\t\t\t\"science\" : "+scince_points+",\n" +
                "\t\t\t\"date\" : "+time+"\n" +
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
        JSONObject jb = null;
        try {
            jb = new JSONObject(jsonString).getJSONObject("gm");
            JSONObject resources = jb.getJSONObject("resources");
            time = resources.getLong("date");
            loadAutoclickers(jb.getJSONObject("autocliker"));

            money = resources.getInt("money");
            hype = resources.getDouble("hype");
            matrials = resources.getDouble("materials");
            scince_points = resources.getDouble("science");
            robots = resources.getDouble("robots");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        offline();
    }

    private void offline() {
        int robotsMade  = 0;
        long offlineTime = (System.currentTimeMillis() / 1000L)- time;
        for(Autoclicker_base auto : mining) {
            matrials += auto.getGatherSpeed()*auto.getAmountOwned() * offlineTime;
        }
        for(Autoclicker_base auto : science) {
            scince_points += auto.getGatherSpeed()*auto.getAmountOwned() * offlineTime;
        }
        for(Autoclicker_base auto : build) {
            robotsMade += auto.getGatherSpeed()*auto.getAmountOwned() * offlineTime;
        }
        if((robotsMade*2) <= matrials){
            robots += robotsMade;
            matrials -= robotsMade*2;
        }
        else{
            robots+=((robotsMade*2) - matrials)/2;
            matrials%=2;
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
        matrials+=gathering_rate;
        return true;
    }

    public boolean makeRobot() {
        if (matrials < manufacture_cost){
            return false;
        }
        matrials-=manufacture_cost;
        robots+=manufacture_amount;
        return true;
    }
    public boolean advertise() {
        hype+=100;
        return true;
    }
    public boolean reserch() {
        scince_points+=reserch_rate;
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
            auto.setPrice(price * 1+auto.getPriceGrowthRate());
            money -= price;
        }
    }

    public void buy10(Type type, int id){
        Autoclicker_base auto = null;
        switch (type){
            case BUILD:
                double price = build.get(id).getPrice();
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

}
