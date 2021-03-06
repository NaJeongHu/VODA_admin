package com.voda.voda_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.voda.voda_admin.Adapter.RecyclerViewAdapter_Menu;
import com.voda.voda_admin.Adapter.RecyclerViewAdapter_Order;
import com.voda.voda_admin.Model.Menu;
import com.voda.voda_admin.Model.Order;
import com.voda.voda_admin.R;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private ArrayAdapter<String> SpinnerAdapter;

    //파이어베이스
    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private FirebaseUser firebaseUser;

    //UI
    private Spinner sp_order;

    //변수
    private String[] orders = {"모두","미완료 주문만", "완료 주문만"};
    private String mycategory;

    //리사이클러뷰(그리드뷰)
    private RecyclerView recycle_open;
    private GridLayoutManager layoutManager;
    private RecyclerViewAdapter_Order adapter;
    private ArrayList<Order> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        init();
//        getDataFromServer();
        int i=1;
    }

    private void init() {
        //파이어베이스
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("voda_handy");
//        firebaseUser = mFirebaseAuth.getCurrentUser();

        //arr 임의로 작성
        Order order1 = new Order("매장식사","2021-12-14 13:56:50","뜨겁지 않게 해주세요","제육 김밥:2&민트초코 김밥:1&파인애플 김밥:4");
        Order order2 = new Order("매장식사","2021-12-14 14:23:20","갑각류는 까주세요","딸기 김밥:2&파인애플 김밥:1");
        Order order3 = new Order("매장식사","2021-12-14 14:56:52","보온팩에 넣어주세요","제육 김밥:2");
        Order order4 = new Order("매장식사","2021-12-14 15:11:12","뜨겁지 않게 해주세요","칵테일:2&민트초코 김밥:1&파인애플 김밥:4");
        Order order5 = new Order("매장식사","2021-12-14 15:24:14","","제육 김밥:2&누드 김밥:10");
        Order order6 = new Order("매장식사","2021-12-14 15:27:00","뜨겁지 않게 해주세요","제육 김밥:2&민트초코 김밥:1&파인애플 김밥:4");
        arr.add(order1);
        arr.add(order2);
        arr.add(order3);
        arr.add(order4);
        arr.add(order5);
        arr.add(order6);

        //스피너
        sp_order = findViewById(R.id.sp_order);
        SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, orders);
        sp_order.setAdapter(SpinnerAdapter);
        sp_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else if (position == 1) {

                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //리사이클러뷰
        recycle_open = findViewById(R.id.recycler_open);
        recycle_open.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,3);
        recycle_open.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter_Order(arr, getApplicationContext());
        recycle_open.setAdapter(adapter);
    }

    private void getDataFromServer() {

        if (arr != null && arr.size() != 0) {
            arr.clear();
        }
        mDatabaseRef.child("UserAccount").child("admin").child(firebaseUser.getUid()).child("category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Log.d("파이어베이스", "파이어베이스 연동 성공");
                mycategory =datasnapshot.getValue(String.class);
                mDatabaseRef.child("Store").child(mycategory).child(firebaseUser.getUid()).child("OrderList").child("조리전").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            Order order1 = snapshot.getValue(Order.class);
                            arr.add(order1);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("파이어베이스", "실패2");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("파이어베이스", "데이터 가져오기 실패");
            }
        });
    }
}