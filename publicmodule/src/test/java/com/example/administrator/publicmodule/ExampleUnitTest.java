package com.example.administrator.publicmodule;

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getFilePathName(){
        String path = "test.jpg";
        String name =  path.substring(path.lastIndexOf(File.separator)+1, path.length());
        Assert.assertEquals(name, "test.jpg");
    }
}