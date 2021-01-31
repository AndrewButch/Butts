package com.andrewbutch.androiddevelopertinkofffintech2021.repository.top

import com.andrewbutch.androiddevelopertinkofffintech2021.api.response.NetworkPost
import com.andrewbutch.androiddevelopertinkofffintech2021.model.Post

object TopPostsMapper {

    fun mapFromNetworkEntity(networkEntity: NetworkPost): TopPostCacheEntity =
        TopPostCacheEntity(
            id = networkEntity.id,
            description = networkEntity.description,
            gifUrl = networkEntity.gifURL,
            votes = networkEntity.votes
        )

    fun mapToPost(cachedEntity: TopPostCacheEntity): Post =
        Post(
            description = cachedEntity.description,
            gifUrl = cachedEntity.gifUrl
        )

    fun mapFromNetworkEntityArray(entities: List<NetworkPost>): List<TopPostCacheEntity> {
        val mappedArray: ArrayList<TopPostCacheEntity> = ArrayList()
        for (entity in entities) {
            mappedArray.add(mapFromNetworkEntity(entity))
        }
        return mappedArray
    }
}