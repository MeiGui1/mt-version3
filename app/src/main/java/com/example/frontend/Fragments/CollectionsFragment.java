package com.example.frontend.Fragments;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.frontend.Activities.MainActivity;
import com.example.frontend.Models.Note;
import com.example.frontend.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class CollectionsFragment extends Fragment {

    private int patientId;
    private int selectedRadioButton;
    private RadioGroup rgMedia;
    private RadioButton rbGallery;
    private RadioButton rbVideos;
    private RadioButton rbDocuments;
    private RadioButton rbWebsites;
    private static View cview;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private int columnCounter = 1;
    private int longClickedMedia;
    Dialog myDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collections, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        cview = view;
        rgMedia = view.findViewById(R.id.rgMedia);
        rbGallery = view.findViewById(R.id.rbGallery);
        rbVideos = view.findViewById(R.id.rbVideos);
        rbDocuments = view.findViewById(R.id.rbDocuments);
        rbWebsites = view.findViewById(R.id.rbWebsites);
        myDialog = new Dialog(getActivity());
        ll1 = (LinearLayout) view.findViewById(R.id.llFirstColumn);
        ll2 = (LinearLayout) view.findViewById(R.id.llSecondColumn);
        ll3 = (LinearLayout) view.findViewById(R.id.llThirdColumn);

        rgMedia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                setDrawablesBlack();
                clearScrollView();
                switch (checkedId) {
                    case R.id.rbGallery:
                        setUpGallery();
                        break;
                    case R.id.rbVideos:
                        setUpVideos();
                        break;
                    case R.id.rbDocuments:
                        setUpDocuments();
                        break;
                    case R.id.rbWebsites:
                        setUpWebsites();
                        break;
                }
            }
        });
        rgMedia.check(R.id.rbGallery);
    }

    private void setDrawablesBlack() {
        rbGallery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_image_black, 0, 0, 0);
        rbVideos.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_black, 0, 0, 0);
        rbDocuments.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_document_black, 0, 0, 0);
        rbWebsites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_web_black, 0, 0, 0);
    }

    private void clearScrollView() {
        columnCounter = 1;
        ll1.removeAllViews();
        ll2.removeAllViews();
        ll3.removeAllViews();
    }

    public ArrayList<String> getImagesPath() {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getActivity().getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }

    public ArrayList<String> getVideosPath() {
        Uri uri;
        ArrayList<String> listOfAllVideos = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfVideo = null;
        uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME};

        cursor = getActivity().getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfVideo = cursor.getString(column_index_data);

            listOfAllVideos.add(PathOfVideo);
        }
        return listOfAllVideos;
    }

    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.media_menu, menu);
        longClickedMedia = v.getId();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ImageView selectedIv = getView().findViewById(longClickedMedia);
        switch (item.getItemId()) {
            case R.id.selectOption:
                if (selectedIv.isSelected()) {
                    selectedIv.setSelected(false);
                    selectedIv.setBackgroundColor(0);
                    Note updatedNote = new Note();
                    /*
                    updatedNote.setSelected(false);
                    updatedNote.setPatientId(patientId);
                    updateNote(lastNoteId, updatedNote);
                    */
                } else {
                    selectedIv.setSelected(true);
                    selectedIv.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                    Note updatedNote = new Note();
                    /*
                    updatedNote.setSelected(true);
                    updatedNote.setPatientId(patientId);
                    updateNote(lastNoteId, updatedNote);
                    */
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showImagePopup(Bitmap image) {
        myDialog.setContentView(R.layout.popup_image);
        TextView btnClose;
        PhotoView photoView = (PhotoView) myDialog.findViewById(R.id.ivDisplay);
        photoView.setImageBitmap(image);
        btnClose = (TextView) myDialog.findViewById(R.id.btnCloseImage);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void showVideoPopup(String path) {
        myDialog.setContentView(R.layout.popup_video);
        TextView btnClose;
        final VideoView videoView = (VideoView) myDialog.findViewById(R.id.vvDisplay);
        videoView.setVideoPath(path);


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        /*
                         * add media controller
                         */
                        MediaController mc = new MediaController(getContext());
                        videoView.setMediaController(mc);
                        /*
                         * and set its position on screen
                         */
                        mc.setAnchorView(videoView);

                        ((ViewGroup) mc.getParent()).removeView(mc);

                        ((FrameLayout) myDialog.findViewById(R.id.vvWrapper))
                                .addView(mc);
                        mc.setVisibility(View.VISIBLE);
                    }
                });
                videoView.start();

            }
        });



        btnClose = (TextView) myDialog.findViewById(R.id.btnCloseImage);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void setUpGallery() {
        rbGallery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_image_white, 0, 0, 0);
        List<String> allImagePaths = new ArrayList<>();
        allImagePaths = getImagesPath();
        for (final String path : allImagePaths) {
            ImageView newIv = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    220);
            lp.setMargins(0, 10, 0, 10);
            newIv.setLayoutParams(lp);
            final Bitmap myBitmap = BitmapFactory.decodeFile(path);
            Bitmap cutBitmap = scaleCenterCrop(myBitmap, 220, 280);
            newIv.setAdjustViewBounds(true);
            newIv.setImageBitmap(cutBitmap);
            newIv.setPadding(7, 7, 7, 7);
            newIv.setId(allImagePaths.indexOf(path));
            newIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showImagePopup(myBitmap);
                }
            });
            registerForContextMenu(newIv);
            switch (columnCounter) {
                case 1:
                    ll1.addView(newIv);
                    columnCounter++;
                    break;
                case 2:
                    ll2.addView(newIv);
                    columnCounter++;
                    break;
                case 3:
                    ll3.addView(newIv);
                    columnCounter = 1;
                    break;
            }
        }
    }

    private void setUpVideos() {
        rbVideos.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_white, 0, 0, 0);
        List<String> allVideoPaths = new ArrayList<>();
        allVideoPaths = getVideosPath();
        for (final String path : allVideoPaths) {
            ImageView newIv = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    220);
            lp.setMargins(0, 10, 0, 10);
            newIv.setLayoutParams(lp);
            final Bitmap myBitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            Bitmap cutBitmap = scaleCenterCrop(myBitmap, 220, 280);
            newIv.setAdjustViewBounds(true);
            newIv.setImageBitmap(cutBitmap);
            newIv.setPadding(7, 7, 7, 7);
            newIv.setId(allVideoPaths.indexOf(path));
            newIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showVideoPopup(path);
                }
            });
            registerForContextMenu(newIv);
            switch (columnCounter) {
                case 1:
                    ll1.addView(newIv);
                    columnCounter++;
                    break;
                case 2:
                    ll2.addView(newIv);
                    columnCounter++;
                    break;
                case 3:
                    ll3.addView(newIv);
                    columnCounter = 1;
                    break;
            }
        }

    }

    private void setUpDocuments() {
        rbDocuments.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_document_white, 0, 0, 0);

    }

    private void setUpWebsites() {
        rbDocuments.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_web_white, 0, 0, 0);

    }

}
