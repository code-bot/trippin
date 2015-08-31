package soundcloud.example.com.soundcloudapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import api.Track;

public class MusicActivity extends Activity {
    private String city, genre;
    public final static String CITY = "com.mycompany.myfirstapp.MESSAGE";
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Intent intent = getIntent();
        city = intent.getStringExtra(MainActivity.CITY);
        genre = intent.getStringExtra(MainActivity.GENRE);
        TextView cityText = (TextView) findViewById(R.id.textView5);
        cityText.setText(city);
        mediaPlayer = new MediaPlayer();
        System.out.println(city);

        try {
            Tracks tracks = new SoundCloudSetup(city, genre).execute().get();
            System.out.println("madeit");


            ArrayList<Track> trackList = tracks.getTrack();
            Track trackFound = null;
            boolean found = false;
            for(int i = 0; !found && i < trackList.size(); i++) {
                Track curr = trackList.get(i);
                System.out.println(curr.getGenre() == null ? "" : curr.getGenre());
                if (curr.getGenre() != null && curr.getGenre().equalsIgnoreCase(genre) && curr.isStreamable() && curr.isDownloadable()) {
                    found = true;
                    trackFound = curr;
                }
            }

            if (trackFound == null) {
                trackFound = trackList.remove(0);
            }

            System.out.println("finished");
            System.out.println(trackFound);

            TextView track = (TextView) findViewById(R.id.textView3);
            track.setText(trackFound.getTitle());
            TextView artist = (TextView) findViewById(R.id.textView4);
            artist.setText(trackFound.getUser().getUsername());

            System.out.println("here");

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            if (trackFound.getArtworkUrl() != null){
                Bitmap bmp = new AlbumArtImage(trackFound.getArtworkUrl()).execute().get();
                imageView.setImageBitmap(bmp);
            }

            new PlayMusic(trackFound, mediaPlayer).execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //new PlayMusic(city, mediaPlayer).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToGenre(View view) {
        mediaPlayer.stop();
        Intent intent = new Intent(this, GenreActivity.class);
        intent.putExtra(CITY, city);
        startActivity(intent);
        finish();
    }
}
