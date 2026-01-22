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
import org.eclipse.sirius.components.papaya.RecordComponent;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Record</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.RecordImpl#getImplements <em>Implements</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.RecordImpl#getComponents <em>Components</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.RecordImpl#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RecordImpl extends ClassifierImpl implements org.eclipse.sirius.components.papaya.Record {
    /**
	 * The cached value of the '{@link #getImplements() <em>Implements</em>}' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getImplements()
	 * @generated
	 * @ordered
	 */
    protected EList<Interface> implements_;

    /**
     * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getComponents()
     * @generated
     * @ordered
     */
    protected EList<RecordComponent> components;

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
    protected RecordImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PapayaPackage.Literals.RECORD;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<RecordComponent> getComponents() {
		if (components == null)
		{
			components = new EObjectContainmentEList<RecordComponent>(RecordComponent.class, this, PapayaPackage.RECORD__COMPONENTS);
		}
		return components;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Interface> getImplements() {
		if (implements_ == null)
		{
			implements_ = new EObjectWithInverseResolvingEList.ManyInverse<Interface>(Interface.class, this, PapayaPackage.RECORD__IMPLEMENTS, PapayaPackage.INTERFACE__IMPLEMENTED_BY);
		}
		return implements_;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getOperations() {
		if (operations == null)
		{
			operations = new EObjectContainmentEList<Operation>(Operation.class, this, PapayaPackage.RECORD__OPERATIONS);
		}
		return operations;
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
			case PapayaPackage.RECORD__IMPLEMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getImplements()).basicAdd(otherEnd, msgs);
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
			case PapayaPackage.RECORD__IMPLEMENTS:
				return ((InternalEList<?>)getImplements()).basicRemove(otherEnd, msgs);
			case PapayaPackage.RECORD__COMPONENTS:
				return ((InternalEList<?>)getComponents()).basicRemove(otherEnd, msgs);
			case PapayaPackage.RECORD__OPERATIONS:
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
			case PapayaPackage.RECORD__IMPLEMENTS:
				return getImplements();
			case PapayaPackage.RECORD__COMPONENTS:
				return getComponents();
			case PapayaPackage.RECORD__OPERATIONS:
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
			case PapayaPackage.RECORD__IMPLEMENTS:
				getImplements().clear();
				getImplements().addAll((Collection<? extends Interface>)newValue);
				return;
			case PapayaPackage.RECORD__COMPONENTS:
				getComponents().clear();
				getComponents().addAll((Collection<? extends RecordComponent>)newValue);
				return;
			case PapayaPackage.RECORD__OPERATIONS:
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
			case PapayaPackage.RECORD__IMPLEMENTS:
				getImplements().clear();
				return;
			case PapayaPackage.RECORD__COMPONENTS:
				getComponents().clear();
				return;
			case PapayaPackage.RECORD__OPERATIONS:
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
			case PapayaPackage.RECORD__IMPLEMENTS:
				return implements_ != null && !implements_.isEmpty();
			case PapayaPackage.RECORD__COMPONENTS:
				return components != null && !components.isEmpty();
			case PapayaPackage.RECORD__OPERATIONS:
				return operations != null && !operations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == InterfaceImplementation.class)
		{
			switch (derivedFeatureID)
			{
				case PapayaPackage.RECORD__IMPLEMENTS: return PapayaPackage.INTERFACE_IMPLEMENTATION__IMPLEMENTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == InterfaceImplementation.class)
		{
			switch (baseFeatureID)
			{
				case PapayaPackage.INTERFACE_IMPLEMENTATION__IMPLEMENTS: return PapayaPackage.RECORD__IMPLEMENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // RecordImpl
