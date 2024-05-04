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
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.papaya.Visibility;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TypeImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TypeImpl#getQualifiedName <em>Qualified Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TypeImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TypeImpl#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TypeImpl extends NamedElementImpl implements Type {
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
     * The default value of the '{@link #getVisibility() <em>Visibility</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getVisibility()
     * @generated
     * @ordered
     */
    protected static final Visibility VISIBILITY_EDEFAULT = Visibility.PUBLIC;

    /**
     * The cached value of the '{@link #getVisibility() <em>Visibility</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getVisibility()
     * @generated
     * @ordered
     */
    protected Visibility visibility = VISIBILITY_EDEFAULT;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Annotation> getAnnotations() {
        if (this.annotations == null) {
            this.annotations = new EObjectResolvingEList<>(Annotation.class, this, PapayaPackage.TYPE__ANNOTATIONS);
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
    public Visibility getVisibility() {
        return this.visibility;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setVisibility(Visibility newVisibility) {
        Visibility oldVisibility = this.visibility;
        this.visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TYPE__VISIBILITY, oldVisibility, this.visibility));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Type> getTypes() {
        if (this.types == null) {
            this.types = new EObjectContainmentEList<>(Type.class, this, PapayaPackage.TYPE__TYPES);
        }
        return this.types;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.TYPE__TYPES:
                return ((InternalEList<?>) this.getTypes()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.TYPE__ANNOTATIONS:
                return this.getAnnotations();
            case PapayaPackage.TYPE__QUALIFIED_NAME:
                return this.getQualifiedName();
            case PapayaPackage.TYPE__VISIBILITY:
                return this.getVisibility();
            case PapayaPackage.TYPE__TYPES:
                return this.getTypes();
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
            case PapayaPackage.TYPE__ANNOTATIONS:
                this.getAnnotations().clear();
                this.getAnnotations().addAll((Collection<? extends Annotation>) newValue);
                return;
            case PapayaPackage.TYPE__VISIBILITY:
                this.setVisibility((Visibility) newValue);
                return;
            case PapayaPackage.TYPE__TYPES:
                this.getTypes().clear();
                this.getTypes().addAll((Collection<? extends Type>) newValue);
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
            case PapayaPackage.TYPE__ANNOTATIONS:
                this.getAnnotations().clear();
                return;
            case PapayaPackage.TYPE__VISIBILITY:
                this.setVisibility(VISIBILITY_EDEFAULT);
                return;
            case PapayaPackage.TYPE__TYPES:
                this.getTypes().clear();
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
            case PapayaPackage.TYPE__ANNOTATIONS:
                return this.annotations != null && !this.annotations.isEmpty();
            case PapayaPackage.TYPE__QUALIFIED_NAME:
                return this.isSetQualifiedName();
            case PapayaPackage.TYPE__VISIBILITY:
                return this.visibility != VISIBILITY_EDEFAULT;
            case PapayaPackage.TYPE__TYPES:
                return this.types != null && !this.types.isEmpty();
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
                case PapayaPackage.TYPE__ANNOTATIONS:
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
                    return PapayaPackage.TYPE__ANNOTATIONS;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (visibility: ");
        result.append(this.visibility);
        result.append(')');
        return result.toString();
    }

} // TypeImpl
