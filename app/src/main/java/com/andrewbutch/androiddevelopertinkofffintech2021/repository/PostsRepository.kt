package com.andrewbutch.androiddevelopertinkofffintech2021.repository

import android.util.Log
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiResponse
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiService
import com.andrewbutch.androiddevelopertinkofffintech2021.api.response.ApiResult
import com.andrewbutch.androiddevelopertinkofffintech2021.model.Post
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostDao
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostMapper
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.top.TopPostDao
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.top.TopPostsMapper
import com.andrewbutch.androiddevelopertinkofffintech2021.utils.NetworkBoundResource
import retrofit2.Response
import kotlin.math.max
import kotlin.math.min

class PostsRepository
private constructor(
    private val apiService: ApiService,
    private val latestPostDao: LatestPostDao,
    private val topPostDao: TopPostDao
) {
    private val TAG = "PostsRepository"
    
    // Latest posts fields
    private val latestPostsId: ArrayList<Int> = ArrayList()
    private var currentLatestPost: Int = -1  // index for latestPostId array
    private var latestPostsPage: Int = 0 // network call page

    // Top posts fields
    private val topPostsId: ArrayList<Int> = ArrayList()
    private var currentTopPost: Int = -1  // index for topPostsId array
    private var topPostsPage: Int = 0 // network call page

    fun getNextLatestPost() = object : NetworkBoundResource<Post, ApiResult>() {
        override fun saveNetworkResultToDBSource(item: ApiResult) {
            val posts = item.result
            Log.d(TAG, "Latest saveNetworkResultToDBSource: size ${posts.size} : $posts")
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
        // Пример: currentLatestPost = 25, но latestPostsId.size = 35 (не нужно загружать, т.к. уже есть посты)
        override fun shouldFetch(): Boolean {
            return (latestPostsId.isEmpty() || currentLatestPost == latestPostsId.size - 1).also {
                Log.d(TAG, "Latest shouldFetch: $it")
            }
        }

        override fun loadFromDb(): Post {
            currentLatestPost = min(latestPostsId.size, currentLatestPost + 1)
            val cachedPost = latestPostDao.getPostById(latestPostsId[currentLatestPost])
            Log.d(TAG, "Latest loadFromDb: post: $currentLatestPost array size: ${latestPostsId.size}")
            return LatestPostMapper.mapToPost(cachedPost)
        }

        override fun createCall(): ApiResponse<ApiResult> {
            return try {
                val response: Response<ApiResult> =
                    apiService.getLatestPosts(latestPostsPage).execute()
                latestPostsPage++
                ApiResponse.create(response)
            } catch (e: Throwable) {
                ApiResponse.create(e)
            }
        }

    }.getResult()

    fun getPreviousLatestPost() = object : NetworkBoundResource<Post, ApiResult>() {
        override fun saveNetworkResultToDBSource(item: ApiResult) {
            val posts = item.result
            Log.d(TAG, "Latest saveNetworkResultToDBSource: size ${posts.size} : $posts")
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
            currentLatestPost = max(0, currentLatestPost - 1)
            val cachedPost = latestPostDao.getPostById(latestPostsId[currentLatestPost])
            Log.d(TAG, "Latest loadFromDb: post: $currentLatestPost array size: ${latestPostsId.size}")
            return LatestPostMapper.mapToPost(cachedPost)
        }

        override fun createCall(): ApiResponse<ApiResult> {
            return try {
                val response: Response<ApiResult> =
                    apiService.getLatestPosts(latestPostsPage).execute()
                ApiResponse.create(response)
            } catch (e: Throwable) {
                ApiResponse.create(e)
            }
        }

    }.getResult()

    fun getNextTopPost() = object : NetworkBoundResource<Post, ApiResult>() {
        override fun saveNetworkResultToDBSource(item: ApiResult) {
            val posts = item.result
            Log.d(TAG, "Top saveNetworkResultToDBSource: size ${posts.size} : $posts")
            val mapped = TopPostsMapper.mapFromNetworkEntityArray(posts)
            topPostDao.insert(mapped)

            // save each id into array for navigation between posts
            for (entity in mapped) {
                topPostsId.add(entity.id)
            }
        }

        // Загружать новые данные, если список Id постов пустой ИЛИ
        // каждые 5 постов при условии, что currentPost равен длине массива.
        // Обновление не происходит, если были вызовы предыдущих постов и currentPost уменьшался
        // Пример: currentTopPost = 25, но topPostsId.size = 35 (не нужно загружать, т.к. уже есть посты)
        override fun shouldFetch(): Boolean {
            return (topPostsId.isEmpty() || currentTopPost == topPostsId.size - 1).also {
                Log.d(TAG, "Top shouldFetch: $it")
            }
        }

        override fun loadFromDb(): Post {
            currentTopPost = min(topPostsId.size, currentTopPost + 1)
            val cachedPost = topPostDao.getPostById(topPostsId[currentTopPost])
            Log.d(TAG, "Top loadFromDb: post: $currentTopPost array size: ${topPostsId.size}")
            return TopPostsMapper.mapToPost(cachedPost)
        }

        override fun createCall(): ApiResponse<ApiResult> {
            return try {
                val response: Response<ApiResult> =
                    apiService.getTopPosts(topPostsPage).execute()
                topPostsPage++
                ApiResponse.create(response)
            } catch (e: Throwable) {
                ApiResponse.create(e)
            }
        }

    }.getResult()
    
    fun getPreviousTopPost() = object : NetworkBoundResource<Post, ApiResult>() {
        override fun saveNetworkResultToDBSource(item: ApiResult) {
            val posts = item.result
            Log.d(TAG, "Top saveNetworkResultToDBSource: size ${posts.size} : $posts")
            val mapped = TopPostsMapper.mapFromNetworkEntityArray(posts)
            topPostDao.insert(mapped)

            // save each id into array for navigation between posts
            for (entity in mapped) {
                topPostsId.add(entity.id)
            }
        }

        override fun shouldFetch(): Boolean {
            return false
        }

        override fun loadFromDb(): Post {
            currentTopPost = max(0, currentTopPost - 1)
            val cachedPost = topPostDao.getPostById(topPostsId[currentTopPost])
            Log.d(TAG, "Top loadFromDb: post: $currentTopPost array size: ${topPostsId.size}")
            return TopPostsMapper.mapToPost(cachedPost)
        }

        override fun createCall(): ApiResponse<ApiResult> {
            return try {
                val response: Response<ApiResult> =
                    apiService.getTopPosts(topPostsPage).execute()
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
                val repo = PostsRepository(apiService, db.latestPostsDao(), db.topPostsDao())
                INSTANCE = repo
                repo
            }
        }
    }
    
    
}