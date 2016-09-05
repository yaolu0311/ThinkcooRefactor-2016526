/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.thinkcoo.mobile.injector.components;
import android.app.Activity;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationModule.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
