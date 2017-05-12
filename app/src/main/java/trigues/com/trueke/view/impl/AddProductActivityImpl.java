package trigues.com.trueke.view.impl;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
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
            String encoded_image = BitMapToString (imageBitmap);

            if (num_photo == 1) {
                e_photo1.setImageBitmap(imageBitmap);
               // photo1 = nPath;
                photo1 = encoded_image;
            }
            else if (num_photo == 2) {
                e_photo2.setImageBitmap(imageBitmap);
                // photo2 = nPath;
                photo2 = encoded_image;
            }
            else if (num_photo == 3) {
                e_photo3.setImageBitmap(imageBitmap);
                //photo3 = nPath;
                photo3 = encoded_image;
            }
            else {
                e_photo4.setImageBitmap(imageBitmap);
                //photo4 = nPath;
                photo4 = encoded_image;
            }

        }
        else if (requestCode == PICTURE_TAKEN_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                nPath = getPathFromURI(selectedImageUri);
                Bitmap bm = BitmapFactory.decodeFile(nPath);
                String encoded_image = BitMapToString (bm);
                Log.i(TAG, "Image Path : " + nPath);
                if (num_photo == 1) {
                    e_photo1.setImageURI(selectedImageUri);
                    //photo1 = nPath;
                    photo1 = encoded_image;
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
            //ProductChecker.checkImages(photo1,photo2,photo3,photo4);
            //photo1 = "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";
            List<String> images = Arrays.asList(photo1,photo2,photo3,photo4);
            presenter.addProduct(title, description, images, category, desired_categories,Integer.valueOf(priceMin),Integer.valueOf(priceMax));
            presenter.addImagesProduct(images);
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
