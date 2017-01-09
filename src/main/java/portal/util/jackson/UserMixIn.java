package portal.util.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"authorities","createdBy","updatedBy", "eMailAddress", "username", 
	"password", "activationCode", "passwordChangeCode", "passwordChangeCodeCreatedAt", 
	"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled", 
	"numberOfFailedAttempts", "numberOfAccountLocks", "totalNumberOfFailedAttempts",
	"lastFailedAttemptTime"})
public interface UserMixIn {
	
}
