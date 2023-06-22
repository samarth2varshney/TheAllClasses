package com.example.theallclasses

object SharedData {

    //courseinfo
    var Boardmap: Map<String, Any>? = null
    var JEEmap: Map<String, Any>? = null
    var NEETmap: Map<String, Any>? = null
    var TeacherTraningCoursemap: Map<String, Any>? = null
    var courseImage: Map<String,String>? = null

    var Mycourses: Map<String, Any>? = null
    var Mycoursesdata: MutableMap<String, Any>? = mutableMapOf()

    // userinfo
    var uid: String = ""
    var username: String = ""
    var email: String = ""

    //bannerinfo
    var imagename:Array<String>?=null
}