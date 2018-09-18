package com.yaratech.yaratube.ui.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
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
    private Uri imageFileUri;

    final int CAMERA = 0;
    final int GALLERY = 1;
    private static final int CAMERA_PERMISSION = 2;
    private static final int GALERY_PERMISSION = 3;

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
                        if (!Util.checkCameraPermissions(getContext())) {
                            requestCameraPermission();
                        } else {
                            onCamera();
                        }
                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!Util.checkGalleryPermissions(getContext())) {
                            requestGalleryPermission();
                        } else {
                            onGallery();
                        }
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
        Uri resultUri = null;
        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA) {
                CropImage
                        .activity(imageFileUri)
                        .setAllowFlipping(true)
                        .setAllowRotation(true)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setOutputCompressQuality(50)
                        .setFixAspectRatio(true)
                        .start(getContext(), this);
            } else if (requestCode == GALLERY) {
                {
                    CropImage.activity(data.getData())
                            .setAllowFlipping(true)
                            .setAllowRotation(true)
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setOutputCompressQuality(50)
                            .setFixAspectRatio(true)
                            .start(getContext(), this);
                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                resultUri = result.getUri();
                Log.d("123546", "onActivityResult: " + resultUri.getPath());
                Glide.with(getContext()).load(resultUri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCamera();

                } else {
                    Log.e("permission", " denied");
                }
                return;
            case GALERY_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onGallery();

                } else {
                    Log.e("permission", " denied");
                }
                return;
        }
    }


    //----------------------------------------------------------------------------------------------

    public void saveProfileDetail() {
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

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                CAMERA_PERMISSION);
    }


    private void requestGalleryPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                GALERY_PERMISSION);
    }

    //----------------------------------------------------------------------------------------------

    public void onCamera() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();

                imageFileUri = Uri.fromFile(new File(photoFile.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getContext(),
                    getActivity().getPackageName() + ".provider",
                    photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, CAMERA);
        }
    }

    public void onGallery() {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, GALLERY);
    }

    public void onRemove() {
        profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
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