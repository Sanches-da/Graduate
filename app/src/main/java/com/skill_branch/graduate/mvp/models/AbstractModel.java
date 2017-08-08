package com.skill_branch.graduate.mvp.models;

import com.birbit.android.jobqueue.JobManager;
import com.skill_branch.graduate.data.managers.DataManager;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.components.DaggerModelComponent;
import com.skill_branch.graduate.di.components.ModelComponent;
import com.skill_branch.graduate.di.modules.ModelModule;

import javax.inject.Inject;

public abstract class AbstractModel {
    @Inject
    DataManager mDataManager;
    @Inject
    JobManager mJobManager;

    public AbstractModel() {
        ModelComponent component= DaggerService.getComponent(ModelComponent.class);
        if (component==null){
            component = createDaggerComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }

    public AbstractModel(DataManager dataManager, JobManager jobManager) {
        mDataManager = dataManager;
        mJobManager = jobManager;
    }

    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public JobManager getJobManager() {
        return mJobManager;
    }
}
