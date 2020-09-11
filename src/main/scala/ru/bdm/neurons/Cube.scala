package ru.bdm.neurons

class Cube(val id:Int) {
  var isLive = true
  var score:Int = 0
  var x:Int = 90
  var y:Int = 90
  var way: Way.Way = Way.UP
  val width = 30
  val height = 30
  val speed = 5
  def tick(): Unit = {
    way match {
      case Way.UP ⇒
        y += speed
      case Way.RIGHT ⇒
        x += speed
      case Way.DOWN ⇒
        y -= speed
      case Way.LEFT ⇒
        x -= speed
    }
  }
  def setWay(way:Way.Way): Unit = {
    if (way == Way.UP && this.way == Way.DOWN ||
      way == Way.DOWN && this.way == Way.UP ||
      way == Way.LEFT && this.way == Way.RIGHT ||
      way == Way.RIGHT && this.way == Way.LEFT )
      isLive = false
    this.way = way
  }
}
