

package com.unicornheight.bakingapp.di.components;

import com.unicornheight.bakingapp.di.module.CakeModule;
import com.unicornheight.bakingapp.di.scope.PerActivity;
import com.unicornheight.bakingapp.modules.home.MainActivity;

import dagger.Component;

@PerActivity
@Component(modules = CakeModule.class, dependencies = ApplicationComponent.class)
public interface CakeComponent {

    void inject(MainActivity activity);
}
