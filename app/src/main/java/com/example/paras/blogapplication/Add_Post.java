package com.example.paras.blogapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class Add_Post extends AppCompatActivity {

    private EditText title_id,time_id,descryp_id;
    private ImageView post_id;
    private Button save_id;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener firebaselistener;
    private FirebaseUser firebaseUser;
    private static final int GalleryCode = 1;
    private Uri imageuri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__post);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("MBlog");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();

        title_id = (EditText) findViewById(R.id.addtitle_id);
        descryp_id = (EditText) findViewById(R.id.adddiscryption_id);
        post_id = (ImageView) findViewById(R.id.image_id);
        save_id = (Button) findViewById(R.id.addpost_id);

        save_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

        post_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,GalleryCode);
            }
        });

    }

    private void startPosting() {


        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

        final String title = title_id
                .getText().toString().trim();
        final String descryp = descryp_id.getText().toString().trim();

        if(!title.isEmpty() && !descryp.isEmpty() && imageuri != null)
        {
            StorageReference filepath = storageReference.child("MBlog_Images").
                    child(imageuri.getLastPathSegment());

            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){

                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String link = uri.toString();

                            DatabaseReference reference = databaseReference.push();

                            // String user_data__id, String timestamp, String image_data_id, String title_data_id, String descryp_data_id



                            Map<String , String> list = new HashMap<>();
                            list.put("title_data_id",title);
                            list.put("descryp_data_id",descryp);
                    list.put("image_data_id",link);
                            list.put("timestamp",String.valueOf(System.currentTimeMillis()));
                            list.put("user_data_id",firebaseUser.getUid());

                            reference.setValue(list);

                            //when all set then go to home page activity

                            startActivity(new Intent(Add_Post.this,HomePage.class));
                            finish();
                        }
                    });



                }
            });



        }
        else if(imageuri !=null)
        {

            StorageReference filepath = storageReference.child("MBlog_Images").
                    child(imageuri.getLastPathSegment());

            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String link = uri.toString();

                            DatabaseReference reference = databaseReference.push();
                            Map<String, String> haspmap = new HashMap<>();
                            haspmap.put("image_data_id",link);
                            haspmap.put("timestamp",String.valueOf(System.currentTimeMillis()));

                            reference.setValue(haspmap);

                            startActivity(new Intent(Add_Post.this,HomePage.class));
                            finish();

                        }
                    });
                }
            });
        }

        else
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryCode && resultCode == RESULT_OK)
        {

            imageuri = data.getData();
            post_id.setImageURI(imageuri);


            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                post_id.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
