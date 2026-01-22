/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.task.impl;

import java.time.Instant;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.Team;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Abstract Task</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getEndTime <em>End Time</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getProgress <em>Progress</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#isComputeStartEndDynamically <em>Compute Start End Dynamically</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getTags <em>Tags</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getAssignedPersons <em>Assigned Persons</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getAssignedTeams <em>Assigned Teams</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getSubTasks <em>Sub Tasks</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractTaskImpl extends MinimalEObjectImpl.Container implements AbstractTask {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
	 * The default value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStartTime()
	 * @generated
	 * @ordered
	 */
    protected static final Instant START_TIME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStartTime()
	 * @generated
	 * @ordered
	 */
    protected Instant startTime = START_TIME_EDEFAULT;

    /**
	 * The default value of the '{@link #getEndTime() <em>End Time</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getEndTime()
	 * @generated
	 * @ordered
	 */
    protected static final Instant END_TIME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getEndTime() <em>End Time</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getEndTime()
	 * @generated
	 * @ordered
	 */
    protected Instant endTime = END_TIME_EDEFAULT;

    /**
	 * The default value of the '{@link #getProgress() <em>Progress</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getProgress()
	 * @generated
	 * @ordered
	 */
    protected static final int PROGRESS_EDEFAULT = 0;

    /**
	 * The cached value of the '{@link #getProgress() <em>Progress</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getProgress()
	 * @generated
	 * @ordered
	 */
    protected int progress = PROGRESS_EDEFAULT;

    /**
	 * The default value of the '{@link #isComputeStartEndDynamically() <em>Compute Start End Dynamically</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isComputeStartEndDynamically()
	 * @generated
	 * @ordered
	 */
    protected static final boolean COMPUTE_START_END_DYNAMICALLY_EDEFAULT = true;

    /**
	 * The cached value of the '{@link #isComputeStartEndDynamically() <em>Compute Start End Dynamically</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isComputeStartEndDynamically()
	 * @generated
	 * @ordered
	 */
    protected boolean computeStartEndDynamically = COMPUTE_START_END_DYNAMICALLY_EDEFAULT;

    /**
	 * The cached value of the '{@link #getTags() <em>Tags</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getTags()
	 * @generated
	 * @ordered
	 */
    protected EList<TaskTag> tags;

    /**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
    protected EList<Task> dependencies;

    /**
     * The cached value of the '{@link #getAssignedPersons() <em>Assigned Persons</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAssignedPersons()
     * @generated
     * @ordered
     */
    protected EList<Person> assignedPersons;

    /**
	 * The cached value of the '{@link #getAssignedTeams() <em>Assigned Teams</em>}' reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getAssignedTeams()
	 * @generated
	 * @ordered
	 */
    protected EList<Team> assignedTeams;

    /**
     * The cached value of the '{@link #getSubTasks() <em>Sub Tasks</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSubTasks()
     * @generated
     * @ordered
     */
    protected EList<Task> subTasks;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected AbstractTaskImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TaskPackage.Literals.ABSTRACT_TASK;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDescription() {
		return description;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__DESCRIPTION, oldDescription, description));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Instant getStartTime() {
		return startTime;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStartTime(Instant newStartTime) {
		Instant oldStartTime = startTime;
		startTime = newStartTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__START_TIME, oldStartTime, startTime));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Instant getEndTime() {
		return endTime;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEndTime(Instant newEndTime) {
		Instant oldEndTime = endTime;
		endTime = newEndTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__END_TIME, oldEndTime, endTime));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getProgress() {
		return progress;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setProgress(int newProgress) {
		int oldProgress = progress;
		progress = newProgress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__PROGRESS, oldProgress, progress));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isComputeStartEndDynamically() {
		return computeStartEndDynamically;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setComputeStartEndDynamically(boolean newComputeStartEndDynamically) {
		boolean oldComputeStartEndDynamically = computeStartEndDynamically;
		computeStartEndDynamically = newComputeStartEndDynamically;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY, oldComputeStartEndDynamically, computeStartEndDynamically));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<TaskTag> getTags() {
		if (tags == null)
		{
			tags = new EObjectResolvingEList<TaskTag>(TaskTag.class, this, TaskPackage.ABSTRACT_TASK__TAGS);
		}
		return tags;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Task> getDependencies() {
		if (dependencies == null)
		{
			dependencies = new EObjectResolvingEList<Task>(Task.class, this, TaskPackage.ABSTRACT_TASK__DEPENDENCIES);
		}
		return dependencies;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Person> getAssignedPersons() {
		if (assignedPersons == null)
		{
			assignedPersons = new EObjectResolvingEList<Person>(Person.class, this, TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS);
		}
		return assignedPersons;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Team> getAssignedTeams() {
		if (assignedTeams == null)
		{
			assignedTeams = new EObjectResolvingEList<Team>(Team.class, this, TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS);
		}
		return assignedTeams;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Task> getSubTasks() {
		if (subTasks == null)
		{
			subTasks = new EObjectContainmentEList<Task>(Task.class, this, TaskPackage.ABSTRACT_TASK__SUB_TASKS);
		}
		return subTasks;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
				return ((InternalEList<?>)getSubTasks()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TaskPackage.ABSTRACT_TASK__NAME:
				return getName();
			case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
				return getDescription();
			case TaskPackage.ABSTRACT_TASK__START_TIME:
				return getStartTime();
			case TaskPackage.ABSTRACT_TASK__END_TIME:
				return getEndTime();
			case TaskPackage.ABSTRACT_TASK__PROGRESS:
				return getProgress();
			case TaskPackage.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY:
				return isComputeStartEndDynamically();
			case TaskPackage.ABSTRACT_TASK__TAGS:
				return getTags();
			case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
				return getDependencies();
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
				return getAssignedPersons();
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
				return getAssignedTeams();
			case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
				return getSubTasks();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case TaskPackage.ABSTRACT_TASK__NAME:
				setName((String)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__START_TIME:
				setStartTime((Instant)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__END_TIME:
				setEndTime((Instant)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__PROGRESS:
				setProgress((Integer)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY:
				setComputeStartEndDynamically((Boolean)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__TAGS:
				getTags().clear();
				getTags().addAll((Collection<? extends TaskTag>)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends Task>)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
				getAssignedPersons().clear();
				getAssignedPersons().addAll((Collection<? extends Person>)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
				getAssignedTeams().clear();
				getAssignedTeams().addAll((Collection<? extends Team>)newValue);
				return;
			case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
				getSubTasks().clear();
				getSubTasks().addAll((Collection<? extends Task>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case TaskPackage.ABSTRACT_TASK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TaskPackage.ABSTRACT_TASK__START_TIME:
				setStartTime(START_TIME_EDEFAULT);
				return;
			case TaskPackage.ABSTRACT_TASK__END_TIME:
				setEndTime(END_TIME_EDEFAULT);
				return;
			case TaskPackage.ABSTRACT_TASK__PROGRESS:
				setProgress(PROGRESS_EDEFAULT);
				return;
			case TaskPackage.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY:
				setComputeStartEndDynamically(COMPUTE_START_END_DYNAMICALLY_EDEFAULT);
				return;
			case TaskPackage.ABSTRACT_TASK__TAGS:
				getTags().clear();
				return;
			case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
				getDependencies().clear();
				return;
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
				getAssignedPersons().clear();
				return;
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
				getAssignedTeams().clear();
				return;
			case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
				getSubTasks().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case TaskPackage.ABSTRACT_TASK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TaskPackage.ABSTRACT_TASK__START_TIME:
				return START_TIME_EDEFAULT == null ? startTime != null : !START_TIME_EDEFAULT.equals(startTime);
			case TaskPackage.ABSTRACT_TASK__END_TIME:
				return END_TIME_EDEFAULT == null ? endTime != null : !END_TIME_EDEFAULT.equals(endTime);
			case TaskPackage.ABSTRACT_TASK__PROGRESS:
				return progress != PROGRESS_EDEFAULT;
			case TaskPackage.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY:
				return computeStartEndDynamically != COMPUTE_START_END_DYNAMICALLY_EDEFAULT;
			case TaskPackage.ABSTRACT_TASK__TAGS:
				return tags != null && !tags.isEmpty();
			case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
				return assignedPersons != null && !assignedPersons.isEmpty();
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
				return assignedTeams != null && !assignedTeams.isEmpty();
			case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
				return subTasks != null && !subTasks.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", startTime: ");
		result.append(startTime);
		result.append(", endTime: ");
		result.append(endTime);
		result.append(", progress: ");
		result.append(progress);
		result.append(", computeStartEndDynamically: ");
		result.append(computeStartEndDynamically);
		result.append(')');
		return result.toString();
	}

} // AbstractTaskImpl
