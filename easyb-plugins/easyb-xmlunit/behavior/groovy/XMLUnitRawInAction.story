import org.custommonkey.xmlunit.Diff
import org.easyb.exception.VerificationException

using "xmlunit"

scenario "XML documents are compared", {
  given "some xml document", {
    control = """<account><id>3A-00</id><name>acme</name></account>"""
  }
  then "XMLUnit allows you to call similar and identical on it against another doc", {
    testXML = """<account><name>acme</name><id>3A-00</id></account>"""
    diff = new Diff(control, testXML)
    diff.similar().shouldBe true
    diff.identical().shouldBe false
  }
}

scenario "XML documents are compared easier via xml unit plugin via the identical call", {
  given "some xml document", {
    control = """<account><id>3A-00</id><name>acme</name></account>"""
  }
  then "the plugin should report that a document isn't identical", {
    testXML = """<account><name>acme</name><id>3A-00</id></account>"""
    ensureThrows(VerificationException) {
      testXML.shouldBeIdenticalTo control, "documents should be identical!!"
    }
  }
  and "the API should support multiple variations of the call, like shouldBeIdenticalTo", {
    ensureThrows(VerificationException) {
      testXML.shouldBeIdenticalTo control
    }
  }
  and "the API should support multiple variations of the call, like shouldBeIdenticalWith", {
    ensureThrows(VerificationException) {
      testXML.shouldBeIdenticalWith control
    }
  }
  and "the API should support multiple variations of the call, like shouldBeIdentical", {
    ensureThrows(VerificationException) {
      testXML.shouldBeIdentical control
    }
  }
  and "the API should support multiple variations of the call, like identical", {
    ensureThrows(VerificationException) {
      testXML.identical control
    }
  }
}


scenario "XML documents are compared easier via xml unit plugin via the identical call no exception", {
  given "some xml document", {
    control = """<account><id>3A-00</id><name>acme</name></account>"""
  }
  then "the plugin should report that a document isn't identical", {
    testXML = """<account><id>3A-00</id><name>acme</name></account>"""
    testXML.shouldBeIdenticalTo control, "documents should be identical!!"
  }
  and "the API should support multiple variations of the call, like shouldBeIdenticalTo", {
    testXML.shouldBeIdenticalTo control
  }
  and "the API should support multiple variations of the call, like shouldBeIdenticalWith", {
    testXML.shouldBeIdenticalWith control
  }
  and "the API should support multiple variations of the call, like shouldBeIdentical", {
    testXML.shouldBeIdentical control
  }
  and "the API should support multiple variations of the call, like identical", {
    testXML.identical control
  }
}


scenario "XML documents are compared easier via xml unit plugin via the similar call", {
  given "some xml document", {
    control = """<account><id>3A-00</id><name>acme</name></account>"""
  }
  then "the plugin should report that a document isn't identical", {
    testXML = """<account><name>acme</name><id>3A-00</id></account>"""
    testXML.shouldBeSimilarTo control, "documents should be identical!!"

  }
  and "the API should support multiple variations of the call, like shouldBeIdenticalTo", {
    testXML.shouldBeSimilarTo control
  }
  and "the API should support multiple variations of the call, like shouldBeIdenticalWith", {
    testXML.shouldBeSimilarWith control
  }
  and "the API should support multiple variations of the call, like shouldBeIdentical", {
    testXML.shouldBeSimilar control
  }
  and "the API should support multiple variations of the call, like identical", {
    testXML.similar control
  }
}

scenario "the plugin exposes an instance of the XMLUnit object", {
  then "the stories should be able to ask for it", {
    //note this XMLUnit is from the binding and already init'ed
    XMLUnit.version.shouldBe "1.3alpha"
    XMLUnit.getIgnoreWhitespace().shouldBe true 
  }
}