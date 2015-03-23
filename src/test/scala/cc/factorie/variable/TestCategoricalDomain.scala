package cc.factorie.variable

import org.junit.Test
import org.scalatest.junit._


class TestCategoricalDomain extends JUnitSuite with cc.factorie.util.FastLogging {

  @Test
  def testCategoricalDomain(): Unit = {

    object FootSizeDomain extends CategoricalDomain[String] {
      value("LARGE")
      value("SMALL")
      freeze()
    }
    // test domain properties
    assert(FootSizeDomain.dimensionSize == 2)
    assert(FootSizeDomain.dimensionDomain == FootSizeDomain)

    // Add new category
    // here should raise an exception, but current implementation just ignore it
    FootSizeDomain += "MEDIUM"
    assert(FootSizeDomain.value("MEDIUM") == null)
    FootSizeDomain.unfreeze()
    FootSizeDomain += "MEDIUM"
    assert(FootSizeDomain.value("MEDIUM") != null)
    assert(FootSizeDomain.dimensionSize == 3)
    FootSizeDomain.freeze()

    // read category values, should be an instance of CategoricalValue[String]
    assert(FootSizeDomain.head.isInstanceOf[CategoricalValue[String]])
    assert(FootSizeDomain.head.category == "LARGE")

    class FootSize(category:String) extends CategoricalVariable(category) {
      override def domain = FootSizeDomain
    }
    // test domain variable
    var v = new FootSize("LARGE")
    assert(v.value == FootSizeDomain.value("LARGE"))
    assert(v.domain == FootSizeDomain)

  }

}
