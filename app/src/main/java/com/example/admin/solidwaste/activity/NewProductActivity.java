package com.example.admin.solidwaste.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.IShowDialogContract;
import com.example.admin.solidwaste.Interface.NewProductContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.component.DaggerIProductRegComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.ProductRegPresenterModule;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProductResponseResponse;
import com.example.admin.solidwaste.presenter.AdminPresenter.ProductRegPresenter.NewProduct;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Retrofit;

public class NewProductActivity extends AppCompatActivity implements NewProductContract.view, IShowDialogContract {
    public static final String TAG = NewProductActivity.class.getName();

    @BindView(R.id.productname)
    MaterialEditText productname;

    @BindView(R.id.type)
    MaterialEditText type;

    @BindView(R.id.color)
    MaterialEditText color;

    @BindView(R.id.grade)
    MaterialEditText grade;

    @BindView(R.id.app_order_val)
    EditText order_val;

    @BindView(R.id.unit)
    EditText unit;

    @BindView(R.id.lin_sugession)
    LinearLayout lin_suggession;

    @BindView(R.id.sugesstionval)
    TextView tv_suggesionval;

    @BindView(R.id.iv_image)
    ImageView iv_image;

    @BindView(R.id.description)
    EditText description;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.submit)
    Button btnsubmit;


    ProgressDialog progressDialog;

    RecyclerView.Adapter adapter;

    SpinnerDialog sd_productname, sd_type, sd_grade, sd_unit;


    //    image
    private final int PICK_IMAGE_CAMERA = 1888, PICK_IMAGE_GALLERY = 71;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;

    String i_productName, i_userId, i_productUnit, i_product_description, i_type, i_color, i_grade;
    int i_productCost, i_productId;
    String s_userid;


    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Inject
    NewProduct newProductPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_reg);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DaggerIProductRegComponent.builder()
                .productRegPresenterModule(new ProductRegPresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);


        s_userid = sharedPreferences.getString(CommonHelper.sharedpref_userid, "");


        if (getIntent().getStringExtra("productname") != null) {

            i_productName = getIntent().getStringExtra("productname");
            i_productCost = getIntent().getIntExtra("cost", 0);
            i_userId = getIntent().getStringExtra("userid");
            i_productUnit = getIntent().getStringExtra("productunit");
            i_productId = getIntent().getIntExtra("pro_id", 0);
            i_product_description = getIntent().getStringExtra("product_description");
            i_type = getIntent().getStringExtra("type");
            i_color = getIntent().getStringExtra("color");
            i_grade = getIntent().getStringExtra("grade");

            btnsubmit.setText("Update");

//            Log.e("producccc",""+i_productCost);
            productname.setText(i_productName);
            order_val.setText(String.valueOf(i_productCost));
            unit.setText(i_productUnit);
            description.setText(i_product_description);
            type.setText(i_type);
            color.setText(i_color);
            grade.setText(i_grade);


        } else {
            btnsubmit.setText("Submit");

        }


