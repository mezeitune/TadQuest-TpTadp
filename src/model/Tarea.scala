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