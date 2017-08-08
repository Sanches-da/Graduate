package com.skill_branch.graduate.ui.screens.photo_list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class PhotoListItem extends RelativeLayout {
    public PhotoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // получаем рассчитанные размеры кнопки
        //final int height = getMeasuredHeight();	// высота
        final int width = getMeasuredWidth();	// ширина

        // теперь задаем новый размер
        setMeasuredDimension(width, width);

        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = width;
        lp.width = width;
        setLayoutParams(lp);
    }



}
