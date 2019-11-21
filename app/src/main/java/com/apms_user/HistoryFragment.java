package com.apms_user;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private ListView listView;
    private HistoryAdapter historyAdapter;
    private ChildEventListener eventListener;
    private List<HistoryHelper> histories;

    private ListView historyListView;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("UsersHistory").child(user.getUid());

        listView = view.findViewById(R.id.historyListView);

        histories = new ArrayList<>();

        historyAdapter = new HistoryAdapter(view.getContext(), R.layout.custom_history_listview, histories);

        listView.setAdapter(historyAdapter);
        return view;
    }

    private void attachDataBaseReadListener() {
        if (eventListener == null) {
            if (eventListener == null)
            {
                eventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        HistoryHelper item = dataSnapshot.getValue(HistoryHelper.class);
                        histories.add(item);
                        historyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

//                    progress.setVisibility(View.INVISIBLE);
                    }
                };
                reference.addChildEventListener(eventListener);
            }

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        attachDataBaseReadListener();
    }

    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        histories.clear();
    }




    private void detachDatabaseReadListener() {
        if (eventListener != null) {
            reference.removeEventListener(eventListener);
            eventListener = null;
        }
    }
}
