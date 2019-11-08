package com.yawjenn.mvvmpractice.data

data class Guess(val guessWord : String, val guess: String) {

    companion object{
        private val guesses = listOf("Dog", "Cat", "Elephant", "Platypus", "Giraffe",
            "Dolphin", "Gorilla", "Rhino", "Hog Rider", "Kraken", "Mouse", "Godzilla")

        fun generateRandomGuess(guessWord: String) : Guess
                = Guess(guessWord = guessWord, guess = guesses.random())
    }
}