package com.chirag.admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.chirag.admin.adminPlantPanel.AdminHomeFragment;
import com.chirag.admin.adminPlantPanel.AdminOrderfragment;
import com.chirag.admin.adminPlantPanel.AdminPendingOrderFragment;
import com.chirag.admin.adminPlantPanel.AdminProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

   // Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // toolbar =findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        setContentView(R.layout.activity_admin_plant_panel_bottom_navigation);
        BottomNavigationView navigationView =findViewById(R.id.chef_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name=getIntent().getStringExtra("PAGE");
        if(name!=null){
            if(name.equalsIgnoreCase("Orderpage")){
                loadcheffragment(new AdminHomeFragment());
            }else if (name.equalsIgnoreCase("Confirmpage")){
                loadcheffragment(new AdminOrderfragment());

            }else if (name.equalsIgnoreCase("AcceptOrderpage")){
                loadcheffragment(new AdminOrderfragment());

            }else if (name.equalsIgnoreCase("Deliverepage")){
                loadcheffragment(new AdminOrderfragment());
            }else{
                loadcheffragment(new AdminHomeFragment());
            }

        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment =null;
        if (item.getItemId() == R.id.chefHome) {
            fragment = new AdminHomeFragment();
        } else if (item.getItemId() == R.id.PendingOrders) {
            fragment = new AdminPendingOrderFragment();
        } else if (item.getItemId() == R.id.Orders) {
            fragment = new AdminOrderfragment();
        } else if (item.getItemId() == R.id.chefProfile) {
            fragment = new AdminProfileFragment();
        }
        return loadcheffragment(fragment);
    }

    private boolean loadcheffragment(Fragment fragment) {
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container ,fragment).commit();
            return true;
        }
        return false;
    }
}