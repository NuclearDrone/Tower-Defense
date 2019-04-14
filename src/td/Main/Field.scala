package td

import scalafx.scene.paint.Color
import td.Main._

class Field(r: Int = 10, c: Int = 10, data: Array[Array[Square]]) {
  
  val field = data
  val rows = r
  val cols = c
  var enemies = List[Enemy]()
  var towers = List[Tower]()
  def fieldWithPos = field.zipWithIndex.map(x => ( x._1.zipWithIndex, x._2))
}

trait Square {
  def tower: Int
  def setTower(x: Int):Boolean
  def letter: String
  def color:String
}

class TowerSquare extends Square {
  var tower = 0
  def setTower(x: Int): Boolean = {
    tower = x
    true
  }
  def letter = "T"
  def color = "blue"
}

class PathSquare extends Square {
  val tower = 0
  def setTower(x: Int) = false
  def letter = "P"
  def color = "brown"
}
class BlockedSquare extends Square {
  val tower = 0
  def setTower(x: Int) = false
  def letter = "B"
  def color = "gray"
}