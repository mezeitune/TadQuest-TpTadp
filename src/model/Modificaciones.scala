package model

object Modificacion{
  implicit object ModificacionOrdering extends Ordering[Modificacion]{
    def compare(a: Modificacion, b: Modificacion) = {
      val ordenModificaciones = List(AgregarItem,VariarStatEn,VariarStatEnSi,ModificarStatPorCantidadItems,
          IncrementarStatsEnPorcentajeDePrincipal,SetearStat,SetearStatSegun,ModificarTodosLosStats)
      ordenModificaciones.indexOf(a.getClass) - ordenModificaciones.indexOf(b.getClass)
    }
  }
}

sealed trait Modificacion{
  def apply(heroe: Heroe): Heroe
}

case class AgregarItem(itemAAgregar: Item) extends Modificacion {
  def apply(heroe: Heroe) = heroe.equipar(itemAAgregar).getOrElse(heroe)//DEBERIA FALLAR SI NO SE PUEDE EQUIPAR, PERO ESO ROMPERIA LA FIRMA
}

sealed trait ModificacionStat extends Modificacion

case class ModificarStatPorCantidadItems(stat: Stat, valor: Int) extends ModificacionStat {
  def apply(heroe: Heroe) = {
    val cantItems = heroe.inventario.size
    val valorAumentado = cantItems * valor
    heroe.incrementarStatBase(stat, valorAumentado)
  }
}

case class SetearStatSegun(statAModificar: Stat , stat: Stat) extends ModificacionStat {
  def apply(heroe: Heroe) = {
    heroe.setStatBase(stat, heroe.stats.get(stat).get)
  }
}

case class ModificarTodosLosStats(valor: Int) extends ModificacionStat {
  def apply(heroe: Heroe) = {
    heroe.stats.keys.foldLeft(heroe) {(heroe,stat) => heroe.setStatBase(stat, 1)}
  }
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

case class VariarStatEnSi(stat: Stat, valor: Int, condicion: Restriccion) extends ModificacionStat {
  def apply(heroe: Heroe)={
    var aMod=0
    if(condicion(heroe)){
      aMod=valor
    }
    heroe.incrementarStatBase(stat, aMod)
  }
}