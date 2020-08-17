package com.theonetech.kotlin.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.theonetech.kotlin.R
import com.theonetech.kotlin.data.connection.ApiClient
import com.theonetech.kotlin.data.connection.ApiInterface
import com.theonetech.kotlin.databinding.ActivityLoginBinding
import com.theonetech.kotlin.domain.utils.SharedPrefUtils
import com.theonetech.kotlin.domain.utils.Utils
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
@SuppressLint("StaticFieldLeak")
class LoginViewModel(val context: Context, val binding: ActivityLoginBinding) : ViewModel() {
    //live data instance
    val loginLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var utils: Utils = Utils()

    //click listener for login buttton click
    @RequiresApi(Build.VERSION_CODES.M)
    fun performOnClick(view: View) {
        if (view.id == R.id.button_login) {
            if (checkValidations(
                    binding.editUsername.text.toString().trim(),
                    binding.editPassword.text.toString().trim()
                ) && utils.isOnline(context)
            ) {
                callLogin()
            }
        } else {
            utils.onSNACK(
                binding.rlMainLayout,
                context,
                context.resources.getString(R.string.error_msg_empty_username)
            )
        }
    }

    //Method for checking username and password validation
    private fun checkValidations(username: String, password: String): Boolean {
        when {
            username.isEmpty() -> {
                utils.onSNACK(
                    binding.rlMainLayout,
                    context,
                    context.resources.getString(R.string.error_msg_empty_username)
                )
                return false
            }
            password.isEmpty() -> {
                utils.onSNACK(
                    binding.rlMainLayout,
                    context,
                    context.resources.getString(R.string.error_msg_empty_password)
                )
                return false
            }
            password.length < 8 -> {
                utils.onSNACK(
                    binding.rlMainLayout,
                    context,
                    context.resources.getString(R.string.error_msg_invalid_password)
                )
                return false
            }
            password.length > 40 -> {
                utils.onSNACK(
                    binding.rlMainLayout,
                    context,
                    context.resources.getString(R.string.error_msg_invalid_password)
                )
                return false
            }
            else -> return true
        }
    }

    //login api call
    private fun callLogin() {
        binding.progressbar.visibility = View.VISIBLE
        val apiService: ApiInterface? = ApiClient.getClient()?.create(
            ApiInterface::class.java
        )
        val jsonObject = JsonObject()
        jsonObject.addProperty("userName", binding.editUsername.text.toString().trim())
        jsonObject.addProperty("password", binding.editPassword.text.toString().trim())
        jsonObject.addProperty("schoolId", 1)
        jsonObject.addProperty("isStudent", true)
        val call: Call<ResponseBody?>? = apiService?.login(jsonObject)
        call?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                binding.progressbar.visibility = View.GONE
                try {
                    if (response.isSuccessful) {
                        val jsonObj = JSONObject(response.body()!!.string())
                        val accessToken = jsonObj.getString("access_token")
                        val tokenType = jsonObj.getString("token_type")
                        SharedPrefUtils.setStr(context, Utils.ACCESS_TOKEN, accessToken)
                        SharedPrefUtils.setStr(context, Utils.TOKEN_TYPE, tokenType)
                        SharedPrefUtils.setBool(context, Utils.IS_LOGGED_IN, true)
                        loginLiveData.value = true
                        utils.onSNACK(
                            binding.rlMainLayout,
                            context,
                            context.resources.getString(R.string.msg_success)
                        )
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.string())
                        val jsonArrayErr=jsonObj.getJSONArray("errors")
                        val jsonObjData=jsonArrayErr.getJSONObject(0);
                        val message=jsonObjData.getString("message")
                        utils.onSNACK(
                            binding.rlMainLayout,
                            context,
                            message
                        )
                        loginLiveData.value=false
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                call: Call<ResponseBody?>,
                t: Throwable
            ) {
                binding.progressbar.visibility = View.GONE
                t.printStackTrace()
                utils.onSNACK(
                    binding.rlMainLayout,
                    context,
                    context.resources.getString(R.string.err_msg)
                )
            }
        })
    }

}