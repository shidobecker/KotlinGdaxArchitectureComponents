package com.karchitecture.shido.karchitecture

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import org.junit.Test

/**
 * Created by Shido on 01/10/2017.
 */
class APITest {
    val endpoint = "https://api.gdax.com"

    @Test
    fun runTest(){
        endpoint.httpGet().responseString{
            request, response, result ->
            println("$request $result $response")

            when(result){
                is Result.Failure ->{
                    /*result.getAs<>()*/
                }
                is Result.Success ->{

                }

            }
        }
        assert(true)
    }
}