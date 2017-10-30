package model
import Types._

class Item (val slotsRequeridos: List[Slot],
    val restricciones: List[Restriccion],
    val modificaciones: List[ModificacionStat],
    val precio: Int){
  
  def puedeEquiparseEn(heroe: Heroe) = restricciones.forall(_.apply(heroe))
  
}