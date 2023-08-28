package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class PanaderiasPanes(
    val idPanaderiaPan: Int,
    val idPanaderia: Int,
    val idPan: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idPanaderiaPan)
        parcel.writeInt(idPanaderia)
        parcel.writeInt(idPan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PanaderiasPanes> {
        override fun createFromParcel(parcel: Parcel): PanaderiasPanes {
            return PanaderiasPanes(parcel)
        }

        override fun newArray(size: Int): Array<PanaderiasPanes?> {
            return arrayOfNulls(size)
        }
    }
}