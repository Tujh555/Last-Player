package com.app.lastplayer.media

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.app.lastplayer.appComponent
import dagger.Lazy
import java.lang.Exception
import javax.inject.Inject

object MediaStyleHelper {
    fun from(
        context: Context,
        mediaSession: MediaSessionCompat?,
        channelId: String
    ): NotificationCompat.Builder = mediaSession?.controller?.let { controller ->
        val mediaMetadata = controller.metadata
        val description = try {
            mediaMetadata.description
        } catch (e: Exception) {
            context.appComponent.metadataBuilder
                .putString(
                    MediaMetadataCompat.METADATA_KEY_TITLE,
                    "Unknown"
                ).putString(
                    MediaMetadataCompat.METADATA_KEY_ALBUM,
                    "Unknown Album"
                ).putString(
                    MediaMetadataCompat.METADATA_KEY_ARTIST,
                    "Unknown authorName"
                ).putLong(
                    MediaMetadataCompat.METADATA_KEY_DURATION,
                    3000L
                ).build().description
        }

        NotificationCompat.Builder(context, channelId)
            .setContentTitle(description.title)
            .setContentText(description.subtitle)
            .setContentIntent(controller.sessionActivity)
            .setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
            ).setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    } ?: NotificationCompat.Builder(context, channelId)
}