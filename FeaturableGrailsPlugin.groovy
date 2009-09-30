import com.lucastex.featurable.*
import java.util.Calendar as CAL

class FeaturableGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "1.1.1 > *"
    def dependsOn = [hibernate:"1.1 > *"]
    def pluginExcludes = [
            "grails-app/views/error.gsp",
			"grails-app/domain/com/lucastex/featurable/Book.groovy",
			"test/integration/com/lucastex/featurable/BookTests.groovy",
			"test/integration/com/lucastex/featurable/DateRangeCreatorTests.groovy",
			"test/integration/com/lucastex/featurable/FeaturedTests.groovy"
    ]

    def author = "Lucas Frare Teixeira"
    def authorEmail = "lucastex@gmail.com"
    def title = "Grails Featurable Plugin"
    def description = "Add the 'featurable' behavior to domain classes."
    def documentation = "http://grails.org/plugin/featurable"

    def doWithSpring = {
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def doWithWebDescriptor = { xml ->
    }

    def doWithDynamicMethods = { ctx ->
	
		println "Adding integer properties"
		Integer.metaClass.propertyMissing = { name ->
			
			switch (name) {
				case "day": 
					return DateRangeCreator.fromDays(delegate)
					break;
				case "month": 
					return DateRangeCreator.fromMonths(delegate)
					break;
				case "year":
					return DateRangeCreator.fromYears(delegate)
					break;
				default:
					throw new MissingPropertyException(delegate)
			}
		}
	
		application.domainClasses*.clazz.each { currentClass ->
			if (currentClass.metaClass.hasProperty(currentClass, 'featurable')) {
				def isFeaturable = currentClass.featurable
				if (isFeaturable) {
					
					println "Adding the 'feature' method to ${currentClass}" 	
					currentClass.metaClass.feature = { expiresAt ->
						
						if (!expiresAt) {
							def c = CAL.instance
							c.add CAL.DAY_OF_MONTH, 30
							expiresAt = c.time
						}
						
						def featured = new Featured(className: currentClass.name, delegateId: delegate.id, expiresAt: expiresAt)
						return featured.save() ? true : false
					}
					
					println "Adding the 'unfeature' method to ${currentClass}"
					currentClass.metaClass.unfeature = {
						def featured = Featured.findWhere(className: currentClass.name, delegateId: delegate.id)
						if (featured) {
							featured.delete()
							return true
						} else {
							return false
						}
					}
					
					println "Adding the 'featured' property to ${currentClass}"
					currentClass.metaClass.propertyMissing = { name ->
						
						if (name.equals("featured")) {
							def featured = Featured.findWhere(className: currentClass.name, delegateId: delegate.id)
							return (featured != null)
						} else {
							throw new MissingPropertyException(delegate)
						}
					}
					
					println "Adding the 'findFeatured' static method to ${currentClass}"
					currentClass.metaClass.'static'.findFeatured = { limit ->
						if (!limit) {
							limit = new Date() + 30
						}
						return Featured.withCriteria() {
							lt('expiresAt', limit)
							eq('className', currentClass.name)
						}
					} //findFeatured
				}//isFeaturable
			}//currentClass
		}//all classes
    }//doWithDynamicMethods

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
