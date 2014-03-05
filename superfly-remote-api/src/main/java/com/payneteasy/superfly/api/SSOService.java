package com.payneteasy.superfly.api;

import java.util.List;

/**
 * Service for SSO communication.
 * 
 * @author Roman Puchkovskiy
 * @since 1.0
 */
public interface SSOService {
	/**
	 * Attempts to authenticate user from the current system using the supplied
	 * username and password. If successful, returns user object, else returns
	 * null.
	 * 
	 * @param username
	 *            username to use when authenticating
	 * @param password
	 *            password to use when authenticating
	 * @param authRequestInfo
	 *            additional info about authentication request
	 * @return user object on success or null when authentication failed (for
	 *         instance, no such user, or password mismatched, or user is
	 *         blocked...)
	 */
	SSOUser authenticate(String username, String password, AuthenticationRequestInfo authRequestInfo);

    /**
   	 * Returns the same data as if user was successfully authenticated,
     * but no actual authentication is made. This could be useful for
     * impersonation feature.
   	 *
   	 * @param username
   	 *            username to get user
   	 * @param subsystemIdentifier
   	 *            identifier of a subsystem
   	 * @return user object on success or null when retrieval fails (for
   	 *         instance, no such user)
     * @since 1.5
   	 */
   	SSOUser pseudoAuthenticate(String username, String subsystemIdentifier);

	/**
	 * Sends data about this system to SSO server.
	 * 
	 * @param subsystemIdentifier
	 *            identifier of this system (it's just a hint for SSO server)
	 * @param actionDescriptions
	 *            descriptions of actions of this system
	 */
	void sendSystemData(String subsystemIdentifier, ActionDescription[] actionDescriptions);

	/**
	 * Returns a list of users with their actions granted through role with the
	 * given principal.
	 * 
	 * @param subsystemIdentifier
	 *            identifier of the subsystem from which users will be obtained
	 * @return users with actions
	 * @since 1.1
	 */
	List<SSOUserWithActions> getUsersWithActions(String subsystemIdentifier);

	/**
	 * Authenticates a user using an HOTP implementation configured on the
	 * Superfly server.
	 * 
	 * @param username	name of the user to authenticate
	 * @param hotp		one-time password
	 * @return true if authentication is successful
	 * @since 1.2
	 */
	boolean authenticateUsingHOTP(String username, String hotp);

	/**
	 * Registers user and gives him requested principal.
     * User is created in incomplete state. In that state, if
     * a registration with the same username is made, existing user
     * is destroyed. To complete a user, call #completeUser() method.
     * Also, user is completed when he logs in.
	 * 
	 * @param username
	 *            name of the user
	 * @param password
	 *            password of the user
	 * @param email
	 *            email of the user
	 * @param subsystemHint
	 *            hint to the identifier of a subsystem from which roles will be
	 *            taken
	 * @param roleGrants
	 *            which roles to grant
	 * @throws UserExistsException
	 *             if user with such a name already exists
	 * @throws PolicyValidationException
	 * @throws BadPublicKeyException
	 * @throws MessageSendException
	 * @since 1.1
	 * @see RoleGrantSpecification
     * @see #completeUser(String)
	 */
	void registerUser(String username, String password, String email, String subsystemHint,
			RoleGrantSpecification[] roleGrants,
			String name, String surname, String secretQuestion, String secretAnswer,
			String publicKey, String organization)
			throws UserExistsException, PolicyValidationException,
			BadPublicKeyException, MessageSendException;

    /**
   	 * Registers user and gives him requested principal.
     * User is created in incomplete state. In that state, if
     * a registration with the same username is made, existing user
     * is destroyed. To complete a user, call #completeUser() method.
     * Also, user is completed when he logs in.
   	 *
   	 * @param registerRequest
   	 *            register request containing all the
     *            data needed to register a user
     *
   	 * @throws UserExistsException
   	 *             if user with such a name already exists
   	 * @throws PolicyValidationException
   	 * @throws BadPublicKeyException
   	 * @throws MessageSendException
   	 * @since 1.1
   	 * @see RoleGrantSpecification
     * @see #completeUser(String)
   	 */
   	void registerUser(UserRegisterRequest registerRequest)
            throws UserExistsException, PolicyValidationException,
   			BadPublicKeyException, MessageSendException;
	
    /**
     * 
     * @param userName user name
     * @param password password
     * @since 1.2
     */
    void changeTempPassword(String userName, String password) throws PolicyValidationException;

    /**
     * Returns a description of the given user.
     * 
     * @param username	username
     * @return description or null if user is not found
     * @since 1.2-4
     */
    UserDescription getUserDescription(String username);

    /**
     * Resets an OTP table and sends it to the user via email in encrypted form
     * (if HOTPProvider supports exporting in binary form).
     * Subsystem to use when sending is determined automatically (for instance,
     * by certificate of a calling subsystem).
     *
     * @param username	            name of the user
     * @throws UserNotFoundException    if no such user
     * @throws MessageSendException     if error while sending message occurs
     * @since 1.2-4
     */
    void resetAndSendOTPTable(String username) throws UserNotFoundException, MessageSendException;
    
    /**
     * Resets an OTP table and sends it to the user via email in encrypted form
     * (if HOTPProvider supports exporting in binary form).
     *
     * @param subsystemIdentifier   identifier of subsystem
     * which smtp server to user when sending message
     * @param username	            name of the user
     * @throws UserNotFoundException    if no such user
     * @throws MessageSendException     if error while sending message occurs
     * @since 1.3-1
     */
    void resetAndSendOTPTable(String subsystemIdentifier, String username) throws UserNotFoundException, MessageSendException;

    /**
     * Updates user's fields.
     * 
     * @param user	user fields
     * @throws UserNotFoundException	if user with such a name is not found
     * @throws BadPublicKeyException	if public key is not valid
     * @since 1.2-4
     */
    void updateUserDescription(UserDescription user) throws UserNotFoundException, BadPublicKeyException;

    /**
     * Resets user's password if policy requires this.
     * 
     * @param username		name of the user
     * @param newPassword	new password
     * @throws UserNotFoundException		if not such user exists
     * @throws PolicyValidationException	if password is bad
     * @since 1.2-4
     */
    void resetPassword(String username, String newPassword) throws UserNotFoundException, PolicyValidationException;

    /**
     * Finds users by list of their logins and returns their status information.
     *
     * @param userNames list of user names (logins)
     * @return user statuses
     * @since 1.3-5
     */
    List<UserStatus> getUserStatuses(List<String> userNames);

    /**
     * Exchanges subsystem token to SSOUser. After this operation
     * returns, subsystem token is not valid anymore and cannot
     * be used for exchanging.
     *
     * @param subsystemToken    subsystem token
     * @return SSOUser or null if token does not exist, expired or
     * already used
     * @since 1.4
     */
    SSOUser exchangeSubsystemToken(String subsystemToken);

    /**
     * Touches sessions: that is, updates their access time to avoid
     * removal. If a session was issued by an SSO session, the latter
     * is touched too.
     *
     * @param sessionIds    IDs of sessions to touch
     */
    void touchSessions(List<Long> sessionIds);

    /**
     * Makes a user complete. This protects a registered user
     * from being overwritten.
     *
     * @param username  name of the user to complete
     * @see #registerUser(UserRegisterRequest)
     * @since 1.5
     */
    void completeUser(String username);
}
