package com.example.sozluk;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter2_2_2 extends RecyclerView.Adapter<CardAdapter2_2_2.CardTasarimTutucu2> {

    private Context mContext;
    private List<User_word> words;
    private Word2data wordData= new Word2data();


    public CardAdapter2_2_2(Context mContext, ArrayList<User_word> words) {
        this.mContext = mContext;
        this.words =  words;

    }


    public class CardTasarimTutucu2 extends RecyclerView.ViewHolder{
        private CardView rowCard;

        private Boolean flag=false;
        private Button button;

        public CardTasarimTutucu2(@NonNull View itemView) {
            super(itemView);
            this.rowCard = itemView.findViewById(R.id.cv2);

            this.button=itemView.findViewById(R.id.button9);
           // this.button = itemView.findViewById(R.id.switch1);
            //this.button.setChecked(true);

        }
    }
    @NonNull
    @Override
    public CardAdapter2_2_2.CardTasarimTutucu2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design2,parent,false);
        return new CardAdapter2_2_2.CardTasarimTutucu2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter2_2_2.CardTasarimTutucu2 holder, int position) {
        final String word = words.get(position).getName();
        final String mean = words.get(position).getMean();


       /* holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
*/
       holder.button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(!holder.flag){
                   holder.button.setText(word);

               }
               else
               {
                   holder.button.setText(mean);
               }

           }
       });

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    //Item'i silme işlemi
    public void removeItem(int position) {
        words.remove(position);
        notifyItemRemoved(position);
    }
    // Snackbar işlemi tıklandığında, restoreItem metodunu kullanarak öğeyi RecyclerView'da geri yükleriz.
    public void restoreItem(User_word item, int position) {
        words.add(position, item);
        notifyItemInserted(position);
    }
    public List<User_word> getData() {
        return words;
    }

    public void Right(User_word user_word){

            wordData.updateLevel(user_word);

            Long date= wordData.calculateTime(user_word);//levela göre date i güncelledim(projede)


            wordData.updateDate(date,user_word);//güncellenen tarihi firebase e kaydettim


    }

    public void Left(User_word user_word) {

        wordData.updateLevel2(user_word);//level ı sıfırladım

        wordData.updateDate2(user_word);// tarihi bugünün tarihi yaptım

    }
}
