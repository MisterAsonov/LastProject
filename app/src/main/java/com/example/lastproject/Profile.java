package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    String creatorID;
    private static final int PICK_IMAGE_REQUEST = 1;

    DatabaseReference user_ref;

    TextView name, email;
    ImageView photo;

    private Uri mImageUri;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile__detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.change_name:
                Toast.makeText(Profile.this, "change_name selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.new_photo:
                Toast.makeText(Profile.this, "new_photo selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile.this, LoginScreen.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(photo);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.textView5);
        email = findViewById(R.id.textView6);
        photo = findViewById(R.id.photo);

        setSupportActionBar(findViewById(R.id.toolbar4));


        user_ref = FirebaseDatabase.getInstance().
                getReference("Users");
        creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        user_ref.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User p = snapshot.getValue(User.class);
                email.setText(p.getEmail());
                name.setText(p.getName() + " " + p.getLastname());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}