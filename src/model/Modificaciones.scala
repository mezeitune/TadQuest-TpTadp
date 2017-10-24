package model

abstract class ModificacionStat (var stat: String, valor: Int){
  def aplicarA(valorInicial: Int): Int
}

class VariarEn(stat: String, valor: Int) extends ModificacionStat(stat, valor){
  override def aplicarA(valorInicial: Int) = valorInicial + valor
}

class Setear(stat: String, valor: Int) extends ModificacionStat(stat, valor){
  override def aplicarA(valorInicial: Int) = valor
}