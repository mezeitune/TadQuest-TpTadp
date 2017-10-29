package model

class Mision(val tareas: List[Tarea] = List(), val recompensa: Any) { //VER CÓMO MODELAR LAS RECOMPENSAS (!!!)
  
  //def serRealizadaPor(equipo: Equipo):Equipo = {}
  
  def heroeQueVaARealizaTarea(equipo: Equipo, tarea: Tarea): Option[Heroe] = {
    val heroeConMasFacilidad = equipo.heroes.foldLeft(equipo.heroes.head) { (heroe1, heroe2) =>
      (tarea.facilidad(heroe1, equipo),tarea.facilidad(heroe2, equipo)) match {
        case (None, _) => heroe2
        case (_, None) => heroe1
        case (Some(facilidadHeroe1), Some(facilidadHeroe2)) => if (facilidadHeroe1 >= facilidadHeroe2) heroe1 else heroe2
        }
    }
    if (tarea.facilidad(heroeConMasFacilidad, equipo).isEmpty) None else Some(heroeConMasFacilidad)
  }
  
}