package com.example.databindinginactivitywithfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.databindinginactivitywithfragment.databinding.FragmentMyBinding;

public class MyFragment extends Fragment {
    private View view;
    private FragmentMyBinding mFragmentMyBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // cách 1 khởi tạo class binding
//        mFragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false);

        // cách 2 khởi tạo class binding
        mFragmentMyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false);


        MyFragmentViewModel myFragmentViewModel = new MyFragmentViewModel("Title MyFragment In Activity With DataBinding");
        mFragmentMyBinding.setMyFragmentViewModel(myFragmentViewModel);
        view = mFragmentMyBinding.getRoot();

        return view;
    }
}
