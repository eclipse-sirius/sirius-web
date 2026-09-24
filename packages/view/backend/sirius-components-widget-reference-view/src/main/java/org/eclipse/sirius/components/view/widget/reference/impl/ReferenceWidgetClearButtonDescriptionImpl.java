/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget Clear Button Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetClearButtonDescriptionImpl#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetClearButtonDescriptionImpl#getBody
 * <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceWidgetClearButtonDescriptionImpl extends MinimalEObjectImpl.Container implements ReferenceWidgetClearButtonDescription {
    /**
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPreconditionExpression()
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPreconditionExpression()
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBody()
     */
    protected EList<Operation> body;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ReferenceWidgetClearButtonDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getPreconditionExpression() {
        return this.preconditionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPreconditionExpression(String newPreconditionExpression) {
        String oldPreconditionExpression = this.preconditionExpression;
        this.preconditionExpression = newPreconditionExpression;
        if (this.eNotificationRequired()) {
            this.eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression,
                    this.preconditionExpression));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
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
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY:
                return this.getBody();
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
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
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
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY:
                this.getBody().clear();
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
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case ReferencePackage.REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
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
        if (this.eIsProxy()) {
            return super.toString();
        }

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(')');
        return result.toString();
    }

} // ReferenceWidgetClearButtonDescriptionImpl
