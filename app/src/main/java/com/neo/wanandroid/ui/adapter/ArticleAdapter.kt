package com.neo.wanandroid.ui.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neo.wanandroid.R
import com.neo.wanandroid.ext.setAdapterAnimation
import com.neo.wanandroid.ext.toHtml
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.ui.widget.custom.CollectView

class ArticleAdapter(dataList: MutableList<ArticleResponse>) :
    BaseDelegateMultiAdapter<ArticleResponse, BaseViewHolder>(dataList) {
    private val Article = 1;        //文章类型
    private val Project = 2;        //项目类型
    private var showTag = true//是否展示标签 tag 一般主页才用的到

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

    constructor(dataList: MutableList<ArticleResponse>, showTag: Boolean) : this(dataList){
        this.showTag = showTag;
    }

    override fun convert(helper: BaseViewHolder, item: ArticleResponse) {
        when (helper.itemViewType) {
            Article -> {
                //文章布局的赋值
                item.run {
                    helper.setText(
                        R.id.item_home_author,
                        if (author.isNotEmpty()) author else shareUser
                    )
                    helper.setText(R.id.item_home_content, title.toHtml())
                    helper.setText(R.id.item_home_type2, "$superChapterName·$chapterName".toHtml())
                    helper.setText(R.id.item_home_date, niceDate)
                    helper.getView<CollectView>(R.id.item_home_collect).isChecked = collect
                    if (showTag) {
                        //展示标签
                        helper.setGone(R.id.item_home_new, !fresh)
                        helper.setGone(R.id.item_home_top, type != 1)
                        if (tags.isNotEmpty()) {
                            helper.setGone(R.id.item_home_type1, false)
                            helper.setText(R.id.item_home_type1, tags[0].name)
                        } else {
                            helper.setGone(R.id.item_home_type1, true)
                        }
                    } else {
                        //隐藏所有标签
                        helper.setGone(R.id.item_home_top, true)
                        helper.setGone(R.id.item_home_type1, true)
                        helper.setGone(R.id.item_home_new, true)
                    }
                }
//                helper.getView<CollectView>(R.id.item_home_collect)
//                    .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
//                        override fun onClick(v: CollectView) {
//                            collectAction.invoke(item, v, helper.adapterPosition)
//                        }
//                    })
            }
            Project -> {
                //项目布局的赋值
                item.run {
                    helper.setText(
                        R.id.item_project_author,
                        if (author.isNotEmpty()) author else shareUser
                    )
                    helper.setText(R.id.item_project_title, title.toHtml())
                    helper.setText(R.id.item_project_content, desc.toHtml())
                    helper.setText(
                        R.id.item_project_type,
                        "$superChapterName·$chapterName".toHtml()
                    )
                    helper.setText(R.id.item_project_date, niceDate)
                    if (showTag) {
                        //展示标签
                        helper.setGone(R.id.item_project_new, !fresh)
                        helper.setGone(R.id.item_project_top, type != 1)
                        if (tags.isNotEmpty()) {
                            helper.setGone(R.id.item_project_type1, false)
                            helper.setText(R.id.item_project_type1, tags[0].name)
                        } else {
                            helper.setGone(R.id.item_project_type1, true)
                        }
                    } else {
                        //隐藏所有标签
                        helper.setGone(R.id.item_project_top, true)
                        helper.setGone(R.id.item_project_type1, true)
                        helper.setGone(R.id.item_project_new, true)
                    }
                    helper.getView<CollectView>(R.id.item_project_collect).isChecked = collect
                    Glide.with(context).load(envelopePic)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(helper.getView(R.id.item_project_imageview))
                }
//                helper.getView<CollectView>(R.id.item_project_collect)
//                    .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
//                        override fun onClick(v: CollectView) {
//                            collectAction.invoke(item, v, helper.adapterPosition)
//                        }
//                    })
            }
        }
    }
}