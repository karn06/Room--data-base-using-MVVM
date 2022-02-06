package com.example.baatutechdemoapp

interface ResponseHandler<T> {
    fun response(response: T)
}