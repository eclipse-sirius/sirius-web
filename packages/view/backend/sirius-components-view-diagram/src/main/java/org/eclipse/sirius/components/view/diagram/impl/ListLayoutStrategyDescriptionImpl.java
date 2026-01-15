/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnWestAtCreationBorderNodes
 * <em>On West At Creation Border Nodes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnEastAtCreationBorderNodes
 * <em>On East At Creation Border Nodes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnSouthAtCreationBorderNodes
 * <em>On South At Creation Border Nodes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnNorthAtCreationBorderNodes
 * <em>On North At Creation Border Nodes</em>}</li>
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
     * The cached value of the '{@link #getOnWestAtCreationBorderNodes() <em>On West At Creation Border Nodes</em>}'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOnWestAtCreationBorderNodes()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> onWestAtCreationBorderNodes;

    /**
     * The cached value of the '{@link #getOnEastAtCreationBorderNodes() <em>On East At Creation Border Nodes</em>}'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOnEastAtCreationBorderNodes()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> onEastAtCreationBorderNodes;

    /**
     * The cached value of the '{@link #getOnSouthAtCreationBorderNodes() <em>On South At Creation Border Nodes</em>}'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOnSouthAtCreationBorderNodes()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> onSouthAtCreationBorderNodes;

    /**
     * The cached value of the '{@link #getOnNorthAtCreationBorderNodes() <em>On North At Creation Border Nodes</em>}'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOnNorthAtCreationBorderNodes()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> onNorthAtCreationBorderNodes;

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
     * The default value of the '{@link #getTopGapExpression() <em>Top Gap Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTopGapExpression()
     */
    protected static final String TOP_GAP_EXPRESSION_EDEFAULT = "";

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
     * The default value of the '{@link #getBottomGapExpression() <em>Bottom Gap Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBottomGapExpression()
     */
    protected static final String BOTTOM_GAP_EXPRESSION_EDEFAULT = "";

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
     * @see #getGrowableNodes()
     * @generated
     * @ordered
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
    public EList<NodeDescription> getOnWestAtCreationBorderNodes() {
        if (this.onWestAtCreationBorderNodes == null) {
            this.onWestAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES);
        }
        return this.onWestAtCreationBorderNodes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getOnEastAtCreationBorderNodes() {
        if (this.onEastAtCreationBorderNodes == null) {
            this.onEastAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES);
        }
        return this.onEastAtCreationBorderNodes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getOnSouthAtCreationBorderNodes() {
        if (this.onSouthAtCreationBorderNodes == null) {
            this.onSouthAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES);
        }
        return this.onSouthAtCreationBorderNodes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getOnNorthAtCreationBorderNodes() {
        if (this.onNorthAtCreationBorderNodes == null) {
            this.onNorthAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES);
        }
        return this.onNorthAtCreationBorderNodes;
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                return this.getOnWestAtCreationBorderNodes();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                return this.getOnEastAtCreationBorderNodes();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                return this.getOnSouthAtCreationBorderNodes();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                return this.getOnNorthAtCreationBorderNodes();
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                this.getOnWestAtCreationBorderNodes().clear();
                this.getOnWestAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                this.getOnEastAtCreationBorderNodes().clear();
                this.getOnEastAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                this.getOnSouthAtCreationBorderNodes().clear();
                this.getOnSouthAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                this.getOnNorthAtCreationBorderNodes().clear();
                this.getOnNorthAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
                return;
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                this.getOnWestAtCreationBorderNodes().clear();
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                this.getOnEastAtCreationBorderNodes().clear();
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                this.getOnSouthAtCreationBorderNodes().clear();
                return;
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                this.getOnNorthAtCreationBorderNodes().clear();
                return;
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
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                return this.onWestAtCreationBorderNodes != null && !this.onWestAtCreationBorderNodes.isEmpty();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                return this.onEastAtCreationBorderNodes != null && !this.onEastAtCreationBorderNodes.isEmpty();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                return this.onSouthAtCreationBorderNodes != null && !this.onSouthAtCreationBorderNodes.isEmpty();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                return this.onNorthAtCreationBorderNodes != null && !this.onNorthAtCreationBorderNodes.isEmpty();
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (areChildNodesDraggableExpression: ");
        result.append(this.areChildNodesDraggableExpression);
        result.append(", topGapExpression: ");
        result.append(this.topGapExpression);
        result.append(", bottomGapExpression: ");
        result.append(this.bottomGapExpression);
        result.append(')');
        return result.toString();
    }

} // ListLayoutStrategyDescriptionImpl
