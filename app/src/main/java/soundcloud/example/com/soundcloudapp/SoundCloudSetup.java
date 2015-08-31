package soundcloud.example.com.soundcloudapp;

import android.os.AsyncTask;

import java.util.ArrayList;

import api.SoundCloud;
import api.Track;
import api.User;

public class SoundCloudSetup extends AsyncTask<String, Void, Tracks> {

    public Exception exception;
    private String city;
    private String genre;

    public SoundCloudSetup(String city, String genre) {
        this.city = city;
        this.genre = genre;
    }

    protected void onPreExecute() {
        // Perform setup - runs on user interface thread
    }

    protected Tracks doInBackground(String... strings) {
        try {
            SoundCloud soundcloud = new SoundCloud(Credentials.client_id,
                    Credentials.client_secret,
                    Credentials.email,
                    Credentials.password);

            ArrayList<User> userResults = soundcloud.findUser(city);
            ArrayList<Track> tracks = new ArrayList<Track>();

            for(User user : userResults) {
                ArrayList<Track> trackResults = soundcloud.findTrack(user.getPermalink());
                for(Track track : trackResults) {
                    tracks.add(track);
                }
            }

            System.out.println(tracks.size());
            System.out.println(tracks);

            return new Tracks(tracks);
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

