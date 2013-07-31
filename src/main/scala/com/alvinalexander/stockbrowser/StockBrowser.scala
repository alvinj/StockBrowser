package com.alvinalexander.stockbrowser

import scala.swing._
import scala.swing.event.WindowClosing
import chrriis.dj.nativeswing.swtimpl.NativeInterface
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser
import java.awt._
import java.awt.event._
import javax.swing._
import scala.collection.mutable.ListBuffer
import scala.swing.event.ButtonClicked
import scala.swing.event.SelectionChanged

/**
 * A "Stock Market Browser".
 * Let's me easily switch between the one-year view of stock symbols I supply.
 * Can also use the browser as a regular web browser, courtesy of JWebBrowser
 * (DJNativeSwing project).
 */
object StockBrowser extends SwingApplication { 

  NativeInterface.open
  val browser = new JavaBrowser
  
  val ADD_BUTTON_TEXT = "Add"
  val REMOVE_BUTTON_TEXT = "Remove"

  val stocksListModel = new DefaultListModel
      
  // the jlist that holds stocks of interest
  val stocksListView = new ListView[String]() {
    peer.setMinimumSize(new Dimension(200, 900))
    peer.setPreferredSize(new Dimension(200, 900))
    selection.intervalMode = ListView.IntervalMode.Single
    val stocks = DataStore.getStocks()
    stocks.map(stocksListModel.addElement(_))
    peer.setModel(stocksListModel)
    //listData = stocks
  }

  // stocks scroll pane
  val listOfStocksScrollPane = new scala.swing.ScrollPane(stocksListView)
  val addStockButton = new scala.swing.Button(ADD_BUTTON_TEXT)
  val removeStockButton = new scala.swing.Button(REMOVE_BUTTON_TEXT)
  val stockListPanel = new BoxPanel(Orientation.Vertical) {
    contents += listOfStocksScrollPane
    contents += addStockButton
    contents += removeStockButton
  }

  // handle the event when the user clicks a stock in the list
  listenTo(stocksListView.selection, addStockButton, removeStockButton)
  reactions +=
    {
      case SelectionChanged(`stocksListView`) =>
        if (!stocksListView.selection.adjusting) {
          val selectedStockSymbol = stocksListView.selection.items(0)
          val url = buildOneYearUrl(selectedStockSymbol)
          browser.webBrowser.navigate(url)
        }
      case ButtonClicked(b) => b.text match {
        case ADD_BUTTON_TEXT => startAddNewStockProcess
        case REMOVE_BUTTON_TEXT => startDeleteSelectedStockProcess
      }
    }
  
  def startAddNewStockProcess {
    val symbol = JOptionPane.showInputDialog(null, "Stock symbol to ADD?")
    if (symbol != null & symbol.trim() != "") {
      stocksListView.peer.getModel.asInstanceOf[DefaultListModel].add(0, symbol)
      DataStore.addStock(symbol)
    }
  }

  def startDeleteSelectedStockProcess {
    val symbol = JOptionPane.showInputDialog(null, "Stock symbol to REMOVE?")
    if (symbol != null & symbol.trim() != "") {
      stocksListView.peer.getModel.asInstanceOf[DefaultListModel].removeElement(symbol)
      DataStore.removeStock(symbol)
    }
  }

  def top = new MainFrame {
    title = "Stock Browser" 
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += stockListPanel
      peer.add(browser)
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }      
    size = desiredInitialSize
    reactions += {
      case WindowClosing(_) => quit
    }
  }
  
  def desiredInitialSize = {
    val screenSize = Toolkit.getDefaultToolkit.getScreenSize
    val w = (screenSize.getWidth * 0.625f).toInt
    val h = (screenSize.getHeight * 0.625f).toInt
    new Dimension(w, h)
  }

  override def main(args: Array[String]) {
    SwingUtilities.invokeLater(new Runnable {
      def run {
        val f = top
        f.peer.setLocationRelativeTo(null)
        f.peer.setVisible(true)
      }
    })
    NativeInterface.runEventPump
  }
  
  def handleClosing (f: MainFrame) {
    f.peer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    f.peer.addWindowListener(new WindowAdapter {
      override def windowClosing(we: WindowEvent) {
        quit
      }
    })
  }
  override def startup(args: Array[String]) {}

  def buildOneYearUrl(symbol: String) = 
    s"http://finance.yahoo.com/echarts?s=${symbol.toUpperCase}+Interactive#symbol=aapl;range=1y;compare=;indicator=volume;charttype=area;crosshair=on;ohlcvalues=0;logscale=off;source=undefined;"

}


class JavaBrowser extends JPanel {

  import java.awt.BorderLayout
  import javax.swing.JPanel

  setLayout(new BorderLayout)
  val webBrowserPanel = new JPanel(new BorderLayout)
  val webBrowser = new JWebBrowser
  webBrowserPanel.add(webBrowser, BorderLayout.CENTER)
  add(webBrowserPanel, BorderLayout.CENTER)

}















