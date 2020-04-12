package com.deleny.kuc;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.deleny.kuc.ui.alltests.AllTestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LabSheetActivity extends AppCompatActivity {

    @BindView(R.id.myAccountTitle)
    TextView myAccountTitle;

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    FragmentManager manager;

    @BindView(R.id.nav_host_fragment)
    LinearLayout navHostFragment;

    AllTestsFragment allTestsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_sheet);
        ButterKnife.bind(this);

        allTestsFragment = new AllTestsFragment();

        manager = getSupportFragmentManager();

        myAccountTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setAllTestsScreen();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_test:
                        setAllTestsScreen();
                        break;
                }
                return false;
            }
        });

    }

    private void setAllTestsScreen() {
        manager.beginTransaction().replace(R.id.nav_host_fragment, allTestsFragment).commit();

    }


}
