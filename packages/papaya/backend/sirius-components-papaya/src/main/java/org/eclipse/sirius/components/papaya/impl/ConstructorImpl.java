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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.Constructor;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Visibility;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Constructor</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ConstructorImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ConstructorImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ConstructorImpl#getVisibility <em>Visibility</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstructorImpl extends MinimalEObjectImpl.Container implements Constructor {
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
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected EList<Parameter> parameters;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConstructorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.CONSTRUCTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Annotation> getAnnotations() {
        if (this.annotations == null) {
            this.annotations = new EObjectResolvingEList<>(Annotation.class, this, PapayaPackage.CONSTRUCTOR__ANNOTATIONS);
        }
        return this.annotations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Parameter> getParameters() {
        if (this.parameters == null) {
            this.parameters = new EObjectContainmentEList<>(Parameter.class, this, PapayaPackage.CONSTRUCTOR__PARAMETERS);
        }
        return this.parameters;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.CONSTRUCTOR__VISIBILITY, oldVisibility, this.visibility));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.CONSTRUCTOR__PARAMETERS:
                return ((InternalEList<?>) this.getParameters()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.CONSTRUCTOR__ANNOTATIONS:
                return this.getAnnotations();
            case PapayaPackage.CONSTRUCTOR__PARAMETERS:
                return this.getParameters();
            case PapayaPackage.CONSTRUCTOR__VISIBILITY:
                return this.getVisibility();
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
            case PapayaPackage.CONSTRUCTOR__ANNOTATIONS:
                this.getAnnotations().clear();
                this.getAnnotations().addAll((Collection<? extends Annotation>) newValue);
                return;
            case PapayaPackage.CONSTRUCTOR__PARAMETERS:
                this.getParameters().clear();
                this.getParameters().addAll((Collection<? extends Parameter>) newValue);
                return;
            case PapayaPackage.CONSTRUCTOR__VISIBILITY:
                this.setVisibility((Visibility) newValue);
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
            case PapayaPackage.CONSTRUCTOR__ANNOTATIONS:
                this.getAnnotations().clear();
                return;
            case PapayaPackage.CONSTRUCTOR__PARAMETERS:
                this.getParameters().clear();
                return;
            case PapayaPackage.CONSTRUCTOR__VISIBILITY:
                this.setVisibility(VISIBILITY_EDEFAULT);
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
            case PapayaPackage.CONSTRUCTOR__ANNOTATIONS:
                return this.annotations != null && !this.annotations.isEmpty();
            case PapayaPackage.CONSTRUCTOR__PARAMETERS:
                return this.parameters != null && !this.parameters.isEmpty();
            case PapayaPackage.CONSTRUCTOR__VISIBILITY:
                return this.visibility != VISIBILITY_EDEFAULT;
        }
        return super.eIsSet(featureID);
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

} // ConstructorImpl
