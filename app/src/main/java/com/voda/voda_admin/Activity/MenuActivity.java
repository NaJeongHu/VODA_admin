package com.voda.voda_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.voda.voda_admin.Adapter.RecyclerViewAdapter_Menu;
import com.voda.voda_admin.Model.Menu;
import com.voda.voda_admin.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {

    //파이어베이스
    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private FirebaseUser firebaseUser;
    private StorageTask uploadTask;
    private StorageReference storageReference;

    //리사이클러뷰
    private RecyclerView recycle_menu;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter_Menu adapter;
    private ArrayList<Menu> arr = new ArrayList<>();

    // 이미지
    private static final int IMAGE_REQUEST = 0;
    private Uri imageUri;

    //Activity UI
    private Button btn_menu_plus;

    //Dialog UI
    private EditText edit_menu_name, edit_menu_category, edit_menu_explanation, edit_menu_tag, edit_menu_price;
    private Button btn_menu_register;
    private ImageView iv_menu_picture;

    //변수
    private String mycategory;
    private String name;
    private String category;
    private String explanation;
    private String tag;
    private Integer price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
        getDataFromServer();

    }

    private void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("voda_handy");
        storageReference = FirebaseStorage.getInstance().getReference("admin_menu_pictures");
        firebaseUser = mFirebaseAuth.getCurrentUser();

        recycle_menu = findViewById(R.id.recycle_menu);
        recycle_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycle_menu.setLayoutManager(layoutManager);

        btn_menu_plus = findViewById(R.id.btn_menu_plus);
        btn_menu_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_dialog(v);
            }
        });
        adapter = new RecyclerViewAdapter_Menu(arr, getApplicationContext());
        recycle_menu.setAdapter(adapter);
    }

    private void getDataFromServer() {

        if (arr != null && arr.size() != 0) {
            arr.clear();
        }
        mDatabaseRef.child("UserAccount").child("admin").child(firebaseUser.getUid()).child("category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Log.d("파이어베이스", "파이어베이스 연동 성공");
                mycategory = (String) datasnapshot.getValue();
                mDatabaseRef.child("Store").child(mycategory).child(firebaseUser.getUid()).child("MenuInfo").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            Log.d("파이어베이스", "파이어베이스 연동 성공3");
                            Menu menu1 = snapshot.getValue(Menu.class);
                            arr.add(menu1);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("파이어베이스", "데이터 가져오기 실패");
            }
        });
    }

    public void menu_dialog(View v) {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_menu_addition, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = 2400;
        alertDialog.getWindow().setAttributes(params);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);

        edit_menu_name = dialogView.findViewById(R.id.edit_menu_name);
        edit_menu_category = dialogView.findViewById(R.id.edit_menu_category);
        edit_menu_explanation = dialogView.findViewById(R.id.edit_menu_explanation);
        edit_menu_tag = dialogView.findViewById(R.id.edit_menu_tag);
        btn_menu_register = dialogView.findViewById(R.id.btn_menu_register);
        iv_menu_picture = dialogView.findViewById(R.id.iv_menu_picture);
        edit_menu_price = dialogView.findViewById(R.id.edit_menu_price);

        iv_menu_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
            }
        });
        btn_menu_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                name = edit_menu_name.getText().toString();
                category = edit_menu_category.getText().toString();
                explanation = edit_menu_explanation.getText().toString();
                tag = edit_menu_tag.getText().toString();
                price = Integer.parseInt(edit_menu_price.getText().toString());

                Menu menu = new Menu();
                menu.setName(name);
                menu.setCategory(category);
                menu.setExplanation(explanation);
                menu.setTag(tag);
                menu.setPrice(price);

                if (imageUri != null) {
                    mDatabaseRef.child("Store").child(mycategory).child(firebaseUser.getUid()).child("MenuInfo").child(name).setValue(menu);
                    uploadImage();

                } else {
                    Toast.makeText(MenuActivity.this, "선택된 이미지가 없습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void uploadImage() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                + "." + getFileExtension(imageUri));

        uploadTask = fileReference.putFile(imageUri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageurl", mUri);

                    mDatabaseRef.child("Store").child(mycategory).child(firebaseUser.getUid()).child("MenuInfo").child(name).updateChildren(map);
                } else {
                    Toast.makeText(MenuActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = MenuActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //set Image to mIvPicture
                if (imageUri != null) {
                    iv_menu_picture.setImageURI(imageUri);
                }
            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}