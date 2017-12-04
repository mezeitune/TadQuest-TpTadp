package model

class Item (val slotsRequeridos: List[Slot],
    val restricciones: List[Restriccion],
    val modificaciones: List[ModificacionStat],
    val precio: Int){
  
  def puedeEquiparseEn(heroe: Heroe) = restricciones.forall(_.apply(heroe))
  
}

case object CascoVikingo extends Item(List(Cabeza), List(heroe => heroe.stats.get(Fuerza).get > 50), List(VariarStatEn(HP,10)), 150)
case object PalitoMagico extends Item(List(ManoIzq), List(), List(VariarStatEn(Inteligencia,20)), 300)
case object ArmaduraEleganteSport extends Item(List(Torso), List(), List(VariarStatEn(Velocidad,30), VariarStatEn(HP,-30)), 400)
case object ArcoViejo extends Item(List(ManoIzq,ManoDer), List(), List(VariarStatEn(Fuerza,2)), 400)
case object EscudoAntiRobo extends Item(List(ManoIzq), List(heroe => heroe.stats.get(Fuerza).get >= 20, heroe => heroe.trabajo != Ladron), List(VariarStatEn(HP,20)), 200)
case object TalismanDeDedicacion extends Item(List(), List(), List(IncrementarStatsEnPorcentajeDePrincipal(10)), 350)
case object TalismanDelMinimalismo extends Item(List(), List(), List(ModificarStatPorCantidadItems(HP,50),ModificarStatPorCantidadItems(HP,50)), 800)

case object VinchaDelBufaloDeAgua extends Item(List(Cabeza), List(heroe => heroe.trabajo.isEmpty), List(), 650) //ver

case object TalismanMaldito extends Item(List(), List(), List(ModificarTodosLosStats(1)), 1)
case object EspadaDeLaVida extends Item(List(ManoIzq), List(), List(SetearStatSegun(Fuerza,HP)), 1000)
