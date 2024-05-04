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
import org.eclipse.sirius.components.papaya.ComponentExchange;
import org.eclipse.sirius.components.papaya.ComponentPort;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Component Exchange</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentExchangeImpl#getPorts <em>Ports</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentExchangeImpl extends NamedElementImpl implements ComponentExchange {
    /**
     * The cached value of the '{@link #getPorts() <em>Ports</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getPorts()
     * @generated
     * @ordered
     */
    protected EList<ComponentPort> ports;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ComponentExchangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.COMPONENT_EXCHANGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ComponentPort> getPorts() {
        if (this.ports == null) {
            this.ports = new EObjectResolvingEList<>(ComponentPort.class, this, PapayaPackage.COMPONENT_EXCHANGE__PORTS);
        }
        return this.ports;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.COMPONENT_EXCHANGE__PORTS:
                return this.getPorts();
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
            case PapayaPackage.COMPONENT_EXCHANGE__PORTS:
                this.getPorts().clear();
                this.getPorts().addAll((Collection<? extends ComponentPort>) newValue);
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
            case PapayaPackage.COMPONENT_EXCHANGE__PORTS:
                this.getPorts().clear();
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
            case PapayaPackage.COMPONENT_EXCHANGE__PORTS:
                return this.ports != null && !this.ports.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ComponentExchangeImpl
