package com.example.examen_ib

class Registers {
    companion object{
        var arregloPanaderiasPanes = arrayListOf<PanaderiasPanes>()

        init{
            //Panaderias
            PanaderiaBDD.TablaPanaderia!!.crearPanaderia(1,"Rosa's cakes","El Ejido","Si","450.50","2020")

            PanaderiaBDD.TablaPanaderia!!.crearPanaderia(2,"Happy Pancakes","Alameda","No","500.75","2019")

            // Panes
            PanaderiaBDD.TablaPanaderia!!.crearPan(1,"Pan de chocolate","Americano","Si","0.50","15")

            PanaderiaBDD.TablaPanaderia!!.crearPan(2,"Cachito con queso","Ecuatoriano","No","0.25","30")


            // Panaderias & Panes
            arregloPanaderiasPanes.add(PanaderiasPanes(1,1,1))

            arregloPanaderiasPanes.add(PanaderiasPanes(2,1,2))

            arregloPanaderiasPanes.add(PanaderiasPanes(3,2,2))


        }
    }
}