package com.lucastex.featurable

import grails.test.*

class DateRangeCreatorTests extends GroovyTestCase {
    
    void testDays() {
		def tomorrow = 1.day
		assertNotNull tomorrow
    }

    void testMonths() {
		def nextMonth = 1.month
		assertNotNull nextMonth
    }

    void testYears() {
		def twoMoreYears = 2.day
		assertNotNull twoMoreYears
    }
}
