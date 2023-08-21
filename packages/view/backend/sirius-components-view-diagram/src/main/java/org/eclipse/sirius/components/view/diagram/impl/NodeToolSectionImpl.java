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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Tool Section</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl#getEdgeTools <em>Edge Tools</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeToolSectionImpl extends ToolSectionImpl implements NodeToolSection {

    /**
     * The cached value of the '{@link #getNodeTools() <em>Node Tools</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getNodeTools()
     */
    protected EList<NodeTool> nodeTools;

    /**
     * The cached value of the '{@link #getEdgeTools() <em>Edge Tools</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEdgeTools()
     */
    protected EList<EdgeTool> edgeTools;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodeToolSectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.NODE_TOOL_SECTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getNodeTools() {
        if (this.nodeTools == null) {
            this.nodeTools = new EObjectContainmentEList<>(NodeTool.class, this, DiagramPackage.NODE_TOOL_SECTION__NODE_TOOLS);
        }
        return this.nodeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeTool> getEdgeTools() {
        if (this.edgeTools == null) {
            this.edgeTools = new EObjectContainmentEList<>(EdgeTool.class, this, DiagramPackage.NODE_TOOL_SECTION__EDGE_TOOLS);
        }
        return this.edgeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.NODE_TOOL_SECTION__NODE_TOOLS:
                return ((InternalEList<?>) this.getNodeTools()).basicRemove(otherEnd, msgs);
            case DiagramPackage.NODE_TOOL_SECTION__EDGE_TOOLS:
                return ((InternalEList<?>) this.getEdgeTools()).basicRemove(otherEnd, msgs);
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
            case DiagramPackage.NODE_TOOL_SECTION__NODE_TOOLS:
                return this.getNodeTools();
            case DiagramPackage.NODE_TOOL_SECTION__EDGE_TOOLS:
                return this.getEdgeTools();
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
            case DiagramPackage.NODE_TOOL_SECTION__NODE_TOOLS:
                this.getNodeTools().clear();
                this.getNodeTools().addAll((Collection<? extends NodeTool>) newValue);
                return;
            case DiagramPackage.NODE_TOOL_SECTION__EDGE_TOOLS:
                this.getEdgeTools().clear();
                this.getEdgeTools().addAll((Collection<? extends EdgeTool>) newValue);
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
            case DiagramPackage.NODE_TOOL_SECTION__NODE_TOOLS:
                this.getNodeTools().clear();
                return;
            case DiagramPackage.NODE_TOOL_SECTION__EDGE_TOOLS:
                this.getEdgeTools().clear();
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
            case DiagramPackage.NODE_TOOL_SECTION__NODE_TOOLS:
                return this.nodeTools != null && !this.nodeTools.isEmpty();
            case DiagramPackage.NODE_TOOL_SECTION__EDGE_TOOLS:
                return this.edgeTools != null && !this.edgeTools.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // NodeToolSectionImpl
