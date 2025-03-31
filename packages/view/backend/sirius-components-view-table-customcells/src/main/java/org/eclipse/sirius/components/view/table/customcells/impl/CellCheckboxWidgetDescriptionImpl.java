/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table.customcells.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription;
import org.eclipse.sirius.components.view.table.customcells.CustomcellsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Cell Checkbox Widget Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.customcells.impl.CellCheckboxWidgetDescriptionImpl#getBody
 * <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CellCheckboxWidgetDescriptionImpl extends MinimalEObjectImpl.Container implements CellCheckboxWidgetDescription {

    /**
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBody()
     */
    protected EList<Operation> body;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CellCheckboxWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CustomcellsPackage.Literals.CELL_CHECKBOX_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<Operation>(Operation.class, this, CustomcellsPackage.CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CustomcellsPackage.CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
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
            case CustomcellsPackage.CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY:
                return this.getBody();
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
            case CustomcellsPackage.CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
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
            case CustomcellsPackage.CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY:
                this.getBody().clear();
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
            case CustomcellsPackage.CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // CellCheckboxWidgetDescriptionImpl
