package com.example.professionalnuzlocker.ui.screens.register

import com.example.professionalnuzlocker.data.model.CombateImportante
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas

/** Elemento de la lista de registro: puede ser una ruta de captura o un combate importante. */
sealed class RegistroItem {
    /** Elemento de ruta con el enum [Rutas] asociado. */
    data class RutaItem(val ruta: Rutas) : RegistroItem()
    /** Elemento de combate con los datos del [CombateImportante] a registrar. */
    data class CombateItem(val combate: CombateImportante) : RegistroItem()
}
