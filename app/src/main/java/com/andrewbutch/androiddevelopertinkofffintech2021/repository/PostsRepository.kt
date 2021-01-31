package com.andrewbutch.androiddevelopertinkofffintech2021.repository

import android.util.Log
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiResponse
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiService
import com.andrewbutch.androiddevelopertinkofffintech2021.api.response.ApiResult
import com.andrewbutch.androiddevelopertinkofffintech2021.model.Post
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostDao
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostMapper
import com.andrewbutch.utils.NetworkBoundResource
import retrofit2.Response
import kotlin.math.max
import kotlin.math.min

class PostsRepository
private constructor(
    private val apiService: ApiService,
    private val latestPostDao: LatestPostDao
) {
    private val TAG = "PostsRepository"

    //
    private val latestPostsId: ArrayList<Int> = ArrayList()
    private var currentPost: Int = -1  // index for latestPostId array
    private var page: Int = 0 // network call page

    fun getNextPost() = object : NetworkBoundResource<Post, ApiResult>() {
        override fun saveNetworkResultToDBSource(item: ApiResult) {
            val posts = item.result
            Log.d(TAG, "saveNetworkResultToDBSource: size ${posts.size} : $posts")
            val mapped = LatestPostMapper.mapFromNetworkEntityArray(posts)
            latestPostDao.insert(mapped)

            // save each id into array for navigation between posts
            for (entity in mapped) {
                latestPostsId.add(entity.id)
            }
        }

        // Загружать новые данные, если список Id постов пустой ИЛИ
        // каждые 5 постов при условии, что currentPost равен длине массива.
        // Обновление не происходит, если были вызовы предыдущих постов и currentPost уменьшался
        // Пример: currentPost = 25, но latestPostsId.size = 35 (не нужно загружать, т.к. уже есть посты)
        override fun shouldFetch(): Boolean {
            return (latestPostsId.isEmpty() || currentPost == latestPostsId.size - 1).also {
                Log.d(TAG, "shouldFetch: $it")
            }
        }

        override fun loadFromDb(): Post {
            currentPost = min(latestPostsId.size, currentPost + 1)
            val cachedPost = latestPostDao.getPostById(latestPostsId[currentPost])
            Log.d(TAG, "loadFromDb: post: $currentPost array size: ${latestPostsId.size}")
            return LatestPostMapper.mapToPost(cachedPost)
        }

        override fun createCall(): ApiResponse<ApiResult> {
            return try {
                val response: Response<ApiResult> =
                    apiService.getLatestPosts(page).execute()
                page++
                ApiResponse.create(response)
            } catch (e: Throwable) {
                ApiResponse.create(e)
            }
        }

    }.getResult()

    fun getPreviousPost() = object : NetworkBoundResource<Post, ApiResult>() {
        override fun saveNetworkResultToDBSource(item: ApiResult) {
            val posts = item.result
            Log.d(TAG, "saveNetworkResultToDBSource: size ${posts.size} : $posts")
            val mapped = LatestPostMapper.mapFromNetworkEntityArray(posts)
            latestPostDao.insert(mapped)

            // save each id into array for navigation between posts
            for (entity in mapped) {
                latestPostsId.add(entity.id)
            }
        }

        override fun shouldFetch(): Boolean {
            return false
        }

        override fun loadFromDb(): Post {
            currentPost = max(0, currentPost - 1)
            val cachedPost = latestPostDao.getPostById(latestPostsId[currentPost])
            Log.d(TAG, "loadFromDb: post: $currentPost array size: ${latestPostsId.size}")
            return LatestPostMapper.mapToPost(cachedPost)
        }

        override fun createCall(): ApiResponse<ApiResult> {
            return try {
                val response: Response<ApiResult> =
                    apiService.getLatestPosts(page).execute()
                ApiResponse.create(response)
            } catch (e: Throwable) {
                ApiResponse.create(e)
            }
        }

    }.getResult()

    companion object {
        private var INSTANCE: PostsRepository? = null

        fun getInstance(apiService: ApiService, db: PostsDatabase): PostsRepository {
            return INSTANCE ?: synchronized(this) {
                val repo = PostsRepository(apiService, db.latestPostsDao())
                INSTANCE = repo
                repo
            }
        }
    }
}