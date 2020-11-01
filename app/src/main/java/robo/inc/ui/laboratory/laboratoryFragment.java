package robo.inc.ui.laboratory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import robo.inc.Game_Master;
import robo.inc.R;

public class laboratoryFragment extends Fragment{
    Game_Master gm = Game_Master.getInstance();
    TextView name1;
    TextView price1;
    TextView description1;
    TextView name2;
    TextView price2;
    TextView description2;
    TextView name3;
    TextView price3;
    TextView description3;

    JSONObject research1 = gm.lab.getJSONArray("path1").getJSONObject(gm.current_path_1);
    JSONObject research2 = gm.lab.getJSONArray("path2").getJSONObject(gm.current_path_2);
    JSONObject research3 = gm.lab.getJSONArray("path3").getJSONObject(gm.current_path_3);

    public laboratoryFragment() throws IOException, JSONException {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_laboratory, container, false);
         name1 = root.findViewById(R.id.name1);
         price1 = root.findViewById(R.id.price1);
         description1 = root.findViewById(R.id.description1);
         name2 = root.findViewById(R.id.name2);
         price2 = root.findViewById(R.id.price2);
         description2 = root.findViewById(R.id.description2);
         name3 = root.findViewById(R.id.name3);
         price3 = root.findViewById(R.id.price3);
         description3 = root.findViewById(R.id.description3);
         TextView buy1 = root.findViewById(R.id.buy1);
         TextView buy2 = root.findViewById(R.id.buy2);
         TextView buy3 = root.findViewById(R.id.buy3);

        try {
            name1.setText(research1.getString("name"));
            price1.setText(research1.getString("cost"));
            description1.setText(research1.getString("description"));
            name2.setText(research2.getString("name"));
            price2.setText(research2.getString("cost"));
            description2.setText(research2.getString("description"));
            name3.setText(research3.getString("name"));
            price3.setText(research3.getString("cost"));
            description3.setText(research3.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        buy1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if(gm.current_path_1 == gm.lab.getJSONArray("path1").length()){
                        return;

                    }
                    if(gm.money < research1.getInt("cost")){
                        return;
                    }
                    gm.current_path_1++;
                    gm.money -= research1.getInt("cost");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    gm.lab(research1);
                    research1 = gm.lab.getJSONArray("path1").getJSONObject(gm.current_path_1);
                    change_upgrades(name1,price1,description1,research1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        buy2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if(gm.current_path_2 == gm.lab.getJSONArray("path2").length()){
                        return;

                    }
                    if(gm.money < research2.getInt("cost")){
                        return;
                    }
                    gm.current_path_2++;
                    gm.money -= research2.getInt("cost");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    gm.lab(research2);
                    research2 = gm.lab.getJSONArray("path2").getJSONObject(gm.current_path_2);
                    change_upgrades(name2,price2,description2,research2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        buy3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if(gm.current_path_3 == gm.lab.getJSONArray("path3").length()){
                        return;

                    }
                    if(gm.money < research3.getInt("cost")){
                        return;
                    }
                    gm.current_path_3++;
                    gm.money -= research1.getInt("cost");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    gm.lab(research3);
                    research3 = gm.lab.getJSONArray("path3").getJSONObject(gm.current_path_3);
                    change_upgrades(name3,price3,description3,research3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        return root;
    }
    public void change_upgrades(TextView n, TextView p, TextView d, JSONObject r) throws JSONException {
        n.setText(r.getString("name"));
        p.setText(r.getString("cost"));
        d.setText(r.getString("description"));
    }
}
