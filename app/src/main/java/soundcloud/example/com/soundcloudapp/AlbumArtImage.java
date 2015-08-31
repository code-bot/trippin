package soundcloud.example.com.soundcloudapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by tika on 8/29/15.
 */

public class AlbumArtImage extends AsyncTask<String, Void, Bitmap> {

    public Exception exception;
    private String urlBitmap;

    public AlbumArtImage(String urlBitmap) {
        this.urlBitmap = urlBitmap;
    }

    protected void onPreExecute() {
        // Perform setup - runs on user interface thread
    }

    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(urlBitmap);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            return bmp;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onProgressUpdate(String[] values) {
        // Update user with progress bar or similar - runs on user interface thread
    }

    protected void onPostExecute(int result) {
    }
}

