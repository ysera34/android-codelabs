package org.inframincer.implicitintents

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ShareCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        open_website_button.setOnClickListener {
            val url = website_edittext.text.toString()
            val webPage = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            } ?: Log.d("ImplicitIntents", "Can't handle this intent!")
        }
        open_location_button.setOnClickListener {
            val loc = location_edittext.text.toString()
            val addressUri = Uri.parse("geo:0,0?q=$loc")
            val intent = Intent(Intent.ACTION_VIEW, addressUri)
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            } ?: Log.d("ImplicitIntents", "Can't handle this intent!")
        }
        share_text_button.setOnClickListener {
            val txt = share_edittext.text.toString()
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this@MainActivity)
                .setText(mimeType)
                .setChooserTitle("Share this text with: ")
                .setText(txt)
                .startChooser()
        }

        take_picture_action_view.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_VIEW)
            handleTakePicture(takePictureIntent)
        }
        // Gallery
        take_picture_action_main.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_MAIN)
            handleTakePicture(takePictureIntent)
        }
        // Camera
        take_picture_media_store_action_image_capture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            handleTakePicture(takePictureIntent)
        }
        take_picture_action_get_content.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_GET_CONTENT)
            handleTakePicture(takePictureIntent)
        }
    }

    private fun handleTakePicture(intent: Intent) {
        intent.resolveActivity(packageManager)?.let {
            startActivity(intent)
        } ?: Log.d("ImplicitIntents", "Can't handle this intent!")
    }
}
