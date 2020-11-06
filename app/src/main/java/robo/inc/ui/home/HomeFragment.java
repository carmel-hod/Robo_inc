package robo.inc.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import robo.inc.AudioPlayer;
import robo.inc.Game_Master;
import robo.inc.MainActivity;
import robo.inc.R;

public class HomeFragment extends Fragment {

    private Game_Master gm = Game_Master.getInstance();
    private View root;
    public HomeFragment() throws IOException {
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        String filename = "android.resource://" + root.getContext().getPackageName() + "/raw/tick";


        final AudioPlayer mp = new AudioPlayer();


        ImageButton mine = (ImageButton) root.findViewById(R.id.mine);
        mine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.gatherMatrials();
                Animation mining = AnimationUtils.loadAnimation(root.getContext(), R.anim.mining);
                root.findViewById(R.id.pick).startAnimation(mining);
                //mp.play(root.getContext(), R.raw.tick_3);
            }

        });

        ImageButton build = (ImageButton) root.findViewById(R.id.build);
        build.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.makeRobot();
                Animation rotate = AnimationUtils.loadAnimation(root.getContext(), R.anim.rotate);
                root.findViewById(R.id.build_head).startAnimation(rotate);
            }

        });

        ImageButton advertise = (ImageButton) root.findViewById(R.id.advertise);
        advertise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.advertise();
                Animation shrinkGrow = AnimationUtils.loadAnimation(root.getContext(), R.anim.shrinkgrow);
                root.findViewById(R.id.advertiseImage).startAnimation(shrinkGrow);
            }

        });

        ImageButton reserch = (ImageButton) root.findViewById(R.id.reserch);
        reserch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.reserch();
                Animation shake = AnimationUtils.loadAnimation(root.getContext(), R.anim.shake);
                root.findViewById(R.id.reserchImage).startAnimation(shake);
            }

        });

        return root;
    }
}