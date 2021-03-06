package com.zj.play.view.home.search.article

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zj.play.view.base.BaseListActivity
import com.zj.play.view.article.ArticleAdapter
import kotlinx.android.synthetic.main.activity_base_list.*

private const val KEYWORD = "KEYWORD"

class ArticleListActivity : BaseListActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(ArticleListViewModel::class.java) }

    private var keyword = ""
    private lateinit var articleAdapter: ArticleAdapter

    override fun initData() {
        super.initData()
        keyword = intent.getStringExtra(KEYWORD) ?: ""
        baseListTitleBar.setTitle(keyword)
        viewModel.articleLiveData.observe(this, {
            if (it.isSuccess) {
                val articleList = it.getOrNull()
                if (articleList != null) {
                    loadFinished()
                    if (page == 1 && viewModel.articleList.size > 0) {
                        viewModel.articleList.clear()
                    }
                    viewModel.articleList.addAll(articleList.datas)
                    if (viewModel.articleList.size == 0) {
                        showNoContentView("没有关于 $keyword 的数据，请更换关键字搜索")
                    }
                    articleAdapter.notifyDataSetChanged()
                } else {
                    showLoadErrorView()
                }
            } else {
                showBadNetworkView { getDataList() }
            }
        })
    }

    override fun initView() {
        super.initView()
        articleAdapter = ArticleAdapter(
            this,
            viewModel.articleList
        )
        articleAdapter.setHasStableIds(true)
        baseListRecycleView.adapter = articleAdapter
    }

    override fun isStaggeredGrid(): Boolean {
        return true
    }

    override fun getDataList() {
        if (viewModel.articleList.size <= 0) startLoading()
        viewModel.getArticleList(page, keyword)
    }

    companion object {
        fun actionStart(context: Context, keyword: String) {
            val intent = Intent(context, ArticleListActivity::class.java).apply {
                putExtra(KEYWORD, keyword)
            }
            context.startActivity(intent)
        }
    }

}
