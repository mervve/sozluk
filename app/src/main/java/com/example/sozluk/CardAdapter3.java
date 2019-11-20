package com.example.sozluk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CardAdapter3 extends RecyclerView.Adapter<CardAdapter3.CardTasarimTutucu3> {


    private Context mContext;
    private ArrayList<String> array;


    public CardAdapter3(Context mContext,ArrayList array) {
        this.mContext = mContext;
        this.array= array;

    }


    public class CardTasarimTutucu3 extends RecyclerView.ViewHolder{
        private CardView rowCard;
        private TextView rowText;

        public CardTasarimTutucu3(@NonNull View itemView) {
            super(itemView);
            this.rowCard = itemView.findViewById(R.id.cv3);
            this.rowText = itemView.findViewById(R.id.textView4);


        }
    }
    @NonNull
    @Override
    public CardAdapter3.CardTasarimTutucu3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design3,parent,false);
        return new CardAdapter3.CardTasarimTutucu3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter3.CardTasarimTutucu3 holder, int position) {

        holder.rowText.setText(array.get(position));

    }

    @Override
    public int getItemCount() {
        return array.size();
    }
}
