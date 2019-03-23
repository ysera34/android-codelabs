package org.inframincer.nativerecyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.ads.MobileAds
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    // List of MenuItems that populate the RecyclerView.
    private val recyclerViewItems = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Create new fragment to display a progress spinner while the data set for the
            // RecyclerView is populated.
            val loadingScreenFragment = LoadingScreenFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, loadingScreenFragment)

            // Commit the transaction.
            transaction.commit()

            // Update the RecyclerView item's list with menu items.
            addMenuItemsFromJson()

            loadMenu()
        }

        // Initialize the Google Mobile Ads SDK
        MobileAds.initialize(
            applicationContext,
            getString(R.string.admob_app_id)
        )
    }

    fun getRecyclerViewItems(): MutableList<Any> {
        return recyclerViewItems
    }

    private fun loadMenu() {
        // Create new fragment and transaction
        val newFragment = RecyclerViewFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }

    /**
     * Adds [MenuItem]'s from a JSON file.
     */
    private fun addMenuItemsFromJson() {
        try {
            val jsonDataString = readJsonDataFromFile()
            val menuItemsJsonArray = JSONArray(jsonDataString)

            for (i in 0 until menuItemsJsonArray.length()) {

                val menuItemObject = menuItemsJsonArray.getJSONObject(i)

                val menuItemName = menuItemObject.getString("name")
                val menuItemDescription = menuItemObject.getString("description")
                val menuItemPrice = menuItemObject.getString("price")
                val menuItemCategory = menuItemObject.getString("category")
                val menuItemImageName = menuItemObject.getString("photo")

                val menuItem = MenuItem(
                    menuItemName, menuItemDescription, menuItemPrice,
                    menuItemCategory, menuItemImageName
                )
                recyclerViewItems.add(menuItem)
            }
        } catch (exception: IOException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        }

    }

    /**
     * Reads the JSON file and converts the JSON data to a [String].
     *
     * @return A [String] representation of the JSON data.
     * @throws IOException if unable to read the JSON file.
     */
    @Throws(IOException::class)
    private fun readJsonDataFromFile(): String {

        var inputStream: InputStream? = null
        val builder = StringBuilder()

        try {
            inputStream = resources.openRawResource(R.raw.menu_items_json)

            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            var jsonDataString = bufferedReader.readLine()
            while (jsonDataString != null) {
                builder.append(jsonDataString)
                jsonDataString = bufferedReader.readLine()
            }
        } finally {
            if (inputStream != null) {
                inputStream!!.close()
            }
        }

        return String(builder)
    }
}
