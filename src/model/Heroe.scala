package model
import scala.util.{ Try, Success, Failure }

sealed trait Stat

case object HP extends Stat
case object Velocidad extends Stat
case object Fuerza extends Stat
case object Inteligencia extends Stat


sealed trait Slot

case object Cabeza extends Slot
case object Torso extends Slot
case object ManoIzq extends Slot
case object ManoDer extends Slot

case class Heroe(
    stats: Map[Stat, Int] = Map[Stat, Int](HP -> 100, Fuerza -> 100, Velocidad -> 100, Inteligencia -> 100),
    inventario: List[Item] = List(),
    trabajo: Trabajo = Ninguno
    ) {
  require(stats.keys.sameElements(List(HP,Fuerza,Velocidad,Inteligencia)))//EN REALIDAD EL ORDEN NO IMPORTARIA, PERO MEH
  //HAY RESTRICCIONES PARA LOS VALORES QUE PUEDE TOMAR UN STAT BASE ???
  
  def slots: List[Slot] = List(Cabeza, Torso, ManoIzq, ManoDer)
  
  def trabajo(nuevoTrabajo: Trabajo) = copy(trabajo = nuevoTrabajo)

  def getStat(nombreStat: Stat): Int = {
    val modificacionesAAplicar = trabajo.modificaciones ++ inventario.flatMap(_.modificaciones)
    1.max(aplicarModificaciones(modificacionesAAplicar).getStatBase(nombreStat))
  }
    
  def getStatBase(nombreStat: Stat) = {
    stats(nombreStat)
  }
    
  def incrementarStatBase(nombreStat: Stat, valor: Int) = {
    setStatBase(nombreStat, getStatBase(nombreStat) + valor)
  }
  
  def setStatBase(nombreStat: Stat, valor: Int) = {
    val nuevosStats = stats + (nombreStat -> valor)
    copy(stats = nuevosStats)
  }
   
  def aplicarModificaciones(modificaciones: List[Modificacion]) = {
    modificaciones.foldLeft(this) {(heroe, modificacion) => modificacion(heroe)}
    //SE PODRÍAN ORDENAR LAS MODIFICACIONES EN BASE A SU TIPO (PARA QUE LAS ABSORBENTES VAYAN AL FINAL) (!!!)
  }
  
  def valorStatPrincipal() = getStat(trabajo.statPrincipal)

  def equipar(item: Item): Try[Heroe] = {
    if (item.puedeEquiparseEn(this)){
      val itemsADesequipar = inventario.filter(unItem => unItem.slotsRequeridos.exists(slot => item.slotsRequeridos.contains(slot)))//DESEQUIPA AMBAS MANOS EN VEZ DE UNA SOLA (!!!)
      val nuevoInventario = inventario.diff(itemsADesequipar) ++ List(item)
      return Success(copy(inventario = nuevoInventario))
    } else {
      return Failure(new NoSePuedeEquiparItemError(item, this))
    }
  }

  def desequipar(item: Item) = copy(inventario = inventario.diff(List(item)))

}

class NoSePuedeEquiparItemError(val item: Item, val heroe: Heroe) extends RuntimeException