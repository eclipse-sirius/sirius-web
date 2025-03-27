/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Free Form Layout Strategy
 * Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl#getOnWestAtCreationBorderNodes
 * <em>On West At Creation Border Nodes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl#getOnEastAtCreationBorderNodes
 * <em>On East At Creation Border Nodes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl#getOnSouthAtCreationBorderNodes
 * <em>On South At Creation Border Nodes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl#getOnNorthAtCreationBorderNodes
 * <em>On North At Creation Border Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FreeFormLayoutStrategyDescriptionImpl extends MinimalEObjectImpl.Container implements FreeFormLayoutStrategyDescription {
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FreeFormLayoutStrategyDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getOnWestAtCreationBorderNodes() {
        if (this.onWestAtCreationBorderNodes == null) {
            this.onWestAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this,
                    DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES);
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
            this.onEastAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this,
                    DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES);
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
            this.onSouthAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this,
                    DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES);
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
            this.onNorthAtCreationBorderNodes = new EObjectResolvingEList<>(NodeDescription.class, this,
                    DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES);
        }
        return this.onNorthAtCreationBorderNodes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                return this.getOnWestAtCreationBorderNodes();
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                return this.getOnEastAtCreationBorderNodes();
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                return this.getOnSouthAtCreationBorderNodes();
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                return this.getOnNorthAtCreationBorderNodes();
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
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                this.getOnWestAtCreationBorderNodes().clear();
                this.getOnWestAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                this.getOnEastAtCreationBorderNodes().clear();
                this.getOnEastAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                this.getOnSouthAtCreationBorderNodes().clear();
                this.getOnSouthAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                this.getOnNorthAtCreationBorderNodes().clear();
                this.getOnNorthAtCreationBorderNodes().addAll((Collection<? extends NodeDescription>) newValue);
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
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                this.getOnWestAtCreationBorderNodes().clear();
                return;
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                this.getOnEastAtCreationBorderNodes().clear();
                return;
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                this.getOnSouthAtCreationBorderNodes().clear();
                return;
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                this.getOnNorthAtCreationBorderNodes().clear();
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
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES:
                return this.onWestAtCreationBorderNodes != null && !this.onWestAtCreationBorderNodes.isEmpty();
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES:
                return this.onEastAtCreationBorderNodes != null && !this.onEastAtCreationBorderNodes.isEmpty();
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES:
                return this.onSouthAtCreationBorderNodes != null && !this.onSouthAtCreationBorderNodes.isEmpty();
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES:
                return this.onNorthAtCreationBorderNodes != null && !this.onNorthAtCreationBorderNodes.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // FreeFormLayoutStrategyDescriptionImpl
