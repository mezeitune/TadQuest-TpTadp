package model

sealed trait Modificacion

sealed trait ModificacionStat extends Modificacion

case class VariarStatEn(stat: String, valor: Int) extends ModificacionStat
case class SetearStat(stat: String, valor: Int) extends ModificacionStat
case class IncrementarStatsEnPorcentajeDePrincipal(porcentaje: Float) extends ModificacionStat