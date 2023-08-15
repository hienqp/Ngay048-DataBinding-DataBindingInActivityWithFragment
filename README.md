___
# DATA BINDING VÀ MVVM

[DataBinding Google Document](https://developer.android.com/topic/libraries/data-binding)

- DataBinding Library là thư viện hỗ trợ, giúp liên kết giao diện UI trong Layout đến các nguồn dữ liệu trong app, bằng cách sử dụng hình thức khai báo trực tiếp trong Layout thay vì viết chương trình điều khiển
- DataBinding Library được Google giới thiệu nhằm hỗ trợ trực tiếp cho mô hình MVVM trong quá trình xây dựng ứng dụng
___

## CONFIGURE DATA BINDING

- để sử dụng DataBinding, trong file __build.gradle Module App__ ta thêm ``buildFeatures`` và thiết lập ``dataBinding true`` vào trong thẻ ``android``
- __build.gradle (module:App)__
```js
android {

    buildFeatures {
        dataBinding true
    }
}

```
- click __Sync Now__ để Gradle đồng bộ dữ liệu
- tất nhiên, ta chỉ mới enable Data Binding chứ chưa thực hiện thao tác sử dụng nó
- Data Binding thường sử dụng với ViewModel class trong kiến trúc MVVM, vì thế ta sẽ tiến hành khai báo class ViewModel và thực hiện các bước sử dụng Data Binding

___

## VIEWMODEL

- xây dựng 1 class ViewModel, ở đây ta sẽ dựng class __MainViewModel__ để chuyên xử lý logic cho __MainActivity__, có nghĩa là class __MainViewModel__ này sẽ hoàn toàn thay thế __MainActivity__ trong việc sử lý logic
- giả sử class __MainViewModel__ quản lý TextView, có nhiệm vụ khởi tạo 1 đối tượng có TextView, set và get dữ liệu cho TextView
- __MainViewModel.java__
```java
public class MainViewModel {
    private String name;

    public MainViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

___

## LAYOUT DÙNG ĐỂ BIND DATA VỚI VIEWMODEL

- ở đây ta sử dụng luôn layout __activity_main__ để bind data giữa layout với đối tượng MainViewModel
- ta chỉnh sửa lại như sau:
    - cấp view group cao nhất là __layout_, bên trong ta khai báo __data__ và __ViewGroup__
    - khai báo sử dụng data (chỉ đến ViewModel được dùng để bind data)
    - khai báo ViewGroup (LinearLayout, FrameLayout, RelativeLayout, ....) nếu cần quản lý nhiều View, hoặc chỉ 1 View
- khai báo thẻ ``<layout></layout>`` là cấp __ViewGroup__ cao nhất
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android" 
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <!--  -->
</layout>
```

- khai báo thẻ ``<data></data>`` bên trong, và chỉ định ``<variable />`` cho thẻ ``data``
- thẻ ``variable`` có 2 thuộc tính là:
    - ``name``: nên đặt trùng tên với __ViewModel__ đang bind data
    - ``type``: hay địa chỉ dẫn đến class __ViewModel__
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="MainViewModel"
            type="com.example.databinding.MainViewModel" />
    </data>

    <!--  -->
</layout>
```

- tiếp tục, trong ``<layout></layout>`` khai báo các ViewGroup hoặc View cần binding data
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="MainViewModel"
            type="com.example.databinding.MainViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:text="@{MainViewModel.name}"
            android:textSize="20sp" />
    </LinearLayout>
</layout>
```
- ở trên ta thấy TextView sẽ dùng để hiển thị thuộc tính ``name`` từ ``MainViewModel`` với cú pháp:
    - ``@{ViewModel.THUỘC_TÍNH}`` : câu lệnh ràng buộc dữ liệu 1 chiều
    - ``@={ViewModel.THUỘC_TÍNH}`` : câu lệnh ràng buộc dữ liệu 2 chiều
- với cú pháp trên, thì bất cứ khi nào __MainViewModel__ thay đổi thuộc tính __name__ thì ngay lập tức TextView trên __activity_main.xml__ sẽ thay đổi theo

___

## MAIN ACTIVITY CLASS

- để class __MainViewModel__ và layout __activity_main__ có thể liên lạc được với nhau, phải thông qua class data binding được sinh ra khi __enable dataBinding__
- trong __MainActivity.java__ trước khi gọi function ``setContentView()`` ta khai báo đối tượng binding tương ứng với layout __activity_main.xml__ thì class binding là __ActivityMainBinding.java__
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
```

- khởi tạo đối tượng class binding thông qua chính class binding gọi đến function ``inflate(getLayoutInflater())``
- hoặc thông qua class ``DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main )`` 
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );

    }
}
```

- khởi tạo đối tượng ViewModel ở đây là __MainViewModel__
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );
        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");

    }
}
```

- thiết lập set dữ liệu ViewModel cho Binding class
- __MainActivity.java__
```java
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
        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
        mActivityMainBinding.setMainViewModel(mainViewModel);

    }
}
```

- gọi function ``setContentView()`` và truyền vào chính là đối tượng Binding thực hiện function ``getRoot()``
- __MainActivity.java__
```java
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
        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
        mActivityMainBinding.setMainViewModel(mainViewModel);

        setContentView(mActivityMainBinding.getRoot());
    }
}
```
___

# DATA BINDING & FRAGMENT IN ACTIVITY

- cách sử dụng __DataBinding__ vơi __Fragment__ trong __Activity__ cũng tương tự như cách sử dụng trong chính __Activity__
- các bước như sau:
    - __enable DataBinding__ trong __build.gradle module:app__ và __Sync Now__
    - cài đặt 1 class __ViewModel__ chuyên xử lý logic cho __Fragment__
    - thiết lập __layout__ cho __Fragment__ với:
        - thẻ ``<layout></layout>`` cao nhất
        - thẻ ``<data></data>`` chứa thẻ ``<variable />`` với các thuộc tính __name__ (tên của class Fragment ViewModel) và __type__ (đường chỉ đến class Fragment ViewModel)
    - cài đặt class __Fragment__ để thực hiện kết nối bind data giữa class __Fragment ViewModel__ và __layout__ của __Fragment__ 
- sau khi thực hiện các bước trên thì việc dựng Fragmnet lên là phần việc còn lại của __Activity__ điều khiển __Fragment__
- ở __Activity__ ta thực hiện các bước sau
    - trong layout của __Activity__ thiết kế 1 layout dùng để chứa __Fragment__
    - trong __Activity__ thực hiện gọi __Fragment__

___

## VIEWMODEL FRAGMENT

- giả sử ta sẽ cài đặt 1 Fragment với tên __MyFragment__, thì ta nên cài đặt ViewModel cho Fragment sao cho liên quan đến Fragment đó, ví dụ __MyFragmentViewModel__
- ta lấy ví dụ Fragment sẽ hiển thị 1 TextView, thì ta sẽ cài đặt __MyFragmentViewModel__ quản lý thuộc tính, mà dữ liệu của thuộc tính này sẽ được bind lên layout của Fragmnet
- __MyFragmentViewModel.java__
```java
public class MyFragmentViewModel {
    private String title;