//        presenter = new NewProduct(NewProductActivity.this);

        progressDialog = new ProgressDialog(this);


        sd_productname = new SpinnerDialog(NewProductActivity.this, newProductPresenter.ServiceNameList(), "Search Product Name", "Close");
        sd_type = new SpinnerDialog(NewProductActivity.this, newProductPresenter.TypeList(), "Search Type", "Close");
        sd_grade = new SpinnerDialog(NewProductActivity.this, newProductPresenter.GradeList(), "Search Grade", "Close");
        sd_unit = new SpinnerDialog(NewProductActivity.this, newProductPresenter.UnitList(), "Search Unit", "Close");

        unit.setText(newProductPresenter.UnitList().get(0));


        sd_productname.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                if (i == 6) {
                    showdialog(NewProductActivity.this, "Product Name", productname);
                } else {
                    productname.setText(s.toString());

                }
            }
        });


        sd_type.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {


                if (i == 6) showdialog(NewProductActivity.this, "Type ", type);
                else type.setText(s.toString());


            }
        });


        sd_grade.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                if (i == 2) {
                    showdialog(NewProductActivity.this, "Grade ", grade);
                } else {
                    grade.setText(s.toString());

                }
            }
        });

        sd_unit.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                if (i == 5) {
                    showdialog(NewProductActivity.this, "Unit", unit);
                } else {
                    unit.setText(s.toString());

                }
            }
        });

        order_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newProductPresenter.priceSuggession(s.toString(), type.getText().toString());
            }
        });

    }

    @OnClick(R.id.submit)
    public void submit() {

        if (btnsubmit.getText().toString().equalsIgnoreCase("Update")) {


            newProductPresenter.updateProduct(destination, productname.getText().toString(), order_val.getText().toString(), i_userId, unit.getText().toString(), String.valueOf(i_productId), description.getText().toString(), type.getText().toString(), color.getText().toString(), grade.getText().toString());


        } else if (btnsubmit.getText().toString().equalsIgnoreCase("Submit")) {
            newProductPresenter.onSubmit(destination, s_userid, productname.getText().toString(), type.getText().toString(), color.getText().toString(), grade.getText().toString(), order_val.getText().toString(),
                    unit.getText().toString(), tv_suggesionval.getText().toString(), imgPath, description.getText().toString());

        }


    }

    @OnClick(R.id.productname)
    public void productName() {
        sd_productname.showSpinerDialog();
    }


    @OnClick(R.id.grade)
    public void grade() {
        sd_grade.showSpinerDialog();
    }

    @OnClick(R.id.type)
    public void type() {
        sd_type.showSpinerDialog();
    }


    @OnClick(R.id.unit)
    public void unit() {
        sd_unit.showSpinerDialog();
    }


    public void showdialog(final Activity activity, String title, final TextView tvName) {

        final Dialog dialog = new Dialog(activity);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_other);

        final MaterialEditText inputVal = (MaterialEditText) dialog.findViewById(R.id.inputval);
        final Button btnSubmit = (Button) dialog.findViewById(R.id.submit);
        final Button btnCancel = (Button) dialog.findViewById(R.id.cancel);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val = inputVal.getText().toString();
                if (val.isEmpty()) {
                    Toasty.warning(activity, "Please Enter value", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    tvName.setText(inputVal.getText().toString());
                }

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    @OnClick(R.id.iv_image)
    public void chooseImage() {
        selectImage();
    }

    @Override
    public void success(String message) {

        finish();
        startActivity(new Intent(getApplicationContext(), ViewProductActivity.class));


        Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure(String message) {
        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void ShowProgress() {
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(R.style.MyAlertDialogTheme);
        progressDialog.show();
    }

    @Override
    public void HideProgress() {
        if (progressDialog != null)
            progressDialog.setMessage("Loading");
        progressDialog.dismiss();
    }

    @Override
    public void pricesuggession(String merchantprice, String merchantname) {

        Log.e("prrrrr", "" + merchantprice);
        Log.e("prrrrr", "" + merchantname);

        if (!merchantprice.equalsIgnoreCase("") && !merchantname.equalsIgnoreCase("")) {
            tv_suggesionval.setVisibility(View.VISIBLE);
            tv_suggesionval.setText("Your price as suggested lower :" + merchantname + " offers \u20B9 " + merchantprice + " Change your price level if you want");
        } else {
            tv_suggesionval.setVisibility(View.GONE);
            tv_suggesionval.setText("");

        }
    }


    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(new ContextThemeWrapper(NewProductActivity.this, R.style.AppTheme));
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            inputStreamImg = null;
            if (requestCode == PICK_IMAGE_CAMERA) {
                try {
                    Uri selectedImage = data.getData();
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


//
//                    File file = new File(Environment.getExternalStorageDirectory()+File.separator+"test");
//                    file.createNewFile();


                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    destination = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "IMG_" + timeStamp + ".jpg");
                    FileOutputStream fo;


                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    imgPath = destination.getAbsolutePath();

//                    tempFile = new File(imgPath);


                    iv_image.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == PICK_IMAGE_GALLERY) {
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    Log.e("Activity", "Pick from Gallery::>>> ");

                    imgPath = getRealPathFromURI(selectedImage);
                    destination = new File(imgPath.toString());
                    iv_image.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public String path(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    public void showDialogd(int position, List<UserProductResponseResponse> userProductResponseResponses) {

    }

    @Override
    public void showDeleteDialog(int position, String title) {

    }

    @Override
    public void calIntent(String mobileno) {

    }
}
