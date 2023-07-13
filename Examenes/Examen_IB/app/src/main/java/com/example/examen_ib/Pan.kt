package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class Pan(
    var  idPan: Int,
    var nombre: String?,
    var origen: String?,
    var esDulce: String?,
    var precio: Double?,
    var stock: Int?

) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idPan)
        parcel.writeString(nombre)
        parcel.writeString(origen)
        parcel.writeString(esDulce)
        parcel.writeDouble(precio!!)
        parcel.writeInt(stock!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${nombre}"
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