package org.inframincer.nativerecyclerview

import android.support.v7.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.formats.UnifiedNativeAd

// A menu item view type.
private const val MENU_ITEM_VIEW_TYPE = 0

// The unified native ad view type.
private const val UNIFIED_NATIVE_AD_VIEW_TYPE = 1

class RecyclerViewAdapter(val context: Context, val recyclerViewItems: List<Any>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                    .inflate(R.layout.menu_item_container, viewGroup, false
                )
                MenuItemViewHolder(menuItemLayoutView)
            }
        }

    /**
     * Replaces the content in the views that make up the menu item view. This method is invoked
     * by the layout manager.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val menuItemHolder = holder as MenuItemViewHolder
        val (name, description, price, category, imageName) = recyclerViewItems[position] as MenuItem

        // Get the menu item image resource ID.
        val imageResID = context.getResources().getIdentifier(
            imageName, "drawable",
            context.getPackageName()
        )

        // Add the menu item details to the menu item view.
        menuItemHolder.menuItemName.text = name
        menuItemHolder.menuItemDescription.text = description
        menuItemHolder.menuItemPrice.text = price
        menuItemHolder.menuItemCategory.text = category
        menuItemHolder.menuItemImage.setImageResource(imageResID)
    }
}