package td

import java.io._
import td._
class Filemanager {
  
  def saveGame(file: File) = {
    
  }
  
  def loadGame(file: File): Gamestate = {
    val input = new BufferedReader(new FileReader(file))
    val game = new Gamestate()
    try {
      var fileEnded = false
      var rows = 0
      var cols = 0
      while(!fileEnded) {
        input.readLine() match {
          case "DATA:" => 
            val gameData = input.readLine()
            val gameScore = gameData.takeWhile(_ != ' ')
            val gameHealth = gameData.drop(gameScore.length + 1).takeWhile(_ != ' ')
            val gameLevel = gameData.drop(gameHealth.length + 1).drop(gameScore.length + 1).takeWhile(_ != ' ')
            
            game.setScore(gameScore.toInt)
            game.setPlayerHealth(gameHealth.toInt)
            game.setLevel(gameLevel.toInt)
            println(game)
            
          case "FIELD:" =>
            def squareType(in: String): Square = {
              in match {
                case "T" => new TowerSquare
                case "P" => new PathSquare
                case "B" => new BlockedSquare
              }
            }
            val fieldSize = input.readLine()
            val rows = fieldSize.takeWhile(_ != ' ')
            val cols = fieldSize.drop(rows.length + 1)
            val data = Array.ofDim[Square](rows.toInt, cols.toInt)
            for (x <- 0 until rows.toInt) {
              data(x) = input.readLine().split(" ").map(squareType)
            }
            game.setField(new Field(rows.toInt, cols.toInt, data))
            
          case "END" => fileEnded = true
          case x => println("wtf happened")
          
        }
      }
      
    } catch {
      case e: IOException => println("File reading error")
    } finally {
      input.close()
    }
    game
  }
  
  def newGame = {
    
  }
  
}