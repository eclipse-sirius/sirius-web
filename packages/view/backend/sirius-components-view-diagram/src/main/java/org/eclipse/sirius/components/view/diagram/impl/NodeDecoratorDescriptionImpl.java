/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.view.diagram.NodeDecoratorDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Decorator Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDecoratorDescriptionImpl#getNodeDescriptions <em>Node
 * Descriptions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeDecoratorDescriptionImpl extends DecoratorDescriptionImpl implements NodeDecoratorDescription {
    /**
     * The cached value of the '{@link #getNodeDescriptions() <em>Node Descriptions</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNodeDescriptions()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> nodeDescriptions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodeDecoratorDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.NODE_DECORATOR_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getNodeDescriptions() {
        if (this.nodeDescriptions == null) {
            this.nodeDescriptions = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.NODE_DECORATOR_DESCRIPTION__NODE_DESCRIPTIONS);
        }
        return this.nodeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.NODE_DECORATOR_DESCRIPTION__NODE_DESCRIPTIONS:
                return this.getNodeDescriptions();
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
            case DiagramPackage.NODE_DECORATOR_DESCRIPTION__NODE_DESCRIPTIONS:
                this.getNodeDescriptions().clear();
                this.getNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
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
            case DiagramPackage.NODE_DECORATOR_DESCRIPTION__NODE_DESCRIPTIONS:
                this.getNodeDescriptions().clear();
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
            case DiagramPackage.NODE_DECORATOR_DESCRIPTION__NODE_DESCRIPTIONS:
                return this.nodeDescriptions != null && !this.nodeDescriptions.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // NodeDecoratorDescriptionImpl
