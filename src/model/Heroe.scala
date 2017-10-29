package model

case class Heroe(
    stats: Map[Stat, Int] = Map[Stat, Int](HP -> 100, Fuerza -> 100, Velocidad -> 100, Inteligencia -> 100),
    slots: List[Slot] = List(Cabeza, Torso, Mano, Mano),
    inventario: List[Item] = List(),
    trabajo: Trabajo = Ninguno
    ) {
  
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
    modificaciones.foldLeft(this) {(heroe, modificacion) => 
      modificacion match {
        case VariarStatEn(stat, valor)                           => heroe.incrementarStatBase(stat, valor)
        case SetearStat(stat, valor)                             => heroe.setStatBase(stat, valor)
        case IncrementarStatsEnPorcentajeDePrincipal(porcentaje) => {
          val incremento = (valorStatPrincipal() * porcentaje).round
          stats.keys.foldLeft(heroe) {(heroe, stat) => heroe.incrementarStatBase(stat, incremento)}
        }
        //otro case
        }
      }
  }
  
  def valorStatPrincipal() = getStat(trabajo.statPrincipal)

  def equipar(item: Item): Heroe = {
    if (item.puedeEquiparseEn(this)){
      val itemsADesequipar = null// = inventario.filter(_.requiereSlot) VER COMO LO OBTENEMOS (!!!)
      val nuevoInventario = inventario.diff(itemsADesequipar) ++ List(item)
      return copy(inventario = nuevoInventario)
    } else {
      throw new Exception("No puede equiparse el item") //VER SI CONVIENE QUE ROMPA O NO
    }
  }

  def desequipar(item: Item) = copy(inventario = inventario.diff(List(item)))

}
