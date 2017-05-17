package trigues.com.trueke.view.impl;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

/**
 * Created by Alba on 24/03/2017.
 */

public class AddProductActivityImpl extends BaseActivityImpl implements AddProductActivity {

    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    private String  photo1, photo2, photo3, photo4, nPath;
    private int num_photo;

    private static final String TAG = "AddProductActivityImpl";
    private String category;
    private List<String> desired_categories;

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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = String.valueOf(System.currentTimeMillis()/1000);
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
            ProductChecker.checkDesiredCategories(desired_categories);
            ProductChecker.checkPrice(priceMin, priceMax);
            //ProductChecker.checkImages(photo1,photo2,photo3,photo4);
            List<String> images = Arrays.asList(photo1,photo2,photo3,photo4);
            //presenter.addProduct(title, description, images, productCategory, desired_categories,Integer.valueOf(priceMin),Integer.valueOf(priceMax));
            presenter.addProduct(title,description,images,"utils", Arrays.asList("utils"),Integer.valueOf(priceMin),Integer.valueOf(priceMax));
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
        //TODO:
        String desiredCategoryText = "";
        for(String category : categories){
            desiredCategoryText = desiredCategoryText + category + "\n";
        }
        if(desiredCategoryText.length() != 0) {
            desiredCategoryText = desiredCategoryText.substring(0, desiredCategoryText.length() - 1);
        }
        e_desiredCategory.setText(desiredCategoryText);
        ListIterator<String> iterator = categories.listIterator();
        while (iterator.hasNext())
        {
            iterator.set(iterator.next().toLowerCase());
        }
        desired_categories = categories;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
