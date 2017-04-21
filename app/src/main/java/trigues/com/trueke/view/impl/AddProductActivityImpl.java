package trigues.com.trueke.view.impl;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import javax.inject.Inject;

import trigues.com.trueke.R;
import trigues.com.trueke.presenter.AddProductPresenter;
import trigues.com.trueke.utils.ProductChecker;
import trigues.com.trueke.view.AddProductActivity;
import trigues.com.trueke.view.UserProductsListActivity;
import trigues.com.trueke.view.fragment.AddProductCategoryFragImpl;

/**
 * Created by Alba on 24/03/2017.
 */

public class AddProductActivityImpl extends BaseActivityImpl implements AddProductActivity {

    Spinner spinner1, spinner2;
    ArrayAdapter<CharSequence> adapter1, adapter2;
    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    private ImageButton btncamera;
    private ImageButton btngallery;
    private ImageView result;
    private static final String TAG = "AddProductActivityImpl";

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


    @Inject
    AddProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);

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


       /*btncamera = (ImageButton) findViewById(R.id.btncamera);
        btngallery = (ImageButton) findViewById(R.id.btngallery);
        result = (ImageView) findViewById(R.id.photo);
        addEvents();
        */

        /*spinner1 = (Spinner) findViewById(R.id.spinner1);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.category1, android.R.layout.simple_spinner_item);
        initializeSpinner(spinner1,adapter1);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.category2, android.R.layout.simple_spinner_item);
        initializeSpinner(spinner2,adapter2);*/
    }

    void initializeSpinner (Spinner spin, ArrayAdapter<CharSequence> adapter) {
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    /*private void addEvents(){
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPictureFromCamera();
            }
        });

        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPictureFromGallery();
            }
        });
    }


    private void getPictureFromCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, PICTURE_TAKEN_FROM_CAMERA);
        }
    }

    private void getPictureFromGallery(){

    //This allows to select the application to use when selecting an image.
    //Intent i = new Intent(Intent.ACTION_GET_CONTENT);
    //i.setType("image/*");
    //startActivityForResult(Intent.createChooser(i, "Escoge una foto"), PICTURE_TAKEN_FROM_GALLERY);

        //This takes images directly from gallery
        Intent gallerypickerIntent = new Intent(Intent.ACTION_PICK);
        gallerypickerIntent.setType("image/*");
        startActivityForResult(gallerypickerIntent, PICTURE_TAKEN_FROM_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICTURE_TAKEN_FROM_CAMERA && resultCode == RESULT_OK ) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            result.setImageBitmap(imageBitmap);
        }
        else if (requestCode == PICTURE_TAKEN_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // Get the path from the Uri
                String path = getPathFromURI(selectedImageUri);
                Log.i(TAG, "Image Path : " + path);
                // Set the image in ImageView
                result.setImageURI(selectedImageUri);
            }
        }
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
*/
    public void onAddProductPressed() {
        String title = e_title.getText().toString();
        String description = e_description.getText().toString();
        String priceMin = e_priceMin.getText().toString();
        String priceMax = e_priceMax.getText().toString();
        //String categoryProduct = spinner1.getSelectedItem().toString();
        //String categoryTrueke = spinner2.getSelectedItem().toString();
        String category = e_category.getText().toString();
        try {
            ProductChecker.checkTitle(title);
            ProductChecker.checkDescription(description);
            ProductChecker.checkPrice(priceMin, priceMax);
            ProductChecker.checkCategory(category);
            //presenter.addProduct(title,description,priceMin,priceMax,category, wants_categories);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void goToShowProductList(){
        startActivity(new Intent(this, UserProductsListActivity.class));
        finish();
    }
}
