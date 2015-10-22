package xdf.roundimageview;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

public class MainActivity extends Activity {

    private RoundImageView sampleImage;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAvatar("https://avatars1.githubusercontent.com/u/50278?v=3&s=400");
    }

    private void setAvatar(String avatarUrl) {
        mImageLoader = initImageLoader(this, mImageLoader, "sample");
        sampleImage = (RoundImageView) findViewById(R.id.online_image);
        mImageLoader.displayImage(avatarUrl, sampleImage);
    }

    public ImageLoader initImageLoader(Context context,
                                       ImageLoader imageLoader, String dirName) {
        imageLoader = ImageLoader.getInstance();
        if (imageLoader.isInited()) {
            imageLoader.destroy();
        }
        imageLoader.init(initImageLoaderConfig(context, dirName));
        return imageLoader;
    }

    private ImageLoaderConfiguration initImageLoaderConfig(
            Context context, String dirName) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCache(new UnlimitedDiscCache(new File(dirName)))
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        return config;
    }

    private int getMemoryCacheSize(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
            // limit
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        return memoryCacheSize;
    }
}
