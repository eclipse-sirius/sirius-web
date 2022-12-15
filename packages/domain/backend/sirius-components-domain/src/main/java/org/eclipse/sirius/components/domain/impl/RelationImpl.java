/**
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.domain.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Relation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.domain.impl.RelationImpl#isContainment <em>Containment</em>}</li>
 * <li>{@link org.eclipse.sirius.components.domain.impl.RelationImpl#getTargetType <em>Target Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RelationImpl extends FeatureImpl implements Relation {
    /**
     * The default value of the '{@link #isContainment() <em>Containment</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isContainment()
     * @generated
     * @ordered
     */
    protected static final boolean CONTAINMENT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isContainment() <em>Containment</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isContainment()
     * @generated
     * @ordered
     */
    protected boolean containment = CONTAINMENT_EDEFAULT;

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
    public boolean isContainment() {
        return this.containment;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setContainment(boolean newContainment) {
        boolean oldContainment = this.containment;
        this.containment = newContainment;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.RELATION__CONTAINMENT, oldContainment, this.containment));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Entity getTargetType() {
        if (this.targetType != null && this.targetType.eIsProxy()) {
            InternalEObject oldTargetType = (InternalEObject) this.targetType;
            this.targetType = (Entity) this.eResolveProxy(oldTargetType);
            if (this.targetType != oldTargetType) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainPackage.RELATION__TARGET_TYPE, oldTargetType, this.targetType));
            }
        }
        return this.targetType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Entity basicGetTargetType() {
        return this.targetType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTargetType(Entity newTargetType) {
        Entity oldTargetType = this.targetType;
        this.targetType = newTargetType;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.RELATION__TARGET_TYPE, oldTargetType, this.targetType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DomainPackage.RELATION__CONTAINMENT:
            return this.isContainment();
        case DomainPackage.RELATION__TARGET_TYPE:
            if (resolve)
                return this.getTargetType();
            return this.basicGetTargetType();
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
        case DomainPackage.RELATION__CONTAINMENT:
            this.setContainment((Boolean) newValue);
            return;
        case DomainPackage.RELATION__TARGET_TYPE:
            this.setTargetType((Entity) newValue);
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
        case DomainPackage.RELATION__CONTAINMENT:
            this.setContainment(CONTAINMENT_EDEFAULT);
            return;
        case DomainPackage.RELATION__TARGET_TYPE:
            this.setTargetType((Entity) null);
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
        case DomainPackage.RELATION__CONTAINMENT:
            return this.containment != CONTAINMENT_EDEFAULT;
        case DomainPackage.RELATION__TARGET_TYPE:
            return this.targetType != null;
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
        result.append(" (containment: ");
        result.append(this.containment);
        result.append(')');
        return result.toString();
    }

} // RelationImpl
