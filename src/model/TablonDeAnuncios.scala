package model
import scala.util.{Try, Success, Failure}

case class TablonDeAnuncios(misiones: List[Mision]) {
  require(!misiones.isEmpty)

  def elegirMision(equipo: Equipo, criterio: CriterioMision) = misiones
    .filter(_.serRealizadaPor(equipo).isSuccess)
    .sortWith((mision1, mision2) => criterio(mision1.serRealizadaPor(equipo).get, mision2.serRealizadaPor(equipo).get))
    .headOption

  def entrenar(equipo: Equipo, criterio: CriterioMision): Try[Equipo] = {
    elegirMision(equipo, criterio).map{mision =>
      val misionARealizar = elegirMision(equipo, criterio)
      var equipoLuegoDeMision = misionARealizar.get.serRealizadaPor(equipo)
      if (equipoLuegoDeMision.isSuccess) copy(misiones = misiones.filter(!_.equals(misionARealizar))).elegirMision(equipoLuegoDeMision.get, criterio)
      equipoLuegoDeMision
    }.getOrElse(Success(equipo))
//    if (elegirMision(equipo, criterio).isDefined) {
//      val misionARealizar = elegirMision(equipo, criterio)
//      var equipoLuegoDeMision = misionARealizar.get.serRealizadaPor(equipo)
//      if (equipoLuegoDeMision.isSuccess) copy(misiones = misiones.filter(!_.equals(misionARealizar))).elegirMision(equipoLuegoDeMision.get, criterio)
//      return equipoLuegoDeMision
//    } else Success(equipo)
  }

}