package com.example.theallclasses

object SharedData {

    //courseinfo
    var Boardmap: Map<String, Any>? = null
    var JEEmap: Map<String, Any>? = null
    var NEETmap: Map<String, Any>? = null
    var TeacherTraningCoursemap: Map<String, Any>? = null
    var courseImage: Map<String,String>? = null

    var Mycourses: Map<String, Any>? = null

//    var userdata: Map<String, Any?> = mutableMapOf(
//        "username" to "",
//        "email" to "",
//        "mycourses" to mutableMapOf<String, String>()
//    )

    val CourseFlag = mapOf(
        "Jee11" to false,
        "Jee12" to false,
        "JeeFullChemistry" to true,
        "JeePhysics" to false
    )

    // userinfo
    var uid: String = ""
    var username: String = ""
    var email: String = ""


    //bannerinfo
    var imagename:Array<String>?=null
}