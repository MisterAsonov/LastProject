package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    String creatorID;
    private static final int PICK_IMAGE_REQUEST = 1;

    DatabaseReference user_ref;
    private StorageReference mStorageRef;


    TextView name, email, who;
    ImageButton back;
    FloatingActionButton btn_change_photo;
    ImageFilterView photo;
    private StorageTask mUploadTask;

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
                openFileChooser();
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

            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(Profile.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.textView5);
        email = findViewById(R.id.email_profile);
        photo = findViewById(R.id.photo);
        who = findViewById(R.id.who_profile);
        back = findViewById(R.id.arrow_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Profile.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
        btn_change_photo = findViewById(R.id.float_btn_add_photo_profile);
        btn_change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        setSupportActionBar(findViewById(R.id.toolbar4));

        user_ref = FirebaseDatabase.getInstance().
                getReference("Users");
        creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mStorageRef = FirebaseStorage.getInstance().getReference("Users");

        user_ref.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User p = snapshot.getValue(User.class);
                email.setText(p.getEmail());
                name.setText(p.getName() + " " + p.getLastname());
                who.setText(p.getWho());

                String link = p.getmImageUrl();

                if(!link.equals(""))
                    Picasso.get().load(link).into(photo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Profile.this, "Upload successful", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();

                                    user_ref.child(creatorID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            User user = snapshot.getValue(User.class);

                                            user.setmImageUrl(url);

                                            user_ref.child(creatorID).setValue(user);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            //no interesting in our purpose in the lesson
                                        }
                                    });
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


}