package com.kokoro.ai.audio

import android.content.Context
import android.media.MediaPlayer
import com.kokoro.ai.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KokoroAudioManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var progressJob: Job? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    init {
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(context, R.raw.kokoro_lofi).apply {
                isLooping = true
                setOnPreparedListener {
                    // Ready to play
                }
                setOnCompletionListener {
                    _isPlaying.value = false
                    _progress.value = 0f
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun play() {
        try {
            if (mediaPlayer == null) {
                initMediaPlayer()
            }
            mediaPlayer?.start()
            _isPlaying.value = true
            startProgressTracker()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pause() {
        try {
            mediaPlayer?.pause()
            _isPlaying.value = false
            stopProgressTracker()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggle() {
        if (_isPlaying.value) {
            pause()
        } else {
            play()
        }
    }

    fun seekTo(fraction: Float) {
        val player = mediaPlayer ?: return
        val duration = player.duration
        if (duration > 0) {
            val target = (duration * fraction.coerceIn(0f, 1f)).toInt()
            player.seekTo(target)
            _progress.value = fraction
        }
    }

    private fun startProgressTracker() {
        stopProgressTracker()
        progressJob = scope.launch {
            while (isActive && _isPlaying.value) {
                mediaPlayer?.let { player ->
                    val duration = player.duration
                    val current = player.currentPosition
                    if (duration > 0) {
                        _progress.value = current.toFloat() / duration.toFloat()
                    }
                }
                delay(300)
            }
        }
    }

    private fun stopProgressTracker() {
        progressJob?.cancel()
        progressJob = null
    }

    fun release() {
        stopProgressTracker()
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        scope.cancel()
    }
}
