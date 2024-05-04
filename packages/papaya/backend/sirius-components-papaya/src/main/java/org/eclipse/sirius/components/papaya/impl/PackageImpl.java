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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.AnnotableElement;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Type;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Package</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.PackageImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.PackageImpl#getQualifiedName <em>Qualified Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.PackageImpl#getTypes <em>Types</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.PackageImpl#getPackages <em>Packages</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageImpl extends NamedElementImpl implements org.eclipse.sirius.components.papaya.Package {
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
     * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getQualifiedName()
     * @generated
     * @ordered
     */
    protected static final String QUALIFIED_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getTypes()
     * @generated
     * @ordered
     */
    protected EList<Type> types;

    /**
     * The cached value of the '{@link #getPackages() <em>Packages</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPackages()
     * @generated
     * @ordered
     */
    protected EList<org.eclipse.sirius.components.papaya.Package> packages;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PackageImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.PACKAGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Annotation> getAnnotations() {
        if (this.annotations == null) {
            this.annotations = new EObjectResolvingEList<>(Annotation.class, this, PapayaPackage.PACKAGE__ANNOTATIONS);
        }
        return this.annotations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getQualifiedName() {
        // TODO: implement this method to return the 'Qualified Name' attribute
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isSetQualifiedName() {
        // TODO: implement this method to return whether the 'Qualified Name' attribute is set
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Type> getTypes() {
        if (this.types == null) {
            this.types = new EObjectContainmentEList<>(Type.class, this, PapayaPackage.PACKAGE__TYPES);
        }
        return this.types;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<org.eclipse.sirius.components.papaya.Package> getPackages() {
        if (this.packages == null) {
            this.packages = new EObjectContainmentEList<>(org.eclipse.sirius.components.papaya.Package.class, this, PapayaPackage.PACKAGE__PACKAGES);
        }
        return this.packages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.PACKAGE__TYPES:
                return ((InternalEList<?>) this.getTypes()).basicRemove(otherEnd, msgs);
            case PapayaPackage.PACKAGE__PACKAGES:
                return ((InternalEList<?>) this.getPackages()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.PACKAGE__ANNOTATIONS:
                return this.getAnnotations();
            case PapayaPackage.PACKAGE__QUALIFIED_NAME:
                return this.getQualifiedName();
            case PapayaPackage.PACKAGE__TYPES:
                return this.getTypes();
            case PapayaPackage.PACKAGE__PACKAGES:
                return this.getPackages();
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
            case PapayaPackage.PACKAGE__ANNOTATIONS:
                this.getAnnotations().clear();
                this.getAnnotations().addAll((Collection<? extends Annotation>) newValue);
                return;
            case PapayaPackage.PACKAGE__TYPES:
                this.getTypes().clear();
                this.getTypes().addAll((Collection<? extends Type>) newValue);
                return;
            case PapayaPackage.PACKAGE__PACKAGES:
                this.getPackages().clear();
                this.getPackages().addAll((Collection<? extends org.eclipse.sirius.components.papaya.Package>) newValue);
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
            case PapayaPackage.PACKAGE__ANNOTATIONS:
                this.getAnnotations().clear();
                return;
            case PapayaPackage.PACKAGE__TYPES:
                this.getTypes().clear();
                return;
            case PapayaPackage.PACKAGE__PACKAGES:
                this.getPackages().clear();
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
            case PapayaPackage.PACKAGE__ANNOTATIONS:
                return this.annotations != null && !this.annotations.isEmpty();
            case PapayaPackage.PACKAGE__QUALIFIED_NAME:
                return this.isSetQualifiedName();
            case PapayaPackage.PACKAGE__TYPES:
                return this.types != null && !this.types.isEmpty();
            case PapayaPackage.PACKAGE__PACKAGES:
                return this.packages != null && !this.packages.isEmpty();
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
                case PapayaPackage.PACKAGE__ANNOTATIONS:
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
                    return PapayaPackage.PACKAGE__ANNOTATIONS;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // PackageImpl
