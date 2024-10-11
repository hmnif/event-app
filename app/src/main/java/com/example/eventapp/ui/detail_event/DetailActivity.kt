package com.example.eventapp.ui.detail_event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.eventapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailEventViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = args.idEvent
        viewModel.getDetailEvent(id)

        setContentView(binding.root)



        viewModel.detailEvent.observe(this) { it ->
            supportActionBar?.title = it.event?.name
            val event = it.event
            if (event?.link != null) {
                binding.buttonRegister.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(event.link)))
                }
            }
            binding.tvOwnerDetailEvent.text = "Diselenggarakan oleh ${it.event?.ownerName}"
            binding.tvTitleDetailEvent.text = event?.name
            val registrants = event?.registrants
            val quota = event?.quota
            binding.tvQuotaDetailEvent.text = "Sisa kuota: ${quota?.minus(registrants!!)}"
            binding.tvBeginTimeDetailEvent.text = "Dimulai pada: ${event?.beginTime}"
            binding.tvDescDetailEvent.text =
                HtmlCompat.fromHtml(event?.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            Glide.with(this)
                .load(event?.mediaCover)
                .into(binding.ivCoverDetailEvent)
        }


        viewModel.loading.observe(this) {
            if (it) {
                binding.progressBarDetailEvent.visibility = View.VISIBLE
                binding.scrollView2.visibility = View.GONE
            } else {
                binding.progressBarDetailEvent.visibility = View.GONE
                binding.scrollView2.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}