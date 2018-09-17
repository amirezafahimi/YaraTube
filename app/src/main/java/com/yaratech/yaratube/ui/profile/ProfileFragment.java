package com.yaratech.yaratube.ui.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.soundcloud.android.crop.Crop;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;


public class ProfileFragment extends Fragment implements ProfileContract.View {

    private OnFragmentInteractionListener mListener;
    ProfilePresenter profilePresenter;

    Button saveButton;
    Button cancelButton;
    ImageView profileImage;
    File destination;
    Uri imagePath;
    Spinner sex;
    EditText nickName;
    TextView birthDate;
    private static int PICK_IMAGE = 12;
    private String imageFilePath = "";

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getFragmentManager().beginTransaction()
                .show(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePresenter = new ProfilePresenter(new Repository(), this);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton = view.findViewById(R.id.save);
        cancelButton = view.findViewById(R.id.cancel);
        profileImage = view.findViewById(R.id.profile_picture);
        sex = view.findViewById(R.id.gender);
        nickName = view.findViewById(R.id.nick_name);
        birthDate = view.findViewById(R.id.birth_date);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View sheetView = getActivity().getLayoutInflater()
                        .inflate(R.layout.on_profile_image_click, null);
                /*LinearLayout open = sheetView.findViewById(R.id.fragment_history_bottom_sheet_open);*/
                LinearLayout camera = sheetView.findViewById(R.id.fragment_history_bottom_sheet_camera);
                LinearLayout gallery = sheetView.findViewById(R.id.fragment_history_bottom_sheet_gallery);
                LinearLayout delete = sheetView.findViewById(R.id.fragment_history_bottom_sheet_delete);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBottomSheetDialog.hide();
                        onCamera();
                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onGallery();
                        mBottomSheetDialog.hide();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRemove();
                        mBottomSheetDialog.hide();
                    }
                });
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileDetail();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA) {
                Glide.with(getContext()).load(imageFilePath)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImage);
            } else if (requestCode == PICK_IMAGE) {
                Glide.with(getContext()).load(data.getData())
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImage);
            }
        }
    }

    public void saveProfileDetail(){
        if (imagePath != null)

            // send image

        profilePresenter.sendProfileData(
                profilePresenter.getUserToken(),
                nickName.getText().toString(),
                getSex(sex),
                birthDate.getText().toString());
    }

    private String getSex(Spinner gender) {
        switch (gender.getSelectedItemPosition()) {
            case 0:
                return "male";

            case 1:
                return "female";

            default:
                return "";
        }
    }

    public void onCamera() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
                imageFilePath = photoFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, CAMERA);
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    public void onGallery() {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    public void onRemove() {
        profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}