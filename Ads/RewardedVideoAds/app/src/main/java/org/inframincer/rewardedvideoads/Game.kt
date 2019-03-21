package org.inframincer.rewardedvideoads

const val COUNTER_TIME = 10L
const val GAME_OVER_REWARD = 1
const val TIME_REMAINING_KEY = "TIME_REMAINING"
const val COIN_COUNT_KEY = "COIN_COUNT"
const val GAME_PAUSE_KEY = "GAME_PAUSE"
const val GAME_OVER_KEY = "GAME_OVER"

data class Game(
    var coinCount: Int = 0,
    var timeRemaining: Long = 0L,
    var over: Boolean = false,
    var pause: Boolean = false
)
