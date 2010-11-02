package com.payneteasy.superfly.spisupport;

/**
 * Service to deal with HOTP management.
 *
 * @author Roman Puchkovskiy
 */
public interface HOTPService {
	/**
	 * Sends a table to a user if provider supports this.
	 * 
	 * @param userId	ID of a user
	 */
	void sendTableIfSupported(long userId);

	/**
	 * Resets a table and sends a new table to a user if provider supports
	 * sending.
	 * 
	 * @param userId	ID of a user
	 */
	void resetTableAndSendIfSupported(long userId);
}
