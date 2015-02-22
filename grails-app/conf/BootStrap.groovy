import com.bellifull.Role
import com.bellifull.User
import com.bellifull.UserRole

class BootStrap {

    def init = { servletContext ->
		//def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
		def adminRole = new Role(authority: 'ROLE_ADMIN').save()
		def userRole = new Role(authority: 'ROLE_USER').save()
		//def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
  
		def testUser = new User(username: 'me', password: 'password')
		testUser.save(flush: true)
  
		UserRole.create testUser, adminRole, true
	//	userRole.save(flush:true)
		
		assert User.count() == 1
		assert Role.count() == 2
		assert UserRole.count() == 1
    }
    def destroy = {
    }
}
