package model
import scala.util.{Try, Success, Failure}

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

  def cantidadMiembrosConTrabajo(unTrabajo: Trabajo) = heroes.count(_.trabajo.contains(unTrabajo))

  def elegirMision(misiones: TablonDeAnuncios, criterio: CriterioMision) = misiones.filter(_.serRealizadaPor(this).isSuccess)
    .sortWith((mision1, mision2) => criterio(mision1.serRealizadaPor(this).get, mision2.serRealizadaPor(this).get))
    .headOption

  def entrenar(misiones: TablonDeAnuncios, criterio: CriterioMision): Try[Equipo] = {
    elegirMision(misiones, criterio).map { mejorMision =>
      val equipoLuegoDeMision = mejorMision.serRealizadaPor(this)
      equipoLuegoDeMision.flatMap {_.entrenar(misiones.filter(!_.equals(mejorMision)), criterio)}//ENTRA EN UN CICLO INFINITO (???)
    }.getOrElse(Success(this))//VER QUÉ PASA SI NO QUEDAN MISIONES VS SI QUEDAN PERO NINGUNA SE PUEDE REALIZAR (!!!)
  }  
  
}