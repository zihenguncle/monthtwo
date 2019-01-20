package com.example.month.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.month.R;
import com.example.month.bean.DetailBean;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.url.Apis;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

public class DetailActivity extends AppCompatActivity implements IView {

    @BindView(R.id.video)
    IjkVideoView ijkVideoView;

    @BindView(R.id.xbanner)
    XBanner xBanner;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.addcar)
    Button addcar;
    IPersenterImpl iPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String url = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";

        AndroidMediaController androidMediaController = new AndroidMediaController(DetailActivity.this, false);
        ijkVideoView.setMediaController(androidMediaController);
        ijkVideoView.setVideoURI(Uri.parse(url));
        ijkVideoView.start();

        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");
        iPersenter = new IPersenterImpl(this);

        iPersenter.startRequestGet(String.format(Apis.DETAIL_URL,pid),DetailBean.class);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ijkVideoView.stopPlayback();
    }

    @Override
    public void success(Object data) {
        if(data instanceof DetailBean){
            String images = ((DetailBean) data).getData().getImages();
            String[] split = images.replace("https", "http").split("\\|");
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            xBanner.setData(list,null);
            xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(DetailActivity.this).load(list.get(position)).into((ImageView) view);
                    xBanner.setPageChangeDuration(1000);
                }
            });
            title.setText(((DetailBean) data).getData().getTitle());
            price.setText(((DetailBean) data).getData().getPrice()+"");
        }
    }

    @Override
    public void failed(String error) {

    }
}
