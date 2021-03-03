/**
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
 */
package org.eclipse.sirius.web.domain.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
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
 * <li>{@link org.eclipse.sirius.web.domain.impl.RelationImpl#getTargetType <em>Target Type</em>}</li>
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
     * The cached value of the '{@link #getTargetType() <em>Target Type</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getTargetType()
     * @generated
     * @ordered
     */
    protected Entity targetType;

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
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.RELATION__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Entity getTargetType() {
        if (targetType != null && targetType.eIsProxy()) {
            InternalEObject oldTargetType = (InternalEObject) targetType;
            targetType = (Entity) eResolveProxy(oldTargetType);
            if (targetType != oldTargetType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainPackage.RELATION__TARGET_TYPE, oldTargetType, targetType));
            }
        }
        return targetType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Entity basicGetTargetType() {
        return targetType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setTargetType(Entity newTargetType) {
        Entity oldTargetType = targetType;
        targetType = newTargetType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.RELATION__TARGET_TYPE, oldTargetType, targetType));
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
            return getName();
        case DomainPackage.RELATION__TARGET_TYPE:
            if (resolve)
                return getTargetType();
            return basicGetTargetType();
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
            setName((String) newValue);
            return;
        case DomainPackage.RELATION__TARGET_TYPE:
            setTargetType((Entity) newValue);
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
            setName(NAME_EDEFAULT);
            return;
        case DomainPackage.RELATION__TARGET_TYPE:
            setTargetType((Entity) null);
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
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
        case DomainPackage.RELATION__TARGET_TYPE:
            return targetType != null;
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
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} // RelationImpl
