package td.Main

import td._
import td.Main._
import scala.collection.mutable.Buffer
import scala.util.Random

class Stagemanager {
  // Vakio vihollisten määrälle per taso.
  private val enemyFactor = 2
  
  
  private val enemyWaves = Array[Array[Enemy]]()
  //Määrittää polun koordinaatit, joita pitkin viholliset kulkevat.
  def determinePath(game: Gamestate, factor: Int): Buffer[(Int, Int)] = {
    val path = Buffer[(Int, Int)]()
    val pathSquaresWithPos = game.getField.fieldWithPos.flatten.filter(x => x._1.letter == "P")
    val pathSquareCoords = pathSquaresWithPos.map(x => (x._2, x._3))
    try {
      val first = pathSquareCoords.find(x => x._1 == 0).getOrElse(throw new IllegalArgumentException("Field doesn't have a path in the left or rightmost column."))
      val last = pathSquareCoords.find(x => x._1 == game.getField.fieldWithPos(0).length - 1 ).getOrElse(throw new IllegalArgumentException("Field doesn't have a path in the left or rightmost column."))
      path += first
      
      def findNext(pathSquare: (Int, Int)):Unit = {
        val nextPath = game.getField.getNeighbors(pathSquare).filter(x => x._2.nonEmpty).filter(x => x._2.get._1.letter == "P").map(x => (x._2.get._2, x._2.get._3)).filter(!path.contains(_))
        if (nextPath.isEmpty || nextPath.head == last) {
          path += last
        } else {
          path += nextPath.head
          findNext(nextPath.head)
        }
      }
      findNext(first)
    } catch {
      case e: IllegalArgumentException => println(e)
    }
    println(path.last)
    path.map(x => (factor + x._1*factor*2, factor + x._2*factor*2))
  }
  
  //Käynnistää seuraavan tason.
  def nextStage(game: Gamestate, factor: Int) = {
    val path = determinePath(game, factor)
    for (i <- 1 to game.getLevel*enemyFactor) {
      game.getField.enemies += new Enemy(path.head._1, path.head._2, path, game.getLevel)
    }
    new Thread(new BattleHandler(game, path, game.getLevel, factor)).start()
  }
}
// BattleHandler-luokka, joka käynnistetään uudessa säikeessä, hallitsee vihollisten liikkeen 
// sekä muutokset pelitilaan kierroksen aikana.
class BattleHandler(game: Gamestate, path: Buffer[(Int, Int)], level: Int, factor: Int) extends Runnable {
  
  // Vakiot vihollisten liikkellelähtemisen väliajoille ja vihollisten etenemisnopeudelle
  private val intervalFactor = 1000
  private val threadSleepTime = 5
  
  def run() = {
    val deletedEnemies = Buffer[Enemy]()
    var i = 0
    val interval = intervalFactor/(level + 4)
    while(game.getHealth > 0 && game.getField.enemies.synchronized{game.getField.enemies != deletedEnemies && game.getField.enemies.size > deletedEnemies.size}) {
      if (i % interval == 0 && game.getField.enemies.synchronized{game.getField.enemies.exists(!_.hasMoved)} ) {
        game.getField.enemies.synchronized{game.getField.enemies.find(!_.hasMoved).get.readyToMove = true}
      }
      for(x <- game.getField.enemies.synchronized{game.getField.enemies}) {
        if (x.getHealth <= 0 && x.readyToMove) {
          game.setScore(10)
          x.readyToMove = false
          deletedEnemies += x
        } 
        if(x.done && x.readyToMove) {
          x.readyToMove = false
          game.setHealth(game.getHealth - 1)
          deletedEnemies += x
        } else if (x.readyToMove) x.move
      }
      for(x <- game.getField.towers) {
        if(x.onCooldown) {
          x.passTime
        } else {
          x.attack(game.getField.enemies.synchronized{game.getField.enemies}, factor)
        }
      }
      
      Thread.sleep(threadSleepTime)
      i += 1
    }
    
    if (game.getHealth <= 0) {
      game.setLost
    } else {
      game.getField.enemies.synchronized{game.getField.enemies.clear()}
      game.getField.towers.foreach(_.resetCD)
      game.setLevel(game.getLevel + 1)
    }
    
  }
}

















