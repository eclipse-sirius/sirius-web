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
 * <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getOwnedTasks <em>Owned Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getOwnedObjectives <em>Owned Objectives</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.ProjectImpl#getOwnedTags <em>Owned Tags</em>}</li>
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
     *
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
     *
     * @generated
     */
    protected ProjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TaskPackage.Literals.PROJECT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.PROJECT__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getOwnedTasks() {
        if (this.ownedTasks == null) {
            this.ownedTasks = new EObjectContainmentEList<>(Task.class, this, TaskPackage.PROJECT__OWNED_TASKS);
        }
        return this.ownedTasks;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Objective> getOwnedObjectives() {
        if (this.ownedObjectives == null) {
            this.ownedObjectives = new EObjectContainmentEList<>(Objective.class, this, TaskPackage.PROJECT__OWNED_OBJECTIVES);
        }
        return this.ownedObjectives;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TaskTag> getOwnedTags() {
        if (this.ownedTags == null) {
            this.ownedTags = new EObjectContainmentEList<>(TaskTag.class, this, TaskPackage.PROJECT__OWNED_TAGS);
        }
        return this.ownedTags;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TaskPackage.PROJECT__OWNED_TASKS:
                return ((InternalEList<?>) this.getOwnedTasks()).basicRemove(otherEnd, msgs);
            case TaskPackage.PROJECT__OWNED_OBJECTIVES:
                return ((InternalEList<?>) this.getOwnedObjectives()).basicRemove(otherEnd, msgs);
            case TaskPackage.PROJECT__OWNED_TAGS:
                return ((InternalEList<?>) this.getOwnedTags()).basicRemove(otherEnd, msgs);
            default:
                return super.eInverseRemove(otherEnd, featureID, msgs);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TaskPackage.PROJECT__NAME:
                return this.getName();
            case TaskPackage.PROJECT__OWNED_TASKS:
                return this.getOwnedTasks();
            case TaskPackage.PROJECT__OWNED_OBJECTIVES:
                return this.getOwnedObjectives();
            case TaskPackage.PROJECT__OWNED_TAGS:
                return this.getOwnedTags();
            default:
                return super.eGet(featureID, resolve, coreType);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case TaskPackage.PROJECT__NAME:
                this.setName((String) newValue);
                return;
            case TaskPackage.PROJECT__OWNED_TASKS:
                this.getOwnedTasks().clear();
                this.getOwnedTasks().addAll((Collection<? extends Task>) newValue);
                return;
            case TaskPackage.PROJECT__OWNED_OBJECTIVES:
                this.getOwnedObjectives().clear();
                this.getOwnedObjectives().addAll((Collection<? extends Objective>) newValue);
                return;
            case TaskPackage.PROJECT__OWNED_TAGS:
                this.getOwnedTags().clear();
                this.getOwnedTags().addAll((Collection<? extends TaskTag>) newValue);
                return;
            default:
                super.eSet(featureID, newValue);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case TaskPackage.PROJECT__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case TaskPackage.PROJECT__OWNED_TASKS:
                this.getOwnedTasks().clear();
                return;
            case TaskPackage.PROJECT__OWNED_OBJECTIVES:
                this.getOwnedObjectives().clear();
                return;
            case TaskPackage.PROJECT__OWNED_TAGS:
                this.getOwnedTags().clear();
                return;
            default:
                super.eUnset(featureID);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case TaskPackage.PROJECT__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case TaskPackage.PROJECT__OWNED_TASKS:
                return this.ownedTasks != null && !this.ownedTasks.isEmpty();
            case TaskPackage.PROJECT__OWNED_OBJECTIVES:
                return this.ownedObjectives != null && !this.ownedObjectives.isEmpty();
            case TaskPackage.PROJECT__OWNED_TAGS:
                return this.ownedTags != null && !this.ownedTags.isEmpty();
            default:
                return super.eIsSet(featureID);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (name: ");
        result.append(this.name);
        result.append(')');
        return result.toString();
    }

} // ProjectImpl
