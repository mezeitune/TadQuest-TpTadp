package model

case class Equipo(val nombre: String, val pozoComun: Int = 0, val heroes: List[Heroe] = List()) {

  def mejorHeroeSegun(criterio: Heroe => Int) = heroes.sortWith((heroe1, heroe2) => criterio.apply(heroe1) >= criterio.apply(heroe2)).headOption

  def lider() = mejorHeroeSegun { _.valorStatPrincipal() }

  def obtenerItem(item: Item) = {
    def incrementoStatPrincipal(heroe: Heroe, item: Item) = {
      heroe.equipar(item).valorStatPrincipal - heroe.valorStatPrincipal
    }
    val heroeAEquiparItem = mejorHeroeSegun(heroe => incrementoStatPrincipal(heroe, item))
    heroeAEquiparItem match {
      case Some(heroe) if (incrementoStatPrincipal(heroe: Heroe, item: Item) > 0) => reemplazarMiembro(heroe.equipar(item), heroe)
      case _ => copy(pozoComun = pozoComun + item.precio)
    }
  }

  def obtenerMiembro(nuevoMiembro: Heroe) = copy(heroes = heroes ++ List(nuevoMiembro))

  def reemplazarMiembro(nuevoMiembro: Heroe, heroeAEliminar: Heroe) = copy(heroes = heroes.diff(List(heroeAEliminar)) ++ List(nuevoMiembro))

  def cantidadMiembrosConTrabajo(unTrabajo: Trabajo) = heroes.count(_.trabajo == unTrabajo)

}