/**
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.form.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Flexbox Container Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getChildren
 * <em>Children</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getFlexDirection <em>Flex
 * Direction</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FlexboxContainerDescriptionImpl extends WidgetDescriptionImpl implements FlexboxContainerDescription {
    /**
     * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildren()
     * @generated
     * @ordered
     */
    protected EList<WidgetDescription> children;

    /**
     * The default value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated
     * @ordered
     */
    protected static final FlexDirection FLEX_DIRECTION_EDEFAULT = FlexDirection.ROW;

    /**
     * The cached value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated
     * @ordered
     */
    protected FlexDirection flexDirection = FLEX_DIRECTION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FlexboxContainerDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<WidgetDescription> getChildren() {
        if (this.children == null) {
            this.children = new EObjectContainmentEList<>(WidgetDescription.class, this, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN);
        }
        return this.children;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FlexDirection getFlexDirection() {
        return this.flexDirection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFlexDirection(FlexDirection newFlexDirection) {
        FlexDirection oldFlexDirection = this.flexDirection;
        this.flexDirection = newFlexDirection == null ? FLEX_DIRECTION_EDEFAULT : newFlexDirection;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION, oldFlexDirection, this.flexDirection));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                return ((InternalEList<?>) this.getChildren()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                return this.getChildren();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                return this.getFlexDirection();
        }
        return super.eGet(featureID, resolve, coreType);
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                this.getChildren().clear();
                this.getChildren().addAll((Collection<? extends WidgetDescription>) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                this.setFlexDirection((FlexDirection) newValue);
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                this.getChildren().clear();
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                this.setFlexDirection(FLEX_DIRECTION_EDEFAULT);
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                return this.children != null && !this.children.isEmpty();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                return this.flexDirection != FLEX_DIRECTION_EDEFAULT;
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
        result.append(" (flexDirection: ");
        result.append(this.flexDirection);
        result.append(')');
        return result.toString();
    }

} // FlexboxContainerDescriptionImpl
