package com.thinkcoo.mobile.injector;

import java.lang.annotation.Retention;
import javax.inject.Scope;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Robert.yao on 2016/7/29.
 */
@Scope
@Retention(RUNTIME)
public @interface ViewScope {

}
