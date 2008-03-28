package org.easyb.maven

import static org.junit.Assert.*
import org.junit.Test

class FooTest {
    @Test
    public void shouldTestGroovy() {
        Foo foo = new Foo()
        assertEquals("bar", foo.saySomething())
    }
}