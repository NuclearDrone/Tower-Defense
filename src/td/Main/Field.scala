package td
import scalafx.scene.paint.Color

class Field(r: Int = 10, c: Int = 10, data: Array[Array[Square]]) {
  
  val field = data
  val rows = r
  val cols = c
}

trait Square {
  def letter: String
  def color:String
}

class TowerSquare extends Square {
  def letter = "T"
  def color = "blue"
}

class PathSquare extends Square {
  def letter = "P"
  def color = "brown"
}
class BlockedSquare extends Square {
  def letter = "B"
  def color = "gray"
}