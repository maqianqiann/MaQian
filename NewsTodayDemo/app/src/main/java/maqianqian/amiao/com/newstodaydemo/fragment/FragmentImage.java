package maqianqian.amiao.com.newstodaydemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import maqianqian.amiao.com.newstodaydemo.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by lenovo on 2017/3/21.
 */

public class FragmentImage extends Fragment {

    private View view;
    private PhotoView photoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragm_layout,null);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        photoView = (PhotoView) view.findViewById(R.id.fragIm_im);
        Bundle bundle=getArguments();
        String url = bundle.getString("imageUrl");
        Glide.with(FragmentImage.this).load(url).into(photoView);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                getActivity().finish();
            }
        });
    }
}
