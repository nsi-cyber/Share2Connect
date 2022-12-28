package com.example.share2connect.Utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
    fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

        fun changeFragment(fragment: Fragment,activity:FragmentManager) {
            val fragmentTransaction = activity?.beginTransaction()
            fragmentTransaction?.replace(R.id.frameLayout, fragment)
            fragmentTransaction?.commit()
        }

    }
}