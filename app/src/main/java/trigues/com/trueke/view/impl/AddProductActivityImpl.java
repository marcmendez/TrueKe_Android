package trigues.com.trueke.view.impl;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import javax.inject.Inject;

import trigues.com.data.datasource.ApiInterface;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.AddProductPresenter;
import trigues.com.trueke.utils.AddProductSquareImageView;
import trigues.com.trueke.utils.ProductChecker;
import trigues.com.trueke.view.AddProductActivity;
import trigues.com.trueke.view.UserProductsListActivity;
import trigues.com.trueke.view.fragment.AddProductCategoryFragImpl;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Alba on 24/03/2017.
 */

public class AddProductActivityImpl extends BaseActivityImpl implements AddProductActivity {

    private static String APP_DIRECTORY = "MyTruekeImages";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "TruekeImages";
    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    private String  photo1, photo2, photo3, photo4, nPath;
    private int num_photo;

    private static final String TAG = "AddProductActivityImpl";
    private boolean show_fragment;
    private String category;

    AddProductCategoryFragImpl addCategoryFrag;

    @BindView(R.id.productName)
    EditText e_title;

    @BindView(R.id.description)
    EditText e_description;

    @BindView(R.id.priceMin)
    EditText e_priceMin;

    @BindView(R.id.priceMax)
    EditText e_priceMax;

    @BindView(R.id.productCategory)
    EditText e_category;

    @BindView(R.id.add_product_close_button)
    ImageButton closeButton;

    @BindView(R.id.add_product_send_button)
    ImageButton addButton;

    @BindView(R.id.image1)
    AddProductSquareImageView e_photo1;

    @BindView(R.id.image2)
    AddProductSquareImageView e_photo2;

    @BindView(R.id.image3)
    AddProductSquareImageView e_photo3;

    @BindView(R.id.image4)
    AddProductSquareImageView e_photo4;


    @Inject
    AddProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);

        show_fragment = false;
        category = photo1 = photo2 = photo3 = photo4="";

        addCategoryFrag = new AddProductCategoryFragImpl();
        e_category.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if (show_fragment)
                    getSupportFragmentManager().beginTransaction()
                            .show(getSupportFragmentManager().findFragmentById(R.id.fragment_category)).commit();
                else
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_category, addCategoryFrag)
                            .commit();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onAddProductPressed();
            }
        });

        addEvents();
    }

    private void addEvents(){
        e_photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_photo = 1;
                showOptions();
            }
        });

        e_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_photo = 2;
                showOptions();
            }
        });

        e_photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_photo = 3;
                showOptions();
            }
        });

        e_photo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_photo = 4;
                showOptions();
            }
        });
    }

    private void showOptions() {
        final CharSequence[] options ={"Hacer foto", "Abrir galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivityImpl.this);
        builder.setTitle("Elige una opción");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which] == "Hacer foto") {
                    //openCamera();
                    getPictureFromCamera();

                }
                else if (options[which] == "Abrir galeria") {
                    getPictureFromGallery();
                   /* Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("images/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona una imagen"), PICTURE_TAKEN_FROM_GALLERY);*/
                }
                else dialog.dismiss();
            }
        });
        builder.show();
    }

    /*private void openCamera() {
        File file = new File (Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated) isDirectoryCreated = file.mkdirs();
        if(isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis()/1000;
            String imageName = timestamp.toString() + ".jpg";

            nPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File (nPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PICTURE_TAKEN_FROM_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case PICTURE_TAKEN_FROM_CAMERA:
                    MediaScannerConnection.scanFile(this, new String[]{nPath}, null,
                    new MediaScannerConnection.OnScanCompletedListener (){
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned" + path + "i");
                            Log.i("ExternalStorage", "-> Uri = " + uri);
                        }
                    });
                    Bitmap bitmap = BitmapFactory.decodeFile(nPath);
                    if (num_photo == 1) e_photo1.setImageBitmap(bitmap);
                    else if (num_photo == 2) e_photo2.setImageBitmap(bitmap);
                    else if (num_photo == 3) e_photo3.setImageBitmap(bitmap);
                    else e_photo4.setImageBitmap(bitmap);
            }
        }
    }*/


    private void getPictureFromCamera(){
       File file = new File (Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated) isDirectoryCreated = file.mkdirs();
        if(isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis()/1000;
            String imageName = timestamp.toString() + ".jpg";

            nPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, PICTURE_TAKEN_FROM_CAMERA);
            }
        }
    }

    private void getPictureFromGallery(){
        Intent gallerypickerIntent = new Intent(Intent.ACTION_PICK);
        gallerypickerIntent.setType("image/*");
        startActivityForResult(gallerypickerIntent, PICTURE_TAKEN_FROM_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICTURE_TAKEN_FROM_CAMERA && resultCode == RESULT_OK ) {
            MediaScannerConnection.scanFile(this, new String[]{nPath}, null,
                    new MediaScannerConnection.OnScanCompletedListener (){
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned" + path + "i");
                            Log.i("ExternalStorage", "-> Uri = " + uri);
                        }
                    });
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (num_photo == 1) {
                e_photo1.setImageBitmap(imageBitmap);
                photo1 = nPath;
            }
            else if (num_photo == 2) {
                e_photo2.setImageBitmap(imageBitmap);
                photo2 = nPath;
            }
            else if (num_photo == 3) {
                e_photo3.setImageBitmap(imageBitmap);
                photo3 = nPath;
            }
            else {
                e_photo4.setImageBitmap(imageBitmap);
                photo4 = nPath;
            }

        }
        else if (requestCode == PICTURE_TAKEN_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                nPath = getPathFromURI(selectedImageUri);
                Log.i(TAG, "Image Path : " + nPath);
                if (num_photo == 1) {
                    e_photo1.setImageURI(selectedImageUri);
                    photo1 = nPath;
                }
                else if (num_photo == 2) {
                    e_photo2.setImageURI(selectedImageUri);
                    photo2 = nPath;
                }
                else if (num_photo == 3) {
                    e_photo3.setImageURI(selectedImageUri);
                    photo3 = nPath;
                }
                else {
                    e_photo4.setImageURI(selectedImageUri);
                    photo4 = nPath;
                }
            }
        }
        Toast.makeText(getApplicationContext(),
                nPath, Toast.LENGTH_LONG).show();
    }

    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void onAddProductPressed() {
        String title = e_title.getText().toString();
        String description = e_description.getText().toString();
        String priceMin = e_priceMin.getText().toString();
        String priceMax = e_priceMax.getText().toString();
        try {
            ProductChecker.checkTitle(title);
            ProductChecker.checkDescription(description);
            ProductChecker.checkCategory(category);
            ProductChecker.checkPrice(priceMin, priceMax);
           //ProductChecker.checkImages(photo1,photo2,photo3,photo4);
            //String images =  photo1+"-"+photo2+"-"+photo3+"-"+photo4
            List<String> images = Arrays.asList(photo1,photo2,photo3,photo4);

            //presenter.addProduct(title, description, images, category, desiredCategories, minPrice, maxPrice);
            presenter.addProduct(title, description, images, "utils", Arrays.asList("utils"), Integer.valueOf(priceMin), Integer.valueOf(priceMax));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void goToShowProductList(){
        startActivity(new Intent(this, UserProductsListActivityImpl.class));
        finish();
    }

    public void onCategoryPressed(String cat){
        category = cat.toLowerCase();
        e_category.setText(cat);
        show_fragment = true;
        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.fragment_category)).commit();
    }

}
