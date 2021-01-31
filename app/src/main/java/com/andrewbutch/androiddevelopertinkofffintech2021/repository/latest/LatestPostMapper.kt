package com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest

import com.andrewbutch.androiddevelopertinkofffintech2021.api.NetworkPost
import com.andrewbutch.androiddevelopertinkofffintech2021.model.Post
import com.andrewbutch.utils.DateUtils

object LatestPostMapper {

    fun mapFromNetworkEntity(networkEntity: NetworkPost): LatestPostCacheEntity =
        LatestPostCacheEntity(
            id = networkEntity.id,
            description = networkEntity.description,
            gifUrl = networkEntity.gifURL,
            createdAt = DateUtils.convertStringDateToLong(networkEntity.date)
        )

    fun mapToPost(cachedEntity: LatestPostCacheEntity): Post =
        Post(
            description = cachedEntity.description,
            gifUrl = cachedEntity.gifUrl
        )

    fun mapFromNetworkEntityArray(entities: List<NetworkPost>): List<LatestPostCacheEntity> {
        val mappedArray: ArrayList<LatestPostCacheEntity> = ArrayList()
        for (entity in entities) {
            mappedArray.add(mapFromNetworkEntity(entity))
        }
        return mappedArray
    }
}