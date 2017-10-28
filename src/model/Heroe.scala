package model



class Heroe(
    val stats: Map[String, Int] = Map[String, Int]("hp" -> 100, "fuerza" -> 100, "velocidad" -> 100, "inteligencia" -> 100),
    val slots: List[String] = List("cabeza", "torso", "mano", "mano"),
    val inventario: List[Item] = List(),
    val trabajo: Trabajo = ninguno
    ) {
  
  def copy(stats: Map[String, Int] = Map[String, Int]("hp" -> 100, "fuerza" -> 100, "velocidad" -> 100, "inteligencia" -> 100),
    slots: List[String] = List("cabeza", "torso", "mano", "mano"),
    inventario: List[Item] = List(),
    trabajo: Trabajo = ninguno) = new Heroe(stats,slots,inventario,trabajo)
  
  def trabajo(nuevoTrabajo: Trabajo) = copy(trabajo = nuevoTrabajo)

  def getStat(nombreStat: String): Int = {
    val modificacionesAAplicar = trabajo.modificaciones ++ inventario.flatMap(_.modificaciones)
    1.max(aplicarModificaciones(modificacionesAAplicar).getStatBase(nombreStat))
  }
    
  def getStatBase(nombreStat: String) = {
    stats(nombreStat)
  }
    
  def incrementarStatBase(nombreStat: String, valor: Int) = {
    setStatBase(nombreStat, getStatBase(nombreStat) + valor)
  }
  
  def setStatBase(nombreStat: String, valor: Int) = {
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


case class Guerrero(
    override val stats: Map[String, Int] = Map[String, Int]("hp" -> 100, "fuerza" -> 100, "velocidad" -> 100, "inteligencia" -> 100),
     override val slots: List[String] = List("cabeza", "torso", "mano", "mano"),
     override val inventario: List[Item] = List(),
     override val trabajo: Trabajo = ninguno
    ) extends Heroe