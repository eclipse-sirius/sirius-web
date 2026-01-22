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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.AnnotableElement;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.GenericType;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Type;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Generic Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.GenericTypeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.GenericTypeImpl#getRawType <em>Raw Type</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.GenericTypeImpl#getTypeArguments <em>Type Arguments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericTypeImpl extends ModelElementImpl implements GenericType {
    /**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
    protected EList<Annotation> annotations;

    /**
	 * The cached value of the '{@link #getRawType() <em>Raw Type</em>}' reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getRawType()
	 * @generated
	 * @ordered
	 */
    protected Type rawType;

    /**
     * The cached value of the '{@link #getTypeArguments() <em>Type Arguments</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTypeArguments()
     * @generated
     * @ordered
     */
    protected EList<GenericType> typeArguments;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected GenericTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PapayaPackage.Literals.GENERIC_TYPE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Annotation> getAnnotations() {
		if (annotations == null)
		{
			annotations = new EObjectResolvingEList<Annotation>(Annotation.class, this, PapayaPackage.GENERIC_TYPE__ANNOTATIONS);
		}
		return annotations;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Type getRawType() {
		if (rawType != null && rawType.eIsProxy())
		{
			InternalEObject oldRawType = (InternalEObject)rawType;
			rawType = (Type)eResolveProxy(oldRawType);
			if (rawType != oldRawType)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapayaPackage.GENERIC_TYPE__RAW_TYPE, oldRawType, rawType));
			}
		}
		return rawType;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public Type basicGetRawType() {
		return rawType;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setRawType(Type newRawType) {
		Type oldRawType = rawType;
		rawType = newRawType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.GENERIC_TYPE__RAW_TYPE, oldRawType, rawType));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<GenericType> getTypeArguments() {
		if (typeArguments == null)
		{
			typeArguments = new EObjectContainmentEList<GenericType>(GenericType.class, this, PapayaPackage.GENERIC_TYPE__TYPE_ARGUMENTS);
		}
		return typeArguments;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case PapayaPackage.GENERIC_TYPE__TYPE_ARGUMENTS:
				return ((InternalEList<?>)getTypeArguments()).basicRemove(otherEnd, msgs);
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
			case PapayaPackage.GENERIC_TYPE__ANNOTATIONS:
				return getAnnotations();
			case PapayaPackage.GENERIC_TYPE__RAW_TYPE:
				if (resolve) return getRawType();
				return basicGetRawType();
			case PapayaPackage.GENERIC_TYPE__TYPE_ARGUMENTS:
				return getTypeArguments();
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
			case PapayaPackage.GENERIC_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case PapayaPackage.GENERIC_TYPE__RAW_TYPE:
				setRawType((Type)newValue);
				return;
			case PapayaPackage.GENERIC_TYPE__TYPE_ARGUMENTS:
				getTypeArguments().clear();
				getTypeArguments().addAll((Collection<? extends GenericType>)newValue);
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
			case PapayaPackage.GENERIC_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case PapayaPackage.GENERIC_TYPE__RAW_TYPE:
				setRawType((Type)null);
				return;
			case PapayaPackage.GENERIC_TYPE__TYPE_ARGUMENTS:
				getTypeArguments().clear();
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
			case PapayaPackage.GENERIC_TYPE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case PapayaPackage.GENERIC_TYPE__RAW_TYPE:
				return rawType != null;
			case PapayaPackage.GENERIC_TYPE__TYPE_ARGUMENTS:
				return typeArguments != null && !typeArguments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == AnnotableElement.class)
		{
			switch (derivedFeatureID)
			{
				case PapayaPackage.GENERIC_TYPE__ANNOTATIONS: return PapayaPackage.ANNOTABLE_ELEMENT__ANNOTATIONS;
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
		if (baseClass == AnnotableElement.class)
		{
			switch (baseFeatureID)
			{
				case PapayaPackage.ANNOTABLE_ELEMENT__ANNOTATIONS: return PapayaPackage.GENERIC_TYPE__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // GenericTypeImpl
