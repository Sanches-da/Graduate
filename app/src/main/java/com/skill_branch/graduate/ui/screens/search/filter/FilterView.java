package com.skill_branch.graduate.ui.screens.search.filter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.mvp.views.AbstractView;

public class FilterView extends AbstractView<FilterScreen.FilterPresenter> {
    public FilterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<FilterScreen.Component>getDaggerComponent(context).inject(this);
    }
}
