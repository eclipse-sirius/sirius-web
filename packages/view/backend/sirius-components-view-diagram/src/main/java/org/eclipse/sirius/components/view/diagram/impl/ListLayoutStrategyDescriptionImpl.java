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
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnWestAtCreationBorderNodes <em>On West At Creation Border Nodes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnEastAtCreationBorderNodes <em>On East At Creation Border Nodes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnSouthAtCreationBorderNodes <em>On South At Creation Border Nodes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getOnNorthAtCreationBorderNodes <em>On North At Creation Border Nodes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getAreChildNodesDraggableExpression <em>Are Child Nodes Draggable Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getTopGapExpression <em>Top Gap Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getBottomGapExpression <em>Bottom Gap Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl#getGrowableNodes <em>Growable Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ListLayoutStrategyDescriptionImpl extends MinimalEObjectImpl.Container implements ListLayoutStrategyDescription {

    /**
	 * The cached value of the '{@link #getOnWestAtCreationBorderNodes() <em>On West At Creation Border Nodes</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOnWestAtCreationBorderNodes()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> onWestAtCreationBorderNodes;

    /**
	 * The cached value of the '{@link #getOnEastAtCreationBorderNodes() <em>On East At Creation Border Nodes</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOnEastAtCreationBorderNodes()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> onEastAtCreationBorderNodes;

    /**
	 * The cached value of the '{@link #getOnSouthAtCreationBorderNodes() <em>On South At Creation Border Nodes</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOnSouthAtCreationBorderNodes()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> onSouthAtCreationBorderNodes;

    /**
	 * The cached value of the '{@link #getOnNorthAtCreationBorderNodes() <em>On North At Creation Border Nodes</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOnNorthAtCreationBorderNodes()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> onNorthAtCreationBorderNodes;

    /**
	 * The default value of the '{@link #getAreChildNodesDraggableExpression() <em>Are Child Nodes Draggable Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAreChildNodesDraggableExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT = "aql:true";

    /**
	 * The cached value of the '{@link #getAreChildNodesDraggableExpression() <em>Are Child Nodes Draggable Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * The cached value of the '{@link #getGrowableNodes() <em>Growable Nodes</em>}' reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getGrowableNodes()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> growableNodes;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ListLayoutStrategyDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getOnWestAtCreationBorderNodes() {
		if (onWestAtCreationBorderNodes == null)
		{
			onWestAtCreationBorderNodes = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES);
		}
		return onWestAtCreationBorderNodes;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getOnEastAtCreationBorderNodes() {
		if (onEastAtCreationBorderNodes == null)
		{
			onEastAtCreationBorderNodes = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES);
		}
		return onEastAtCreationBorderNodes;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getOnSouthAtCreationBorderNodes() {
		if (onSouthAtCreationBorderNodes == null)
		{
			onSouthAtCreationBorderNodes = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES);
		}
		return onSouthAtCreationBorderNodes;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getOnNorthAtCreationBorderNodes() {
		if (onNorthAtCreationBorderNodes == null)
		{
			onNorthAtCreationBorderNodes = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES);
		}
		return onNorthAtCreationBorderNodes;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getAreChildNodesDraggableExpression() {
		return areChildNodesDraggableExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setAreChildNodesDraggableExpression(String newAreChildNodesDraggableExpression) {
		String oldAreChildNodesDraggableExpression = areChildNodesDraggableExpression;
		areChildNodesDraggableExpression = newAreChildNodesDraggableExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION, oldAreChildNodesDraggableExpression, areChildNodesDraggableExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getTopGapExpression() {
		return topGapExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTopGapExpression(String newTopGapExpression) {
		String oldTopGapExpression = topGapExpression;
		topGapExpression = newTopGapExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION, oldTopGapExpression, topGapExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getBottomGapExpression() {
		return bottomGapExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBottomGapExpression(String newBottomGapExpression) {
		String oldBottomGapExpression = bottomGapExpression;
		bottomGapExpression = newBottomGapExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION, oldBottomGapExpression, bottomGapExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getGrowableNodes() {
		if (growableNodes == null)
		{
			growableNodes = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES);
		}
		return growableNodes;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
				return getOnWestAtCreationBorderNodes();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
				return getOnEastAtCreationBorderNodes();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
				return getOnSouthAtCreationBorderNodes();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
				return getOnNorthAtCreationBorderNodes();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
				return getAreChildNodesDraggableExpression();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
				return getTopGapExpression();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
				return getBottomGapExpression();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
				return getGrowableNodes();
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
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
				getOnWestAtCreationBorderNodes().clear();
				getOnWestAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
				getOnEastAtCreationBorderNodes().clear();
				getOnEastAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
				getOnSouthAtCreationBorderNodes().clear();
				getOnSouthAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
				getOnNorthAtCreationBorderNodes().clear();
				getOnNorthAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
				setAreChildNodesDraggableExpression((String)newValue);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
				setTopGapExpression((String)newValue);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
				setBottomGapExpression((String)newValue);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
				getGrowableNodes().clear();
				getGrowableNodes().addAll((Collection<? extends NodeDescription>)newValue);
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
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
				getOnWestAtCreationBorderNodes().clear();
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
				getOnEastAtCreationBorderNodes().clear();
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
				getOnSouthAtCreationBorderNodes().clear();
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
				getOnNorthAtCreationBorderNodes().clear();
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
				setAreChildNodesDraggableExpression(ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
				setTopGapExpression(TOP_GAP_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
				setBottomGapExpression(BOTTOM_GAP_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
				getGrowableNodes().clear();
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
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
				return onWestAtCreationBorderNodes != null && !onWestAtCreationBorderNodes.isEmpty();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
				return onEastAtCreationBorderNodes != null && !onEastAtCreationBorderNodes.isEmpty();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
				return onSouthAtCreationBorderNodes != null && !onSouthAtCreationBorderNodes.isEmpty();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
				return onNorthAtCreationBorderNodes != null && !onNorthAtCreationBorderNodes.isEmpty();
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
				return ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT == null ? areChildNodesDraggableExpression != null : !ARE_CHILD_NODES_DRAGGABLE_EXPRESSION_EDEFAULT.equals(areChildNodesDraggableExpression);
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
				return TOP_GAP_EXPRESSION_EDEFAULT == null ? topGapExpression != null : !TOP_GAP_EXPRESSION_EDEFAULT.equals(topGapExpression);
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
				return BOTTOM_GAP_EXPRESSION_EDEFAULT == null ? bottomGapExpression != null : !BOTTOM_GAP_EXPRESSION_EDEFAULT.equals(bottomGapExpression);
			case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES:
				return growableNodes != null && !growableNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (areChildNodesDraggableExpression: ");
		result.append(areChildNodesDraggableExpression);
		result.append(", topGapExpression: ");
		result.append(topGapExpression);
		result.append(", bottomGapExpression: ");
		result.append(bottomGapExpression);
		result.append(')');
		return result.toString();
	}

} // ListLayoutStrategyDescriptionImpl
