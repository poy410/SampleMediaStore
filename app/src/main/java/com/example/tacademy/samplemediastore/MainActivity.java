package com.example.tacademy.samplemediastore;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
    String selection = MediaStore.Images.Media.MIME_TYPE + " = ?";
    String[] args = {"image/jpeg"};
    String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

    GridView gridView;
    SimpleCursorAdapter mAdapter;
    int columnIndexData=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView)findViewById(R.id.gridView);

        String[] from={MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
        int[] to={R.id.image_icon, R.id.text_name};
        mAdapter=new SimpleCursorAdapter(this, R.layout.view_checkable,null,from,to,0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == columnIndexData){
                    ImageView iv = (ImageView)view;
                    String path = cursor.getString(columnIndex);
                    Glide.with(MainActivity.this).load(new File(path)).into(iv);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(mAdapter);

        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        columnIndexData=data.getColumnIndex(MediaStore.Images.Media.DATA);
        mAdapter.swapCursor(data);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle b) {

        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, args, sortOrder);
    }
}
