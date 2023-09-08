package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class Pan(
    var idPan: String?,
    var idPanaderia_Pan: String?,
    var nombrePan: String?,
    var origenPan: String?,
    var esDulce: String?,
    var precioPan: Double?,
    var stockPan: Int?

) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idPan)
        parcel.writeString(idPanaderia_Pan)
        parcel.writeString(nombrePan)
        parcel.writeString(origenPan)
        parcel.writeString(esDulce)
        parcel.writeDouble(precioPan!!)
        parcel.writeInt(stockPan!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${nombrePan}"
    }

    companion object CREATOR : Parcelable.Creator<Pan> {
        override fun createFromParcel(parcel: Parcel): Pan {
            return Pan(parcel)
        }

        override fun newArray(size: Int): Array<Pan?> {
            return arrayOfNulls(size)
        }
    }

}