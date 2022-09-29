package com.example.musicplayer2.adapter

import android.os.Parcel
import android.os.Parcelable

data class MusicData(val img:String? = null,val title:String? = null,val url:String? = null,val id:String? = null,val band:String? = null): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(img)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(id)
        parcel.writeString(band)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicData> {
        override fun createFromParcel(parcel: Parcel): MusicData {
            return MusicData(parcel)
        }

        override fun newArray(size: Int): Array<MusicData?> {
            return arrayOfNulls(size)
        }
    }
}
