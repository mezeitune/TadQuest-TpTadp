package model

class TablonDeAnuncios(val misiones: List[Mision]) {
  require(!misiones.isEmpty)

  def elegirMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean) = misiones.filter(_.serRealizadaPor(equipo).isSuccess).sortWith((mision1, mision2) => criterio.apply(mision1.serRealizadaPor(equipo).get, mision2.serRealizadaPor(equipo).get)).headOption

  //def entrenar(equipo: Equipo) = 
  //QUÉ CRITERIO USO PARA ELEGIR LA MEJOR MISION?
  //EXISTE ALGUN METODO SIMILAR A FOLD PERO QUE PERMITA DEFINIR EL ORDEN DE RECORRIDO?

}