package model
import scala.util.{Try, Success, Failure}
import Types._

class Mision(val tareas: List[Tarea] = List(), val recompensa: Recompensa) { //VER CÓMO MODELAR LAS RECOMPENSAS (!!!)
  
  def serRealizadaPor(equipo: Equipo): Try[Equipo] = {
    val equipoLuegoDeMision = tareas.foldLeft(Try(equipo)) {(equipo, tarea) =>
      if (equipo.isSuccess) tarea.ejecutar(heroeQueVaARealizaTarea(equipo.get, tarea), equipo.get)
      else equipo
      }
    if (equipoLuegoDeMision.isSuccess) equipoLuegoDeMision.map(equipo => cobrarRecompensa(equipo))
    else equipoLuegoDeMision
  }
  
  def heroeQueVaARealizaTarea(equipo: Equipo, tarea: Tarea): Option[Heroe] = { //VER SI CONVIENE QUE ESTÉ EN LA TAREA
    val heroeConMasFacilidad = equipo.heroes.foldLeft(equipo.heroes.head) { (heroe1, heroe2) =>//VER DE USAR MEJOR HEROE SEGUN
      (tarea.facilidad(heroe1, equipo),tarea.facilidad(heroe2, equipo)) match {
        case (None, _) => heroe2
        case (_, None) => heroe1
        case (Some(facilidadHeroe1), Some(facilidadHeroe2)) => if (facilidadHeroe1 >= facilidadHeroe2) heroe1 else heroe2
        }
    }
    if (tarea.facilidad(heroeConMasFacilidad, equipo).isEmpty) None else Some(heroeConMasFacilidad)
  }
  
  def cobrarRecompensa(equipo: Equipo) = recompensa.apply(equipo)
  
}