    public MyFragmentViewModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
```
___
## LAYOUT FRAGMENT

- vì layout của Fragment này sẽ được sử dụng __DataBinding__ nên cách cài đặt cũng tương tự như khi sử dụng DataBinding trên Activity
- ta thực hiện các bước sau:
    - tạo 1 file layout
    - thiết lập thẻ cao nhất là ``<layout></layout>``
    - trong thẻ __layout__ cài đặt thẻ ``<data></data>`` và __layout__ thành phần giao diện của Fragment
        - trong thẻ __data__ khai báo thẻ ``<variable />`` với các thuộc tính __name__ và __type__
            - __name__: tên class ViewModel của Fragment
            - __type__: đường dẫn đến class ViewModel của Fragment
        - __layout__ thành phần giao diện có thể là
            - 1 ViewGroup chứa các View
            - hoặc chỉ có 1 View nếu giao diện của Fragment đơn giản chỉ có 1 View
- __fragment_my.xml__
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="MyFragmentViewModel"
            type="com.example.databindinginactivitywithfragment.MyFragmentViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#4DD0E1"
        android:orientation="vertical"
        android:padding="20dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{MyFragmentViewModel.title}"
            android:textColor="@color/black"
            android:textSize="20sp"
            android:textStyle="bold" />
    </LinearLayout>
</layout>
```
- ở file layout trên ta thấy __TextView__ được ràng buộc dữ liệu với ViewModel của Fragment ở thuộc tính __text__, nghĩa là ViewModel của Fragment mà thay đổi giá trị thuộc tính __title__ thì __TextView__ này cũng sẽ thay đổi theo
- cú pháp ràng buộc dữ liệu:
    - ``@{ViewModel.THUỘC_TÍNH}``: ràng buộc dữ liệu 1 chiều
    - ``@={ViewModel.THUỘC_TÍNH}``: ràng buộc dữ liệu 2 chiều

___

## FRAGMENT và androidx

- ta có thể cài đặt __Fragment__ theo template của __Android Studio__ hoặc tạo thủ công bằng cách tạo file class __Fragment__ và file __layout__ cho Fragment
- vì ta sử dụng __DataBinding__ của __library androidx__ vì vậy khi khởi tạo __Fragment__ hoặc khi thực hiện __gọi__ Fragment cũng phải sử dụng __androidx__
- ta cài đặt class __MyFragment extends Fragment__ của __androidx library__ sau đó __override__ lại function __onCreateView()__

- __MyFragment.java__
```java
package com.example.databindinginactivitywithfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
```

- sau khi có được class Fragment, xử lý tính toán thường được xử lý trong __onCreateView__
- mà __onCreateView__ là function trả về là __View__
- ta khai báo 2 đối tượng __View__ và __ViewModel__ cho Fragment
- __MyFragment.java__
```java
public class MyFragment extends Fragment {
    private View view;
    private FragmentMyBinding mFragmentMyBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
```
- trước lệnh ``return`` trong __onCreateView__, ta khởi tạo:
    - đối tượng class binding bằng:
        - chính class binding đó gọi function __inflate__
        - hoặc class __DataBindingUtil__ gọi function __inflate__
- __MyFragment.java__
```java
public class MyFragment extends Fragment {
    private View view;
    private FragmentMyBinding mFragmentMyBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // cách 1 khởi tạo đối tượng class binding
        // mFragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false);

        // cách 2 khởi tạo đối tượng class binding
       mFragmentMyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
```

- khởi tạo đối tượng ViewModel của Fragment
- __set__ ViewModel cho đối tượng class binding
- thông qua đối tượng class binding sau khi đã gọi __setMyFragmentViewModel__, gọi đến function __getRoot()__ trả về 1 đối tượng View
- cuối cùng lệnh ``return`` của Fragment chính là đối tượng View
- __MyFragment.java__
```java
public class MyFragment extends Fragment {
    private View view;
    private FragmentMyBinding mFragmentMyBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // cách 1 khởi tạo class binding
        mFragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false);

        // cách 2 khởi tạo class binding
//        mFragmentMyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false);


        MyFragmentViewModel myFragmentViewModel = new MyFragmentViewModel("Title MyFragment In Activity With DataBinding");
        mFragmentMyBinding.setMyFragmentViewModel(myFragmentViewModel);
        view = mFragmentMyBinding.getRoot();

        return view;
    }
}
```

___

## DỰNG FRAGMENT TRONG ACTIVITY

- trong layout của Activity ta thêm 1 View dùng để chứa Fragment
- __activity_main.xml__
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="MainViewModel"
            type="com.example.databindinginactivitywithfragment.MainViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:text="@{MainViewModel.name}"
            android:textSize="20sp" />

        <FrameLayout
            android:id="@+id/contain_fragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </LinearLayout>
</layout>
```

- trong __MainActivity__ trước khi gọi lệnh ``setContentView()`` ta thực hiện gọi hiển thị Fragment lên View chứa Fragment trong Activity
- __MainActivity.java__
```java
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
```