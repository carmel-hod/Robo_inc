package robo.inc.ui.dashboard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import robo.inc.App;
import robo.inc.Game_Master;
import robo.inc.R;
import robo.inc.autoclickers.Autoclicker_base;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {
    private Game_Master gm = Game_Master.getInstance();



    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView price;
        public TextView amount;
        public ImageView pic;
        public View layout;
        public TextView x1;
        public TextView x10;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            x1 = v.findViewById(R.id.x1);
            x10 = v.findViewById(R.id.x10);
            txtHeader = (TextView) v.findViewById(R.id.name1);
            txtFooter = (TextView) v.findViewById(R.id.gathering_speed);
            price = (TextView) v.findViewById(R.id.price1);
            amount = (TextView) v.findViewById(R.id.amount);
            pic = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Autoclicker_base item) {
        switch (item.getType()){
            case BUILD:
                gm.build.add(position, item);
                break;
            case MINING:
                gm.mining.add(position, item);
                break;
            case SCIENCE:
                gm.science.add(position, item);
        }
        notifyItemInserted(position);
    }

//    public void remove(int position) {
//        values.remove(position);
//        notifyItemRemoved(position);
//    }

    public RowAdapter() throws IOException {
    }

    @Override
    public RowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    List<Autoclicker_base> values = new ArrayList<>();

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        values = new ArrayList<>();
        values.addAll(gm.mining);
        values.addAll(gm.build);
        values.addAll(gm.science);

        final Autoclicker_base autoclicker = values.get(position);
        final int positionFinal = position;

        holder.x1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.buy1(autoclicker.getType(), autoclicker.getId());
                values = new ArrayList<>();
                values.addAll(gm.mining);
                values.addAll(gm.build);
                values.addAll(gm.science);
                notifyDataSetChanged();
            }

        });

        holder.x10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.buy10(autoclicker.getType(), autoclicker.getId());
                values = new ArrayList<>();
                values.addAll(gm.mining);
                values.addAll(gm.build);
                values.addAll(gm.science);
                notifyDataSetChanged();
            }

        });

        holder.txtHeader.setText(autoclicker.getName());
        holder.price.setText("$" + (int)autoclicker.getPrice());
        holder.amount.setText(String.valueOf(autoclicker.getAmountOwned()));

        int photoInt = App.getContext().getResources().getIdentifier(autoclicker.getPicture(), "drawable", "robo.inc");

        holder.pic.setImageDrawable(App.getContext().getResources().getDrawable(photoInt, null));
        String gatherRate = "";
        switch (autoclicker.getType()){
            case BUILD:
                gatherRate = "+" + (autoclicker.getGatherSpeed()) + "/s";
                break;
            case MINING:
                gatherRate = "+" + (autoclicker.getGatherSpeed()) + "/s";
                break;
            case SCIENCE:
                gatherRate = "+" + (autoclicker.getGatherSpeed()) + "/s";
        }
        holder.txtFooter.setText(gatherRate);
    }

//    toDo return real value
    @Override
    public int getItemCount() {
        return gm.build.size()+gm.science.size()+gm.mining.size();
    }


}
