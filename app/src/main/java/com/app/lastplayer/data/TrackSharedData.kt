package com.app.lastplayer.data

import android.os.Parcel
import android.os.Parcelable

class TrackSharedData(
    val imageUrl: String = "",
    val trackName: String = "",
    val authorName: String = "",
    val trackUrl: String = "",
    val duration: String = "",
    val albumName: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(trackName)
        parcel.writeString(authorName)
        parcel.writeString(trackUrl)
        parcel.writeString(duration)
        parcel.writeString(albumName)
    }

    override fun describeContents() = 0

    override fun toString() = "TrackData($trackName, $authorName) "

    companion object CREATOR : Parcelable.Creator<TrackSharedData> {
        override fun createFromParcel(parcel: Parcel): TrackSharedData {
            return TrackSharedData(parcel)
        }

        override fun newArray(size: Int): Array<TrackSharedData?> {
            return arrayOfNulls(size)
        }
    }
}