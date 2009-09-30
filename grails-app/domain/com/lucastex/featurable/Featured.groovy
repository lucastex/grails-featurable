package com.lucastex.featurable

class Featured {

	Long delegateId
	String className
	Date expiresAt
	
	static mapping = {
		className column:'className', index:'ClassName_Index'
	}

    static constraints = {
		delegateId(nullable:false)
		className(nullable:false)
		expiresAt(validator: { it > new Date() })
    }

	String toString() {
		"[${className}] -> id: ${delegateId} will expire in ${expiresAt}"
	}
}
