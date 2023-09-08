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

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Drop Node Tool</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DropNodeToolImpl#getAcceptedNodeTypes <em>Accepted Node
 * Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DropNodeToolImpl extends ToolImpl implements DropNodeTool {
    /**
     * The cached value of the '{@link #getAcceptedNodeTypes() <em>Accepted Node Types</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAcceptedNodeTypes()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> acceptedNodeTypes;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DropNodeToolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.DROP_NODE_TOOL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getAcceptedNodeTypes() {
        if (this.acceptedNodeTypes == null) {
            this.acceptedNodeTypes = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.DROP_NODE_TOOL__ACCEPTED_NODE_TYPES);
        }
        return this.acceptedNodeTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.DROP_NODE_TOOL__ACCEPTED_NODE_TYPES:
                return this.getAcceptedNodeTypes();
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
            case DiagramPackage.DROP_NODE_TOOL__ACCEPTED_NODE_TYPES:
                this.getAcceptedNodeTypes().clear();
                this.getAcceptedNodeTypes().addAll((Collection<? extends NodeDescription>) newValue);
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
            case DiagramPackage.DROP_NODE_TOOL__ACCEPTED_NODE_TYPES:
                this.getAcceptedNodeTypes().clear();
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
            case DiagramPackage.DROP_NODE_TOOL__ACCEPTED_NODE_TYPES:
                return this.acceptedNodeTypes != null && !this.acceptedNodeTypes.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // DropNodeToolImpl
