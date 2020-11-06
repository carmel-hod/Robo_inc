package robo.inc.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import robo.inc.App;
import robo.inc.MainActivity;
import robo.inc.R;

public class NotificationsFragment extends Fragment {
    public MainActivity ma = MainActivity.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button restart = root.findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to do this");
                builder.setMessage("This will wipe away all data and close the app");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ma.setWipeData(true);
                                try {
                                    ma.onCreateFileManagment();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ma.setWipeData(false);
                                App.doRestart(root.getContext());

                            }

                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        return root;
    }
}