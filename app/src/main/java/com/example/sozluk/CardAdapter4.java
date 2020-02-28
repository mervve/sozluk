package com.example.sozluk;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter4 extends RecyclerView.Adapter<CardAdapter4.CardTasarimTutucu> {

    private Context mContext;
    private ArrayList<Word> words;
    private SharedPreferences.Editor e;
    private ArrayList<String> sharedP;
    private String word = null;
    private int id=0;
    private StringBuffer str = new StringBuffer("");
    private boolean flag=true;
    private SharedPreferences sp;




    public CardAdapter4(Context mContext, ArrayList<Word> words) {
        this.mContext = mContext;
        this.words =  words;
    }


    public class CardTasarimTutucu extends RecyclerView.ViewHolder{
        private CardView rowCard;
        private TextView rowText;

        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);
            this.rowCard = itemView.findViewById(R.id.cardView4);
            this.rowText = itemView.findViewById(R.id.textView);
        }
    }
    @NonNull
    @Override
    public CardAdapter4.CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design4,parent,false);
        return new CardAdapter4.CardTasarimTutucu(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter4.CardTasarimTutucu holder, int position) {

        sp = mContext.getSharedPreferences("checked",Context.MODE_PRIVATE);
        sharedP= new ArrayList<>();
        e=sp.edit();
        word = words.get(position).getMean();
        Log.e("word",word);
        id= words.get(position).getId();
        Log.e("id",Integer.toString(id));



        holder.rowText.setText(word);
        str.append(Integer.toString(id));
        str.append(",");
        e.putString("str",str.toString());

        e.commit();



    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
