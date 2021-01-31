package com.andrewbutch.androiddevelopertinkofffintech2021.repository

import android.util.Log
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiResponse
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiResult
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiService
import com.andrewbutch.androiddevelopertinkofffintech2021.model.Post
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostDao
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostMapper
import com.andrewbutch.utils.NetworkBoundResource
import retrofit2.Response

class PostsRepository
private constructor(
    private val apiService: ApiService,
    private val latestPostDao: LatestPostDao
) {
    private val TAG = "PostsRepository"
    private val latestPostsId: ArrayList<Int> = ArrayList()
    private var next: Int = 1

    fun getNextPost() = object : NetworkBoundResource<Post, ApiResult>() {
        override fun saveNetworkResultToDBSource(item: ApiResult) {
            val posts = item.result
            Log.d(TAG, "saveNetworkResultToDBSource: size ${posts.size} : $posts")
            val mapped = LatestPostMapper.mapFromNetworkEntityArray(posts)
            latestPostDao.insert(mapped)

            // cache ID of loaded posts
            for (entity in mapped) {
                latestPostsId.add(entity.id)
            }
        }

        override fun shouldFetch(): Boolean {
            return (latestPostsId.isEmpty() || next % 5 == 0).also {
                Log.d(TAG, "shouldFetch: $it")
            }
        }

        override fun loadFromDb(): Post {
            val cachedPost = latestPostDao.getPostById(latestPostsId[next])
            Log.d(TAG, "loadFromDb: $cachedPost")
            next++
            return LatestPostMapper.mapToPost(cachedPost)
        }

        override fun createCall(): ApiResponse<ApiResult> {
            return try {
                val response: Response<ApiResult> =
                    apiService.getLatestPosts(next).execute()
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