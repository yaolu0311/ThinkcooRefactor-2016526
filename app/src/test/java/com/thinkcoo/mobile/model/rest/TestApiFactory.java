package com.thinkcoo.mobile.model.rest;

import com.example.administrator.publicmodule.model.rest.ApiFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Robert.yao on 2016/3/25.
 */
@RunWith(JUnit4.class)
public class TestApiFactory {

    ApiFactory apiFactory;

    @Before
    public void setup(){
       // apiFactory =ApiFactoryImpl.getInstance();
    }

    @Test
    public void testCreateLoginApi(){

//        apiFactory.putWebNode(ApiFactoryImpl.PERSONAL_PREFIX,"http://192.168.0.1");
//        UserApi apibase = apiFactory.createApiByClass(UserApi.class);
//        Assert.assertNotNull(apibase);
//        Assert.assertEquals(apiFactory.cacheSize(),1);
//        UserApi apibase2 = apiFactory.createApiByClass(UserApi.class);
//        Assert.assertSame(apibase,apibase2);
    }

    @Test
    public void testCreateAccountApi(){

//        AccountApi accountApi = apiFactory.createApiByClass(AccountApi.class);
//        Assert.assertNotNull(accountApi);
//        Assert.assertEquals(apiFactory.cacheSize(),1);
    }

    @Test
    public void testPutWebNode(){

        apiFactory.putWebNode("","http://192.168.0.1");
        Assert.assertEquals(apiFactory.getWebNodeSize(), 2);
        apiFactory.putWebNode("","http://192.168.0.1");
        Assert.assertEquals(apiFactory.getWebNodeSize(), 2);
        apiFactory.putWebNode("111","http://192.168.0.1");
        Assert.assertEquals(apiFactory.getWebNodeSize(), 3);
    }



}
