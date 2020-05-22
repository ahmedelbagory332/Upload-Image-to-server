package com.example.bego.api_mvvm_retrofit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bego.api_mvvm_retrofit.MultiPart.ApiClient;
import com.example.bego.api_mvvm_retrofit.MultiPart.FileUtils;
import com.example.bego.api_mvvm_retrofit.MultiPart.ServerResponse;
import com.example.bego.api_mvvm_retrofit.uploadImageBase64.Api;
import com.example.bego.api_mvvm_retrofit.uploadImageBase64.Model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    ImageView imageView;
    Button selectImg,uploadImg;
    EditText imgTitle;
    private  static final int IMAGE = 100;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.imageView);
        selectImg = (Button) findViewById(R.id.selectImg);
        uploadImg = (Button) findViewById(R.id.uploadImg);
        imgTitle = (EditText) findViewById(R.id.imgTitle);


        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImageBase64();

            }
        });

    }



    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE && resultCode==RESULT_OK && data!=null)
        {
            // Get the Image from data
            Uri selectedImage = data.getData();

            // for upload decoded image

//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
//                imageView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            // for upload multipart image
           imageView.setImageURI(selectedImage);


            uploadImageMultiPart(selectedImage);

        }
    }


    private String imageTostring(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imgbyte = byteArrayOutputStream.toByteArray();
        return  Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }


    private void uploadImageBase64(){

        String image = imageTostring();
        String title = imgTitle.getText().toString();

        Api api = com.example.bego.api_mvvm_retrofit.uploadImageBase64.ApiClient.getApiClient().create(Api.class);
        Call<Model> call  = api.uploadImage(title,image);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                Toast.makeText(MainActivity.this, model.getResponse(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public static MultipartBody.Part prepareFilePart(Context context, String partName, Uri fileUri) {
        File file = FileUtils.getFile(context, fileUri);

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects
                                .requireNonNull(context.getContentResolver().getType(fileUri))),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private void uploadImageMultiPart(Uri fileUri) {

        String title = imgTitle.getText().toString();

        MultipartBody.Part fileToSend = prepareFilePart(getApplicationContext(), "image_path", fileUri);


        ApiClient.getINSTANCE().writePost(title,fileToSend).enqueue(new Callback<List<ServerResponse>>() {
            @Override
            public void onResponse(Call<List<ServerResponse>> call, Response<List<ServerResponse>> response) {

                List<ServerResponse> serverResponses = response.body();

                for (int i = 0; i < serverResponses.size(); i++) {
                    Toast.makeText(
                            getApplicationContext(), serverResponses.get(i).getMessage()
                            +
                            "\n\n"
                            + serverResponses.get(i).getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ServerResponse>> call, Throwable t) {

                Log.d("bigo error  :  ",  t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
