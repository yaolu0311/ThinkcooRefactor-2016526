package com.thinkcoo.mobile.presentation;

import android.accounts.NetworkErrorException;
import com.thinkcoo.mobile.BuildConfig;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.ThinkcooApp;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Robert.yao on 2016/3/28.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class , sdk = 21 , application = ThinkcooApp.class)
public class TestErrorMessageFactory {

    ErrorMessageFactory tErrorMessageFactory;
    //WelcomeActivity tWelcomeActivity;

    @Before
    public void setup(){
        //tWelcomeActivity = Robolectric.buildActivity(WelcomeActivity.class).get();
        //tErrorMessageFactory = new ErrorMessageFactory(tWelcomeActivity);
    }

    @Test
    public void testInitErrorMessageFactory(){
        //Assert.assertSame(tWelcomeActivity.getApplicationContext(),tErrorMessageFactory.getContext());
    }

    @Test
    public void testCreateNetworkErrorExceptionMsg(){

//        NetworkErrorException networkErrorException = new NetworkErrorException("net work error 404");
//        String errorMsg = tErrorMessageFactory.create(networkErrorException);
//        String resMsg = tWelcomeActivity.getString(R.string.net_no);
//        Assert.assertNotNull(errorMsg);
//        Assert.assertEquals(errorMsg, resMsg);
    }

    @Test
    public void testUnknownExceptionCreateMsg(){

//        Exception exception = new Exception("unknown");
//        String errorMsg = tErrorMessageFactory.create(exception);
//        String resMsg = tWelcomeActivity.getString(R.string.default_error_msg);
//        Assert.assertNotNull(errorMsg);
//        Assert.assertEquals(errorMsg,resMsg);
    }

}
