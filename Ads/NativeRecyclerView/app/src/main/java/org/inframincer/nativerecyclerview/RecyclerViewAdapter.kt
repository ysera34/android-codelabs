package org.inframincer.nativerecyclerview

import android.support.v7.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView

// A menu item view type.
private const val MENU_ITEM_VIEW_TYPE = 0

// The unified native ad view type.
private const val UNIFIED_NATIVE_AD_VIEW_TYPE = 1

class RecyclerViewAdapter(val context: Context, val recyclerViewItems: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * The [MenuItemViewHolder] class.
     * Provides a reference to each view in the menu item view.
     */
    inner class MenuItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal val menuItemName: TextView
        internal val menuItemDescription: TextView
        internal val menuItemPrice: TextView
        internal val menuItemCategory: TextView
        internal val menuItemImage: ImageView

        init {
            menuItemImage = view.findViewById(R.id.menu_item_image)
            menuItemName = view.findViewById(R.id.menu_item_name)
            menuItemPrice = view.findViewById(R.id.menu_item_price)
            menuItemCategory = view.findViewById(R.id.menu_item_category)
            menuItemDescription = view.findViewById(R.id.menu_item_description)
        }
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    /**
     * Determines the view type for the given position.
     */
    override fun getItemViewType(position: Int): Int {
        val recyclerViewItem = recyclerViewItems[position]
        if (recyclerViewItem is UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE
        }
        return MENU_ITEM_VIEW_TYPE
    }

    /**
     * Creates a new view for a menu item view. This method is invoked by the layout manager.
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val unifiedNativeAdView = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.ad_unified, viewGroup, false)
                UnifiedNativeAdViewHolder(unifiedNativeAdView)
            }
            else -> { // include MENU_ITEM_VIEW_TYPE
                val menuItemLayoutView = LayoutInflater.from(viewGroup.context)
                    .inflate(
                        R.layout.menu_item_container, viewGroup, false
                    )
                MenuItemViewHolder(menuItemLayoutView)
            }
        }

    /**
     * Replaces the content in the views that make up the menu item view. This method is invoked
     * by the layout manager.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val nativeAd = recyclerViewItems[position] as UnifiedNativeAd
                populateNativeAdView(nativeAd, (holder as UnifiedNativeAdViewHolder).adView)
            }
            else -> { // include MENU_ITEM_VIEW_TYPE
                val menuItemHolder = holder as MenuItemViewHolder
                val (name, description, price, category, imageName) = recyclerViewItems[position] as MenuItem

                // Get the menu item image resource ID.
                val imageResID = context.resources.getIdentifier(
                    imageName, "drawable",
                    context.packageName
                )

                // Add the menu item details to the menu item view.
                menuItemHolder.menuItemName.text = name
                menuItemHolder.menuItemDescription.text = description
                menuItemHolder.menuItemPrice.text = price
                menuItemHolder.menuItemCategory.text = category
                menuItemHolder.menuItemImage.setImageResource(imageResID)
            }
        }
    }

    private fun populateNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as? TextView)?.text = nativeAd.headline
        (adView.bodyView as? TextView?)?.text = nativeAd.body
        (adView.callToActionView as? Button)?.text = nativeAd.callToAction

        // These assets aren't guaranteed to be in every UnifiedNativeAd,
        // so it's important to check before trying to display them.
        val icon = nativeAd.icon
        if (icon == null) {
            adView.iconView.visibility = View.INVISIBLE
        } else {
            (adView.iconView as ImageView).setImageDrawable(icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd)
    }
}
