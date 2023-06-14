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

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceOwnerExpression
 * <em>Reference Owner Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceNameExpression
 * <em>Reference Name Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceWidgetDescriptionImpl extends WidgetDescriptionImpl implements ReferenceWidgetDescription {

    /**
     * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;
    /**
     * The default value of the '{@link #getReferenceOwnerExpression() <em>Reference Owner Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getReferenceOwnerExpression()
     */
    protected static final String REFERENCE_OWNER_EXPRESSION_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getReferenceOwnerExpression() <em>Reference Owner Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getReferenceOwnerExpression()
     */
    protected String referenceOwnerExpression = REFERENCE_OWNER_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getReferenceNameExpression()
     */
    protected static final String REFERENCE_NAME_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getReferenceNameExpression()
     */
    protected String referenceNameExpression = REFERENCE_NAME_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ReferenceWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getReferenceOwnerExpression() {
        return this.referenceOwnerExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setReferenceOwnerExpression(String newReferenceOwnerExpression) {
        String oldReferenceOwnerExpression = this.referenceOwnerExpression;
        this.referenceOwnerExpression = newReferenceOwnerExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION, oldReferenceOwnerExpression,
                    this.referenceOwnerExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getReferenceNameExpression() {
        return this.referenceNameExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setReferenceNameExpression(String newReferenceNameExpression) {
        String oldReferenceNameExpression = this.referenceNameExpression;
        this.referenceNameExpression = newReferenceNameExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION, oldReferenceNameExpression, this.referenceNameExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsEnabledExpression() {
        return this.isEnabledExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsEnabledExpression(String newIsEnabledExpression) {
        String oldIsEnabledExpression = this.isEnabledExpression;
        this.isEnabledExpression = newIsEnabledExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                return this.getReferenceOwnerExpression();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                return this.getReferenceNameExpression();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                this.setReferenceOwnerExpression((String) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                this.setReferenceNameExpression((String) newValue);
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
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                this.setReferenceOwnerExpression(REFERENCE_OWNER_EXPRESSION_EDEFAULT);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                this.setReferenceNameExpression(REFERENCE_NAME_EXPRESSION_EDEFAULT);
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
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return !Objects.equals(IS_ENABLED_EXPRESSION_EDEFAULT, this.isEnabledExpression);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                return REFERENCE_OWNER_EXPRESSION_EDEFAULT == null ? this.referenceOwnerExpression != null : !REFERENCE_OWNER_EXPRESSION_EDEFAULT.equals(this.referenceOwnerExpression);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                return REFERENCE_NAME_EXPRESSION_EDEFAULT == null ? this.referenceNameExpression != null : !REFERENCE_NAME_EXPRESSION_EDEFAULT.equals(this.referenceNameExpression);
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
        result.append(" (isEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(", referenceOwnerExpression: ");
        result.append(this.referenceOwnerExpression);
        result.append(", referenceNameExpression: ");
        result.append(this.referenceNameExpression);
        result.append(')');
        return result.toString();
    }

} // ReferenceWidgetDescriptionImpl
