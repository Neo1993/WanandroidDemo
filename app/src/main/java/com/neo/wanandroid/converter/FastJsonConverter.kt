package com.neo.wanandroid.converter

import com.alibaba.fastjson.JSON
import com.drake.net.convert.JSONConvert
import org.json.JSONObject
import java.lang.reflect.Type

class FastJsonConverter : JSONConvert(code = "errorCode", message = "errorMsg", success = "0") {

    override fun <R> String.parseBody(succeed: Type): R? {
        return try {
            JSON.parseObject(JSONObject(this).getString("data"), succeed)
        } catch (e: Exception) {
            JSON.parseObject(this, succeed)
        }
    }
}