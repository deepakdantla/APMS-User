package com.apms_user;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private TextView name, email, id, address;
    private Button logout;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View currentView =  inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        name = currentView.findViewById(R.id.user_name);
        email = currentView.findViewById(R.id.user_email);
        id = currentView.findViewById(R.id.user_id);
        address = currentView.findViewById(R.id.user_address);


        logout = currentView.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i = new Intent(ProfileFragment.this.getContext(), ActivityLogin.class);
                startActivity(i);
            }
        });


        return currentView;
    }



    public void fetchProfileFromDataBase()
    {
        databaseReference.child("userInfo").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileHelper profileHelper = dataSnapshot.getValue(ProfileHelper.class);
                name.setText(profileHelper.getUsername());
                id.setText(user.getUid());
                email.setText(profileHelper.getEmailAddress());
                address.setText(profileHelper.getAddressUser());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        fetchProfileFromDataBase();
    }
}
