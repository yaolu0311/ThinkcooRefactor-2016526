package com.thinkcoo.mobile;

import android.content.Context;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
/**
 * Created by Robert.yao on 2016/5/12.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21 , application = ApplicationStub.class)
public abstract class ApplicationTestCase {
    public Context getApplicationContext(){
        return RuntimeEnvironment.application;
    }
}
