package ru.bdm.neurons

import java.awt.image.BufferedImage

import org.jcodec.api.awt.AWTSequenceEncoder
import org.jcodec.common.io.NIOUtils
import org.jcodec.common.model.Rational

object Started extends App {

  var comm = 'c'
  val cube = new Cube(1)
  val map = new AllMap(cube :: Nil)
  map.start()

  val out = NIOUtils.writableFileChannel("test.mp4")

  val encoder = new AWTSequenceEncoder(out, Rational.R(6, 1))

  val image = new BufferedImage(map.width, map.height, BufferedImage.TYPE_INT_RGB)
  writeMapToImage()


  var num = 0
  def writeMapToImage(): Unit = {
    for (i ← 0 until map.width)
      for (j ← 0 until map.height)
        image.setRGB(i, j, if (map.arr(i)(j) == 0) 0x000000 else 0xffffff)
    encoder.encodeImage(image)
    num += 1
    println(num / 480f * 100 + "%")
  }
  for (j ← 1 to 4) {
    for (i ← 1 to 30) {
      cube.setWay(Way.RIGHT)
      map.tick()
      writeMapToImage()
    }
    for (i ← 1 to 30) {
      cube.setWay(Way.UP)
      map.tick()
      writeMapToImage()
    }
    for (i ← 1 to 30) {
      cube.setWay(Way.LEFT)
      map.tick()
      writeMapToImage()
    }
    for (i ← 1 to 30) {
      cube.setWay(Way.DOWN)
      map.tick()
      writeMapToImage()
    }
  }

  //  while (comm != 'e') {
  //    comm = StdIn.readChar()
  //    if(comm == 'w') {
  //      cube.setWay(Way.UP)
  //      println("\nUP")
  //    } else if(comm == 'd') {
  //      cube.setWay(Way.RIGHT)
  //      println("\nRIGHT")
  //    } else if(comm == 's') {
  //      cube.setWay(Way.DOWN)
  //      println("\nDOWN")
  //    } else if(comm == 'a') {
  //      cube.setWay(Way.LEFT)
  //      println("\nLEFT")
  //    }
  //    map.tick()
  //    writeMapToImage()
  //  }
  encoder.finish()
  NIOUtils.closeQuietly(out)

}