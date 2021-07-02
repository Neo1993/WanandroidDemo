package com.neo.wanandroid.ui.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neo.wanandroid.R
import com.neo.wanandroid.ext.setAdapterAnimation
import com.neo.wanandroid.model.bean.ArticleResponse

class ArticleAdapter(dataList: MutableList<ArticleResponse>) :
    BaseDelegateMultiAdapter<ArticleResponse, BaseViewHolder>(dataList) {
    private val Article = 1;        //文章类型
    private val Project = 2;        //项目类型

    init {
        setAdapterAnimation(2)
        //设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<ArticleResponse>() {
            override fun getItemType(data: List<ArticleResponse>, position: Int): Int {
                return if (TextUtils.isEmpty(data[position].envelopePic)) Article else Project
            }
        })
        //绑定item类型
        getMultiTypeDelegate().let {
            it?.addItemType(Article, R.layout.item_adapter_article)
            it?.addItemType(Project, R.layout.item_adapter_project)
        }
    }

    override fun convert(holder: BaseViewHolder, item: ArticleResponse) {
        when (holder.itemViewType){
            Article -> {

            }
            Project -> {

            }
        }
    }
}