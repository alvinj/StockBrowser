package com.alvinalexander.stockbrowser

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter

object DataStore {
  
  // data file
  val dataFile = System.getProperty("user.home") + "/.ddstockbrowser"
  val stocks = new ArrayBuffer[String]()

  def getStocks(): List[String] = {
    if ((new File(dataFile).exists())) {
      val stocksFromFile = for (line <- Source.fromFile(dataFile).getLines()) yield line
      stocks.appendAll(stocksFromFile)
      stocks.toList
    } else {
      Nil
    }
  }

  def addStock(symbol: String) {
    stocks += symbol
    saveStocks()
  }

  def removeStock(symbol: String) {
    stocks -= symbol
    saveStocks()
  }

  // save the stock symbols, in sorted order
  private def saveStocks() {
    val file = new File(dataFile) 
    val bw = new BufferedWriter(new FileWriter(file))
    for (stock <- stocks.sorted) bw.write(s"$stock\n")
    bw.close()
  }

  
}