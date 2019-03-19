package org.inframincer.rewardedvideoads

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardedVideoAd

class MainActivity : AppCompatActivity() {

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
}
