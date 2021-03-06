package com.zj.play.network

import com.zj.play.model.*
import com.zj.play.room.entity.ProjectClassify
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 版权：渤海新能 版权所有
 * @author zhujiang
 * 版本：1.5
 * 创建日期：2020/5/18
 * 描述：PlayAndroid
 *
 */
interface ProjectService {

    @GET("project/tree/json")
    fun getProjectTree(): Call<BaseModel<List<ProjectClassify>>>

    @GET("project/list/{page}/json")
    fun getProject(@Path("page") page: Int, @Query("cid") cid: Int): Call<BaseModel<ArticleList>>

}