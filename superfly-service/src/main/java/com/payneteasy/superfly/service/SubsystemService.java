package com.payneteasy.superfly.service;

import java.util.List;

import com.payneteasy.superfly.model.RoutineResult;
import com.payneteasy.superfly.model.ui.subsystem.UISubsystem;
import com.payneteasy.superfly.model.ui.subsystem.UISubsystemForFilter;
import com.payneteasy.superfly.model.ui.subsystem.UISubsystemForList;

/**
 * Service for subsystems.
 * 
 * @author Roman Puchkovskiy
 */
public interface SubsystemService {
	/**
	 * Returns a list of all subsystems for a list in the UI.
	 * 
	 * @return subsystems
	 */
	List<UISubsystemForList> getSubsystems();

	/**
	 * Creates a subsystem.
	 * 
	 * @param subsystem
	 *            subsystem to create
	 */
	RoutineResult createSubsystem(UISubsystem subsystem);

	/**
	 * Updates a subsystem.
	 * 
	 * @param subsystem
	 *            subsystem to update
	 */
	RoutineResult updateSubsystem(UISubsystem subsystem);

	/**
	 * Deletes a subsystem.
	 * 
	 * @param subsystemId
	 *            ID of a subsystem to delete
	 */
	RoutineResult deleteSubsystem(Long subsystemId);

	/**
	 * Returns a list of all subsystems for a subsystem-based filter.
	 * 
	 * @return subsystems
	 */
	List<UISubsystemForFilter> getSubsystemsForFilter();

	/**
	 * Returns a subsystem by its ID.
	 * 
	 * @param subsystemId	ID of the subsystem
	 * @return subsystem or null if no such subsystem
	 */
	UISubsystem getSubsystem(long subsystemId);

	/**
	 * Returns a subsystem by its name.
	 * 
	 * @param subsystemName	name of the subsystem
	 * @return subsystem or null if no such subsystem
	 */
	UISubsystem getSubsystemByName(String subsystemName);
}
