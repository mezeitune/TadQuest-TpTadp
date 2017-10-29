package model

//NOSE COMO HACER PARA QUE CUANDO HEREDE DEL OPTION , NO ESTE ATADO A LA MONADA DE TENER QUE DEVOLVER POR EJEMPLLO SOME(10), YA QUE
//SI HABRIA QUE CAMBIAR ESO POR UNA LIST TE CAGA EL MODELO

//TODAS LAS TAREAS TIENEN LOS MISMOS PARAMETROS , COMO HAGO PARA EVITAR ESCRIBIRMELOS SIEMPRE QUE HEREDO DE TAREA?????


trait Tarea{

  def facilidad(heroe: Heroe, equipo: Equipo) : Option[Int]
   
  
/*  def serRealizarPor(equipo:Equipo):Equipo = {
    
  }*/
  
 

  
  
}


class PelearContraMonstruo (modificaciones: List[Modificacion]) extends Tarea{
  
    def facilidad(heroe: Heroe, equipo: Equipo) = {
       
        equipo.lider().map(_.trabajo match {
          case Guerrero           => 20
          case _                  => 10
          
          
        })
        
    }
  
  
}

class ForzarPuerta (modificaciones: List[Modificacion]) extends Tarea{
  
    def facilidad(heroe: Heroe, equipo: Equipo) = {
       
       Some(heroe.getStat(Inteligencia) + equipo.cantidadMiembrosConTrabajo(Ladron)*10)
      
        
    }
  
  
}

class RobarTalisman (modificaciones: List[Modificacion]) extends Tarea{
  
    def facilidad(heroe: Heroe, equipo: Equipo) = {
       
        equipo.lider().flatMap(_.trabajo match {  //te deja un unico option //VERIFICAR QUE ANDE BIEN!!
          case Ladron            => Some(heroe.getStat(Velocidad))
          case _                 => None
          
          
        })
        
    }
  
  
}