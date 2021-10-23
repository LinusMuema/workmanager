package com.moose.work.data

import kotlinx.coroutines.delay

object Data {

    private val names = arrayListOf("John Doe", "Linus Moose", "Peter Kay", "Dankat Dennis", "Mick Jagger", "Jane Doe", "Lorem Ipsum", "Jenny clyde", "Missy clark", "Kate Hudson")

    suspend fun getUser(): User {
        delay(1000L)
        return User(
            name = names.random(),
            number = (9999..99999).random(),
            image = "https://randomuser.me/api/portraits/lego/${(0..5).random()}.jpg"
        )
    }
}