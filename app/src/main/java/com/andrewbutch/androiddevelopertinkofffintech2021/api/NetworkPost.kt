package com.andrewbutch.androiddevelopertinkofffintech2021.api

import com.google.gson.annotations.Expose

/*
    id: 3,
    description: "Когда я деплою на боевой сервер.",
    votes: 627,
    author: "paralainer",
    date: "Mar 11, 2013 1:36:01 PM",
    gifURL: http://static.devli.ru/public/images/gifs/201303/81cf4c06-505c-4dae-bfeb-f01bbd28117f.gif,
    gifSize: 1598600,
    previewURL: https://static.devli.ru/public/images/previews/201303/4293a4af-aa6e-4dcc-b52b-96f1c3fdbfe6.jpg,
    videoURL: http://static.devli.ru/public/images/v/201607/3566d10e-05af-4867-b200-14a6e89764cc.mp4,
    videoPath: "/public/images/v/201607/3566d10e-05af-4867-b200-14a6e89764cc.mp4",
    videoSize: 97385,
    type: "gif",
    width: "480",
    height: "253",
    commentsCount: 6,
    fileSize: 1598600,
    canVote: false
 */
data class NetworkPost(
    @Expose val id: Int,
    @Expose val description: String,
    @Expose val votes: Int,
    @Expose val author: String,
    @Expose val date: String,
    @Expose val gifURL: String,
    @Expose val gifSize: Int,
    @Expose val previewURL: String,
    @Expose val videoURL: String,
    @Expose val videoPath: String,
    @Expose val videoSize: Int,
    @Expose val type: String,
    @Expose val width: String,
    @Expose val height: String,
    @Expose val commentsCount: Int,
    @Expose val fileSize: Int,
    @Expose val canVote: Boolean
)

