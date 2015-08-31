package soundcloud.example.com.soundcloudapp;

import android.media.MediaPlayer;
import android.os.AsyncTask;

import api.Track;


public class PlayMusic extends AsyncTask<String, Void, Integer> {

    public Exception exception;
    private Track track;
    private MediaPlayer mediaPlayer;

    public PlayMusic(Track track, MediaPlayer mediaPlayer) {
        this.track = track;
        this.mediaPlayer = mediaPlayer;
    }

    protected void onPreExecute() {
        // Perform setup - runs on user interface thread
    }

    protected Integer doInBackground(String... strings) {
        try {
            System.out.println(track.toString());
            String src = "http://api.soundcloud.com/tracks/" + track.getId() + "/stream?client_id="+Credentials.client_id;
            System.out.println(src);
            mediaPlayer.setDataSource(src);
            System.out.println("datasource");
            mediaPlayer.prepare();
            System.out.println("start playing");
            mediaPlayer.start();
            return 1;
        } catch (Exception e) {
            this.exception = e;
            return 0;
        }
    }

    protected void onProgressUpdate(String[] values) {
        // Update user with progress bar or similar - runs on user interface thread
    }

    protected void onPostExecute(int result) {
        // Update user interface
    }
}

