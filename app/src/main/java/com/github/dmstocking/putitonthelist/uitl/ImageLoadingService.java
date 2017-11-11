package com.github.dmstocking.putitonthelist.uitl;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;

@Singleton
public class ImageLoadingService {

    @Inject
    public ImageLoadingService() {
    }

    public class Builder {

        @NonNull private final URI image;

        public Builder(URI image) {
            this.image = image;
        }

        public void into(ImageView imageView) {
            Glide.with(imageView)
                    .load(toUri(image))
                    .into(imageView);
        }
    }

    public Builder load(URI image) {
        return new Builder(image);
    }


    private Uri toUri(URI image) {
        return Uri.parse(image.toString());
    }
}
