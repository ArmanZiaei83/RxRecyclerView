package com.example.rxrecyclerview.presentation;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.rxrecyclerview.R;
import com.example.rxrecyclerview.data.entity.RetrofitModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {
    List<RetrofitModel> retrofitModels = new ArrayList<>();
    List<RetrofitModel> fullModels;
    Context context;

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<RetrofitModel> models = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                models.addAll(fullModels);
            }else {
                String filteredResult = charSequence.toString().toLowerCase().trim();
                for (RetrofitModel model : fullModels) {
                    if (model.getTitle().toLowerCase().contains(filteredResult)){
                        models.add(model);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = models;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            retrofitModels.clear();
            retrofitModels.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reycler_view , parent , false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        RetrofitModel retrofitModel = retrofitModels.get(position);

        GlideUrl glideUrl = new GlideUrl(retrofitModel.getUrl(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "app_agent")
                .build());

        Glide.with(context).load(glideUrl).into(holder.picture);
        holder.title.setText(retrofitModel.getTitle());
        new Handler().postDelayed(() -> {
            if(holder.picture.getDrawable() == null){
                holder.bar.setVisibility(View.VISIBLE);
            }else{
                holder.bar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    @Override
    public int getItemCount() {
        return retrofitModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView title;
        ProgressBar bar;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picture);
            title = itemView.findViewById(R.id.title);
            bar = itemView.findViewById(R.id.progressBar);
        }

    }
    public void setUrls(List<RetrofitModel> retrofitModels) {
        this.retrofitModels = retrofitModels;
        fullModels = new ArrayList<>(retrofitModels);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<RetrofitModel> getUrls() {
        return retrofitModels;
    }

    public Context getContext() {
        return context;
    }
}
