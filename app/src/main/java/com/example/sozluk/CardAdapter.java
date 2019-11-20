package com.example.sozluk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardTasarimTutucu> {
    private Context mContext;
    private List<String> words;


    public CardAdapter(Context mContext, List<String> words) {
        this.mContext = mContext;
        this.words =  words;
    }


    public class CardTasarimTutucu extends RecyclerView.ViewHolder{
        private CardView rowCard;
        private CheckedTextView rowText;

        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);
            this.rowCard = itemView.findViewById(R.id.rowCard);
            this.rowText = itemView.findViewById(R.id.rowText);
        }
    }
    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design,parent,false);
        return new CardTasarimTutucu(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardTasarimTutucu holder, int position) {
        String word = words.get(position);
        holder.rowText.setText(word);
        holder.rowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rowText.isChecked()) {
// set cheek mark drawable and set checked property to false

                    holder.rowText.setCheckMarkDrawable(null);
                    holder.rowText.setChecked(false);
                } else {
// set cheek mark drawable and set checked property to true

                    holder.rowText.setCheckMarkDrawable(R.drawable.ic_check_black_24dp);
                    holder.rowText.setChecked(true);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

}

