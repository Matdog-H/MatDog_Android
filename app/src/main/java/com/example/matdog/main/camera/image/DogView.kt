package com.example.matdog.main.camera.image

interface DogView {
    fun displayDogBreed(dogBreed: String, winPercent: Float)
    fun displayError()
}