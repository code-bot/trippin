package soundcloud.example.com.soundcloudapp;

import java.util.ArrayList;

import api.Track;

/**
 * Created by tika on 8/29/15.
 */
public class Tracks {

    private ArrayList<Track> tracks;

    public Tracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<Track> getTrack() {
        return tracks;
    }

}
