/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.domain.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.domain.Entity;
import org.eclipse.sirius.web.domain.Relation;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Relation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.impl.RelationImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.impl.RelationImpl#getTargetTypes <em>Target Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RelationImpl extends MinimalEObjectImpl.Container implements Relation {
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
     * The cached value of the '{@link #getTargetTypes() <em>Target Types</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getTargetTypes()
     * @generated
     * @ordered
     */
    protected EList<Entity> targetTypes;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RelationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DomainPackage.Literals.RELATION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.RELATION__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Entity> getTargetTypes() {
        if (this.targetTypes == null) {
            this.targetTypes = new EObjectResolvingEList<>(Entity.class, this, DomainPackage.RELATION__TARGET_TYPES);
        }
        return this.targetTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DomainPackage.RELATION__NAME:
            return this.getName();
        case DomainPackage.RELATION__TARGET_TYPES:
            return this.getTargetTypes();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DomainPackage.RELATION__NAME:
            this.setName((String) newValue);
            return;
        case DomainPackage.RELATION__TARGET_TYPES:
            this.getTargetTypes().clear();
            this.getTargetTypes().addAll((Collection<? extends Entity>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case DomainPackage.RELATION__NAME:
            this.setName(NAME_EDEFAULT);
            return;
        case DomainPackage.RELATION__TARGET_TYPES:
            this.getTargetTypes().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DomainPackage.RELATION__NAME:
            return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
        case DomainPackage.RELATION__TARGET_TYPES:
            return this.targetTypes != null && !this.targetTypes.isEmpty();
        }
        return super.eIsSet(featureID);
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
        result.append(" (name: "); //$NON-NLS-1$
        result.append(this.name);
        result.append(')');
        return result.toString();
    }

} // RelationImpl
