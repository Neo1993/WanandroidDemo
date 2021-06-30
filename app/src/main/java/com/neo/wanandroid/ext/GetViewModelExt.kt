package com.neo.wanandroid.ext

import java.lang.reflect.ParameterizedType

fun <VM> getVMClazz(obj : Any) : Class<VM> {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
}