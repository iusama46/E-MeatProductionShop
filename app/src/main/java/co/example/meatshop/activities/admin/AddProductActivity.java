package co.example.meatshop.activities.admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import co.example.meatshop.R;
import co.example.meatshop.utils.CShowProgress;

public class AddProductActivity extends AppCompatActivity {
    static final int OPEN_MEDIA_PICKER = 1;
    EditText expiryDate;
    int selectedCategory = 0;
    ArrayList<String> categoriesList = new ArrayList<>();
    int stateIndex = 0;
    Uri imageUri;
    File file;
    String fileExtension = ".jpg";
    String imgUrl = "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setSpinners();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CShowProgress cShowProgress = CShowProgress.getInstance();

        EditText name = findViewById(R.id.name);
        EditText quantity = findViewById(R.id.quantity);
        expiryDate = findViewById(R.id.date);
        EditText description = findViewById(R.id.description);
        EditText stock = findViewById(R.id.stock);
        EditText price = findViewById(R.id.price);
        Button btn = findViewById(R.id.add);
        Button chooseMedia = findViewById(R.id.choose);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            expiryDate.setText(ProductActivity.getProductList().get(id).getExpiryDate());
            name.setText(ProductActivity.getProductList().get(id).getpName());
            quantity.setText(ProductActivity.getProductList().get(id).getQty());
            stock.setText(ProductActivity.getProductList().get(id).getStock());
            description.setText(ProductActivity.getProductList().get(id).getpDescription());
            imgUrl = ProductActivity.getProductList().get(id).getpImg();
        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("products").child(datetime + fileExtension);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cShowProgress.showProgress(AddProductActivity.this);
                if (id != -1) {
                    storageReference.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw Objects.requireNonNull(task.getException());
                            }
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downUri = task.getResult();
                                final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                                dataMap.put("name", name.getText().toString());
                                dataMap.put("quantity", quantity.getText().toString());

                                if (expiryDate.getText().toString() != null && !expiryDate.getText().toString().isEmpty())
                                    dataMap.put("expiryDate", expiryDate.getText().toString());
                                else
                                    dataMap.put("expiryDate", "none");
                                dataMap.put("stock", stock.getText().toString());
                                dataMap.put("description", description.getText().toString());
                                dataMap.put("category", selectedCategory);
                                dataMap.put("type", stateIndex);
                                dataMap.put("price", price.getText().toString());

                                if (downUri.toString() != null)
                                    dataMap.put("img", downUri.toString());
                                else
                                    dataMap.put("img", imgUrl);

                                try {

                                    reference.child(ProductActivity.getProductList().get(id).getId()).setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AddProductActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                            cShowProgress.hideProgress();
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            cShowProgress.hideProgress();
                                            Toast.makeText(AddProductActivity.this, "Failed to add data, error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (Exception e) {
                                    cShowProgress.hideProgress();
                                    Toast.makeText(AddProductActivity.this, "Failed to add data, error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    return;
                }


                storageReference.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        }
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downUri = task.getResult();
                            final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                            dataMap.put("name", name.getText().toString());
                            dataMap.put("quantity", quantity.getText().toString());

                            if (expiryDate.getText().toString() != null && !expiryDate.getText().toString().isEmpty())
                                dataMap.put("expiryDate", expiryDate.getText().toString());
                            else
                                dataMap.put("expiryDate", "none");
                            dataMap.put("stock", stock.getText().toString());
                            dataMap.put("description", description.getText().toString());
                            dataMap.put("category", selectedCategory);
                            dataMap.put("type", stateIndex);
                            dataMap.put("price", price.getText().toString());

                            if (downUri.toString() != null)
                                dataMap.put("img", downUri.toString());
                            else
                                dataMap.put("img", imgUrl);

                            try {

                                reference.push().setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                                        cShowProgress.hideProgress();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        cShowProgress.hideProgress();
                                        Toast.makeText(AddProductActivity.this, "Failed to add data, error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                cShowProgress.hideProgress();
                                Toast.makeText(AddProductActivity.this, "Failed to add data, error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }

        });
        chooseMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AddProductActivity.this)
                        .galleryOnly()
                        .compress(512) //// Final image size will be less than 1 MB(Optional)
                        .maxResultSize(512, 512)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (data != null) {
                imageUri = data.getData();
                file = ImagePicker.Companion.getFile(data);

                assert file != null;
                fileExtension = file.getName().substring(file.getName().lastIndexOf("."));

                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();


            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinners() {

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);


        categoriesList.add("Mutton");
        categoriesList.add("Beef");
        categoriesList.add("Chicken");
        categoriesList.add("Chicken Dasi");
        categoriesList.add("Fish");

        spinner.setItems(categoriesList);
        spinner.setHint("Category");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selectedCategory = position;
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        MaterialSpinner spinnerState = (MaterialSpinner) findViewById(R.id.state);


        spinnerState.setItems("Fresh", "Frozen");
        spinnerState.setHint("Fresh");
        spinnerState.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                stateIndex = position;

                if (position == 1) {
                    expiryDate.setVisibility(View.VISIBLE);
                } else {
                    expiryDate.setVisibility(View.GONE);
                }
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}