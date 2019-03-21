package org.inframincer.rewardedvideoads

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), RewardedVideoAdListener {

    private var rewardedVideoAd: RewardedVideoAd? = null
    private var countDownTimer: CountDownTimer? = null
    var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Google Mobile Ads SDK
        MobileAds.initialize(
            applicationContext,
            getString(R.string.admob_app_id)
        )

        // Get reference to singleton RewardedVideoAd object
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        rewardedVideoAd?.rewardedVideoAdListener = this

        // Set click handler
        retry_game_button.setOnClickListener {
            startGame()
        }
        watch_video_button.setOnClickListener {
            if (rewardedVideoAd!!.isLoaded) {
                rewardedVideoAd?.show()
                toast("RewardedVideoAd object to display the rewarded video ad.")
            }
        }

        // Get the "show ad" button, which shows a rewarded video when clicked.
        watch_video_button.visibility = View.INVISIBLE

        if (savedInstanceState == null) {
            coin_count_text.text = String.format(getString(R.string.coin_count_text, game.coinCount))
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        game.coinCount = savedInstanceState?.getInt(COIN_COUNT_KEY) ?: 0
        game.timeRemaining = savedInstanceState?.getLong(TIME_REMAINING_KEY) ?: 0L
        game.pause = savedInstanceState?.getBoolean(GAME_PAUSE_KEY) ?: false
        game.over = savedInstanceState?.getBoolean(GAME_OVER_KEY) ?: false
        coin_count_text.text = String.format(getString(R.string.coin_count_text, game.coinCount))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(COIN_COUNT_KEY, game.coinCount)
        outState?.putLong(TIME_REMAINING_KEY, game.timeRemaining)
        outState?.putBoolean(GAME_PAUSE_KEY, game.pause)
        outState?.putBoolean(GAME_OVER_KEY, game.over)
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        if (!game.over && game.pause) {
            resumeGame()
        }
        if (game.over) {
            retry_game_button.visibility = View.VISIBLE
        }
        if (game.over && rewardedVideoAd!!.isLoaded) {
            watch_video_button.visibility = View.VISIBLE
        }
        rewardedVideoAd?.resume(this)
    }

    override fun onPause() {
        super.onPause()
        pauseGame()
        rewardedVideoAd!!.pause(this)
    }

    override fun onDestroy() {
        rewardedVideoAd!!.destroy(this)
        super.onDestroy()
    }

    override fun onRewardedVideoAdLoaded() {
        toast("onRewardedVideoAdLoaded")
    }

    override fun onRewardedVideoAdOpened() {
        toast("onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoStarted() {
        toast("onRewardedVideoStarted")
    }

    override fun onRewardedVideoAdClosed() {
        toast("onRewardedVideoAdClosed")
    }

    override fun onRewarded(p0: RewardItem?) {
        val amount = p0?.amount ?: 0
        toast("onRewarded")
        toast("Ad triggered reward. reward amount: $amount")
        addCoins(amount)
    }

    override fun onRewardedVideoAdLeftApplication() {
        toast("onRewardedVideoAdLeftApplication")
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        toast("onRewardedVideoAdFailedToLoad")
    }

    override fun onRewardedVideoCompleted() {
        toast("onRewardedVideoCompleted")
    }

    private fun addCoins(amount: Int) {
        game.coinCount += amount
        coin_count_text.text = String.format(getString(R.string.coin_count_text, game.coinCount))
    }

    private fun startGame() {
        retry_game_button.visibility = View.INVISIBLE
        watch_video_button.visibility = View.INVISIBLE
        createTimer(COUNTER_TIME)
        game.pause = false
        game.over = false

        rewardedVideoAd?.loadAd(getString(R.string.ad_unit_id), AdRequest.Builder().build())
    }

    private fun resumeGame() {
        createTimer(game.timeRemaining)
        game.pause = false
    }

    private fun pauseGame() {
        countDownTimer?.cancel()
        game.pause = true
    }

    private fun finishGame() {
        retry_game_button.visibility = View.VISIBLE
        if (rewardedVideoAd!!.isLoaded) {
            watch_video_button.visibility = View.VISIBLE
        }
        timer_text_view.text = getString(R.string.finish_game_text)
        addCoins(GAME_OVER_REWARD)
        retry_game_button.visibility = View.VISIBLE
        game.over = true
    }

    private fun createTimer(time: Long) {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        countDownTimer = object : CountDownTimer(time * 1000, 50) {
            override fun onTick(millisUnitFinished: Long) {
                game.timeRemaining = millisUnitFinished / 1000 + 1
                timer_text_view.text = String.format(getString(R.string.timer_text, game.timeRemaining))
            }

            override fun onFinish() {
                finishGame()
            }
        }
        countDownTimer?.start()
    }
}
