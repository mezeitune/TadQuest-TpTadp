package model

import org.junit.Before
import org.junit.Test
import org.junit.Assert

class EquipoTests {
  
  var guerrero: Heroe = null
  var mago: Heroe = null
  var ladron: Heroe = null
  var equipo: Equipo = null
  var espada: Item = null
  var espadaDeCarton: Item = null
  
  @Before
  def setup{
    guerrero = new Heroe().trabajo(Guerrero)
    mago = new Heroe().trabajo(Mago)
    ladron = new Heroe().trabajo(Ladron)
    equipo = new Equipo("losSuperHeroes", 0, List(guerrero, mago, ladron))
    espada = new Item(List(ManoDer), List((heroe => heroe.stats.get(Fuerza).get > 50)), List(VariarStatEn(Fuerza,100)), 200)
    espadaDeCarton = new Item(List(ManoDer), List(), List(VariarStatEn(Fuerza,0)), 5)
  }
  
  //Tests: Punto 2 -> Hay equipo
  
  @Test
  def mejorHeroeSegunCumpleCriterio(){
    val mejorHeroe = equipo.mejorHeroeSegun(heroe => heroe.getStat(Fuerza) + 10).get
    Assert.assertEquals(guerrero, mejorHeroe)
  }
  
  @Test
  def heroeObtieneItemPorSuEquipo(){
    val nuevoEquipo = equipo.obtenerItem(espada)
    Assert.assertTrue(nuevoEquipo.heroes.exists(heroe => heroe.inventario.contains(espada)))
  }
  
  @Test
  def pozoAumentaSegunItemQueNoBeneficiaAlEquipo(){
    val nuevoEquipo = equipo.obtenerItem(espadaDeCarton)
    Assert.assertEquals(5, nuevoEquipo.pozoComun)
  }
  
  @Test
  def equipoObtieneNuevoMiembro(){
    val nuevoMiembro = new Heroe()
    val nuevoEquipo = equipo.obtenerMiembro(nuevoMiembro)
    Assert.assertTrue(nuevoEquipo.heroes.contains(nuevoMiembro))
  }
  
  @Test
  def equipoReemplazaMiembroConOtro(){
    val nuevoMiembro = new Heroe()
    val nuevoEquipo = equipo.reemplazarMiembro(nuevoMiembro, guerrero)
    Assert.assertTrue(nuevoEquipo.heroes.contains(nuevoMiembro) && !nuevoEquipo.heroes.contains(guerrero))
  }
  
  @Test
  def magoEsLiderDelEquipo(){
    val heroeLider = equipo.lider().get
    Assert.assertEquals(mago, heroeLider)
  }
  
}