package com.app.lastplayer.data.remote

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Album(
    val id: String = "",

    val name: String = "",

    @SerializedName("releasedate")
    val releaseDate: String = "",

    @SerializedName("artist_id")
    val authorId: String = "",

    @SerializedName("artist_name")
    val authorName: String = "",

    val image: String = "",

    @SerializedName("zip")
    val downloadArchiveUrl: String = "",

    @SerializedName("shorturl")
    val shortUrl: String = "",

    @SerializedName("shareurl")
    val shareUrl: String = "",

    @SerializedName("zip_allowed")
    val isDownloadable: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(releaseDate)
        parcel.writeString(authorId)
        parcel.writeString(authorName)
        parcel.writeString(image)
        parcel.writeString(downloadArchiveUrl)
        parcel.writeString(shortUrl)
        parcel.writeString(shareUrl)
        parcel.writeByte(if (isDownloadable) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }

}