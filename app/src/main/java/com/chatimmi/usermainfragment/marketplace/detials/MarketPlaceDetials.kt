package com.chatimmi.usermainfragment.marketplace.detials

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityMarktePlaceDetialsBinding
import com.chatimmi.usermainfragment.group.PagerAdapterGroup
import com.chatimmi.usermainfragment.marketplace.detials.consultant.ConsultantFragment
import com.chatimmi.usermainfragment.marketplace.detials.school.SchoolFragment
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


@Suppress("DEPRECATION")
class MarketPlaceDetials : BaseActivitykt(), Player.EventListener {
    lateinit var binding: ActivityMarktePlaceDetialsBinding

    private var exoPlayer: SimpleExoPlayer? = null
    private var playbackPosition = 0L
    private val sourceUrl: String = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    private val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_markte_place_detials)
        val w = window
        w.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.ivBack.setOnClickListener(){
            onBackPressed()
        }
        createVm()
    }
    private fun createVm() {
        val listOfFragments = listOf(
                SchoolFragment.newInstance("1"),
                ConsultantFragment.newInstance("2")
        )

        val viewPagerAdapter = MarketPlaceDetialsPagerAdapter(supportFragmentManager, listOfFragments as List<Fragment>, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.pager.adapter = viewPagerAdapter
        binding.pager.offscreenPageLimit = 2
        binding.tabCategory.setupWithViewPager(binding.pager)
    }
    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun changeScreenOrientation() {
        val orientation: Int = this@MarketPlaceDetials.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            var params = binding.exoPlay.layoutParams as RelativeLayout.LayoutParams
            params.height = resources.getDimension(R.dimen._300sdp).toInt()
            binding.exoPlay.layoutParams = params
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            binding.exoPlay.layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT
        }


    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(baseContext, DefaultRenderersFactory(this),
                DefaultTrackSelector(adaptiveTrackSelectionFactory),
                DefaultLoadControl())
        preparePlayer()

        var orientationIcon = findViewById<ImageView>(R.id.ivImage)
        orientationIcon.setOnClickListener() {
            changeScreenOrientation()
        }
        binding.exoPlay.player = exoPlayer
        exoPlayer!!.seekTo(playbackPosition)
        exoPlayer!!.playWhenReady = true
        exoPlayer!!.addListener(this)
    }

    private fun releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer!!.getContentPosition()
            exoPlayer!!.release()
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("us")
        return ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(DefaultExtractorsFactory())
                .createMediaSource(uri)
    }

    private fun preparePlayer() {
        val uri = Uri.parse(sourceUrl)
        val mediaSource = buildMediaSource(uri)
        exoPlayer!!.prepare(mediaSource)
    }


/*    fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}

    fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {}

    fun onLoadingChanged(isLoading: Boolean) {}

    fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        updateProgressBar()
        if (playbackState == Player.STATE_BUFFERING) binding.progressBar.setVisibility(View.VISIBLE) else if (playbackState == Player.STATE_READY) binding.progressBar.setVisibility(View.GONE)
    }*/

    override fun onRepeatModeChanged(repeatMode: Int) {}

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}

    override fun onPlayerError(error: ExoPlaybackException) {}

    override fun onPositionDiscontinuity(reason: Int) {}

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
}