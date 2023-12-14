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
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>List Layout Strategy Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getAreChildNodesDraggableExpression
 * <em>Are Child Nodes Draggable Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ListLayoutStrategyDescriptionImpl extends MinimalEObjectImpl.Container implements ListLayoutStrategyDescription {
    /**
     * The default value of the '{@link #getAreChildNodesDraggableExpression() <em>Are Child Nodes Draggable
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAreChildNodesDraggableExpression()
     * @generated
     * @ordered
     */
    protected static final String ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT = "aql:true";

    /**
     * The cached value of the '{@link #getAreChildNodesDraggableExpression() <em>Are Child Nodes Draggable
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAreChildNodesDraggableExpression()
     * @generated
     * @ordered
     */
    protected String areChildNodesDraggableExpression = ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT;

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
        return DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getAreChildNodesDraggableExpression() {
        return this.areChildNodesDraggableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAreChildNodesDraggableExpression(String newAreChildNodesDraggableExpression) {
        String oldAreChildNodesDraggableExpression = this.areChildNodesDraggableExpression;
        this.areChildNodesDraggableExpression = newAreChildNodesDraggableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION, oldAreChildNodesDraggableExpression,
                    this.areChildNodesDraggableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
                return this.getAreChildNodesDraggableExpression();
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
                this.setAreChildNodesDraggableExpression((String) newValue);
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
                this.setAreChildNodesDraggableExpression(ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT);
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
                return ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT == null ? this.areChildNodesDraggableExpression != null
                        : !ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT.equals(this.areChildNodesDraggableExpression);
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
        result.append(" (areChildNodesDraggableExpression: ");
        result.append(this.areChildNodesDraggableExpression);
        result.append(')');
        return result.toString();
    }

} // ListLayoutStrategyDescriptionImpl
