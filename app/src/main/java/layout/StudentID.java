package layout;

import com.tble.brgo.R;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import android.net.Uri;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.io.IOException;
import java.text.SimpleDateFormat;
import android.content.ContentValues;
import android.provider.MediaStore.Images;
import android.media.Image;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentID#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentID extends Fragment {
    String mCurrentPhotoPath;
    Uri mPhotoURI;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView picView;
    FloatingActionButton button;
    public StudentID() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StudentID.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentID newInstance() {
        StudentID fragment = new StudentID();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_id, container, false);
        picView = (ImageView)view.findViewById(R.id.idPic);
        picView.setScaleType(ImageView.ScaleType.FIT_XY);
        button = (FloatingActionButton)view.findViewById(R.id.picButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        picView = (ImageView)view.findViewById(R.id.idPic);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(!sharedPref.getBoolean("hasOpened",false))
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            sharedPref.edit().putBoolean("hasOpened",true).commit();
        }
        picView.setImageURI(Uri.parse(sharedPref.getString("StudentID","")));
        return view;
    }
    //Starts the picture process
    public void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                try {
                    mPhotoURI = Uri.fromFile(photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

    //creates a permanently stored file for the image to be saved
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir,imageFileName + ".jpg");
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //saves the file
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == android.app.Activity.RESULT_OK){
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("StudentID", mPhotoURI.toString()).commit();
                System.out.println(mPhotoURI);
            picView.setImageURI(mPhotoURI);
        }
    }
}
