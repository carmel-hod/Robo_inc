package robo.inc;

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
    private Game_Master gm = Game_Master.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrials = findViewById(R.id.matriels);
        robots = findViewById(R.id.robots);
        hype = findViewById(R.id.hype);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        final Handler handler = new Handler(Looper.getMainLooper()   );
        handler.postDelayed(
                new Runnable(){
                    public void run(){
                        matrials.setText("Matrials:" + Integer.toString(gm.matrials));
                        robots.setText("robots:" + Integer.toString(gm.robots));
                        hype.setText("hype:" + Integer.toString(gm.advertise_points));
                        handler.postDelayed(this, 100);
                    }
                }, 100);


    }



}