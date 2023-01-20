package com.contelli.jettrivia.repository

import android.util.Log
import com.contelli.jettrivia.data.DataOrException
import com.contelli.jettrivia.model.QuestionItem
import com.contelli.jettrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()
    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (e: Exception) {
            dataOrException.exception = e
            Log.d(
                "Exception",
                "getAllQuestions: ${dataOrException.exception!!.localizedMessage}"
            )
        }
        return dataOrException
    }
}