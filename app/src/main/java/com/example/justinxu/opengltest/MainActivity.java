package com.example.justinxu.opengltest;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    GLSurfaceView mView;
    GlRenderer renderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        this.mView = new GLSurfaceView(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int widthPixels = (int) (dm.widthPixels * density);
        int heightPixels = (int)(dm.heightPixels * density);
        this.renderer = new GlRenderer(this, widthPixels, heightPixels);
        this.mView.setRenderer(this.renderer);
        this.mView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        setContentView(this.mView);


    }

}
