package com.example.justinxu.opengltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author impaler
 *
 */
public class Square {
	
	private FloatBuffer vertexBuffer;	// buffer holding the vertices
	private float vertices[] = {
			-1.0f, -1.78f,  0.0f,		// V1 - bottom left
			-1.0f,  1.78f,  0.0f,		// V2 - top left
			 1.0f, -1.78f,  0.0f,		// V3 - bottom right
			 1.0f,  1.78f,  0.0f			// V4 - top right
	};

	private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	private float texture[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f		// bottom right	(V3)
	};
	
	/** The texture pointer */
	private int[] textures = new int[1];

	public Square() {
		this.updateVerticesBuffer();

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	/**
	 * Load the texture for the square
	 * @param gl
	 * @param context
	 */
	public void loadGLTexture(GL10 gl, Context context) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.r660);
		this.loadGLTexture(gl, bitmap);
		// Clean up
		bitmap.recycle();
	}

	public void loadGLTexture(GL10 gl, Bitmap bitmap){
        if (null == bitmap || bitmap.isRecycled()) {
            return;
        }
		// generate one texture pointer
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap
        if (null == bitmap || bitmap.isRecycled()) {
            return;
        }else{
        }
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	}

	public void loadGLTexture(GL10 gl, Context context, Bitmap bitmap){
		if (null == bitmap){
            if (null != context) {
                Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.instaflash_titlescreen);
                this.loadGLTexture(gl, bm);
                // Clean up
                bm.recycle();
            }else{
                throw new RuntimeException("Both context and bitmap is null");
            }
		}else if (!bitmap.isRecycled()){
			this.loadGLTexture(gl, bitmap);

		}
	}

    /*
    public void updateSize(int width, int height){
        if (0 == width || 0 ==height)
            return;
        float xCoordinate = 1.0f;
        float yCoordinate = 1.0f;
        if (width > height){
            yCoordinate = (float)height / width;
        }else{
            xCoordinate = (float)width / height;
        }
        this.vertices = new float[]{
                -xCoordinate, -yCoordinate, 0.0f,        // V1 - bottom left
                -xCoordinate, yCoordinate, 0.0f,        // V2 - top left
                xCoordinate, -yCoordinate, 0.0f,        // V3 - bottom right
                xCoordinate, yCoordinate, 0.0f,            // V4 - top right
        };
        this.updateVerticesBuffer();
    }
    */

    public void updateVerticesBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        // allocates the memory from the byte buffer
        vertexBuffer = byteBuffer.asFloatBuffer();

        // fill the vertexBuffer with the vertices
        vertexBuffer.put(vertices);

        // set the cursor position to the beginning of the buffer
        vertexBuffer.position(0);
    }


	
	/** The draw method for the square with the GL context */
	public void draw(GL10 gl) {
//		gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);

        // Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        /*
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
        gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, this.vertices.length / 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        */
	}
}
