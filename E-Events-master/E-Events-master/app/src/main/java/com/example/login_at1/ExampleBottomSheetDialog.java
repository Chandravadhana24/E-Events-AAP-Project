package com.example.login_at1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.app.Activity.RESULT_OK;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private BottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.bottom_sheet_layout_dp,container,false);

        Button gallerybutton=v.findViewById(R.id.gallerybutton);
        Button camerabutton=v.findViewById(R.id.camerabutton);

        gallerybutton.setOnClickListener(this);
        camerabutton.setOnClickListener(this);

        /*gallerybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,3);
            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri imageuri = data.getData();
                    mListener.OnButtonClicked(imageuri);
                    dismiss();
                    //ImageView imageView = (ImageView) findViewById(R.id.iv);
                    //imageView.setImageURI(imageUri);
                }
            }

        });


        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,4);


            }
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 4) {
                    Uri imageuri = (Uri) data.getExtras().get("data");
                    Log.d("success", "went into created class oda onactivityresult");
                    mListener.OnButtonClicked(imageuri);
                    //Toast.makeText(this,String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
                }
            }

        });
*/

        return v;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.gallerybutton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,3);

        }
        if(view.getId()==R.id.camerabutton)
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,4);

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageuri = data.getData();
            mListener.OnButtonClicked(imageuri);
            dismiss();
            //ImageView imageView = (ImageView) findViewById(R.id.iv);
            //imageView.setImageURI(imageUri);
        }
        if (requestCode == 4) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //Log.d("success", "went into created class oda onactivityresult");
            mListener.OnButtonClicked(photo);
            //Toast.makeText(this,String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }




    public interface BottomSheetListener{
        void OnButtonClicked(Uri imageuri);
        void OnButtonClicked(Bitmap photo);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        }catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement BottomSheetListener");
        }
    }
}