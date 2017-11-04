package model

sealed trait ModificacionStat extends Function1[Heroe,Heroe]{//SI NO EXTIENDE DE FUNCTION, SCALA NO LO RECONOCE COMO DE TIPO MODIFICACION (!!!)
  def apply(heroe: Heroe): Heroe
}

case class VariarStatEn(stat: Stat, valor: Int) extends ModificacionStat {
  def apply(heroe: Heroe) = heroe.incrementarStatBase(stat, valor)
}
case class SetearStat(stat: Stat, valor: Int) extends ModificacionStat {
  def apply(heroe: Heroe) = heroe.setStatBase(stat, valor)
}
case class IncrementarStatsEnPorcentajeDePrincipal(porcentaje: Float) extends ModificacionStat {
  def apply(heroe: Heroe) = {
    val incremento = (heroe.valorStatPrincipal * porcentaje).round
    heroe.stats.keys.foldLeft(heroe) {(heroe, stat) => heroe.incrementarStatBase(stat, incremento)}
  }
}