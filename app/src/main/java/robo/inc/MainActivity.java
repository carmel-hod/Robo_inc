package robo.inc;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private TextView matrials;
    private TextView robots;
    private TextView hype;
    private TextView sci;
    private TextView money;
    private Game_Master gm = Game_Master.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrials = findViewById(R.id.matriels);
        robots = findViewById(R.id.robots);
        money = findViewById(R.id.money);
        hype = findViewById(R.id.hype);
        sci = findViewById(R.id.sci);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        final Handler handler = new Handler(Looper.getMainLooper()   );
        handler.postDelayed(
                new Runnable(){
                    public void run(){
                        matrials.setText("M:" + Integer.toString(gm.matrials));
                        robots.setText("R:" + Integer.toString(gm.robots));
                        money.setText("$:" + Integer.toString(gm.money));
                        hype.setText("H:" + Integer.toString(gm.advertise_points));
                        sci.setText("S:" + Integer.toString(gm.scince_points));
                        robots.setTypeface(null, Typeface.BOLD);
                        hype.setTypeface(null, Typeface.BOLD);
                        money.setTypeface(null, Typeface.BOLD);
                        sci.setTypeface(null, Typeface.BOLD);
                        matrials.setTypeface(null, Typeface.BOLD);
                        handler.postDelayed(this, 100);
                    }
                }, 100);


    }



}