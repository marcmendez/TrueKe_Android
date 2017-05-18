package trigues.com.trueke.view.impl;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.text.SimpleDateFormat;
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
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.AddProductPresenter;
import trigues.com.trueke.utils.AddProductSquareImageView;
import trigues.com.trueke.utils.ProductChecker;
import trigues.com.trueke.view.AddProductActivity;
import trigues.com.trueke.view.fragment.AddProductCategoryFragImpl;
import trigues.com.trueke.view.fragment.AddProductDesiredCategoryFragImpl;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Alba on 24/03/2017.
 */

public class AddProductActivityImpl extends BaseActivityImpl implements AddProductActivity {

    private final int MY_PERMISSIONS = 3;
    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    private String  photo1, photo2, photo3, photo4, nPath;
    private int num_photo;

    private static final String TAG = "AddProductActivityImpl";
    private String category;
    private List<String> desired_categories;
    private RelativeLayout mRlView;

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

    @BindView(R.id.productDesiredCategory)
    EditText e_desiredCategory;

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);
        ButterKnife.bind(this);

        category = photo1 = photo2 = photo3 = photo4="";
        desired_categories = Arrays.asList("");

        addCategoryFrag = new AddProductCategoryFragImpl();
        e_category.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                addFullScreenFragment(new AddProductCategoryFragImpl());
            }
        });

        e_desiredCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFullScreenFragment(new AddProductDesiredCategoryFragImpl());
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

        if(mayRequestStoragePermission()) {
            e_photo1.setEnabled(true);
            e_photo2.setEnabled(true);
            e_photo3.setEnabled(true);
            e_photo4.setEnabled(true);
        }
        else {
            e_photo1.setEnabled(false);
            e_photo2.setEnabled(false);
            e_photo3.setEnabled(false);
            e_photo4.setEnabled(false);
        }

        addEvents();
    }

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showOptions() {
        final CharSequence[] options ={"Hacer foto", "Abrir galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivityImpl.this);
        builder.setTitle("Elige una opción");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which] == "Hacer foto") {
                    getPictureFromCamera();
                }
                else if (options[which] == "Abrir galeria") {
                    getPictureFromGallery();
                }
                else dialog.dismiss();
            }
        });
        builder.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getPictureFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("error","error while creating the File");
            }
            if (photoFile != null) {
               /*Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);   */
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
                startActivityForResult(takePictureIntent, PICTURE_TAKEN_FROM_CAMERA);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        nPath = image.getAbsolutePath();
        return image;
    }

    private void getPictureFromGallery(){
        Intent gallerypickerIntent = new Intent(Intent.ACTION_PICK);
        gallerypickerIntent.setType("image/*");
        startActivityForResult(gallerypickerIntent, PICTURE_TAKEN_FROM_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AddProductActivityImpl.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                e_photo1.setEnabled(true);
                e_photo2.setEnabled(true);
                e_photo3.setEnabled(true);
                e_photo4.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivityImpl.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
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
            String encoded_image = BitMapToString (imageBitmap);

            if (num_photo == 1) {
                e_photo1.setImageBitmap(imageBitmap);
                photo1 = encoded_image;
            }
            else if (num_photo == 2) {
                e_photo2.setImageBitmap(imageBitmap);
                photo2 = encoded_image;
            }
            else if (num_photo == 3) {
                e_photo3.setImageBitmap(imageBitmap);
                photo3 = encoded_image;
            }
            else {
                e_photo4.setImageBitmap(imageBitmap);
                photo4 = encoded_image;
            }

        }
        else if (requestCode == PICTURE_TAKEN_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                nPath = getPathFromURI(selectedImageUri);
                Bitmap bm = BitmapFactory.decodeFile(nPath);
                String encoded_image = BitMapToString (bm);
                //Log.i(TAG, "Image Path : " + nPath);
                if (num_photo == 1) {
                    e_photo1.setImageURI(selectedImageUri);
                    photo1 = encoded_image;
                }
                else if (num_photo == 2) {
                    e_photo2.setImageURI(selectedImageUri);
                    photo2 =  encoded_image;
                }
                else if (num_photo == 3) {
                    e_photo3.setImageURI(selectedImageUri);
                    photo3 =  encoded_image;
                }
                else {
                    e_photo4.setImageURI(selectedImageUri);
                    photo4 =  encoded_image;
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

    //path to base64
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
            ProductChecker.checkDesiredCategories(desired_categories);
            ProductChecker.checkPrice(priceMin, priceMax);
            ProductChecker.checkImages(photo1,photo2,photo3,photo4);
            List<String> images = Arrays.asList(photo1,photo2,photo3,photo4);
            //Log.i("images","ret image1 addProduct"+photo1);
            //Log.i("images","ret image2 addProduct"+photo2);
            //Log.i("images","ret image3 addProduct"+photo3);
            presenter.addProduct(title, description, images, category, desired_categories,Integer.valueOf(priceMin),Integer.valueOf(priceMax));
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
    }

    public void onDesiredCategoryPressed(List<String> categories){
        String desiredCategoryText = "";
        for(String category : categories){
            desiredCategoryText = desiredCategoryText + category + "\n";
        }
        if(desiredCategoryText.length() != 0) {
            desiredCategoryText = desiredCategoryText.substring(0, desiredCategoryText.length() - 1);
        }
        e_desiredCategory.setText(desiredCategoryText);
        /*ListIterator<String> iterator = categories.listIterator();
        while (iterator.hasNext())
        {
            iterator.set(iterator.next().toLowerCase());
        }*/
        desired_categories = categories;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
