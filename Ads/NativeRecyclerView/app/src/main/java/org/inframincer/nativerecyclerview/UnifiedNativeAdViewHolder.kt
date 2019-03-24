package org.inframincer.nativerecyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.gms.ads.formats.UnifiedNativeAdView

class UnifiedNativeAdViewHolder(view: View): RecyclerView.ViewHolder(view) {

    var adView: UnifiedNativeAdView = view.findViewById(R.id.ad_view)

    init {
        // The MediaView will display a video asset if one is present in the ad,
        // and the first image asset otherwise.
        adView.mediaView = adView.findViewById(R.id.ad_media)

        // Register the view used for each individual asset.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)
    }
}
