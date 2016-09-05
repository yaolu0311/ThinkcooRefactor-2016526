package com.thinkcoo.mobile.injector;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Robert.yao on 2016/3/21.
 * 该注解用于标注组件跟Activity有相同的生命周期,在该生命周期类组件是单例的
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {}
