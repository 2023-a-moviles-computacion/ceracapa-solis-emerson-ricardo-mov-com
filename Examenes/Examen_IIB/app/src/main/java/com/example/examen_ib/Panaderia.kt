package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class Panaderia(
    var idPanaderia: Int,
    var nombrePanaderia: String?,
    var ubicacionPanaderia: String?,
    var esCafeteria: String?,
    var arriendoPanaderia: Double?,
    var anioFundacion: Int?
) :Parcelable{
    override fun toString(): String {
        return "${nombrePanaderia}"
    }

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
        parcel.writeInt(idPanaderia)
        parcel.writeString(nombrePanaderia)
        parcel.writeString(ubicacionPanaderia)
        parcel.writeString(esCafeteria)
        parcel.writeDouble(arriendoPanaderia!!)
        parcel.writeInt(anioFundacion!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Panaderia> {
        override fun createFromParcel(parcel: Parcel): Panaderia {
            return Panaderia(parcel)
        }

        override fun newArray(size: Int): Array<Panaderia?> {
            return arrayOfNulls(size)
        }
    }
}