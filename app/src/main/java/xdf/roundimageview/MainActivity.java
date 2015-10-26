package xdf.roundimageview;

import android.app.Activity;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends Activity {

    private RoundImageView sampleImage;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleImage = (RoundImageView) findViewById(R.id.online_image);
        mImageLoader = sampleImage.initImageLoader("sample");

        mImageLoader.displayImage("https://avatars1.githubusercontent.com/u/50278?v=3&s=400", sampleImage);
    }

}
