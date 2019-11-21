package com.apms_user;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private ChildEventListener eventListener;
    private TextView slotNo;
    private Button parkIn, parkOut;
    private int temp = 0;
    private String parkedSlotNo;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View currView = inflater.inflate(R.layout.fragment_dashboard, container, false);


        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        slotNo = currView.findViewById(R.id.slotNumber);

        parkIn = currView.findViewById(R.id.park_now_btn);
        parkOut = currView.findViewById(R.id.depart_btn);



        parkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchForEmptySlotAndFillIt();
            }
        });

        parkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDepartDetails();
            }
        });




        return currView;
    }


    @Override
    public void onStart() {
        super.onStart();
        getDataFromDatabase();
    }


    @Override
    public void onResume() {
        super.onResume();
        getDataFromDatabase();
    }

    public void getDataFromDatabase()
    {
        databaseReference.child("userInfo").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileHelper profileHelper = dataSnapshot.getValue(ProfileHelper.class);
                String slotNoStr = profileHelper.getSlotNumber();
                if (slotNoStr.equals("None"))
                {
                    slotNo.setText("--");
                    parkIn.setVisibility(View.VISIBLE);
                    parkOut.setVisibility(View.INVISIBLE);
                }
                else
                {
                    slotNo.setText(slotNoStr);
                    parkedSlotNo = slotNoStr;
                    parkOut.setVisibility(View.VISIBLE);
                    parkIn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void searchForEmptySlotAndFillIt()
    {
        databaseReference.child("slotsInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> slots = dataSnapshot.getChildren().iterator();



                while (slots.hasNext())
                {
                    SlotFetchHelper slotFetchHelper = slots.next().getValue(SlotFetchHelper.class);
                    if (slotFetchHelper.getIsFilled().equals("no") && temp == 0)
                    {
                        temp = 1;
                        int slotNumber = slotFetchHelper.getSlotNumber();
                        slotNo.setText(""+slotNumber);
                        database.getReference().child("slotsInfo").child("slot"+slotFetchHelper.getSlotNumber()).child("isFilled").setValue("yes");
                        database.getReference().child("slotsInfo").child("slot"+slotFetchHelper.getSlotNumber()).child("userInSlot").setValue(user.getUid());
                        database.getReference().child("userInfo").child(user.getUid()).child("slotNumber").setValue(""+slotNumber);
                        Log.i("Slot", ""+slotFetchHelper.getSlotNumber());
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        HistoryHelper helper = new HistoryHelper("Parking-in", ""+slotNumber, ""+dateFormat.format(date));
                        databaseReference.child("UsersHistory").child(user.getUid()).push().setValue(helper);
                        parkOut.setVisibility(View.VISIBLE);
                        parkIn.setVisibility(View.INVISIBLE);
                        break;
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateDepartDetails()
    {
        database.getReference().child("slotsInfo").child("slot"+parkedSlotNo).child("isFilled").setValue("no");
        database.getReference().child("slotsInfo").child("slot"+parkedSlotNo).child("userInSlot").setValue("None");
        database.getReference().child("userInfo").child(user.getUid()).child("slotNumber").setValue("None");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        HistoryHelper helper = new HistoryHelper("Parking-Out", ""+parkedSlotNo, ""+dateFormat.format(date));
        databaseReference.child("UsersHistory").child(user.getUid()).push().setValue(helper);
        parkOut.setVisibility(View.INVISIBLE);
        parkIn.setVisibility(View.VISIBLE);
        slotNo.setText("--");
    }

}
