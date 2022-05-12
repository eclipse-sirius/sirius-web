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
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Textfield Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.TextfieldDescriptionStyleImpl#getBackgroundColor <em>Background
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.TextfieldDescriptionStyleImpl#getForegroundColor <em>Foreground
 * Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TextfieldDescriptionStyleImpl extends WidgetDescriptionStyleImpl implements TextfieldDescriptionStyle {
    /**
     * The default value of the '{@link #getBackgroundColor() <em>Background Color</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColor()
     * @generated
     * @ordered
     */
    protected static final String BACKGROUND_COLOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColor()
     * @generated
     * @ordered
     */
    protected String backgroundColor = BACKGROUND_COLOR_EDEFAULT;

    /**
     * The default value of the '{@link #getForegroundColor() <em>Foreground Color</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getForegroundColor()
     * @generated
     * @ordered
     */
    protected static final String FOREGROUND_COLOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getForegroundColor() <em>Foreground Color</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getForegroundColor()
     * @generated
     * @ordered
     */
    protected String foregroundColor = FOREGROUND_COLOR_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TextfieldDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.TEXTFIELD_DESCRIPTION_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBackgroundColor(String newBackgroundColor) {
        String oldBackgroundColor = this.backgroundColor;
        this.backgroundColor = newBackgroundColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getForegroundColor() {
        return this.foregroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setForegroundColor(String newForegroundColor) {
        String oldForegroundColor = this.foregroundColor;
        this.foregroundColor = newForegroundColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR:
            return this.getBackgroundColor();
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR:
            return this.getForegroundColor();
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
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR:
            this.setBackgroundColor((String) newValue);
            return;
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR:
            this.setForegroundColor((String) newValue);
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
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR:
            this.setBackgroundColor(BACKGROUND_COLOR_EDEFAULT);
            return;
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR:
            this.setForegroundColor(FOREGROUND_COLOR_EDEFAULT);
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
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR:
            return BACKGROUND_COLOR_EDEFAULT == null ? this.backgroundColor != null : !BACKGROUND_COLOR_EDEFAULT.equals(this.backgroundColor);
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR:
            return FOREGROUND_COLOR_EDEFAULT == null ? this.foregroundColor != null : !FOREGROUND_COLOR_EDEFAULT.equals(this.foregroundColor);
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
        result.append(" (backgroundColor: "); //$NON-NLS-1$
        result.append(this.backgroundColor);
        result.append(", foregroundColor: "); //$NON-NLS-1$
        result.append(this.foregroundColor);
        result.append(')');
        return result.toString();
    }

} // TextfieldDescriptionStyleImpl
