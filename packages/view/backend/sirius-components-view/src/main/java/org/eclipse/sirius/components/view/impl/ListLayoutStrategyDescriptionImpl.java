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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.LayoutDirection;
import org.eclipse.sirius.components.view.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>List Layout Strategy Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.ListLayoutStrategyDescriptionImpl#getDirection
 * <em>Direction</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ListLayoutStrategyDescriptionImpl extends MinimalEObjectImpl.Container implements ListLayoutStrategyDescription {
    /**
     * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDirection()
     * @generated
     * @ordered
     */
    protected static final LayoutDirection DIRECTION_EDEFAULT = LayoutDirection.COLUMN;

    /**
     * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDirection()
     * @generated
     * @ordered
     */
    protected LayoutDirection direction = DIRECTION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ListLayoutStrategyDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LayoutDirection getDirection() {
        return this.direction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDirection(LayoutDirection newDirection) {
        LayoutDirection oldDirection = this.direction;
        this.direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__DIRECTION, oldDirection, this.direction));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__DIRECTION:
            return this.getDirection();
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
        case ViewPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__DIRECTION:
            this.setDirection((LayoutDirection) newValue);
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
        case ViewPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__DIRECTION:
            this.setDirection(DIRECTION_EDEFAULT);
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
        case ViewPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__DIRECTION:
            return this.direction != DIRECTION_EDEFAULT;
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
        result.append(" (direction: "); //$NON-NLS-1$
        result.append(this.direction);
        result.append(')');
        return result.toString();
    }

} // ListLayoutStrategyDescriptionImpl
