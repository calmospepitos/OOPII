package com.example.annexe7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout parent;
    Surface surf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = findViewById(R.id.parent);

        // Étape 1
        surf = new Surface(this);
        // Étape 3
        parent.addView(surf);
    }

    private class Surface extends View {
        Bitmap backgroundImage;
        Paint paint;
        boolean isFirstTouch = true;
        int start_x, start_y, end_x, end_y;

        public Surface(Context context) {
            super(context);

            // Set the background image
            setBackgroundResource(R.drawable.carte);

            // Initialize paint for drawing the red square
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
        }

        @Override
        protected void OnDraw(Canvas canvas) {
            super.onDraw(canvas);
            // Draw the background image on the canvas
            canvas.drawBitmap(backgroundImage, 0, 0, null);
            // Draw the square if touched for the first time
            if (!isFirstTouch) {
                canvas.drawRect(start_x, start_y, end_x, end_y, paint);
            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (isFirstTouch) {
                        start_x = event.getX();
                        start_y = event.getY();
                        end_x = event.getX();
                        end_y = event.getY();
                        isFirstTouch = false;
                    }
                    invalidate();
                    return true;
                default:
                    return super.onTouchEvent(event);
            }
        }
    }
}