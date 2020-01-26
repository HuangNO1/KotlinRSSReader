package com.example.kotlinrssreader.Model

data class RootObject(val status: String, val feed: Feed, val item: List<Item>)