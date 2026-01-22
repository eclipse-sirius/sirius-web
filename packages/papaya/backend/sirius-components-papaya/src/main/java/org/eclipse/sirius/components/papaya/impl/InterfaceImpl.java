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
import org.eclipse.sirius.components.papaya.Classifier;
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
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getExtends <em>Extends</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getExtendedBy <em>Extended By</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getImplementedBy <em>Implemented By</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getSubtypes <em>Subtypes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getAllSubtypes <em>All Subtypes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InterfaceImpl extends ClassifierImpl implements Interface {
    /**
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
    protected EList<Interface> extends_;

    /**
	 * The cached value of the '{@link #getExtendedBy() <em>Extended By</em>}' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getExtendedBy()
	 * @generated
	 * @ordered
	 */
    protected EList<Interface> extendedBy;

    /**
	 * The cached value of the '{@link #getImplementedBy() <em>Implemented By</em>}' reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getImplementedBy()
	 * @generated
	 * @ordered
	 */
    protected EList<InterfaceImplementation> implementedBy;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected InterfaceImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PapayaPackage.Literals.INTERFACE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Interface> getExtends() {
		if (extends_ == null)
		{
			extends_ = new EObjectWithInverseResolvingEList.ManyInverse<Interface>(Interface.class, this, PapayaPackage.INTERFACE__EXTENDS, PapayaPackage.INTERFACE__EXTENDED_BY);
		}
		return extends_;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Interface> getExtendedBy() {
		if (extendedBy == null)
		{
			extendedBy = new EObjectWithInverseResolvingEList.ManyInverse<Interface>(Interface.class, this, PapayaPackage.INTERFACE__EXTENDED_BY, PapayaPackage.INTERFACE__EXTENDS);
		}
		return extendedBy;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getOperations() {
		if (operations == null)
		{
			operations = new EObjectContainmentEList<Operation>(Operation.class, this, PapayaPackage.INTERFACE__OPERATIONS);
		}
		return operations;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<InterfaceImplementation> getImplementedBy() {
		if (implementedBy == null)
		{
			implementedBy = new EObjectWithInverseResolvingEList.ManyInverse<InterfaceImplementation>(InterfaceImplementation.class, this, PapayaPackage.INTERFACE__IMPLEMENTED_BY, PapayaPackage.INTERFACE_IMPLEMENTATION__IMPLEMENTS);
		}
		return implementedBy;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Classifier> getSubtypes() {
		// TODO: implement this method to return the 'Subtypes' reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Classifier> getAllSubtypes() {
		// TODO: implement this method to return the 'All Subtypes' reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case PapayaPackage.INTERFACE__EXTENDS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getExtends()).basicAdd(otherEnd, msgs);
			case PapayaPackage.INTERFACE__EXTENDED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getExtendedBy()).basicAdd(otherEnd, msgs);
			case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getImplementedBy()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case PapayaPackage.INTERFACE__EXTENDS:
				return ((InternalEList<?>)getExtends()).basicRemove(otherEnd, msgs);
			case PapayaPackage.INTERFACE__EXTENDED_BY:
				return ((InternalEList<?>)getExtendedBy()).basicRemove(otherEnd, msgs);
			case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
				return ((InternalEList<?>)getImplementedBy()).basicRemove(otherEnd, msgs);
			case PapayaPackage.INTERFACE__OPERATIONS:
				return ((InternalEList<?>)getOperations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case PapayaPackage.INTERFACE__EXTENDS:
				return getExtends();
			case PapayaPackage.INTERFACE__EXTENDED_BY:
				return getExtendedBy();
			case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
				return getImplementedBy();
			case PapayaPackage.INTERFACE__SUBTYPES:
				return getSubtypes();
			case PapayaPackage.INTERFACE__ALL_SUBTYPES:
				return getAllSubtypes();
			case PapayaPackage.INTERFACE__OPERATIONS:
				return getOperations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case PapayaPackage.INTERFACE__EXTENDS:
				getExtends().clear();
				getExtends().addAll((Collection<? extends Interface>)newValue);
				return;
			case PapayaPackage.INTERFACE__EXTENDED_BY:
				getExtendedBy().clear();
				getExtendedBy().addAll((Collection<? extends Interface>)newValue);
				return;
			case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
				getImplementedBy().clear();
				getImplementedBy().addAll((Collection<? extends InterfaceImplementation>)newValue);
				return;
			case PapayaPackage.INTERFACE__OPERATIONS:
				getOperations().clear();
				getOperations().addAll((Collection<? extends Operation>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case PapayaPackage.INTERFACE__EXTENDS:
				getExtends().clear();
				return;
			case PapayaPackage.INTERFACE__EXTENDED_BY:
				getExtendedBy().clear();
				return;
			case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
				getImplementedBy().clear();
				return;
			case PapayaPackage.INTERFACE__OPERATIONS:
				getOperations().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case PapayaPackage.INTERFACE__EXTENDS:
				return extends_ != null && !extends_.isEmpty();
			case PapayaPackage.INTERFACE__EXTENDED_BY:
				return extendedBy != null && !extendedBy.isEmpty();
			case PapayaPackage.INTERFACE__IMPLEMENTED_BY:
				return implementedBy != null && !implementedBy.isEmpty();
			case PapayaPackage.INTERFACE__SUBTYPES:
				return !getSubtypes().isEmpty();
			case PapayaPackage.INTERFACE__ALL_SUBTYPES:
				return !getAllSubtypes().isEmpty();
			case PapayaPackage.INTERFACE__OPERATIONS:
				return operations != null && !operations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // InterfaceImpl
