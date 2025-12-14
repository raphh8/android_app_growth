package com.projet.mobile.growth.list

import java.io.Serializable

data class Task(val id: String, var title: String, var description: String = "You can add your own description!") : Serializable