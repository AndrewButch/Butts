package com.andrewbutch.androiddevelopertinkofffintech2021.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiEmptyResponse
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiErrorResponse
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiResponse
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiSuccessResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// ResultType: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
abstract class NetworkBoundResource<ResultType, RequestObject> {
    private val TAG = NetworkBoundResource::class.java.simpleName
    private var flowResult: Flow<Resource<ResultType>> = flow {

        // emit loading
        emit(Resource.Loading<ResultType>(null))

        // fetch new data from network or return from database
        if (shouldFetch()) {
            val networkResult = fetchFromNetwork()
            emit(networkResult)
        } else {
            // get data from database
            val cachedData: ResultType = loadFromDb()
            emit(Resource.Success(cachedData))
        }
    }

    private fun fetchFromNetwork(): Resource<ResultType> {
        val apiResponse: ApiResponse<RequestObject> = createCall()
        when (apiResponse) {
            is ApiSuccessResponse -> {
                // get data from ApiResponse
                val networkData =
                    extractDataFromNetworkResponse(apiResponse as ApiSuccessResponse<ResultType>) as RequestObject

                // save data into local database
                saveNetworkResultToDBSource(networkData)

                // get updated data from database
                val cachedData: ResultType = loadFromDb()

                // return success
                return Resource.Success(cachedData)

            }
            is ApiEmptyResponse -> {
                Log.d(TAG, "fetchFromNetwork: ApiEmptyResponse")
                return Resource.Error("Empty result")
            }
            is ApiErrorResponse -> {
                Log.d(TAG, "fetchFromNetwork: ApiErrorResponse ${apiResponse.errorMessage}")
                return Resource.Error(apiResponse.errorMessage)
            }
        }

    }

    @WorkerThread
    private fun extractDataFromNetworkResponse(response: ApiSuccessResponse<ResultType>): ResultType {
        return response.body
    }

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveNetworkResultToDBSource(item: RequestObject)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(): Boolean

    // Called to get the cached data from the database.
    @MainThread
    protected abstract fun loadFromDb(): ResultType

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): ApiResponse<RequestObject>

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun getResult() = flowResult
}