package td.UI

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.MenuBar
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuItem
import scalafx.scene.control.SplitPane
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.TilePane

object UIApp extends JFXApp {
  stage = new PrimaryStage {
    title = "Tornipuolustus"
    scene = new Scene(800, 600) {
      
      val menuBar = new MenuBar
      val gameMenu = new Menu("Game")
      val newGame = new MenuItem("New")
      val openGame = new MenuItem("Open")
      val saveGame = new MenuItem("Save")
      val quitGame = new MenuItem("Quit")
      gameMenu.items = List(newGame, openGame, saveGame, quitGame)
      menuBar.menus = List(gameMenu)
      
      val Button1 = new Button("Press me!")
      val Button2 = new Button("Press me!")
      val Button3 = new Button("Press me!")
      val Button4 = new Button("Press me!")
      val Button5 = new Button("Press me!")
      val buttonPane = new TilePane
      val gameWindow = new BorderPane
      buttonPane.layoutX = 200
      buttonPane.children += Button1
      buttonPane.children += Button2
      buttonPane.children += Button3
      buttonPane.children += Button4
      buttonPane.children += Button5
      val split = new SplitPane
      split.items ++= List(buttonPane, gameWindow)
      split.dividerPositions = 0.25
      
      
      val rootPane = new BorderPane
      rootPane.top = menuBar
      rootPane.center = split
      
      root = rootPane
      
    }
  }
}