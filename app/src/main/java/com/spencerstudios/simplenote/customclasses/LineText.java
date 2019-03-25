package com.spencerstudios.simplenote.customclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class LineText extends android.support.v7.widget.AppCompatEditText {
    public LineText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        paint.setColor(Color.parseColor("#626262"));
        int lineWidth = getMeasuredWidth();
        int lineHeight = getLineHeight();
        int linePadding = getExtendedPaddingTop();
        int lines = ((getMeasuredHeight() - getExtendedPaddingTop()) - getExtendedPaddingBottom()) / lineHeight;
        if (lines < getLineCount()) {
            lines = getLineCount();
        }
        int numLines = lines + 1;
        for (int i = 1; i < numLines; i++) {
            int y = (i * lineHeight) + linePadding;
            canvas.drawLine(0.0f, (float) y, (float) lineWidth, (float) y, paint);
        }
        super.onDraw(canvas);
    }
}