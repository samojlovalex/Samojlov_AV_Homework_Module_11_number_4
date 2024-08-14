package com.example.samojlov_av_homework_module_11_number_4

data class User(val name: String, val age: String) {
    override fun toString() = "Имя: $name; Возраст: $age"
}