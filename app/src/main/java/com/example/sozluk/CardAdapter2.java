package com.example.sozluk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.CardTasarimTutucu2> {

    private Context mContext;
    private List<Word> words;


    public CardAdapter2(Context mContext, ArrayList<Word> words) {
        this.mContext = mContext;
        this.words =  words;

    }


    public class CardTasarimTutucu2 extends RecyclerView.ViewHolder{
        private CardView rowCard;
        private TextView rowText;
        private Switch button;

        public CardTasarimTutucu2(@NonNull View itemView) {
            super(itemView);
            this.rowCard = itemView.findViewById(R.id.cv2);
            this.rowText = itemView.findViewById(R.id.textView2);
            this.button = itemView.findViewById(R.id.switch1);
            this.button.setChecked(true);

        }
    }
    @NonNull
    @Override
    public CardTasarimTutucu2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design2,parent,false);
        return new CardTasarimTutucu2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardTasarimTutucu2 holder, int position) {
        final String word = words.get(position).getName();
        final String mean = words.get(position).getMean();
        holder.rowText.setText(word);

       holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
               if (bChecked) {
                   holder.rowText.setText(word);
                   holder.button.setText("tr ");
               } else {
                   holder.rowText.setText(mean);
                   holder.button.setText("en ");
               }
           }
       });


    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
