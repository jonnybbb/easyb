package org.easyb.plugin.xmlunit

import org.custommonkey.xmlunit.Diff
import org.easyb.exception.VerificationException

class XMLUnitCategories {

  static void shouldBeIdenticalTo(Object self, value, msg = null) {
    def diff = new Diff(self, value)
    if (!diff.identical()) {
      throwValidationException("Expected documents to be identical. Differences were ${diff.toString()}", msg)
    }
  }
  
  static void shouldBeSimilarTo(Object self, value, msg = null) {
    def diff = new Diff(self, value)
    if (!diff.similar()) {
      throwValidationException("Expected documents to be similar. Differences were ${diff.toString()}", msg)
    }
  }

  static void identical(Object self, value, msg = null) {
    shouldBeIdenticalTo(self, value, msg)
  }

  static void shouldBeIdenticalWith(Object self, value, msg = null) {
    shouldBeIdenticalTo(self, value, msg)
  }

  static void shouldBeIdentical(Object self, value, msg = null) {
    shouldBeIdenticalTo(self, value, msg)
  }

  static void shouldBeSimilar(Object self, value, msg = null) {
    shouldBeSimilarTo(self, value, msg)
  }

  static void similar(Object self, value, msg = null) {
    shouldBeSimilarTo(self, value, msg)
  }

  static void shouldBeSimilarWith(Object self, value, msg = null) {
    shouldBeSimilarTo(self, value, msg)
  }

  static void throwValidationException(out, msg) {
    throw new VerificationException((msg != null ? "\"" + msg + "\", " + out : out))
  }
}