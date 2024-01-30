/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>List Layout Strategy Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getAreChildNodesDraggableExpression
 * <em>Are Child Nodes Draggable Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getTopGapExpression
 * <em>Top Gap Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getBottomGapExpression
 * <em>Bottom Gap Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getGrowableNodes
 * <em>Growable Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ListLayoutStrategyDescriptionImpl extends MinimalEObjectImpl.Container implements ListLayoutStrategyDescription {

    /**
     * The default value of the '{@link #getAreChildNodesDraggableExpression() <em>Are Child Nodes Draggable
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAreChildNodesDraggableExpression()
     */
    protected static final String ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT = "aql:true";
    /**
     * The default value of the '{@link #getTopGapExpression() <em>Top Gap Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTopGapExpression()
     */
    protected static final String TOP_GAP_EXPRESSION_EDEFAULT = "";
    /**
     * The default value of the '{@link #getBottomGapExpression() <em>Bottom Gap Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBottomGapExpression()
     */
    protected static final String BOTTOM_GAP_EXPRESSION_EDEFAULT = "";
    /**
     * The cached value of the '{@link #getAreChildNodesDraggableExpression() <em>Are Child Nodes Draggable
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAreChildNodesDraggableExpression()
     */
    protected String areChildNodesDraggableExpression = ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getTopGapExpression() <em>Top Gap Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTopGapExpression()
     */
    protected String topGapExpression = TOP_GAP_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getBottomGapExpression() <em>Bottom Gap Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBottomGapExpression()
     */
    protected String bottomGapExpression = BOTTOM_GAP_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getGrowableNodes() <em>Growable Nodes</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getGrowableNodes()
     */
    protected EList<NodeDescription> growableNodes;

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
    public String getTopGapExpression() {
        return this.topGapExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTopGapExpression(String newTopGapExpression) {
        String oldTopGapExpression = this.topGapExpression;
        this.topGapExpression = newTopGapExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION, oldTopGapExpression, this.topGapExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getBottomGapExpression() {
        return this.bottomGapExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBottomGapExpression(String newBottomGapExpression) {
        String oldBottomGapExpression = this.bottomGapExpression;
        this.bottomGapExpression = newBottomGapExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION, oldBottomGapExpression, this.bottomGapExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getGrowableNodes() {
        if (this.growableNodes == null) {
            this.growableNodes = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES);
        }
        return this.growableNodes;
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
                return this.getTopGapExpression();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
                return this.getBottomGapExpression();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
                return this.getGrowableNodes();
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
                this.setAreChildNodesDraggableExpression((String) newValue);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
                this.setTopGapExpression((String) newValue);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
                this.setBottomGapExpression((String) newValue);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
                this.getGrowableNodes().clear();
                this.getGrowableNodes().addAll((Collection<? extends NodeDescription>) newValue);
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
                this.setTopGapExpression(TOP_GAP_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
                this.setBottomGapExpression(BOTTOM_GAP_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
                this.getGrowableNodes().clear();
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
                return TOP_GAP_EXPRESSION_EDEFAULT == null ? this.topGapExpression != null : !TOP_GAP_EXPRESSION_EDEFAULT.equals(this.topGapExpression);
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
                return BOTTOM_GAP_EXPRESSION_EDEFAULT == null ? this.bottomGapExpression != null : !BOTTOM_GAP_EXPRESSION_EDEFAULT.equals(this.bottomGapExpression);
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
                return this.growableNodes != null && !this.growableNodes.isEmpty();
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

        String result = super.toString() + " (areChildNodesDraggableExpression: " +
                this.areChildNodesDraggableExpression +
                ", topGapExpression: " +
                this.topGapExpression +
                ", bottomGapExpression: " +
                this.bottomGapExpression +
                ')';
        return result;
    }

} // ListLayoutStrategyDescriptionImpl
