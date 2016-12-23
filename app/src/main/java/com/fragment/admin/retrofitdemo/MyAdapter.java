package com.fragment.admin.retrofitdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/20.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MRViewHolder> {
    private List<MovieEntity.Subject> list;
    private Context context;
    private LayoutInflater mInflater;
    RecyclerItemClickListener recyclerItemClickListener;


    public MyAdapter(List<MovieEntity.Subject> list, Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public interface RecyclerItemClickListener {
        public void OnItemClick(View v, int pos);
    }


    @Override
    public MRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview, parent, false);
        MRViewHolder viewHolder = new MRViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final MRViewHolder holder, final int position) {
        if (holder == null) {
            Log.e("ADapter", "holder is null");
        }
        if (list == null) {
            Log.e("ADapter", "list is not null" + list.size());
        }
        View view = holder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerItemClickListener.OnItemClick(view, position);
            }
        });
        holder.tv_MovieName.setText("片名:"+list.get(position).getTitle());
//        StringBuffer stringBuffer = new StringBuffer();
//        List<MovieEntity.Cast> cast = (List<MovieEntity.Cast>) list.get(position).getCasts();
//        for (int i = 0; i < cast.size(); i++) {
//            stringBuffer.append(cast.get(i).getName() + " ");
//        }
        holder.tv_MovieNameO.setText(list.get(position).getOriginal_title());
        holder.tv_Rating.setText("豆瓣评分："+list.get(position).getRating().getAverage());
        Glide.with(context)
                .load(list.get(position).getImages().getLarge())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(480, 800) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.item_pic.setImageBitmap(resource);
//                        mAttacher.update();
                    }
                });
    }

    /**
     * RecyclerView的ViewHolder
     */
    class MRViewHolder extends RecyclerView.ViewHolder {
        TextView tv_MovieName;
        TextView tv_MovieNameO;
        TextView tv_Rating;
        ImageView item_pic;

        public MRViewHolder(View itemView) {
            super(itemView);
            tv_MovieName = (TextView) itemView.findViewById(R.id.tv_MovieName);
            tv_MovieNameO = (TextView) itemView.findViewById(R.id.tv_MovieNameO);
            tv_Rating = (TextView) itemView.findViewById(R.id.tv_Rating);

            item_pic = (ImageView) itemView.findViewById(R.id.item_pic);
        }
    }
}

