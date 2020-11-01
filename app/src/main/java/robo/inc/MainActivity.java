package robo.inc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import robo.inc.autoclickers.Autoclicker_base;

public class MainActivity extends AppCompatActivity {
    private AudioPlayer mp = new AudioPlayer();

    private TextView matrials;
    private Random rd = new Random();
    private TextView robots;
    private TextView hype;
    private TextView sci;
    private TextView money;
    private TextView robotSpeed;
    private TextView hypeSpeed;
    private TextView sciSpeed;
    private TextView matrialsSpeed;
    private Game_Master gm = Game_Master.getInstance();


    double matLast = 0;
    double sciLast = 0;
    double robtLast = 0;
    double hypeLast = 0;
    int iterationNumber = 0;
    double matPerSec;
    double sciPerSec;
    double robotPerSec;
    double hypePerSec;

    // false == save data
    boolean wipeData = false;

    private static MainActivity instance;

    public MainActivity() throws IOException {
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    Log.e("Permmision", "no read external storage permition");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp.playForever(getApplicationContext(), R.raw.megalovania);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        setContentView(R.layout.activity_main);
        matrials = findViewById(R.id.matriels);
        robots = findViewById(R.id.robots);
        money = findViewById(R.id.money);
        hype = findViewById(R.id.hype);
        sci = findViewById(R.id.sci);
        robotSpeed = findViewById(R.id.robotsSpeed);
        hypeSpeed = findViewById(R.id.hypeSpeed);
        sciSpeed = findViewById(R.id.sciSpeed);
        matrialsSpeed = findViewById(R.id.matrielsSpeed);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_autoclickers, R.id.navigation_settings, R.id.navigation_laboratory)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        try {
            onCreateFileManagment();
        } catch (IOException e) {
            e.printStackTrace();
        }

        instance = this;


//      main loop
        final Handler handler = new Handler(Looper.getMainLooper()   );
        handler.postDelayed(
                new Runnable(){
                    public void run(){
                        iterationNumber++;
                        int sinceLastSec = iterationNumber%10;


                        gm.sellRobots();
                        //number of robots sold per 100ms not happy
                        autoclickGenerate();
                        gm.sellage_Amount = (int) (Math.abs(rd.nextGaussian() * gm.hype * 0.1));
                        gm.hype*=0.99;

                        if(sinceLastSec == 0){
                            matPerSec = gm.materials - matLast;
                            sciPerSec = gm.scince_points - sciLast;
                            robotPerSec = gm.robots - robtLast;
                            hypePerSec = gm.hype - hypeLast;
                            matLast = gm.materials;
                            sciLast = gm.scince_points;
                            robtLast = gm.robots;
                            hypeLast = gm.hype;
                        }


                        updateUI(matPerSec, sciPerSec, robotPerSec, hypePerSec);

                        handler.postDelayed(this, 100);

                    }
                }, 100);
    }

    @Override
    protected void onStop() {
        super.onStop();
        File file = new File(getFilesDir(), "Auto.json");
        gm.time = System.currentTimeMillis() / 1000L;
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(gm.toJson().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.stop();
    }


    public static MainActivity getInstance() {
        return instance;
    }

    private void updateUI(double matS, double sciS, double robtS, double hypeS) {
        matrials.setText("M:" + shrinkNum((int) gm.materials));
        robots.setText("R:" + shrinkNum((int) gm.robots));
        money.setText("$:" + shrinkNum((int) gm.money));
        hype.setText("H:" + shrinkNum((int) gm.hype));
        sci.setText("S:" + shrinkNum((int) gm.scince_points));
        matrialsSpeed.setText("+" + String.format("%.1f", matS) + "/s");
        robotSpeed.setText("+" + String.format("%.1f", robtS) + "/s");
        hypeSpeed.setText("" + String.format("%.1f", hypeS) + "/s");
        sciSpeed.setText("+" + String.format("%.1f", sciS) + "/s");
        robots.setTypeface(null, Typeface.BOLD);
        hype.setTypeface(null, Typeface.BOLD);
        money.setTypeface(null, Typeface.BOLD);
        sci.setTypeface(null, Typeface.BOLD);
        matrials.setTypeface(null, Typeface.BOLD);
    }

    private void autoclickGenerate(){
        for(int i=0; i < gm.mining.size(); i++){
            gm.materials += gm.mining.get(i).getAmountOwned() * (gm.mining.get(i).getGatherSpeed()/10);
        }
        for(int i=0; i < gm.science.size(); i++){
            gm.scince_points += gm.science.get(i).getAmountOwned() * (gm.science.get(i).getGatherSpeed()/10);
        }
        for(int i=0; i < gm.build.size(); i++){
            Autoclicker_base auto = gm.build.get(i);
            double materialNeeded = gm.manufacture_cost * auto.getAmountOwned() * auto.getGatherSpeed() / 10;
            if(gm.materials < materialNeeded){
                gm.robots += (int) gm.materials/gm.manufacture_cost;
                gm.materials %= gm.manufacture_cost;
            }
            else{
                gm.robots += auto.getAmountOwned() * (auto.getGatherSpeed()/10);
                gm.materials -= materialNeeded;
            }
        }
    }
    public void onCreateFileManagment() throws IOException {
        File file = new File(getFilesDir(), "Auto.json");
        if(!file.exists() || wipeData == true){
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(gm.loadJSON(R.raw.autoclickers).getBytes());
            }
        }
    }

    public String loadFile(String name) throws IOException {
        File file = new File(getFilesDir(), name);
        int length = (int) file.length();
        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file);
        try {
            in.read(bytes);
        } finally {
            in.close();
        }

        return new String(bytes);
    }
    public String shrinkNum(int num) {
        if(num < 1000){
            return "" + num;
        }
        else if(num >= 1000 && num < (Math.pow(10.0,6))){
            return num/1000 + "K";
        }
        else if(num >= (Math.pow(10.0,6)) && num < (Math.pow(10.0,9))){
            return (int)(num/(Math.pow(10.0,6))) + "M";
        }
        else if(num >= (Math.pow(10.0,9)) && num < (Math.pow(10.0,12))){
            return (int)(num/(Math.pow(10.0,9))) + "B";
        }
        return "";
    }

    public void setWipeData(boolean wipeData) {
        this.wipeData = wipeData;
    }
}