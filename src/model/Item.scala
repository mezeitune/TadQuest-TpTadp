package model

class Item (val slotsRequeridos: List[String], val restricciones: List[(Heroe=>Boolean)], val modificaciones: List[ModificacionStat]){
  
  def puedeEquiparseEn(heroe: Heroe) = restricciones.forall(_.apply(heroe))
  
  def aplicarModificacionesA(nombreStat: String, valorInicial: Int) = {
    val modificacionesAlStat = modificaciones.filter({_.stat == nombreStat})
    modificacionesAlStat.foldLeft(valorInicial) ((valor, modificacion) => modificacion.aplicarA(valor))
  }
  
}