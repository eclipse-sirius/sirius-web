/**
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.ImageDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Image Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.ImageDescriptionImpl#getUrl <em>Url</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImageDescriptionImpl extends WidgetDescriptionImpl implements ImageDescription {
    /**
     * The default value of the '{@link #getUrlExpression() <em>Url Expression</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getUrlExpression()
     * @generated
     * @ordered
     */
    protected static final String URL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUrlExpression() <em>Url Expression</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getUrlExpression()
     * @generated
     * @ordered
     */
    protected String urlExpression = URL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getMaxWidthExpression() <em>Max Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMaxWidthExpression()
     * @generated
     * @ordered
     */
    protected static final String MAX_WIDTH_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMaxWidthExpression() <em>Max Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMaxWidthExpression()
     * @generated
     * @ordered
     */
    protected String maxWidthExpression = MAX_WIDTH_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ImageDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.IMAGE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getUrlExpression() {
        return this.urlExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUrlExpression(String newUrlExpression) {
        String oldUrlExpression = this.urlExpression;
        this.urlExpression = newUrlExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.IMAGE_DESCRIPTION__URL_EXPRESSION, oldUrlExpression, this.urlExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getMaxWidthExpression() {
        return this.maxWidthExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMaxWidthExpression(String newMaxWidthExpression) {
        String oldMaxWidthExpression = this.maxWidthExpression;
        this.maxWidthExpression = newMaxWidthExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION, oldMaxWidthExpression, this.maxWidthExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
            return this.getUrlExpression();
        case ViewPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
            return this.getMaxWidthExpression();
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
        case ViewPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
            this.setUrlExpression((String) newValue);
            return;
        case ViewPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
            this.setMaxWidthExpression((String) newValue);
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
        case ViewPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
            this.setUrlExpression(URL_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
            this.setMaxWidthExpression(MAX_WIDTH_EXPRESSION_EDEFAULT);
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
        case ViewPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
            return URL_EXPRESSION_EDEFAULT == null ? this.urlExpression != null : !URL_EXPRESSION_EDEFAULT.equals(this.urlExpression);
        case ViewPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
            return MAX_WIDTH_EXPRESSION_EDEFAULT == null ? this.maxWidthExpression != null : !MAX_WIDTH_EXPRESSION_EDEFAULT.equals(this.maxWidthExpression);
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
        result.append(" (urlExpression: ");
        result.append(this.urlExpression);
        result.append(", maxWidthExpression: ");
        result.append(this.maxWidthExpression);
        result.append(')');
        return result.toString();
    }

} // ImageDescriptionImpl
