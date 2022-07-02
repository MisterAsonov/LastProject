package com.example.lastproject;


import static com.example.lastproject.Utils.TABLE_NAME_USER;
import static com.example.lastproject.Utils.TABLE_USER_COL_EMAIL;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class teacher_group_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<User> StudentsList;
    ArrayList<String> keys;
    MyGroupAdapter adapter;
    FloatingActionButton btn_addStudent;

    private static final String TAG = "Student";

    DatabaseReference post_ref, student;
    private FirebaseAuth mAuth;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_group_fragment,container,false);

        db = getActivity().openOrCreateDatabase(Utils.DATABASE_NAME,android.content.Context.MODE_PRIVATE ,null);
        db.execSQL("delete from " + TABLE_NAME_USER);

        StudentsList = new ArrayList<User>();
        keys = new ArrayList<String>();

        recyclerView = view.findViewById(R.id.group_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_addStudent = view.findViewById(R.id.fab_btn);

        btn_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                qr_code_dialog newFragmen = new qr_code_dialog();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragmen).addToBackStack(null).commit();


            }
        });

        adapter = new MyGroupAdapter(StudentsList, getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        post_ref = FirebaseDatabase.getInstance().
                getReference("Groups");
        student = FirebaseDatabase.getInstance().
                getReference("Users");

        retrieveData();
        retriveDB();

        Log.d(TAG, "StudentsList: " + StudentsList);
        return view;

    }

    private void retriveDB() {
        StudentsList.clear();
        Cursor cursor3 = db.rawQuery("select * from " + TABLE_NAME_USER, null);
        while (cursor3.moveToNext()) {

            String email = cursor3.getString(0);
            String name = cursor3.getString(1);
            String lastname = cursor3.getString(2);
            String who = cursor3.getString(3);
            String referal = cursor3.getString(4);
            String UID = cursor3.getString(5);
            String mImageUrl = cursor3.getString(6);

            User user = new User(email, name, lastname, who, referal,UID,mImageUrl);

            StudentsList.add(user);
            recyclerView.setAdapter(adapter);
        }

    }

    private void retrieveData() {

        String teaceherId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        student.child(teaceherId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               User uid = snapshot.getValue(User.class);

                post_ref.child(uid.referal_link).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        StudentsList.clear();
                        keys.clear();
                        for (DataSnapshot tmp : snapshot.getChildren()) {
                            if(tmp.getKey().equals("Moadon")) continue;
                            PersonInGroup st = tmp.getValue(PersonInGroup.class);
                            String idOfstident = st.getId_of_student();

                            student.child(idOfstident).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User p = snapshot.getValue(User.class);
                                    /**
                                     * ERROR
                                     * table tbl_user has 6 columns but 7 values were supplied (code 1 SQLITE_ERROR[1]): , while compiling:
                                     * insert into tbl_user values('t1@gmail.com','Valery','Asonov','Teacher','pgBMMqxoiuQzb4SwBnZVjDGWj8m1','pgBMMqxoiuQzb4SwBnZVjDGWj8m1',
                                     * 'https://firebasestorage
                                     */
                                    db.execSQL("insert into tbl_user values('"+p.getEmail() +
                                            "','" + p.getName() + "','" + p.getLastname() + "','" + p.getWho() + "','" +
                                            p.getReferal_link() + "','" + p.getUID() + "','" + p.getmImageUrl() + "')");

                                    adapter.setKeys(keys);

                                    Log.d(TAG, "StudentsList2: " + StudentsList);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        Log.d(TAG, "StudentsList3: " + StudentsList);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}