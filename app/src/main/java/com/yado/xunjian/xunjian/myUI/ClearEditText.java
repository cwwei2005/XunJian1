package com.yado.xunjian.xunjian.myUI;

/**
 * Created by Administrator on 2017/11/16.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.yado.xunjian.xunjian.R;


@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearDrawable = getResources().getDrawable(R.drawable.ic_clear_edittext);//图标
        this.setBackgroundColor(Color.parseColor("#00000000"));
        this.setTextSize(15f);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);//text空时图标被隐藏
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {//点击区域是图标
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP],
                visible ? mClearDrawable : null, getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }
}

