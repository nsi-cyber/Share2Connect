package com.example.share2connect.Utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.share2connect.R
import java.io.ByteArrayOutputStream

class Helper
{
    companion object {
        fun toBitmap(data:ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(data, 0, data.size)
        }

        fun imageToBitmap(image: ImageView): Array<Byte> {
            val drawable = image.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 1, stream)
                return stream.toByteArray().toTypedArray()
            } else if (drawable is VectorDrawable) {
                val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 1, stream)
                return stream.toByteArray().toTypedArray()
            }
            throw IllegalArgumentException("Unsupported drawable type")
        }

        fun changeFragment(fragment: Fragment,activity:FragmentManager) {
            val fragmentTransaction = activity?.beginTransaction()
            fragmentTransaction?.replace(R.id.frameLayout, fragment)
            fragmentTransaction?.commit()
        }

    }
}