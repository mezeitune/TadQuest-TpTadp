package model

// ORDENADOR (!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!)

sealed trait Modificacion extends Ordered[Modificacion]{
  val prioridad: PrioridadModificacion
  def apply(heroe: Heroe): Heroe
  def compare(that: Modificacion) = this.prioridad.compare(that.prioridad)
}

sealed trait ModificacionStat extends Modificacion

case class ModificarStatPorCantidadItems(stat: Stat, valor: Int) extends ModificacionStat {
  val prioridad = BAJA
  def apply(heroe: Heroe) = {
    val cantItems = heroe.inventario.size
    val valorAumentado = cantItems * valor
    heroe.incrementarStatBase(stat, valorAumentado)
  }
}

case class SetearStatSegun(statAModificar: Stat , stat: Stat) extends ModificacionStat {
  val prioridad = BAJA
  def apply(heroe: Heroe) = {
    heroe.setStatBase(stat, heroe.stats.get(stat).get)
  }
}

case class ModificarTodosLosStats(valor: Int) extends ModificacionStat {
  val prioridad = BAJA
  def apply(heroe: Heroe) = {
    heroe.stats.keys.foldLeft(heroe) {(heroe,stat) => heroe.setStatBase(stat, 1)}
  }
}

case class VariarStatEn(stat: Stat, valor: Int) extends ModificacionStat {
  val prioridad = ALTA
  def apply(heroe: Heroe) = heroe.incrementarStatBase(stat, valor)
}
case class SetearStat(stat: Stat, valor: Int) extends ModificacionStat {
  val prioridad = BAJA
  def apply(heroe: Heroe) = heroe.setStatBase(stat, valor)
}
case class IncrementarStatsEnPorcentajeDePrincipal(porcentaje: Float) extends ModificacionStat {
  val prioridad = MEDIA
  def apply(heroe: Heroe) = {
    val incremento = (heroe.valorStatPrincipal * porcentaje).round
    heroe.stats.keys.foldLeft(heroe) {(heroe, stat) => heroe.incrementarStatBase(stat, incremento)}
  }
}

case class VariarStatEnSi(stat: Stat, valor: Int, condicion: Restriccion) extends ModificacionStat {
  val prioridad = ALTA
  def apply(heroe: Heroe)={
    var aMod=0
    if(condicion(heroe)){
      aMod=valor
    }
    heroe.incrementarStatBase(stat, aMod)
  }
}


sealed abstract class PrioridadModificacion (val prioridad: Int) extends Ordered[PrioridadModificacion]{
  def compare(that: PrioridadModificacion) = this.prioridad - that.prioridad
}
case object ALTA extends PrioridadModificacion(3)
case object MEDIA extends PrioridadModificacion(2)
case object BAJA extends PrioridadModificacion(1)