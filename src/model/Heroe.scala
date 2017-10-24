package model

class Heroe {
  var stats = collection.mutable.Map[String, Int]("hp" -> 100, "fuerza" -> 100, "velocidad" -> 100, "inteligencia" -> 100)
  val slots = List("cabeza", "torso", "mano", "mano")
  var inventario: List[Item] = List()
  var trabajo: Trabajo = null//HAY ALGO MEJOR PARA PONER???
  
  def stat(nombreStat: String) = 1.max(stats(nombreStat))
  
  def stat(nombreStat: String, valor: Int){
    stats(nombreStat) = valor
  }
  
  def equipar(item: Item){
    
  }
  
  def desequipar(item: Item){
    
  }
  
}