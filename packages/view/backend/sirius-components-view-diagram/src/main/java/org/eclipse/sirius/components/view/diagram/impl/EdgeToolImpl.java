/**
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.diagram.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeTool;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Tool</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl#getTargetElementDescriptions <em>Target
 * Element Descriptions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeToolImpl extends ToolImpl implements EdgeTool {
    /**
     * The cached value of the '{@link #getTargetElementDescriptions() <em>Target Element Descriptions</em>}' reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetElementDescriptions()
     * @generated
     * @ordered
     */
    protected EList<DiagramElementDescription> targetElementDescriptions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EdgeToolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.EDGE_TOOL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DiagramElementDescription> getTargetElementDescriptions() {
        if (this.targetElementDescriptions == null) {
            this.targetElementDescriptions = new EObjectResolvingEList<>(DiagramElementDescription.class, this, DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS);
        }
        return this.targetElementDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                return this.getTargetElementDescriptions();
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
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                this.getTargetElementDescriptions().clear();
                this.getTargetElementDescriptions().addAll((Collection<? extends DiagramElementDescription>) newValue);
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
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                this.getTargetElementDescriptions().clear();
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
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                return this.targetElementDescriptions != null && !this.targetElementDescriptions.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // EdgeToolImpl
