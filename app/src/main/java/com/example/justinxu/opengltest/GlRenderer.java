package com.example.justinxu.opengltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

import java.util.Locale;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * @author impaler
 *
 */
public class GlRenderer implements Renderer {

	private Square      square;		// the square
	private Context 	context;
    private Bitmap      bitmap;
    private int         screenWidth;
    private int         screenHeight;


	/** Constructor to set the handed over context */
	public GlRenderer(Context context) {
		this.context = context;
        if (null != context) {
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        }
		// initialise the square
		this.square = new Square();
	}

    public GlRenderer(Context context, int screenWidth, int screenHeight){
        this(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Log.v("justin",String.format(Locale.getDefault(),"screen width:%d, screen height : %d", this.screenWidth, this.screenHeight));
    }

	@Override
	public void onDrawFrame(GL10 gl) {
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		// Drawing

												// is the same as moving the camera 5 units away
        float widthRation = (float)this.screenWidth / this.bitmap.getWidth();
        float heightRatio = (float)this.screenHeight / this.bitmap.getHeight();
//        Log.v("justin",)
//        gl.glScalef((float)this.screenWidth / this.bitmap.getWidth(), (float)this.screenHeight / this.bitmap.getHeight(), 1.0f);
        gl.glTranslatef(0.0f, 0.0f, -5.0f);		// move 5 units INTO the screen

//		gl.glScalef(0.5f, 0.5f, 0.5f);			// scale the square to 50% 
												// otherwise it will be too large
		/*
        if(!this.bitmapDrawed) {
            WeakReference<Bitmap> weakBm = this.getBitmap();
            square.loadGLTexture(gl, this.context, weakBm.get());
        }else{
			square.loadGLTexture(gl, this.context);
		}
		*/
		square.loadGLTexture(gl, this.context, this.bitmap);
		square.draw(gl);						// Draw the triangle

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
//        if (null != this.square)
//            this.square.updateSize(width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square
        Bitmap bm = this.getDrawingBitmap();

		square.loadGLTexture(gl, this.context, bm);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 

	}

    public Bitmap getDrawingBitmap(){
        return this.bitmap;
    }


}
