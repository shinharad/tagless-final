package com.example
package todo

import java.time.format.DateTimeFormatter

object Main extends App {
  val crudController: crud.ControllerOld =
    crud
      .DependencyGraph
      .dsl(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy HH:mm"))

  crudController.run()
}
