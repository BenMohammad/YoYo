package com.benmohammad.yoyo.customedittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.benmohammad.yoyo.R;

public class LineNumberEditText extends AppCompatEditText {

    private Rect rect;
    private Paint paint;
    final LineNumberEditText me;

    public LineNumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        me = this;
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.FadeYellow));
        paint.setTextSize(40);
        setHorizontallyScrolling(true);
        setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseLine = getBaseline();
        for(int i = 0; i < getLineCount(); i++) {
            canvas.drawText(String.format(" %d " , (i + 1)), rect.left, baseLine, paint);
            baseLine += getLineHeight();
        }
    }
}
