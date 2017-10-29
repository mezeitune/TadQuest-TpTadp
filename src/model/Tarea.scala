package model

//FALTA AGREGAR MODIFICACIONES DE CADA TAREA (!!!)

abstract class Tarea(modificaciones: List[Modificacion]){

  def facilidad(heroe: Heroe, equipo: Equipo) : Option[Int]
  
  //def serRealizarPor(equipo:Equipo): Equipo
  
}

object pelearContraMonstruo extends Tarea(List()){
  
    def facilidad(heroe: Heroe, equipo: Equipo) = {
        equipo.lider().map(_.trabajo match {
          case Guerrero           => 20
          case _                  => 10         
        })
    }
}

object forzarPuerta extends Tarea(List()){
 
    def facilidad(heroe: Heroe, equipo: Equipo) = {
       Some(heroe.getStat(Inteligencia) + equipo.cantidadMiembrosConTrabajo(Ladron)*10)
    }

}

object robarTalisman extends Tarea(List()){
  
    def facilidad(heroe: Heroe, equipo: Equipo) = {
        equipo.lider().flatMap(_.trabajo match {  //te deja un unico option //VERIFICAR QUE ANDE BIEN!!
          case Ladron            => Some(heroe.getStat(Velocidad))
          case _                 => None
        }) 
    }
  
}