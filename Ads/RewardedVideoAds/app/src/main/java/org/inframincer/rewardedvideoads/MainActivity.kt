package org.inframincer.rewardedvideoads

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), RewardedVideoAdListener {

    private lateinit var rewardedVideoAd: RewardedVideoAd

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

        // Set click handler
        watch_video_button.setOnClickListener {
            if (rewardedVideoAd.isLoaded) {
                rewardedVideoAd.show()
                toast("RewardedVideoAd object to display the rewarded video ad.")
            }
        }
    }

    override fun onResume() {
        rewardedVideoAd.resume(this)
        super.onResume()
    }

    override fun onPause() {
        rewardedVideoAd.pause(this)
        super.onPause()
    }

    override fun onDestroy() {
        rewardedVideoAd.destroy(this)
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
        toast("onRewarded")
        toast("Ad triggered reward. reward amount: ${p0?.amount ?: 0}")
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
}
