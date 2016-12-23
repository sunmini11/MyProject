package projectegco.com.myproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by dell pc on 23/12/2559.
 */
public class ImageViewZoom extends View {
    private Drawable image;
    private float scaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;

    public ImageViewZoom(Context context){
        super(context);
        //image = context.getDrawable(R.mipmap.ic_launcher);
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
