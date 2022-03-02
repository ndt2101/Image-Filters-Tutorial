package com.tuan2101.imagefilters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tuan2101.imagefilters.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                data?.data?.let { uri ->
                    Intent(applicationContext, EditingNewImageActivity::class.java).also { editNewImageIntent ->
                        editNewImageIntent.putExtra(Constants.IMAGE_URI, uri)
                        startActivity(editNewImageIntent)
                    }
                }
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()
    }

    private fun setListener() {
        binding.editNewImage.setOnClickListener {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // xin quyen truy cap
                launcher.launch(it)
            }
        }
    }
}