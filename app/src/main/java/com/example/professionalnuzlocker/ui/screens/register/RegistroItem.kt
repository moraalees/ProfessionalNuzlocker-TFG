package com.example.professionalnuzlocker.ui.screens.register

import com.example.professionalnuzlocker.data.model.CombateImportante
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas

sealed class RegistroItem {
    data class RutaItem(val ruta: Rutas) : RegistroItem()
    data class CombateItem(val combate: CombateImportante) : RegistroItem()
}
