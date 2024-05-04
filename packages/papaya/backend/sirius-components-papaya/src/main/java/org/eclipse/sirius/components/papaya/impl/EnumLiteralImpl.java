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
import org.eclipse.sirius.components.papaya.AnnotableElement;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.EnumLiteral;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Enum Literal</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.EnumLiteralImpl#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumLiteralImpl extends NamedElementImpl implements EnumLiteral {
    /**
     * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getAnnotations()
     * @generated
     * @ordered
     */
    protected EList<Annotation> annotations;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EnumLiteralImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.ENUM_LITERAL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Annotation> getAnnotations() {
        if (this.annotations == null) {
            this.annotations = new EObjectResolvingEList<>(Annotation.class, this, PapayaPackage.ENUM_LITERAL__ANNOTATIONS);
        }
        return this.annotations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.ENUM_LITERAL__ANNOTATIONS:
                return this.getAnnotations();
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
            case PapayaPackage.ENUM_LITERAL__ANNOTATIONS:
                this.getAnnotations().clear();
                this.getAnnotations().addAll((Collection<? extends Annotation>) newValue);
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
            case PapayaPackage.ENUM_LITERAL__ANNOTATIONS:
                this.getAnnotations().clear();
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
            case PapayaPackage.ENUM_LITERAL__ANNOTATIONS:
                return this.annotations != null && !this.annotations.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == AnnotableElement.class) {
            switch (derivedFeatureID) {
                case PapayaPackage.ENUM_LITERAL__ANNOTATIONS:
                    return PapayaPackage.ANNOTABLE_ELEMENT__ANNOTATIONS;
                default:
                    return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == AnnotableElement.class) {
            switch (baseFeatureID) {
                case PapayaPackage.ANNOTABLE_ELEMENT__ANNOTATIONS:
                    return PapayaPackage.ENUM_LITERAL__ANNOTATIONS;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // EnumLiteralImpl
