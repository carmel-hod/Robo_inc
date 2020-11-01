package robo.inc.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import robo.inc.R;
import robo.inc.autoclickers.Autoclicker_base;
import robo.inc.autoclickers.Type;

public class  DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rowAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_autoclickers, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);


        try {
            rowAdapter = new RowAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(rowAdapter);



        return root;
    }


}
