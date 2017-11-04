package model

sealed trait ModificacionStat {
  def apply(heroe: Heroe): Heroe
}

case class VariarStatEn(stat: Stat, valor: Int) extends ModificacionStat
case class SetearStat(stat: Stat, valor: Int) extends ModificacionStat
case class IncrementarStatsEnPorcentajeDePrincipal(porcentaje: Float) extends ModificacionStat

//TODO: agregar la logica de modificaciones