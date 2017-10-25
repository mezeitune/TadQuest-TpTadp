package model

class Trabajo (val statPrincipal: String, val modificaciones: List[ModificacionStat]) {
  def aplicarModificacionesA(nombreStat: String, valorInicial: Int) = {//ES EXACTAMENTE EL MISMO QUE EN EL ITEM (!!!)
    val modificacionesAlStat = modificaciones.filter({_.stat == nombreStat})
    modificacionesAlStat.foldLeft(valorInicial) ((valor, modificacion) => modificacion.aplicarA(valor))
  }
}

object ninguno extends Trabajo("", List())