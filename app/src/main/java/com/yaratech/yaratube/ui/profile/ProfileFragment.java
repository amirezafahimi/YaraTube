package com.yaratech.yaratube.ui.profile;

import android.Manifest;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
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


public class ProfileFragment extends Fragment
        implements DatePickerDialog.OnDateSetListener,
        ProfileContract.View {

    private OnFragmentInteractionListener mListener;
    ProfilePresenter profilePresenter;

    Button saveButton;
    Button cancelButton;
    Button signOutButton;
    ImageView profileImage;
    File profileImageFile;
    File originalProfileImageFile;
    Uri imagePath;
    Spinner sexDropDown;
    EditText nickName;
    TextView birthDate;
    Uri imageFileUri;
    String birthDateString;
    String gender;

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
        signOutButton = view.findViewById(R.id.sign_out);
        profileImage = view.findViewById(R.id.profile_picture);
        sexDropDown = view.findViewById(R.id.gender);
        String[] items = new String[]{"مرد", "زن"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, items);
        sexDropDown.setAdapter(adapter);
        nickName = view.findViewById(R.id.nick_name);
        birthDate = view.findViewById(R.id.birth_date);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profilePresenter.readUserDataFromDB();
        if (Util.isNetworkAvailable(getContext())) {
            profilePresenter.getProfileDataFromServer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        sexDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Log.e("item 1", "onItemSelected: ");
                        gender = "Male";
                        break;
                    case 1:
                        Log.e("item 2", "onItemSelected: ");
                        gender = "Female";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                // no-op
            }
        });

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
                        /*onRemove();
                        mBottomSheetDialog.hide();*/
                    }
                });
            }
        });

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileDetail();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                // [END configure_signin]

                // [START build_client]
                // Build a GoogleSignInClient with the options specified by gso.
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                profilePresenter.signOutUser();
                getFragmentManager().popBackStack();
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


                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                            getContext().getContentResolver(),
                            result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                FileOutputStream fileOutputStream = null;

                try {
                    profileImageFile = originalProfileImageFile;
                    profileImageFile.createNewFile();
                    fileOutputStream = new FileOutputStream(profileImageFile);
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // because file name is always same
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true);

                if (profileImageFile != null) {
                    profilePresenter.sendProfieImageToServer(profileImageFile);
                } else {
                    setProfileImage(null);
                }
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

    //----------------------------------------------------------------------------------------------

    public void saveProfileDetail() {
        profilePresenter.sendProfileDataToServer(
                nickName.getText().toString(),
                gender,
                birthDateString,
                Util.getDeviceId(getContext()),
                Util.getDeviceModel(),
                Util.getDeviceOS());
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

            try {
                originalProfileImageFile = createImageFile();
                imageFileUri = Uri.fromFile(new File(originalProfileImageFile.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getContext(),
                    getActivity().getPackageName() + ".provider",
                    originalProfileImageFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, CAMERA);
        }
    }

    public void onGallery() {

        try {
            originalProfileImageFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

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
        /*profilePresenter.sendProfileImageToDB("");*/
        profilePresenter.sendProfieImageToServer(null);
        profileImageFile = null;
    }

    public void setDate() {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        dpd.setThemeDark(false);
        dpd.show(getActivity().getFragmentManager(), "tag");
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String monthOfYearString;
        String dayOfMonthString;

        if (monthOfYear < 10)
            monthOfYearString = "0" + monthOfYear;
        else
            monthOfYearString = "" + (monthOfYear + 1);

        if (dayOfMonth < 10)
            dayOfMonthString = "0" + dayOfMonth;
        else
            dayOfMonthString = "" + dayOfMonth
                    ;
        birthDateString = year + "-" + monthOfYearString + "-" + dayOfMonthString;
        birthDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        Log.e("tag", birthDateString);

    }

    @Override
    public void showMessege(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void fillFroile(String nickName, String gender, String birthDate) {
        if (nickName != "")
            this.nickName.setText(nickName);
        this.birthDate.setText(birthDate);
        if (gender != null && gender.equals("Female"))
            sexDropDown.setSelection(1);
        else if (gender != null && gender.equals("Male"))
            sexDropDown.setSelection(0);
    }

    @Override
    public void setProfileImage(Uri imageUri) {
        if (getActivity() == null) {
            return;
        }
        if (imageUri != null) {
            Glide.with(getContext()).load(imageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage);
            profileImageFile = null;
        } else {
            profileImageFile = null;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}