package com.example.paras.blogapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder>
{

    private Context context;
    private List<Data> dataArrayList;

    public RecyclerView_Adapter(Context context, List<Data> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public RecyclerView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_Adapter.ViewHolder holder, int position)
    {

        Data data = dataArrayList.get(position);
        String image = null;

        holder.title.setText(data.getTitle_data_id());
        holder.descryp.setText(data.getDescryp_data_id());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String format = dateFormat.format(new Date(Long.valueOf(data.getTimestamp())).getTime());

        holder.time.setText(format);

        image = data.getImage_data_id();

        //TODO: use picasso for image handling

        Picasso.with(context).
                load(image).
                into(holder.image);

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title,descryp;
        private TextView time;
        private ImageView image;
        private String user_id ;

        public ViewHolder(@NonNull View itemView,Context ctx)
        {
            super(itemView);

            context = ctx;

            title = (TextView) itemView.findViewById(R.id.title_id);
            descryp = (TextView) itemView.findViewById(R.id.description_id);
            image = (ImageView) itemView.findViewById(R.id.post_id);
            time = (TextView) itemView.findViewById(R.id.timestamp_id);

            user_id=null;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //if you want to click card and go to next page then write here
                    // TODO : i want it clickable later

                }
            });

        }
    }
}
