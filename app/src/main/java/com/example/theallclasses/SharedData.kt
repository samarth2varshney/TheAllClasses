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

    //userUID Document
    var UIDmap: Map<String, Any>? = null

    //bannerinfo
    var path = ""
    var imagename:Array<String>?=null
}