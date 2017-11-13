package model

sealed trait Modificacion extends Ordered[Modificacion]{
  val prioridad: PrioridadModificacion
  def apply(heroe: Heroe): Heroe
  def compare(that: Modificacion) = this.prioridad.compare(that.prioridad)
}

sealed trait ModificacionStat extends Modificacion

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

sealed abstract class PrioridadModificacion (val prioridad: Int) extends Ordered[PrioridadModificacion]{//MEJOR FORMA DE ORDENAR (???)
  def compare(that: PrioridadModificacion) = this.prioridad - that.prioridad
}
case object ALTA extends PrioridadModificacion(3)
case object MEDIA extends PrioridadModificacion(2)
case object BAJA extends PrioridadModificacion(1)

/*type Modificacion = Heroe => Heroe //YA NO PUEDE SER CUALQUIER FUNCIÓN, PORQUE ADEMÁS DE APLICARSE TIENE QUE PODER ORDENARSE
sealed trait ModificacionStat extends Function1[Heroe,Heroe]{//SI NO EXTIENDE DE FUNCTION, SCALA NO LO RECONOCE COMO DE TIPO MODIFICACION (!!!)
  def apply(heroe: Heroe): Heroe
}*/