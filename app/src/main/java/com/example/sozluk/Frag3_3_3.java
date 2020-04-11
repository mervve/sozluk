package com.example.sozluk;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Frag3_3_3 extends Fragment {
    private RecyclerView rv;
    private CardAdapter2_2_2 adapter;
    private ArrayList<Word2> wordArrayList;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser= mAuth.getCurrentUser();

    private User_word item;
    private RelativeLayout rl;


    private DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime");

    private ArrayList<User_word> user_words= new ArrayList<>();
    private ArrayList<User_word> user_words2= new ArrayList<>();
    Timestamp ts=new Timestamp(System.currentTimeMillis());
    Date date=new Date(ts.getTime());

    String date2 ;

    @Override
    public void onStart(){
        super.onStart();

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user_words.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User_word user_word= new User_word();
                    user_word.setMean(postSnapshot.child("mean").getValue(String.class)) ;
                    user_word.setName(postSnapshot.child("name").getValue(String.class));
                    user_word.setKey(postSnapshot.child("key").getValue(String.class));
                    user_word.setId(postSnapshot.child("id").getValue(String.class));
                    user_word.setDate(postSnapshot.child("date").getValue(Long.class));
                    user_word.setLevel(postSnapshot.child("level").getValue(int.class));

                    if(user_word.getId().equals(firebaseUser.getUid())){
                        user_words.add(user_word);
                    }

                    date2 = convertTime(user_words.get(user_words.size()-1).getDate());

                    if( date.toString().equals(date2))
                    {
                        user_words2.add(user_words.get(user_words.size()-1));


                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_frag3, container,false);

        rv=view.findViewById(R.id.rv2);
        rl=view.findViewById(R.id.rl2);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        adapter= new CardAdapter2_2_2(getActivity(),user_words2);
        rv.setAdapter(adapter);
        enableSwipeToDeleteAndUndo();



        return view;
    }
    private void enableSwipeToDeleteAndUndo() {
        SwipeController swipeController = new SwipeController(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();

                item = adapter.getData().get(position);
                Log.e("key",item.getKey());

                if(i== ItemTouchHelper.RIGHT){
                    if(item==null){
                        Log.e("null","null burada");
                    }
                    adapter.Right(item);
                }
                if(i== ItemTouchHelper.LEFT){
                    adapter.Left(item);
                }

                adapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(rl, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        rv.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rv);
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
