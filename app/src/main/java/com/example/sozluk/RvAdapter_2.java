package com.example.sozluk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvAdapter_2 extends RecyclerView.Adapter<RvAdapter_2.MyViewHolder> {
    private ArrayList<String> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        RelativeLayout relativeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textView4);
        }
    }
    public RvAdapter_2(ArrayList<String> data) {
        this.data = data;
    }
    @Override
    public RvAdapter_2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design3, parent, false);
        return new RvAdapter_2.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RvAdapter_2.MyViewHolder holder, int position) {
        holder.mTitle.setText(data.get(position));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    //Item'i silme işlemi
    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    // Snackbar işlemi tıklandığında, restoreItem metodunu kullanarak öğeyi RecyclerView'da geri yükleriz.
    public void restoreItem(String item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }
    public ArrayList<String> getData() {
        return data;
    }
}
