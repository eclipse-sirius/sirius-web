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
package org.eclipse.sirius.components.view.form.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.view.impl.ConditionalImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional Date Time Description
 * Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getBackgroundColor
 * <em>Background Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getForegroundColor
 * <em>Foreground Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#isItalic
 * <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#isBold
 * <em>Bold</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalDateTimeDescriptionStyleImpl extends ConditionalImpl implements ConditionalDateTimeDescriptionStyle {
    /**
     * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColor()
     * @generated
     * @ordered
     */
    protected UserColor backgroundColor;

    /**
     * The cached value of the '{@link #getForegroundColor() <em>Foreground Color</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getForegroundColor()
     * @generated
     * @ordered
     */
    protected UserColor foregroundColor;

    /**
     * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected static final boolean ITALIC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected boolean italic = ITALIC_EDEFAULT;

    /**
     * The default value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected static final boolean BOLD_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected boolean bold = BOLD_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalDateTimeDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getBackgroundColor() {
        if (this.backgroundColor != null && this.backgroundColor.eIsProxy()) {
            InternalEObject oldBackgroundColor = (InternalEObject) this.backgroundColor;
            this.backgroundColor = (UserColor) this.eResolveProxy(oldBackgroundColor);
            if (this.backgroundColor != oldBackgroundColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
            }
        }
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBackgroundColor(UserColor newBackgroundColor) {
        UserColor oldBackgroundColor = this.backgroundColor;
        this.backgroundColor = newBackgroundColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getForegroundColor() {
        if (this.foregroundColor != null && this.foregroundColor.eIsProxy()) {
            InternalEObject oldForegroundColor = (InternalEObject) this.foregroundColor;
            this.foregroundColor = (UserColor) this.eResolveProxy(oldForegroundColor);
            if (this.foregroundColor != oldForegroundColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
            }
        }
        return this.foregroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetForegroundColor() {
        return this.foregroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setForegroundColor(UserColor newForegroundColor) {
        UserColor oldForegroundColor = this.foregroundColor;
        this.foregroundColor = newForegroundColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isItalic() {
        return this.italic;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setItalic(boolean newItalic) {
        boolean oldItalic = this.italic;
        this.italic = newItalic;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC, oldItalic, this.italic));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isBold() {
        return this.bold;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBold(boolean newBold) {
        boolean oldBold = this.bold;
        this.bold = newBold;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD, oldBold, this.bold));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                if (resolve)
                    return this.getBackgroundColor();
                return this.basicGetBackgroundColor();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                if (resolve)
                    return this.getForegroundColor();
                return this.basicGetForegroundColor();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                return this.isItalic();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                return this.isBold();
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
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                this.setBold((Boolean) newValue);
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
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) null);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) null);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                this.setBold(BOLD_EDEFAULT);
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
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                return this.backgroundColor != null;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                return this.foregroundColor != null;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                return this.bold != BOLD_EDEFAULT;
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
        if (baseClass == WidgetDescriptionStyle.class) {
            switch (derivedFeatureID) {
                default:
                    return -1;
            }
        }
        if (baseClass == DateTimeDescriptionStyle.class) {
            switch (derivedFeatureID) {
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD;
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
        if (baseClass == WidgetDescriptionStyle.class) {
            switch (baseFeatureID) {
                default:
                    return -1;
            }
        }
        if (baseClass == DateTimeDescriptionStyle.class) {
            switch (baseFeatureID) {
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD;
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
        result.append(" (italic: ");
        result.append(this.italic);
        result.append(", bold: ");
        result.append(this.bold);
        result.append(')');
        return result.toString();
    }

} // ConditionalDateTimeDescriptionStyleImpl
