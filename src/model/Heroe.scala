package model

case class Heroe(
    val stats: Map[String, Int] = Map[String, Int]("hp" -> 100, "fuerza" -> 100, "velocidad" -> 100, "inteligencia" -> 100),
    //COMO INICIALIZO LOS STATS "POR CONSTRUCTOR" ???
    val slots:List[String] = List("cabeza", "torso", "mano", "mano"),
    val inventario: List[Item] = List(),
    val trabajo: Trabajo = ninguno //HAY ALGO MEJOR PARA PONER???
    ) {
  
  def trabajo(nuevoTrabajo: Trabajo) = copy(trabajo = nuevoTrabajo)

  def stat(nombreStat: String) = 1.max(inventario.foldLeft
      (trabajo.aplicarModificacionesA(nombreStat, stats(nombreStat)))((valor, item) => item.aplicarModificacionesA(nombreStat, valor)))

  def stat(nombreStat: String, valor: Int) = {
    val nuevosStats = stats + (nombreStat -> valor)
    copy(stats = nuevosStats)
  }

  def equipar(item: Item): Heroe = {
    if (item.puedeEquiparseEn(this)){
      val itemsADesequipar = null// = inventario.filter(_.requiereSlot) VER COMO LO OBTENEMOS (!!!)
      val nuevoInventario = inventario.diff(itemsADesequipar) ++ List(item)
      return copy(inventario = nuevoInventario)
    } else {
      throw new Exception("No puede equiparse el item") //VER SI CONVIENE QUE ROMPA O NO
    }
  }

  def desequipar(item: Item) = copy(inventario = inventario.diff(List(item)))

}