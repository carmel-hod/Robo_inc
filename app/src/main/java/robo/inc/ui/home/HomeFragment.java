package robo.inc.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import robo.inc.Game_Master;
import robo.inc.R;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private Game_Master gm = Game_Master.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton mine = (ImageButton) root.findViewById(R.id.mine);
        mine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.gatherMatrials();
            }

        });

        ImageButton build = (ImageButton) root.findViewById(R.id.build);
        build.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.makeRobot();
            }

        });

        ImageButton advertise = (ImageButton) root.findViewById(R.id.advertise);
        advertise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.advertise();
            }

        });

        ImageButton reserch = (ImageButton) root.findViewById(R.id.reserch);
        reserch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.reserch();
            }

        });

        return root;
    }
}