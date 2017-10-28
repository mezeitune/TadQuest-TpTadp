package model

//NOSE COMO HACER PARA QUE CUANDO HEREDE DEL OPTION , NO ESTE ATADO A LA MONADA DE TENER QUE DEVOLVER POR EJEMPLLO SOME(10), YA QUE
//SI HABRIA QUE CAMBIAR ESO POR UNA LIST TE CAGA EL MODELO

//TODAS LAS TAREAS TIENEN LOS MISMOS PARAMETROS , COMO HAGO PARA EVITAR ESCRIBIRMELOS SIEMPRE QUE HEREDO DE TAREA?????


trait Tarea{

  def facilidad() : Option[Int]
  

  
  
}


class PelearContraMonstruo (modificaciones: List[Modificacion], heroe: Heroe, equipo: Equipo) extends Tarea{
  
    def facilidad():Option[Int] ={
       
        equipo.lider() match {
          case g:Guerrero            => Some(20)
          case _                     => Some(10)
          
          
        }
        
    }
  
  
}

class ForzarPuerta (modificaciones: List[Modificacion], heroe: Heroe, equipo: Equipo) extends Tarea{
  
    def facilidad():Option[Int] ={
       
     Some(heroe.getStat("inteligencia") + equipo.heroes.foldLeft(0) {(acum, heroe) => 
      heroe match {
        case l:Ladron                => acum + 10
        case _                       => 0
        }
      })
        
    }
  
  
}

class RobarTalisman (modificaciones: List[Modificacion], heroe: Heroe, equipo: Equipo) extends Tarea{
  
    def facilidad():Option[Int] ={
       
        equipo.lider() match {
          case l:Ladron            => Some(heroe.getStat("velocidad"))
          case _                   => None
          
          
        }
        
    }
  
  
}