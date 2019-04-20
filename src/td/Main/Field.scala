package td

import scalafx.scene.paint.Color
import td.Main._
import scala.collection.mutable.Buffer

class Field(r: Int = 10, c: Int = 10, data: Array[Array[Square]]) {
  
  val field = data
  val rows = r
  val cols = c
  var enemies = Buffer[Enemy]()
  var towers = Buffer[Tower]()
  
  // Palauttaa pelikentän ruudut tupleina, jotka sisältävät myös sen koordinaatit pelikentällä.
  // Huom! Koordinaatit ovat Array^2 -koordinaatteja, eivät käyttöliittymän koordinaatteja.
  def fieldWithPos = field.zipWithIndex.map(x => ( x._1.zipWithIndex, x._2)).map(x => x._1.map(y => (y._1, y._2, x._2)))
  
  // Palauttaa parametriksi syötetyn koordinaatin viereiset ruudut ja niiden suunnat tuplena.
  // Huom! Koordinaatit ovat Array^2 -koordinaatteja, eivät käyttöliittymän koordinaatteja.
  def getNeighbors(coords: (Int, Int)) = {
    Array[(String, Option[(Square, Int, Int)])](("N", try {Some(fieldWithPos(coords._2-1)(coords._1))} catch {case e: ArrayIndexOutOfBoundsException => None }) , ("E",try { Some(fieldWithPos(coords._2)(coords._1+1))} catch {case e: ArrayIndexOutOfBoundsException => None }) , ("S", try {Some(fieldWithPos(coords._2+1)(coords._1))} catch {case e: ArrayIndexOutOfBoundsException => None }) , ("W", try {Some(fieldWithPos(coords._2)(coords._1-1))} catch {case e: ArrayIndexOutOfBoundsException => None }))
  }
}
// Pelikentän ruutujen yläluokka
trait Square {
  def tower: Int
  def setTower(x: Int):Boolean
  def letter: String
  def color:String
}
// Torniruutu: Voi sisältää tornin.
class TowerSquare extends Square {
  var tower = 0
  def setTower(x: Int): Boolean = {
    tower = x
    true
  }
  def letter = "T"
  def color = "blue"
}
// Polkuruutu: Vihollisten kulkureitti.
class PathSquare extends Square {
  val tower = 0
  def setTower(x: Int) = false
  def letter = "P"
  def color = "brown"
}
// Seinäruutu: Estää tornien asettamisen sekä vihollisten kulun.
class BlockedSquare extends Square {
  val tower = 0
  def setTower(x: Int) = false
  def letter = "B"
  def color = "gray"
}