package com.example.sozluk;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardTasarimTutucu> {
    private Context mContext;
    private ArrayList<Word> words;
    private SharedPreferences.Editor e;
    private ArrayList<String> sharedP;
    private String word = null;
    private int id=0;
    private StringBuffer str = null;
    private boolean flag=true;
    private SharedPreferences sp;




    public CardAdapter(Context mContext, ArrayList<Word> words) {
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

        sp = mContext.getSharedPreferences("checked",Context.MODE_PRIVATE);
        sharedP= new ArrayList<>();
        e=sp.edit();
        word = words.get(position).getMean();
        id= words.get(position).getId();
        str= new StringBuffer("");


       holder.rowText.setText(word);
        holder.rowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rowText.isChecked()) {

                    e.remove("str");
                    holder.rowText.setCheckMarkDrawable(null);
                    holder.rowText.setChecked(false);
                } else {

                    e.putInt("str",id);
                    holder.rowText.setCheckMarkDrawable(R.drawable.ic_check_black_24dp);
                    holder.rowText.setChecked(true);

                }


                e.commit();

            }

        });



    }

    @Override
    public int getItemCount() {
        return words.size();
    }

}

