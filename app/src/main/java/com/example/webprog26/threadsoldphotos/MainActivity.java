package com.example.webprog26.threadsoldphotos;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BitmapAddictor{

    private static final String TAG = "MainActivity_TAG";
    private UiHandler mUiHandler;
    private BitmapsHandlerThread mBitmapsHandlerThread;
    private ArrayList<Bitmap> mBitmaps;
    private GridItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!SdChecker.isExternalStorageReadable()){
            Toast.makeText(this, getResources().getString(R.string.sd_card_is_missing), Toast.LENGTH_SHORT).show();
            return;
        }

        mBitmaps = new ArrayList<>();
        mAdapter = new GridItemAdapter(this, mBitmaps);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);

        new AsyncFilesLoader().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        mUiHandler = new UiHandler(this);
        mBitmapsHandlerThread = new BitmapsHandlerThread(mUiHandler);
        mBitmapsHandlerThread.start();
        mBitmapsHandlerThread.prepareHandler();
    }

    private class AsyncFilesLoader extends AsyncTask<Void, Void, ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            return getFilesPathsList(PhotosFinder.getPhotosAndVideosFilesFromSDCard());
        }

        @Override
        protected void onPostExecute(ArrayList<String> files) {
            super.onPostExecute(files);
            Log.i(TAG, "filesList.size(): " + files.size());
            mBitmapsHandlerThread.decodeBitmaps(files);
        }

        /**
         * Gets paths to files from given {@link ArrayList<File>}
         * @param filesList
         * @return ArrayList<String>
         */
        private ArrayList<String> getFilesPathsList(ArrayList<File> filesList){
            ArrayList<String> filesPathsList = new ArrayList<>();

            for(File file: filesList){
                filesPathsList.add(file.getAbsolutePath());
            }

            return filesPathsList;
        }
    }

    @Override
    public void addBitmapToList(Bitmap bitmap) {
        mBitmaps.add(bitmap);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmapsHandlerThread.quit();
        mUiHandler.clearMessagesQueue();
    }
}
