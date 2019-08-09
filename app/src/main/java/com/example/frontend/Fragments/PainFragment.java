package com.example.frontend.Fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.Models.PainBeginning;
import com.example.frontend.Models.PainBeginning;
import com.example.frontend.Models.PainCurrent;
import com.example.frontend.Models.PsychoSocialAfter;
import com.example.frontend.Models.PainBeginning;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;

import java.io.ByteArrayOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PainFragment extends Fragment {

    private int patientId;
    private ImageView ivLocationTeeth;
    private ImageView ivLocationFaceLeft;
    private ImageView ivLocationFaceRight;
    private int openedLocationImage;
    RadioGroup rgBeginningCurrent;
    RadioButton rbBeginning;
    RadioButton rbCurrent;

    boolean initialSetUpBeginningDone = false;
    boolean initialSetUpCurrentDone = false;

    private PainBeginning painBeginningOfPatient = new PainBeginning();
    private PainCurrent painCurrentOfPatient = new PainCurrent();

    SeekBar seekBar;


    enum PointInTime {
        BEGINNING,
        CURRENT
    }

    PointInTime selectedPointInTime = PointInTime.BEGINNING;
    Bitmap alteredBitmap = Bitmap.createBitmap(600, 450, Bitmap.Config.ARGB_8888);
    Bitmap bmp = Bitmap.createBitmap(600, 450, Bitmap.Config.ARGB_8888);
    Canvas canvas;
    Paint paint;
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;
    boolean init = true;
    Dialog myDialog;
    private View.OnClickListener onClickLocationImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivLocationTeeth:
                    bmp = ((BitmapDrawable) ivLocationTeeth.getDrawable()).getBitmap();
                    openedLocationImage = R.id.ivLocationTeeth;
                    break;
                case R.id.ivLocationFaceLeft:
                    bmp = ((BitmapDrawable) ivLocationFaceLeft.getDrawable()).getBitmap();
                    openedLocationImage = R.id.ivLocationFaceLeft;
                    break;
                case R.id.ivLocationFaceRight:
                    bmp = ((BitmapDrawable) ivLocationFaceRight.getDrawable()).getBitmap();
                    openedLocationImage = R.id.ivLocationFaceRight;
                    break;
            }
            bmp = resize(bmp, 600, 450);
            showImagePopup();
        }
    };

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    @Override
    public void onDestroyView() {
        setPainBeginning();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");
        initializePainBeginningOfPatient();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        ivLocationTeeth = view.findViewById(R.id.ivLocationTeeth);
        ivLocationTeeth.setOnClickListener(onClickLocationImage);
        ivLocationFaceLeft = view.findViewById(R.id.ivLocationFaceLeft);
        ivLocationFaceLeft.setOnClickListener(onClickLocationImage);
        ivLocationFaceRight = view.findViewById(R.id.ivLocationFaceRight);
        ivLocationFaceRight.setOnClickListener(onClickLocationImage);
        myDialog = new Dialog(getActivity());
        myDialog.setCanceledOnTouchOutside(false);

        rbBeginning = view.findViewById(R.id.rbBeginning);
        rbCurrent = view.findViewById(R.id.rbCurrent);

        rgBeginningCurrent = view.findViewById(R.id.rgBeginningCurrent);
        rgBeginningCurrent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rbBeginning:
                        selectedPointInTime = PointInTime.BEGINNING;
                        break;
                    case R.id.rbCurrent:
                        selectedPointInTime = PointInTime.CURRENT;
                        break;
                }
            }
        });
        rbBeginning.setChecked(true);

        seekBar = (SeekBar) view.findViewById(R.id.sbIntensity);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView t = (TextView) view.findViewById(R.id.tvIntensity);
                t.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initializePainBeginningOfPatient() {
        painBeginningOfPatient.setPatient_id(patientId);
        painBeginningOfPatient.setIntensity(0);
        Bitmap bmpTeeth = BitmapFactory.decodeResource(getResources(), R.drawable.teeth);
        Bitmap bmpFaceLeft = BitmapFactory.decodeResource(getResources(), R.drawable.face_left);
        Bitmap bmpFaceRight = BitmapFactory.decodeResource(getResources(), R.drawable.face_right);

        painBeginningOfPatient.setLocation_teeth(bitmapToByte(bmpTeeth));
        painBeginningOfPatient.setLocation_face_left(bitmapToByte(bmpFaceLeft));
        painBeginningOfPatient.setLocation_face_right(bitmapToByte(bmpFaceRight));
        //painBeginningOfPatient.setLocation_teeth(null);
        //painBeginningOfPatient.setLocation_face_left(null);
        //painBeginningOfPatient.setLocation_face_right(null);
        painBeginningOfPatient.setPain_pattern(null);
        painBeginningOfPatient.setDull(false);
        painBeginningOfPatient.setPulling(false);
        painBeginningOfPatient.setStinging(false);
        painBeginningOfPatient.setPulsating(false);
        painBeginningOfPatient.setBurning(false);
        painBeginningOfPatient.setPinsneedles(false);
        painBeginningOfPatient.setTingling(false);
        painBeginningOfPatient.setNumb(false);
    }

    public byte[] bitmapToByte(Bitmap drawing) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        drawing.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] drawingByteArray = bos.toByteArray();
        return drawingByteArray;
    }

    public void setUpCanvas(final ImageView imageView) {
        if (init) {
            paint = new Paint();
            paint.setColor(getResources().getColor(R.color.colorDarkBlue));
            paint.setStrokeWidth(5);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setXfermode(null);
            paint.setAlpha(0xff);
            init = false;
        }
        alteredBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp
                .getHeight(), bmp.getConfig());
        canvas = new Canvas(alteredBitmap);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bmp, matrix, paint);
        imageView.setImageBitmap(alteredBitmap);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downx = event.getX();
                        downy = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        upx = event.getX();
                        upy = event.getY();
                        canvas.drawLine(downx, downy, upx, upy, paint);
                        imageView.invalidate();
                        downx = upx;
                        downy = upy;
                        break;
                    case MotionEvent.ACTION_UP:
                        upx = event.getX();
                        upy = event.getY();
                        canvas.drawLine(downx, downy, upx, upy, paint);
                        imageView.invalidate();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    private void showImagePopup() {
        myDialog.setContentView(R.layout.popup_image_drawable);
        Button btnCancel;
        Button btnSave;
        ImageView btnDelete;
        final ImageView imageView = (ImageView) myDialog.findViewById(R.id.ivDisplay);
        btnDelete = (ImageView) myDialog.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (openedLocationImage) {
                    case R.id.ivLocationTeeth:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.teeth);
                        break;
                    case R.id.ivLocationFaceLeft:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.face_left);
                        break;
                    case R.id.ivLocationFaceRight:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.face_right);
                        break;
                }
                bmp = resize(bmp, 600, 450);
                setUpCanvas(imageView);
            }
        });
        btnCancel = (Button) myDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        btnSave = (Button) myDialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (openedLocationImage) {
                    case R.id.ivLocationTeeth:
                        ivLocationTeeth.setImageBitmap(alteredBitmap);
                        painBeginningOfPatient.setLocation_teeth(bitmapToByte(alteredBitmap));

                        break;
                    case R.id.ivLocationFaceLeft:
                        ivLocationFaceLeft.setImageBitmap(alteredBitmap);
                        painBeginningOfPatient.setLocation_face_left(bitmapToByte(alteredBitmap));
                        break;
                    case R.id.ivLocationFaceRight:
                        ivLocationFaceRight.setImageBitmap(alteredBitmap);
                        painBeginningOfPatient.setLocation_face_right(bitmapToByte(alteredBitmap));
                        break;
                }

                myDialog.dismiss();
            }
        });
        setUpCanvas(imageView);
        myDialog.show();
    }


    public void addNewPainBeginning(PainBeginning painBeginning) {
        Call<ResponseBody> call = jsonPlaceHolderApi.createPainBeginning(painBeginning);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "create PainBeginning NOT successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePainBeginning(final PainBeginning updatedPainBeginning) {
        Call<ResponseBody> call = jsonPlaceHolderApi.updatePainBeginning(patientId, updatedPainBeginning);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "update PainBeginning NOT successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setPainBeginning() {
        Call<Boolean> call = jsonPlaceHolderApi.existsPainBeginning(patientId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    return;
                } else {
                    boolean PainBeginningExists = response.body();
                    if (PainBeginningExists) {
                        updatePainBeginning(painBeginningOfPatient);
                    } else {
                        addNewPainBeginning(painBeginningOfPatient);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpAllViewsBeginning() {
        Call<PainBeginning> call = jsonPlaceHolderApi.getPainBeginning(patientId);
        call.enqueue(new Callback<PainBeginning>() {
            @Override
            public void onResponse(Call<PainBeginning> call, Response<PainBeginning> response) {
                if (!response.isSuccessful()) {
                    return;
                } else {
                    painBeginningOfPatient = response.body();
                    Bitmap bmTeeth = BitmapFactory.decodeByteArray(painBeginningOfPatient.getLocation_teeth(), 0, painBeginningOfPatient.getLocation_teeth().length);
                    Bitmap bmFaceLeft = BitmapFactory.decodeByteArray(painBeginningOfPatient.getLocation_face_left(), 0, painBeginningOfPatient.getLocation_face_left().length);
                    Bitmap bmFaceRight = BitmapFactory.decodeByteArray(painBeginningOfPatient.getLocation_face_right(), 0, painBeginningOfPatient.getLocation_face_right().length);
                    ivLocationTeeth.setImageBitmap(bmTeeth);
                    ivLocationFaceLeft.setImageBitmap(bmFaceLeft);
                    ivLocationFaceRight.setImageBitmap(bmFaceRight);
                    seekBar.setProgress(painBeginningOfPatient.getIntensity());
                    //TODO: Select Pattern, select selectedButtons
                }
            }

            @Override
            public void onFailure(Call<PainBeginning> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
