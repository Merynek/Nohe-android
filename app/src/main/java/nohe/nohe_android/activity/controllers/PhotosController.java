package nohe.nohe_android.activity.controllers;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import nohe.nohe_android.R;
import nohe.nohe_android.activity.services.PagerService;

public class PhotosController {
    private AppCompatActivity context;
    private PagerService pagerService;
    private Button button;
    private Button dependButton;
    private Button btnRemovePhoto;
    private ArrayList<Bitmap> photoCollection;

    public PhotosController(AppCompatActivity context, PagerService pagerService, ArrayList<Bitmap> photoCollection, Button button, Button dependButton, Button btnRemovePhoto) {
        this.context = context;
        this.pagerService = pagerService;
        this.button = button;
        this.dependButton = dependButton;
        this.btnRemovePhoto = btnRemovePhoto;
        this.photoCollection = photoCollection;
        setDependButtonVisibility();
        setRemovePhotoButtonVisibility();
    }

    public void addPhoto(Bitmap photo) {
        photoCollection.add(photo);
        this.updateImageSwitcher();
        setCameraButtonVisibility();
        setDependButtonVisibility();
        setRemovePhotoButtonVisibility();
    }

    public void removePhoto() {
        ViewPager mViewPager = (ViewPager) this.context.findViewById(R.id.photo_show_pager);

        if(photoCollection.size() > 0) {
            photoCollection.remove(mViewPager.getCurrentItem());
            updateImageSwitcher();
        }
        setCameraButtonVisibility();
        setDependButtonVisibility();
        setRemovePhotoButtonVisibility();
    }

    public String[] getPhotosInBase64(ArrayList<Bitmap> photoCollection) {
        String[] photos = new String[photoCollection.size()];

        for (int i = 0; i < photoCollection.size(); i++) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photoCollection.get(i).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            photos[i] = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        }

        return photos;
    }

    private void updateImageSwitcher() {
        ViewPager mViewPager = (ViewPager) this.context.findViewById(R.id.photo_show_pager);
        mViewPager.setAdapter(pagerService);

        // todo: make it work
//        mViewPager.setBackground(this.photoCollection.size() < 1 ? R.drawable.images : null);
    }

    private void setCameraButtonVisibility() {
        if (this.photoCollection.size() > 2) {
            this.button.setEnabled(false);
        } else {
            this.button.setEnabled(true);
        }
    }

    private void setDependButtonVisibility() {
        if (this.photoCollection.size() >= 1 && this.photoCollection.size() <= 3) {
            this.dependButton.setEnabled(true);
        } else {
            this.dependButton.setEnabled(false);
        }
    }

    private void setRemovePhotoButtonVisibility() {
        if (this.photoCollection.size() >= 1) {
            this.btnRemovePhoto.setVisibility(View.VISIBLE);
        } else {
            this.btnRemovePhoto.setVisibility(View.INVISIBLE);
        }
    }
}
