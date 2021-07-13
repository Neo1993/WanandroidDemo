package com.neo.wanandroid.model.repository.request

import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.network.ApiResponse
import com.neo.wanandroid.network.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * 创建单例对象
 */
val httpRequstManager: HttpRequestManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HttpRequestManager()
}

class HttpRequestManager {
    suspend fun getHomeArticleList(page : Int) : ApiResponse<ApiPageResponse<ArrayList<ArticleResponse>>>{
        //同时异步请求2个接口，请求完成后合并数据
        return withContext(Dispatchers.IO) {
            val articleList = async { apiService.getHomeArticleList(page) }
            if(page == 0){
                val topData = async { apiService.getTopArticleList() }
                articleList.await().data.datas.addAll(0,topData.await().data)
                articleList.await()
            }else{
                articleList.await()
            }
        }

    }
}