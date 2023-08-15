package com.example.databindinginactivitywithfragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.example.databindinginactivitywithfragment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
//        mActivityMainBinding.setMainViewModel(mainViewModel);

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );
        MainViewModel mainViewModel = new MainViewModel("Data Binding In Activity With Fragment Tutorial");
        mActivityMainBinding.setMainViewModel(mainViewModel);

        openMyFragment();

        setContentView(mActivityMainBinding.getRoot());
    }

    public void openMyFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contain_fragment, new MyFragment());
        fragmentTransaction.commitAllowingStateLoss();
    }
}