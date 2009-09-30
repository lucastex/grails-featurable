package com.lucastex.featurable

import java.util.Calendar as CAL
import static java.util.Calendar.*

class DateRangeCreator {
	
	static Date fromDays(quantity) {
		def c = CAL.instance
		c.add DAY_OF_MONTH, quantity
		c.time
	}
	
	static Date fromMonths(quantity) {
		def c = CAL.instance
		c.add MONTH, quantity
		c.time
	}
	
	static Date fromYears(quantity) {
		def c = CAL.instance
		c.add YEAR, quantity
		c.time
	}
}