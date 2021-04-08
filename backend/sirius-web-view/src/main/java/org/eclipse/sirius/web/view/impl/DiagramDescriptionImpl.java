/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.web.view.DiagramDescription;
import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Diagram Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.impl.DiagramDescriptionImpl#getNodeDescriptions <em>Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.DiagramDescriptionImpl#getEdgeDescriptions <em>Edge
 * Descriptions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramDescriptionImpl extends RepresentationDescriptionImpl implements DiagramDescription {
    /**
     * The cached value of the '{@link #getNodeDescriptions() <em>Node Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNodeDescriptions()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> nodeDescriptions;

    /**
     * The cached value of the '{@link #getEdgeDescriptions() <em>Edge Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEdgeDescriptions()
     * @generated
     * @ordered
     */
    protected EList<EdgeDescription> edgeDescriptions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DIAGRAM_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getNodeDescriptions() {
        if (this.nodeDescriptions == null) {
            this.nodeDescriptions = new EObjectContainmentEList<>(NodeDescription.class, this, ViewPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
        }
        return this.nodeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeDescription> getEdgeDescriptions() {
        if (this.edgeDescriptions == null) {
            this.edgeDescriptions = new EObjectContainmentEList<>(EdgeDescription.class, this, ViewPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);
        }
        return this.edgeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ViewPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
            return ((InternalEList<?>) this.getNodeDescriptions()).basicRemove(otherEnd, msgs);
        case ViewPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
            return ((InternalEList<?>) this.getEdgeDescriptions()).basicRemove(otherEnd, msgs);
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
        case ViewPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
            return this.getNodeDescriptions();
        case ViewPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
            return this.getEdgeDescriptions();
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
        case ViewPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
            this.getNodeDescriptions().clear();
            this.getNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
            return;
        case ViewPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
            this.getEdgeDescriptions().clear();
            this.getEdgeDescriptions().addAll((Collection<? extends EdgeDescription>) newValue);
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
        case ViewPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
            this.getNodeDescriptions().clear();
            return;
        case ViewPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
            this.getEdgeDescriptions().clear();
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
        case ViewPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
            return this.nodeDescriptions != null && !this.nodeDescriptions.isEmpty();
        case ViewPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
            return this.edgeDescriptions != null && !this.edgeDescriptions.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // DiagramDescriptionImpl
