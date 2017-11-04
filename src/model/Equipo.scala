package model

case class Equipo(val nombre: String, val pozoComun: Int = 0, val heroes: List[Heroe] = List()) {

  def mejorHeroeSegun(criterio: Heroe => Int) = heroes.sortWith((heroe1, heroe2) => criterio.apply(heroe1) >= criterio.apply(heroe2)).headOption

  def lider() = mejorHeroeSegun { _.valorStatPrincipal() }

  def obtenerItem(item: Item) = {
    def incrementoStatPrincipal(heroe: Heroe, item: Item) = {
      heroe.equipar(item).map(_.valorStatPrincipal - heroe.valorStatPrincipal).getOrElse(Int.MinValue)
      //Si el incremento es menor a 0, no se le va a equipar el item
    }
    val heroeAEquiparItem = mejorHeroeSegun(heroe => incrementoStatPrincipal(heroe, item))
    heroeAEquiparItem match {
      case Some(heroe) if (incrementoStatPrincipal(heroe: Heroe, item: Item) > 0) => reemplazarMiembro(heroe.equipar(item).get, heroe)
      case _ => copy(pozoComun = pozoComun + item.precio)
    }
  }

  def obtenerMiembro(nuevoMiembro: Heroe) = copy(heroes = heroes ++ List(nuevoMiembro))

  def reemplazarMiembro(nuevoMiembro: Heroe, heroeAEliminar: Heroe) = copy(heroes = heroes.diff(List(heroeAEliminar)) ++ List(nuevoMiembro))

  def cantidadMiembrosConTrabajo(unTrabajo: Trabajo) = heroes.count(_.trabajo == unTrabajo)

}