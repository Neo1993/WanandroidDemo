package com.lib.common.ext

import java.lang.reflect.ParameterizedType

/**
 * 返回obj的第一个泛型的class对象
 */
fun <VM> getVmClazz(obj: Any) : VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

fun <T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) notNullAction.invoke(this) else nullAction.invoke()
}