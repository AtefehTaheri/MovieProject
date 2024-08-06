package ir.atefehtaheri.network.adapter


import ir.atefehtaheri.network.NetworkResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class NetworkResponseCall<S : Any, E : Any> (
    private val delegate: Call<S>,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {
    override fun clone(): Call<NetworkResponse<S, E>> {
        return NetworkResponseCall(delegate.clone(), errorBodyConverter)
    }

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted


    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()


    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                if (response.isSuccessful) {
                    val successBody = response.body()
                    val networkResponse = NetworkResponse.Success(successBody, response.headers())
                    callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
                } else {
                    val errorBody = response.errorBody()
                    val code = response.code()

                    val error = when {
                        errorBody == null -> null
                        errorBody.contentLength() == 0L -> null
                        else -> try {
                            errorBodyConverter.convert(errorBody)
                        } catch (ex: Exception) {
                            null
                        }
                    }

                    if (error != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiError(error, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.UnknownError(IOException("Unknown error")))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> NetworkResponse.NetworkError(t)
                    else -> NetworkResponse.UnknownError(t)
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

}