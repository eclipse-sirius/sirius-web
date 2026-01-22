/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.task.Objective;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.TaskTag;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Project</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getOwnedTasks <em>Owned Tasks</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getOwnedObjectives <em>Owned Objectives</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getOwnedTags <em>Owned Tags</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends MinimalEObjectImpl.Container implements Project {
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
     * The cached value of the '{@link #getOwnedTasks() <em>Owned Tasks</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOwnedTasks()
     * @generated
     * @ordered
     */
    protected EList<Task> ownedTasks;

    /**
	 * The cached value of the '{@link #getOwnedObjectives() <em>Owned Objectives</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOwnedObjectives()
	 * @generated
	 * @ordered
	 */
    protected EList<Objective> ownedObjectives;

    /**
     * The cached value of the '{@link #getOwnedTags() <em>Owned Tags</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOwnedTags()
     * @generated
     * @ordered
     */
    protected EList<TaskTag> ownedTags;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ProjectImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TaskPackage.Literals.PROJECT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.PROJECT__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Task> getOwnedTasks() {
		if (ownedTasks == null)
		{
			ownedTasks = new EObjectContainmentEList<Task>(Task.class, this, TaskPackage.PROJECT__OWNED_TASKS);
		}
		return ownedTasks;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Objective> getOwnedObjectives() {
		if (ownedObjectives == null)
		{
			ownedObjectives = new EObjectContainmentEList<Objective>(Objective.class, this, TaskPackage.PROJECT__OWNED_OBJECTIVES);
		}
		return ownedObjectives;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<TaskTag> getOwnedTags() {
		if (ownedTags == null)
		{
			ownedTags = new EObjectContainmentEList<TaskTag>(TaskTag.class, this, TaskPackage.PROJECT__OWNED_TAGS);
		}
		return ownedTags;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TaskPackage.PROJECT__OWNED_TASKS:
				return ((InternalEList<?>)getOwnedTasks()).basicRemove(otherEnd, msgs);
			case TaskPackage.PROJECT__OWNED_OBJECTIVES:
				return ((InternalEList<?>)getOwnedObjectives()).basicRemove(otherEnd, msgs);
			case TaskPackage.PROJECT__OWNED_TAGS:
				return ((InternalEList<?>)getOwnedTags()).basicRemove(otherEnd, msgs);
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
			case TaskPackage.PROJECT__NAME:
				return getName();
			case TaskPackage.PROJECT__OWNED_TASKS:
				return getOwnedTasks();
			case TaskPackage.PROJECT__OWNED_OBJECTIVES:
				return getOwnedObjectives();
			case TaskPackage.PROJECT__OWNED_TAGS:
				return getOwnedTags();
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
			case TaskPackage.PROJECT__NAME:
				setName((String)newValue);
				return;
			case TaskPackage.PROJECT__OWNED_TASKS:
				getOwnedTasks().clear();
				getOwnedTasks().addAll((Collection<? extends Task>)newValue);
				return;
			case TaskPackage.PROJECT__OWNED_OBJECTIVES:
				getOwnedObjectives().clear();
				getOwnedObjectives().addAll((Collection<? extends Objective>)newValue);
				return;
			case TaskPackage.PROJECT__OWNED_TAGS:
				getOwnedTags().clear();
				getOwnedTags().addAll((Collection<? extends TaskTag>)newValue);
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
			case TaskPackage.PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TaskPackage.PROJECT__OWNED_TASKS:
				getOwnedTasks().clear();
				return;
			case TaskPackage.PROJECT__OWNED_OBJECTIVES:
				getOwnedObjectives().clear();
				return;
			case TaskPackage.PROJECT__OWNED_TAGS:
				getOwnedTags().clear();
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
			case TaskPackage.PROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TaskPackage.PROJECT__OWNED_TASKS:
				return ownedTasks != null && !ownedTasks.isEmpty();
			case TaskPackage.PROJECT__OWNED_OBJECTIVES:
				return ownedObjectives != null && !ownedObjectives.isEmpty();
			case TaskPackage.PROJECT__OWNED_TAGS:
				return ownedTags != null && !ownedTags.isEmpty();
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
		result.append(')');
		return result.toString();
	}

} // ProjectImpl
