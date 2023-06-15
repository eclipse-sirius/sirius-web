/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widgets.reference.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.impl.WidgetDescriptionImpl;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Widget Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceOwnerExpression <em>Reference Owner Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceNameExpression <em>Reference Name Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceWidgetDescriptionImpl extends WidgetDescriptionImpl implements ReferenceWidgetDescription {
    /**
     * The default value of the '{@link #getReferenceOwnerExpression() <em>Reference Owner Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceOwnerExpression()
     * @generated
     * @ordered
     */
    protected static final String REFERENCE_OWNER_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceOwnerExpression() <em>Reference Owner Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceOwnerExpression()
     * @generated
     * @ordered
     */
    protected String referenceOwnerExpression = REFERENCE_OWNER_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceNameExpression()
     * @generated
     * @ordered
     */
    protected static final String REFERENCE_NAME_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceNameExpression()
     * @generated
     * @ordered
     */
    protected String referenceNameExpression = REFERENCE_NAME_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ReferenceWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getReferenceOwnerExpression() {
        return referenceOwnerExpression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setReferenceOwnerExpression(String newReferenceOwnerExpression) {
        String oldReferenceOwnerExpression = referenceOwnerExpression;
        referenceOwnerExpression = newReferenceOwnerExpression;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION, oldReferenceOwnerExpression, referenceOwnerExpression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getReferenceNameExpression() {
        return referenceNameExpression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setReferenceNameExpression(String newReferenceNameExpression) {
        String oldReferenceNameExpression = referenceNameExpression;
        referenceNameExpression = newReferenceNameExpression;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION, oldReferenceNameExpression, referenceNameExpression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                return getReferenceOwnerExpression();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                return getReferenceNameExpression();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                setReferenceOwnerExpression((String)newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                setReferenceNameExpression((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                setReferenceOwnerExpression(REFERENCE_OWNER_EXPRESSION_EDEFAULT);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                setReferenceNameExpression(REFERENCE_NAME_EXPRESSION_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                return REFERENCE_OWNER_EXPRESSION_EDEFAULT == null ? referenceOwnerExpression != null : !REFERENCE_OWNER_EXPRESSION_EDEFAULT.equals(referenceOwnerExpression);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                return REFERENCE_NAME_EXPRESSION_EDEFAULT == null ? referenceNameExpression != null : !REFERENCE_NAME_EXPRESSION_EDEFAULT.equals(referenceNameExpression);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (referenceOwnerExpression: ");
        result.append(referenceOwnerExpression);
        result.append(", referenceNameExpression: ");
        result.append(referenceNameExpression);
        result.append(')');
        return result.toString();
    }

} //ReferenceWidgetDescriptionImpl
