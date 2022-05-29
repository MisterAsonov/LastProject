package com.example.lastproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity  {

    private DrawerLayout drawer;
    TabLayout tabLayout;
    ViewPager2 pager2;
    MainTabAdapter adapter;
    NavigationView navigationView;
    MaterialToolbar toolbar;
    TextView header_email, header_name;
    CircleImageView photo;

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar3);

        reference = FirebaseDatabase.getInstance().
                getReference("Users");
        String creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        tabLayout = findViewById(R.id.tab_layout2);
        pager2 = findViewById(R.id.view_pager2);

        tabLayout.addTab(tabLayout.newTab().setText("ACTIVITIES"));
        tabLayout.addTab(tabLayout.newTab().setText("REPORTS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new MainTabAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(GravityCompat.START);

            }
        });

        View headerView = getLayoutInflater().inflate(R.layout.nav_header, navigationView, false);
        navigationView.addHeaderView(headerView);

        ImageView headerImage = headerView.findViewById(R.id.profile);
        header_email = headerView.findViewById(R.id.header_email);
        header_name = headerView.findViewById(R.id.header_name);
        photo = headerImage.findViewById(R.id.profile);

        reference.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User p = snapshot.getValue(User.class);
                header_email.setText(p.getEmail());
                header_name.setText(p.getName() + " " + p.getLastname());

                String link = p.getmImageUrl();
                if(!link.equals(""))
                    Picasso.get().load(link).into(photo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no interesting in our purpose in the lesson
            }
        });


        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Profile.class);
                startActivity(intent2);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawer.closeDrawer(GravityCompat.START);

                switch (id) {
                    case R.id.Home:
                        break;

                    case R.id.notification:
                        Toast.makeText(MainActivity.this, "notification is Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.support:
                        Intent intent4 = new Intent(MainActivity.this, Support_page.class);
                        startActivity(intent4);
                        break;

                    case R.id.settings:
                        Intent intent3 = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent3);
                        break;

                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                        startActivity(intent);
                        break;

                    default:
                        return true;

                }

                return false;
            }
        });

    }

}