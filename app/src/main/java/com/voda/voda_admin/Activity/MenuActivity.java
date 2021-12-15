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
//        getDataFromServer();

    }

    private void init() {
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("voda_handy");
//        storageReference = FirebaseStorage.getInstance().getReference("admin_menu_pictures");
//        firebaseUser = mFirebaseAuth.getCurrentUser();

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

        //임의로 menu 작성
        Menu menu1 = new Menu("제육김밥","한식","제육이 들어간 김밥입니다","추천",5000,"https://lh3.googleusercontent.com/proxy/rxPsPGZaTjXd5C_OOZ4egP5LDhLGfrRSyjCSnNTm3pbNZIqn7jBFC-ygmTNn-FE1pZkleZ7M9KMc7b3g6mgkxxoTmOQ5aTcHRO8Qqw9fr4w6xOkDCxE");
        Menu menu2 = new Menu("딸기김밥","한식","딸기와의 조화가 어울리는 김밥! 강력 추천합니다","강력 추천",4000,"data:image/jpeg;base64,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");
        Menu menu3 = new Menu("초코김밥","한식","카카오 함유 98% 농도의 초콜릿으로 만든 초코김밥입니다","베스트",4000,"https://t1.daumcdn.net/cfile/tistory/2638F44554D0D45323");
        Menu menu4 = new Menu("누드김밥","한식","부끄러운 김밥><","추천",3000,"https://mblogthumb-phinf.pstatic.net/20120716_38/godqhrrhvms_1342428853460dHRnP_JPEG/SAM_1389.JPG?type=w2");

        arr.add(menu1);
        arr.add(menu2);
        arr.add(menu3);
        arr.add(menu4);
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