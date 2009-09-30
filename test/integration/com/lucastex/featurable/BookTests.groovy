package com.lucastex.featurable

import grails.test.*

class BookTests extends GroovyTestCase {
    
    void testStaticPropertyExistance() {
		assert Book.class.metaClass.hasProperty(Book.class, 'featurable')
    }

	void testStaticPropertyValue() {
		assert Book.featurable == true
    }
}
