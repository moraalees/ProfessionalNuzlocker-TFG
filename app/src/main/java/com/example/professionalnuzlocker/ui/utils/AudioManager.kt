package com.example.professionalnuzlocker.ui.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import com.example.professionalnuzlocker.R

class AudioManager(private val context: Context) {
    private var isUserPaused = false
    private var isAppInBackground = false
    private val soundPool: SoundPool
    private val soundMap = mutableMapOf<GameSound, Int>()
    private var bgPlayer: MediaPlayer? = null
    private var battlePlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private var fadeToken = 0

    companion object {
        private const val FADE_MS = 1000L
        private const val FADE_STEPS = 10
        private const val BG_VOLUME = 0.3f
    }

    init {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(attrs)
            .build()

        soundMap[GameSound.CLICK] = soundPool.load(context, R.raw.click, 1)
        soundMap[GameSound.LEVEL_UP] = soundPool.load(context, R.raw.subida_nivel, 1)
        soundMap[GameSound.CAPTURE] = soundPool.load(context, R.raw.captura, 1)
    }

    fun playSound(sound: GameSound) {
        val id = soundMap[sound] ?: return
        soundPool.play(id, 1f, 1f, 1, 0, 1f)
        if (sound == GameSound.CAPTURE) duckBgForCapture()
    }

    private fun duckBgForCapture() {
        val bg = bgPlayer ?: return
        if (!bg.isPlaying) return
        try { bg.setVolume(0.04f, 0.04f) } catch (_: Exception) {}
        handler.postDelayed({
            if (!isUserPaused && !isAppInBackground) {
                try { bg.setVolume(BG_VOLUME, BG_VOLUME) } catch (_: Exception) {}
            }
        }, 2500)
    }

    fun startAmbientMusic() {
        isAppInBackground = false
        if (bgPlayer == null) {
            if (!isUserPaused) {
                bgPlayer = createBgPlayer().also { it.start() }
            }
        } else if (!isUserPaused) {
            bgPlayer?.start()
        }
    }

    fun pauseAmbientMusic() {
        isAppInBackground = true
        bgPlayer?.pause()
    }

    fun resumeAmbientMusic() {
        isAppInBackground = false
        if (!isUserPaused) bgPlayer?.start()
    }

    fun toggleAmbientMusic() {
        if (bgPlayer == null) return
        isUserPaused = !isUserPaused
        if (isUserPaused) {
            bgPlayer?.pause()
        } else if (!isAppInBackground) {
            bgPlayer?.start()
        }
    }
    fun isMusicPaused() = isUserPaused

    fun crossfadeToBattle() {
        if (isUserPaused) return
        val token = ++fadeToken

        battlePlayer?.release()
        battlePlayer = MediaPlayer.create(context, R.raw.batalla).apply {
            setVolume(0f, 0f)
            setOnCompletionListener {
                if (fadeToken == token) crossfadeToAmbient()
            }
            start()
            fadeIn(this, 1f, FADE_MS, token)
        }

        val bg = bgPlayer
        if (bg != null && bg.isPlaying) {
            fadeOut(bg, BG_VOLUME, FADE_MS, token) {
                bg.pause()
            }
        }
    }

    fun crossfadeToAmbient() {
        val token = ++fadeToken
        if (!isUserPaused && !isAppInBackground) {
            val bg = bgPlayer
            if (bg != null) {
                bg.setVolume(0f, 0f)
                bg.start()
                fadeIn(bg, BG_VOLUME, FADE_MS, token)
            } else {
                bgPlayer = createBgPlayer().apply {
                    setVolume(0f, 0f)
                    start()
                    fadeIn(this, BG_VOLUME, FADE_MS, token)
                }
            }
        }

        val battle = battlePlayer
        if (battle != null && battle.isPlaying) {
            fadeOut(battle, 1f, FADE_MS, token) {
                if (fadeToken == token) {
                    battle.release()
                    battlePlayer = null
                }
            }
        } else {
            battle?.release()
            battlePlayer = null
        }
    }

    private fun createBgPlayer(): MediaPlayer =
        MediaPlayer.create(context, R.raw.lobby1).apply {
            setVolume(BG_VOLUME, BG_VOLUME)
            setOnCompletionListener {
                val myToken = fadeToken
                handler.postDelayed({
                    if (fadeToken == myToken && !isUserPaused && !isAppInBackground) {
                        start()
                    }
                }, 3000)
            }
        }

    private fun fadeOut(
        player: MediaPlayer,
        fromVol: Float,
        durationMs: Long,
        token: Int, onDone: () -> Unit
    ) {
        val stepMs = durationMs / FADE_STEPS
        for (i in 1..FADE_STEPS) {
            handler.postDelayed({
                if (fadeToken == token) {
                    val vol = fromVol * (1f - i.toFloat() / FADE_STEPS)
                    try { player.setVolume(vol, vol) } catch (_: Exception) {}
                    if (i == FADE_STEPS) onDone()
                }
            }, i * stepMs)
        }
    }

    private fun fadeIn(
        player: MediaPlayer,
        toVol: Float,
        durationMs: Long,
        token: Int
    ) {
        val stepMs = durationMs / FADE_STEPS
        for (i in 1..FADE_STEPS) {
            handler.postDelayed({
                if (fadeToken == token) {
                    val vol = toVol * (i.toFloat() / FADE_STEPS)
                    try { player.setVolume(vol, vol) } catch (_: Exception) {}
                }
            }, i * stepMs)
        }
    }
}
