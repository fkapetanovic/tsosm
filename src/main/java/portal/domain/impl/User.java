package portal.domain.impl;

import portal.domain.AuditableOnUpdate;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import portal.domain.SoftDeletable;

public class User extends DataEntity implements SoftDeletable,
		AuditableOnUpdate, UserDetails, Serializable {

	private static final long serialVersionUID = 1L;
	private String eMailAddress;
	private String username;
	private String password;
	private String retypePassword;
	private String activationCode;
	private String passwordChangeCode;
	private Date passwordChangeCodeCreatedAt;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private int numberOfFailedAttempts;
	private int numberOfAccountLocks;
	private int totalNumberOfFailedAttempts;
	private Date lastFailedAttemptTime;

	private Collection<Authority> authorities = new HashSet<Authority>();

	private Date createDate;
	private Date updateDate;
	private User createdBy;
	private User updatedBy;
	private boolean deleted;

	public String geteMailAddress() {
		return eMailAddress;
	}

	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public User getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public User getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getPasswordChangeCode() {
		return passwordChangeCode;
	}

	public void setPasswordChangeCode(String passwordChangeCode) {
		this.passwordChangeCode = passwordChangeCode;
	}

	public Date getPasswordChangeCodeCreatedAt() {
		return passwordChangeCodeCreatedAt;
	}

	public void setPasswordChangeCodeCreatedAt(
			Date passwordChangeCodeCreatedAt) {
		this.passwordChangeCodeCreatedAt = passwordChangeCodeCreatedAt;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

		for (Authority authority : authorities) {
			grantedAuthorities.add((GrantedAuthority) authority);
		}
		return grantedAuthorities;
	}

	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(Authority authority) {
		this.authorities.add(authority);
	}

	public void clearAuthorities() {
		this.authorities.clear();
	}

	public Date getLastFailedAttemptTime() {
		return lastFailedAttemptTime;
	}

	public void setLastFailedAttemptTime(
			Date lastUnsuccessfulAttemptTime) {
		this.lastFailedAttemptTime = lastUnsuccessfulAttemptTime;
	}

	public int getNumberOfAccountLocks() {
		return numberOfAccountLocks;
	}

	public void setNumberOfAccountLocks(int numberOfAccountLocks) {
		this.numberOfAccountLocks = numberOfAccountLocks;
	}

	public int getNumberOfFailedAttempts() {
		return numberOfFailedAttempts;
	}

	public void setNumberOfFailedAttempts(int numberOfUnsuccessfulAttempts) {
		this.numberOfFailedAttempts = numberOfUnsuccessfulAttempts;
	}

	public int getTotalNumberOfFailedAttempts() {
		return totalNumberOfFailedAttempts;
	}

	public void setTotalNumberOfFailedAttempts(int totalNumberOfAccountLocks) {
		this.totalNumberOfFailedAttempts = totalNumberOfAccountLocks;
	}
}
