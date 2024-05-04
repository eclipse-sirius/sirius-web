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
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Visibility;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Operation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationImpl extends TypedElementImpl implements Operation {
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
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected EList<Parameter> parameters;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.OPERATION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATION__VISIBILITY, oldVisibility, this.visibility));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Parameter> getParameters() {
        if (this.parameters == null) {
            this.parameters = new EObjectContainmentEList<>(Parameter.class, this, PapayaPackage.OPERATION__PARAMETERS);
        }
        return this.parameters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.OPERATION__PARAMETERS:
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
            case PapayaPackage.OPERATION__VISIBILITY:
                return this.getVisibility();
            case PapayaPackage.OPERATION__PARAMETERS:
                return this.getParameters();
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
            case PapayaPackage.OPERATION__VISIBILITY:
                this.setVisibility((Visibility) newValue);
                return;
            case PapayaPackage.OPERATION__PARAMETERS:
                this.getParameters().clear();
                this.getParameters().addAll((Collection<? extends Parameter>) newValue);
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
            case PapayaPackage.OPERATION__VISIBILITY:
                this.setVisibility(VISIBILITY_EDEFAULT);
                return;
            case PapayaPackage.OPERATION__PARAMETERS:
                this.getParameters().clear();
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
            case PapayaPackage.OPERATION__VISIBILITY:
                return this.visibility != VISIBILITY_EDEFAULT;
            case PapayaPackage.OPERATION__PARAMETERS:
                return this.parameters != null && !this.parameters.isEmpty();
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

} // OperationImpl
