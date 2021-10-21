package com.voda.voda_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스

    private RecyclerView recycle_menu;
    private Button btn_menu_plus;

    private String mycategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recycle_menu = findViewById(R.id.recycle_menu);
        btn_menu_plus = findViewById(R.id.btn_menu_plus);
        btn_menu_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_dialog(v);
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("voda_handy");
    }

    public void menu_dialog(View v) {
        EditText edit_menu_name, edit_menu_category, edit_menu_explanation, edit_menu_tag, edit_menu_price;
        Button btn_menu_register;

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_menu_addition, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        alertDialog.show();

        edit_menu_name = dialogView.findViewById(R.id.edit_menu_name);
        edit_menu_category = dialogView.findViewById(R.id.edit_menu_category);
        edit_menu_explanation = dialogView.findViewById(R.id.edit_menu_explanation);
        edit_menu_tag = dialogView.findViewById(R.id.edit_menu_tag);
        btn_menu_register = dialogView.findViewById(R.id.btn_menu_register);
        edit_menu_price = dialogView.findViewById(R.id.edit_menu_price);

        btn_menu_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                String name = edit_menu_name.getText().toString();
                String category = edit_menu_category.getText().toString();
                String explanation = edit_menu_explanation.getText().toString();
                String tag = edit_menu_tag.getText().toString();
                Integer price = Integer.parseInt(edit_menu_price.getText().toString());

                Menu menu = new Menu();
                menu.setName(name);
                menu.setCategory(category);
                menu.setExplanation(explanation);
                menu.setTag(tag);
                menu.setPrice(price);

                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                mDatabaseRef.child("UserAccount").child("admin").child(firebaseUser.getUid()).child("category").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        Log.d("아ss녕","ㄹㅇㄹ");
                        mycategory = (String) datasnapshot.getValue();
                        mDatabaseRef.child("Store").child(mycategory).child(firebaseUser.getUid()).child("MenuInfo").child(name).setValue(menu);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("아녕","ㄹㅇㄹ");
                    }
                });
            }
        });
    }
}