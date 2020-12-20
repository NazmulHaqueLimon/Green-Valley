 package com.example.greenvalley.dataRepository

import java.lang.Exception

data class DataOrException<T,E:Exception>(
        val data :T?,
        val exception: Exception?
){

}
