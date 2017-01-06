package projectegco.com.myproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by dell pc on 23/12/2559.
 */
public class ImageViewZoom extends View {
    private Drawable image;
    private float scaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    protected static final String absolutePath = "path";


    public ImageViewZoom(Context context){
        super(context);

        //Set photo
//        String imgPath = absolutePath;
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
//        ImageView myimage = (ImageView)findViewById(R.id.zoomImg);
//        myimage.setImageBitmap(myBitmap);

        Drawable d = new BitmapDrawable(getResources(), absolutePath);

       // image = context.getDrawable(R.mipmap.ic_launcher);
        setFocusable(true);
        image.setBounds(0,0,image.getIntrinsicWidth(),image.getIntrinsicHeight());
        scaleGestureDetector = new ScaleGestureDetector(context,new ScaleListener());
    }

    @Override protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(scaleFactor,scaleFactor); //zoom x y same factor
        image.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        scaleGestureDetector.onTouchEvent(event);
        invalidate();
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f,Math.min(scaleFactor,5.0f));
            invalidate();
            return true;
        }


    }
}
