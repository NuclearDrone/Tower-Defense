package td.Main
import scala.collection.mutable.Buffer
import scala.math._

class Enemy(var x: Int, var y: Int, val path: Buffer[(Int, Int)], val level: Int) {
  private var health = 100 + level/10 * 100
  var hasMoved = false
  var readyToMove = false
  private var counter = 1
  private var nextPath:(Int, Int) = null
  val pathTravelled = Buffer[(Int, Int)]()
  var done = false
  
  def getHealth = health
  def setHealth(x: Int) = if (path.head != (x, y)) health = x 
  private def getCounter = min(path.length - 1, counter)
  
  
  // Liikuttaa vihollista yhden pikselin verran polun suuntaan.
  def move() = {
    hasMoved = true
    if (path.head == (x, y)) {
      nextPath = path(getCounter)
    } else if (path.last == (x, y)) {
      done = true
    } else if (nextPath == (x, y)) {
      pathTravelled += nextPath
      counter += 1
      nextPath = path(getCounter)
    }
    val newX = nextPath._1 - x
    val newY = nextPath._2 - y
    val direction = {
      (if (newX == 0) newX else newX/abs(newX), if (newY == 0) newY else newY/abs(newY))
    }
    this.x += direction._1
    this.y += direction._2
    
  }
}