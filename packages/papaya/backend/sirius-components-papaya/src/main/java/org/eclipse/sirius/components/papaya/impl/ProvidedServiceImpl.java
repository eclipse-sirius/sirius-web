/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.papaya.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.ProvidedService;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Provided Service</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProvidedServiceImpl#getContracts <em>Contracts</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProvidedServiceImpl extends NamedElementImpl implements ProvidedService {
    /**
     * The cached value of the '{@link #getContracts() <em>Contracts</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getContracts()
     * @generated
     * @ordered
     */
    protected EList<Interface> contracts;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ProvidedServiceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.PROVIDED_SERVICE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Interface> getContracts() {
        if (this.contracts == null) {
            this.contracts = new EObjectResolvingEList<>(Interface.class, this, PapayaPackage.PROVIDED_SERVICE__CONTRACTS);
        }
        return this.contracts;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.PROVIDED_SERVICE__CONTRACTS:
                return this.getContracts();
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
            case PapayaPackage.PROVIDED_SERVICE__CONTRACTS:
                this.getContracts().clear();
                this.getContracts().addAll((Collection<? extends Interface>) newValue);
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
            case PapayaPackage.PROVIDED_SERVICE__CONTRACTS:
                this.getContracts().clear();
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
            case PapayaPackage.PROVIDED_SERVICE__CONTRACTS:
                return this.contracts != null && !this.contracts.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ProvidedServiceImpl
