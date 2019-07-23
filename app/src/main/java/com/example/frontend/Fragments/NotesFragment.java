package com.example.frontend.Fragments;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.frontend.Fragments.Notes.PaintView;
import com.example.frontend.Globals;
import com.example.frontend.Models.Note;
import com.example.frontend.Models.PatientDiagnosis;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;

import java.io.ByteArrayOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.frontend.Fragments.Notes.PaintView.DEFAULT_COLOR;

public class NotesFragment extends Fragment {

    private PaintView paintView;
    private View cView;
    private int patientId;
    private LinearLayout linearLayout;
    private ImageView chosenImageView;
    private int paintWidth = Globals.getInstance().getFragmentWidth()/4*3;
    private int paintHeight = Globals.getInstance().getFragmentHeight();
    Bitmap bmp = Bitmap.createBitmap(paintWidth, paintHeight, Bitmap.Config.ARGB_8888);
    Bitmap alteredBitmap = Bitmap.createBitmap(paintWidth, paintHeight, Bitmap.Config.ARGB_8888);
    Canvas canvas;
    Paint paint;
    Matrix matrix;
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;
    boolean init = true;
    boolean eraserMode = false;
    private MenuItem eraserItem;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    /*Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();*/

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        patientId = getArguments().getInt("patientId");
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.post(new Runnable() {
                    public void run() {
                        cView = view;
                        linearLayout = (LinearLayout) cView.findViewById(R.id.linearLayout);
                        chosenImageView = (ImageView) view.findViewById(R.id.ChoosenImageView);
                        setUpCanvas();

                    }
                });
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setUpCanvas(){
        if(init){
            canvas = new Canvas(alteredBitmap);
            canvas.drawColor(0xffffffff);
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(3);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setXfermode(null);
            paint.setAlpha(0xff);
            init = false;
        }
        chosenImageView.setImageBitmap(alteredBitmap);
        chosenImageView.setOnTouchListener(new View.OnTouchListener() {
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
                        chosenImageView.invalidate();
                        downx = upx;
                        downy = upy;
                        break;
                    case MotionEvent.ACTION_UP:
                        upx = event.getX();
                        upy = event.getY();
                        canvas.drawLine(downx, downy, upx, upy, paint);
                        chosenImageView.invalidate();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pen_menu, menu);
        eraserItem = menu.findItem(R.id.rubber);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rubber:
                if(!eraserMode){
                    item.setIcon(R.drawable.ic_pen_white);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    paint.setStrokeWidth(10);
                    eraserMode = true;
                }else {
                    item.setIcon(R.drawable.ic_rubber);
                    paint.setXfermode(null);
                    paint.setStrokeWidth(3);
                    eraserMode = false;
                }
                return true;
            case R.id.template_save:
                return true;
            case R.id.template_open:
                return true;
            case R.id.clear:
                init = true;
                setUpCanvas();
                return true;
            case R.id.save:
                byte[] savedByte = bitmapToByte(alteredBitmap);
                Note newNote = new Note();
                newNote.setSelected(false);
                newNote.setPatientId(patientId);
                newNote.setNoteBytes(savedByte);
                addNewNote(newNote);
                addByteArrayToView(savedByte);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public byte[] bitmapToByte(Bitmap drawing) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        drawing.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] drawingByteArray = bos.toByteArray();
        return drawingByteArray;
    }

    public void addByteArrayToView(byte[] drawing) {
        final Bitmap bmp = BitmapFactory.decodeByteArray(drawing, 0, drawing.length);
        final ImageView image = new ImageView(getContext());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        param.setMargins(0, 0, 0, 20);
        image.setLayoutParams(param);
        image.setAdjustViewBounds(true);
        image.setPadding(5, 5, 5, 5);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image.isSelected()) {
                    image.setSelected(false);
                    image.setBackgroundColor(0);
                } else {
                    image.setSelected(true);
                    image.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
                }
            }
        });

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onLongClick(View view) {
                if(eraserMode){
                    eraserItem.setIcon(R.drawable.ic_rubber);
                    paint.setXfermode(null);
                    paint.setStrokeWidth(3);
                    eraserMode = false;
                }
                alteredBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp
                        .getHeight(), bmp.getConfig());
                canvas = new Canvas(alteredBitmap);
                Matrix matrix = new Matrix();
                canvas.drawBitmap(bmp, matrix, paint);

                chosenImageView.setImageBitmap(alteredBitmap);
                chosenImageView.setOnTouchListener(new View.OnTouchListener() {
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
                                    chosenImageView.invalidate();
                                    downx = upx;
                                    downy = upy;
                                break;
                            case MotionEvent.ACTION_UP:
                                upx = event.getX();
                                upy = event.getY();
                                canvas.drawLine(downx, downy, upx, upy, paint);
                                chosenImageView.invalidate();
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                return true;
            }

        });
        image.setImageBitmap(bmp);
        linearLayout.addView(image);
    }

    public void addNewNote(final Note note) {
        Call<ResponseBody> call = jsonPlaceHolderApi.createNote(note);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getActivity(), "createNote successful "+ note.getPatientId() + note.isSelected(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "createNote NOT successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
