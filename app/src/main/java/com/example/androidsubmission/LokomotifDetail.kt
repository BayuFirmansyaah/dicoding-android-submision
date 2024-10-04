package com.example.androidsubmission

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream

class LokomotifDetail : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val DETAIL_LOKOMOTIF = "detail_lokomotif"
    }

    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var image: ImageView
    private lateinit var shareButton: Button
    private var lokomotif: Lokomotif? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokomotif_detail)

        title = findViewById(R.id.title_lokomotif)
        description = findViewById(R.id.desc_lokomotif)
        image = findViewById(R.id.image_lokomotif)
        shareButton = findViewById(R.id.share_button)

        shareButton.setOnClickListener(this)

        lokomotif = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DETAIL_LOKOMOTIF, Lokomotif::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_LOKOMOTIF)
        }

        lokomotif?.let {
            title.text = it.name
            description.text = it.description
            Glide.with(this)
                .load(it.image)
                .into(image)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.share_button -> {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "image/*"
                    putExtra(Intent.EXTRA_TEXT, "${lokomotif?.name} -${lokomotif?.description}")
                    putExtra(Intent.EXTRA_STREAM, getContentUri())
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(Intent.createChooser(shareIntent, "Bagikan Dengan "))
            }
        }
    }

    private fun getContentUri(): Uri? {
        val bitmap: Bitmap? = (image.drawable as? BitmapDrawable)?.bitmap ?: drawableToBitmap(image.drawable)

        if (bitmap == null) {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
            return null
        }

        val imagesFolder = File(cacheDir, "images")
        var contentUri: Uri? = null

        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
            }
            contentUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
        } catch (e: Exception) {
            Log.e("LokomotifDetail", "Error saving image: ${e.message}", e)
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

        return contentUri
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}
