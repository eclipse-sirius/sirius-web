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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.InterfaceImplementation;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Interface</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getExtendedBy <em>Extended By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getOperations <em>Operations</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getImplementedBy <em>Implemented By</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InterfaceImpl extends ClassifierImpl implements Interface {
    /**
     * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getExtends()
     * @generated
     * @ordered
     */
    protected EList<Interface> extends_;

    /**
     * The cached value of the '{@link #getExtendedBy() <em>Extended By</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getExtendedBy()
     * @generated
     * @ordered
     */
    protected EList<Interface> extendedBy;

    /**
     * The cached value of the '{@link #getOperations() <em>Operations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOperations()
     * @generated
     * @ordered
     */
    protected EList<Operation> operations;

    /**
     * The cached value of the '{@link #getImplementedBy() <em>Implemented By</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getImplementedBy()
     * @generated
     * @ordered
     */
    protected EList<InterfaceImplementation> implementedBy;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected InterfaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.INTERFACE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Interface> getExtends() {
        if (this.extends_ == null) {
            this.extends_ = new EObjectWithInverseResolvingEList.ManyInverse<>(Interface.class, this, PapayaPackage.INTERFACE__EXTENDS, PapayaPackage.INTERFACE__EXTENDED_BY);
        }
        return this.extends_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Interface> getExtendedBy() {
        if (this.extendedBy == null) {
            this.extendedBy = new EObjectWithInverseResolvingEList.ManyInverse<>(Interface.class, this, PapayaPackage.INTERFACE__EXTENDED_BY, PapayaPackage.INTERFACE__EXTENDS);
        }
        return this.extendedBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getOperations() {
        if (this.operations == null) {
            this.operations = new EObjectContainmentEList<>(Operation.class, this, PapayaPackage.INTERFACE__OPERATIONS);
        }
        return this.operations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<InterfaceImplementation> getImplementedBy() {
        if (this.implementedBy == null) {
            this.implementedBy = new EObjectWithInverseResolvingEList.ManyInverse<>(InterfaceImplementation.class, this, PapayaPackage.INTERFACE__IMPLEMENTED_BY,
                    PapayaPackage.INTERFACE_IMPLEMENTATION__IMPLEMENTS);
        }
        return this.implementedBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.INTERFACE__EXTENDS:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getExtends()).basicAdd(otherEnd, msgs);
            case PapayaPackage.INTERFACE__EXTENDED_BY:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getExtendedBy()).basicAdd(otherEnd, msgs);
            case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getImplementedBy()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.INTERFACE__EXTENDS:
                return ((InternalEList<?>) this.getExtends()).basicRemove(otherEnd, msgs);
            case PapayaPackage.INTERFACE__EXTENDED_BY:
                return ((InternalEList<?>) this.getExtendedBy()).basicRemove(otherEnd, msgs);
            case PapayaPackage.INTERFACE__OPERATIONS:
                return ((InternalEList<?>) this.getOperations()).basicRemove(otherEnd, msgs);
            case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
                return ((InternalEList<?>) this.getImplementedBy()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.INTERFACE__EXTENDS:
                return this.getExtends();
            case PapayaPackage.INTERFACE__EXTENDED_BY:
                return this.getExtendedBy();
            case PapayaPackage.INTERFACE__OPERATIONS:
                return this.getOperations();
            case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
                return this.getImplementedBy();
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
            case PapayaPackage.INTERFACE__EXTENDS:
                this.getExtends().clear();
                this.getExtends().addAll((Collection<? extends Interface>) newValue);
                return;
            case PapayaPackage.INTERFACE__EXTENDED_BY:
                this.getExtendedBy().clear();
                this.getExtendedBy().addAll((Collection<? extends Interface>) newValue);
                return;
            case PapayaPackage.INTERFACE__OPERATIONS:
                this.getOperations().clear();
                this.getOperations().addAll((Collection<? extends Operation>) newValue);
                return;
            case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
                this.getImplementedBy().clear();
                this.getImplementedBy().addAll((Collection<? extends InterfaceImplementation>) newValue);
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
            case PapayaPackage.INTERFACE__EXTENDS:
                this.getExtends().clear();
                return;
            case PapayaPackage.INTERFACE__EXTENDED_BY:
                this.getExtendedBy().clear();
                return;
            case PapayaPackage.INTERFACE__OPERATIONS:
                this.getOperations().clear();
                return;
            case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
                this.getImplementedBy().clear();
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
            case PapayaPackage.INTERFACE__EXTENDS:
                return this.extends_ != null && !this.extends_.isEmpty();
            case PapayaPackage.INTERFACE__EXTENDED_BY:
                return this.extendedBy != null && !this.extendedBy.isEmpty();
            case PapayaPackage.INTERFACE__OPERATIONS:
                return this.operations != null && !this.operations.isEmpty();
            case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
                return this.implementedBy != null && !this.implementedBy.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // InterfaceImpl
