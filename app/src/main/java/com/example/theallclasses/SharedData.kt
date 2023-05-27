package com.example.theallclasses

object SharedData {

    //courseinfo
    var Boardmap: Map<String, Any>? = null
    var JEEmap: Map<String, Any>? = null
    var NEETmap: Map<String, Any>? = null
    var TeacherTraningCoursemap: Map<String, Any>? = null
    var courseImage: Map<String,String>? = null

    // userinfo
    var uid: String = ""
    var username: String = ""
    var board:Boolean = false
    var jee:Boolean = false
    var neet:Boolean = false
    var teachertrainingcourse:Boolean = false

    //bannerinfo
    var path = ""
    var imagename:Array<String>?=null
}