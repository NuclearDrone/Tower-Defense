package td
import scalafx.scene.paint.Color

class Field(rows: Int = 10, cols: Int = 10, data: Array[Array[Square]]) {
  
  val field = data
  
  
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