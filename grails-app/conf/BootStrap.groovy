import com.bellifull.Role
import com.bellifull.User
import com.bellifull.UserRole

class BootStrap {

    def init = { servletContext ->
		
		def userRole = Role.findByAuthority("ROLE_USER") ?: new Role(authority: "ROLE_USER").save()
		def adminRole = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save()
		
		//def adminRole = new Role(authority: "ROLE_ADMIN").save(failOnError: true)
	//	def userRole = new Role(authority: "ROLE_USER").save(failOnError: true)
  
		def testUser = new User(username: "me", password: "password")
		testUser.save(flush: true)
  
		UserRole.create testUser, adminRole, true
		
		assert User.count() == 1
		assert Role.count() == 2
		assert UserRole.count() == 1
    }
    def destroy = {
    }
}
