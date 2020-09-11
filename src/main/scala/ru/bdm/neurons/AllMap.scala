package ru.bdm.neurons

class AllMap(var cubes: Seq[Cube]) {
  val width = 930
  val height = 930
  val arr: Array[Array[Int]] = Array.ofDim[Int](width, height)
  for (i ← 0 until width)
    for (j ← 0 until height)
      arr(i)(j) = 0

  def start(): Unit = {
    for (cube ← cubes) {
      fill(cube.id, cube.x - cube.width - cube.width / 2, cube.y - cube.height - cube.height / 2, cube.width * 3, cube.height * 3)
    }
  }

  def tick(): Unit = {
    cubes.foreach(_.tick())
    var listRemoved: List[Cube] = Nil
    cubes.foreach { cube ⇒
      if (cube.x + cube.width / 2 > width || cube.x - cube.width / 2 < 0 ||
        cube.y + cube.height / 2 > height || cube.y - cube.height / 2 < 0)
        cube.isLive = false
      if (!cube.isLive) {
        listRemoved ::= cube
      } else {
        val start_x = math.max(cube.x, 0)
        val end_x = math.min(cube.x + cube.width, this.width)
        val start_y = math.max(cube.y, 0)
        val end_y = math.min(cube.y + cube.height, this.height)

        for (i ← start_x until end_x)
          for (j ← start_y until end_y) {
            if (arr(i)(j) == 0) {
              cube.score += 1
              arr(i)(j) = -cube.id
            } else if (arr(i)(j) == cube.id || arr(i)(j) == -cube.id)
              ()
            else {
              cube.score += 5
              arr(i)(j) = -cube.id
            }
          }
      }

      cubes = cubes.filterNot(listRemoved.contains(_))

      for {
        i ← 0 until width
        j ← 0 until height
        cube ← listRemoved
      } {
        if (arr(i)(j) == cube.id || arr(i)(j) == -cube.id)
          arr(i)(j) = 0
      }
    }
    var sum = 0
    for {
      i ← 0 until width
      j ← 0 until height
    }{
      if (arr(i)(j) != 0)
        sum += 1
    }
    println(s"sum = $sum")
  }

  def fill(id: Int, x: Int, y: Int, width: Int, height: Int): Unit = {
    val start_x = math.max(x, 0)
    val end_x = math.min(x + width, this.width)
    val start_y = math.max(y, 0)
    val end_y = math.min(y + height, this.height)
    for (i ← start_x until end_x)
      for (j ← start_y until end_y)
        arr(i)(j) = id
  }
}
