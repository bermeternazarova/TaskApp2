package com.example.taskapp2.ui.onBoard

data class BoardModel(
    var image :Int?=null,
    var title :String?=null,
    var description:String?=null,
    var isLast:Boolean ?= false
):java.io.Serializable
