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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.Team;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Company</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.task.impl.CompanyImpl#getOwnedTeams <em>Owned Teams</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.CompanyImpl#getOwnedPersons <em>Owned Persons</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.CompanyImpl#getOwnedProjects <em>Owned Projects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompanyImpl extends ResourceImpl implements Company {
    /**
     * The cached value of the '{@link #getOwnedTeams() <em>Owned Teams</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOwnedTeams()
     * @generated
     * @ordered
     */
    protected EList<Team> ownedTeams;

    /**
     * The cached value of the '{@link #getOwnedPersons() <em>Owned Persons</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOwnedPersons()
     * @generated
     * @ordered
     */
    protected EList<Person> ownedPersons;

    /**
     * The cached value of the '{@link #getOwnedProjects() <em>Owned Projects</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOwnedProjects()
     * @generated
     * @ordered
     */
    protected EList<Project> ownedProjects;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected CompanyImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TaskPackage.Literals.COMPANY;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Team> getOwnedTeams() {
		if (ownedTeams == null)
		{
			ownedTeams = new EObjectContainmentEList<Team>(Team.class, this, TaskPackage.COMPANY__OWNED_TEAMS);
		}
		return ownedTeams;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Person> getOwnedPersons() {
		if (ownedPersons == null)
		{
			ownedPersons = new EObjectContainmentEList<Person>(Person.class, this, TaskPackage.COMPANY__OWNED_PERSONS);
		}
		return ownedPersons;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Project> getOwnedProjects() {
		if (ownedProjects == null)
		{
			ownedProjects = new EObjectContainmentEList<Project>(Project.class, this, TaskPackage.COMPANY__OWNED_PROJECTS);
		}
		return ownedProjects;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TaskPackage.COMPANY__OWNED_TEAMS:
				return ((InternalEList<?>)getOwnedTeams()).basicRemove(otherEnd, msgs);
			case TaskPackage.COMPANY__OWNED_PERSONS:
				return ((InternalEList<?>)getOwnedPersons()).basicRemove(otherEnd, msgs);
			case TaskPackage.COMPANY__OWNED_PROJECTS:
				return ((InternalEList<?>)getOwnedProjects()).basicRemove(otherEnd, msgs);
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
			case TaskPackage.COMPANY__OWNED_TEAMS:
				return getOwnedTeams();
			case TaskPackage.COMPANY__OWNED_PERSONS:
				return getOwnedPersons();
			case TaskPackage.COMPANY__OWNED_PROJECTS:
				return getOwnedProjects();
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
			case TaskPackage.COMPANY__OWNED_TEAMS:
				getOwnedTeams().clear();
				getOwnedTeams().addAll((Collection<? extends Team>)newValue);
				return;
			case TaskPackage.COMPANY__OWNED_PERSONS:
				getOwnedPersons().clear();
				getOwnedPersons().addAll((Collection<? extends Person>)newValue);
				return;
			case TaskPackage.COMPANY__OWNED_PROJECTS:
				getOwnedProjects().clear();
				getOwnedProjects().addAll((Collection<? extends Project>)newValue);
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
			case TaskPackage.COMPANY__OWNED_TEAMS:
				getOwnedTeams().clear();
				return;
			case TaskPackage.COMPANY__OWNED_PERSONS:
				getOwnedPersons().clear();
				return;
			case TaskPackage.COMPANY__OWNED_PROJECTS:
				getOwnedProjects().clear();
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
			case TaskPackage.COMPANY__OWNED_TEAMS:
				return ownedTeams != null && !ownedTeams.isEmpty();
			case TaskPackage.COMPANY__OWNED_PERSONS:
				return ownedPersons != null && !ownedPersons.isEmpty();
			case TaskPackage.COMPANY__OWNED_PROJECTS:
				return ownedProjects != null && !ownedProjects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // CompanyImpl
