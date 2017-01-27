package com.example.saurabh.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;




public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<MoviedetailsAdapter> moviedetails;
    private Context mContext;


    public MyRecyclerViewAdapter(Context context, List<MoviedetailsAdapter> moviedetails) {
        this.moviedetails = moviedetails;
        this.mContext = context;
    }
   public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView,textView1,textView2,textView4;


        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.adapter_epl_paper_pic);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.textView1=(TextView) view.findViewById(R.id.rating);
            this.textView2 = (TextView) view.findViewById(R.id.vT_al_orderdetails);
            this.textView4=(TextView) view.findViewById(R.id.year);
        }
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listadapter, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
          MoviedetailsAdapter movieItem = moviedetails.get(i);
               Log.e("saurabh",""+movieItem);
        //Download image using picasso library
        if (!TextUtils.isEmpty(movieItem.getMimage())) {
            Picasso.with(mContext).load(movieItem.getMimage())
                    .into(customViewHolder.imageView);
        }

        //Setting text view title
       /* CustomViewHolder.textView.setText(movieItem.getMtitle());
        customViewHolder.textView2.setText(""+movieItem.getGenre());
        customViewHolder.textView1.setText(movieItem.getMrating());
        customViewHolder.textView4.setText(movieItem.getMreleaseYear());*/

    }

    @Override
    public int getItemCount() {
        return (null != moviedetails ? moviedetails.size() : 0);
    }
}