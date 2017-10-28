package model




trait Tarea{

  def facilidad()
  

  
  
}


class PelearContraMonstruo (modificaciones: List[Modificacion], heroe: Heroe, equipo: Equipo) extends Tarea{
  
    def facilidad(){
       
        equipo.lider() match {
          case g:Guerrero            => 20
          case _                     => 10
          
          
        }
        
    }
  
  
}

class ForzarPuerta (modificaciones: List[Modificacion], heroe: Heroe, equipo: Equipo) extends Tarea{
  
    def facilidad(){
       
     heroe.getStat("inteligencia") + equipo.heroes.foldLeft(0) {(acum, heroe) => 
      heroe match {
        case l:Ladron                => acum + 10
        case _                       => 0
        }
      }
        
    }
  
  
}