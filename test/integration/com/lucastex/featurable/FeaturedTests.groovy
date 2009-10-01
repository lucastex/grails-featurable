package com.lucastex.featurable

import grails.test.*

class FeaturedTests extends GroovyTestCase {
    
    void testFeature() {
	
		//Insert the book in the database
		def book = new Book()
		book.title = "Mastering Guitar Hero 3"
		book.isbn = "123456789-40"
		assert book.save()
		
		//Feature it
		assertTrue book.feature()
    }

	void testUnfeature() {
		
		//Insert the book in the database
		def book = new Book()
		book.title = "PS3 for dummies"
		book.isbn = "111222333-99"
		assert book.save()
		
		//Feature it
		assertTrue book.feature()
		
		//Unfeature it
		assertTrue book.unfeature()
		
		//Can`t unfeature it again
		assertFalse book.unfeature()
	}
	
	void testFeaturedProperty() {
		
		//Insert the book in the database
		def book = new Book()
		book.title = "Definitive Guide to Grails"
		book.isbn = "177648190-11"
		assert book.save()
		
		//Feature it
		assertTrue book.feature()
		
		//Is it featured?
		assertTrue book.featured
		
		//Unfeature it
		assertTrue book.unfeature()
		
		//Hey! You can't be featured!
		assertFalse book.featured
	}
	
	void testListFeatured() {
		
		//Insert the book in the database
		def book = new Book()
		book.title = "Mastering Guitar Hero 3"
		book.isbn = "123456789-40"
		assert book.save()
		assertTrue book.feature(1.year)
		
		//Insert the book in the database
		def book2 = new Book()
		book2.title = "PS3 for dummies"
		book2.isbn = "111222333-99"
		assert book2.save()
		assertTrue book2.feature(2.years)
		
		def book3 = new Book()
		book3.title = "Definitive Guide to Grails"
		book3.isbn = "177648190-11"
		assert book3.save()
		assertTrue book3.feature(3.days)
		
		def featuredBooks = Book.findFeatured()
		assertEquals "3 featured items", featuredBooks.size(), 3
		
		book2.unfeature()
		
		featuredBooks = Book.findFeatured()
		assertEquals "2 featured items", featuredBooks.size(), 2
		
		featuredBooks = Book.findFeatured(1.month)
		assertEquals "just one featured item for next month", featuredBooks.size(), 1
	}
	
	
}
