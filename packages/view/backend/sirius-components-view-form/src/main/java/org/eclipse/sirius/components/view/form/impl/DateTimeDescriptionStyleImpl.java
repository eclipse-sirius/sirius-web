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
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetFlexboxLayout;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Date Time Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#getFlexDirection <em>Flex
 * Direction</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#getGap <em>Gap</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#getLabelFlex <em>Label
 * Flex</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#getValueFlex <em>Value
 * Flex</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#getBackgroundColor
 * <em>Background Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#getForegroundColor
 * <em>Foreground Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionStyleImpl#isBold <em>Bold</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DateTimeDescriptionStyleImpl extends WidgetDescriptionStyleImpl implements DateTimeDescriptionStyle {
    /**
     * The default value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated
     * @ordered
     */
    protected static final String FLEX_DIRECTION_EDEFAULT = "column";

    /**
     * The cached value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated
     * @ordered
     */
    protected String flexDirection = FLEX_DIRECTION_EDEFAULT;

    /**
     * The default value of the '{@link #getGap() <em>Gap</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getGap()
     * @generated
     * @ordered
     */
    protected static final String GAP_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getGap() <em>Gap</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGap()
     * @generated
     * @ordered
     */
    protected String gap = GAP_EDEFAULT;

    /**
     * The default value of the '{@link #getLabelFlex() <em>Label Flex</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelFlex()
     * @generated
     * @ordered
     */
    protected static final String LABEL_FLEX_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getLabelFlex() <em>Label Flex</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelFlex()
     * @generated
     * @ordered
     */
    protected String labelFlex = LABEL_FLEX_EDEFAULT;

    /**
     * The default value of the '{@link #getValueFlex() <em>Value Flex</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getValueFlex()
     * @generated
     * @ordered
     */
    protected static final String VALUE_FLEX_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getValueFlex() <em>Value Flex</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getValueFlex()
     * @generated
     * @ordered
     */
    protected String valueFlex = VALUE_FLEX_EDEFAULT;

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
    protected DateTimeDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.DATE_TIME_DESCRIPTION_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getFlexDirection() {
        return this.flexDirection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFlexDirection(String newFlexDirection) {
        String oldFlexDirection = this.flexDirection;
        this.flexDirection = newFlexDirection;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__FLEX_DIRECTION, oldFlexDirection, this.flexDirection));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getGap() {
        return this.gap;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setGap(String newGap) {
        String oldGap = this.gap;
        this.gap = newGap;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__GAP, oldGap, this.gap));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelFlex() {
        return this.labelFlex;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelFlex(String newLabelFlex) {
        String oldLabelFlex = this.labelFlex;
        this.labelFlex = newLabelFlex;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__LABEL_FLEX, oldLabelFlex, this.labelFlex));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValueFlex() {
        return this.valueFlex;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValueFlex(String newValueFlex) {
        String oldValueFlex = this.valueFlex;
        this.valueFlex = newValueFlex;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__VALUE_FLEX, oldValueFlex, this.valueFlex));
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC, oldItalic, this.italic));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD, oldBold, this.bold));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FLEX_DIRECTION:
                return this.getFlexDirection();
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__GAP:
                return this.getGap();
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__LABEL_FLEX:
                return this.getLabelFlex();
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__VALUE_FLEX:
                return this.getValueFlex();
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                if (resolve)
                    return this.getBackgroundColor();
                return this.basicGetBackgroundColor();
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                if (resolve)
                    return this.getForegroundColor();
                return this.basicGetForegroundColor();
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                return this.isItalic();
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD:
                return this.isBold();
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
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FLEX_DIRECTION:
                this.setFlexDirection((String) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__GAP:
                this.setGap((String) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__LABEL_FLEX:
                this.setLabelFlex((String) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__VALUE_FLEX:
                this.setValueFlex((String) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD:
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
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FLEX_DIRECTION:
                this.setFlexDirection(FLEX_DIRECTION_EDEFAULT);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__GAP:
                this.setGap(GAP_EDEFAULT);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__LABEL_FLEX:
                this.setLabelFlex(LABEL_FLEX_EDEFAULT);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__VALUE_FLEX:
                this.setValueFlex(VALUE_FLEX_EDEFAULT);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) null);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) null);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD:
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
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FLEX_DIRECTION:
                return FLEX_DIRECTION_EDEFAULT == null ? this.flexDirection != null : !FLEX_DIRECTION_EDEFAULT.equals(this.flexDirection);
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__GAP:
                return GAP_EDEFAULT == null ? this.gap != null : !GAP_EDEFAULT.equals(this.gap);
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__LABEL_FLEX:
                return LABEL_FLEX_EDEFAULT == null ? this.labelFlex != null : !LABEL_FLEX_EDEFAULT.equals(this.labelFlex);
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__VALUE_FLEX:
                return VALUE_FLEX_EDEFAULT == null ? this.valueFlex != null : !VALUE_FLEX_EDEFAULT.equals(this.valueFlex);
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                return this.backgroundColor != null;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                return this.foregroundColor != null;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD:
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
        if (baseClass == WidgetFlexboxLayout.class) {
            switch (derivedFeatureID) {
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FLEX_DIRECTION:
                    return FormPackage.WIDGET_FLEXBOX_LAYOUT__FLEX_DIRECTION;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__GAP:
                    return FormPackage.WIDGET_FLEXBOX_LAYOUT__GAP;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__LABEL_FLEX:
                    return FormPackage.WIDGET_FLEXBOX_LAYOUT__LABEL_FLEX;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__VALUE_FLEX:
                    return FormPackage.WIDGET_FLEXBOX_LAYOUT__VALUE_FLEX;
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
        if (baseClass == WidgetFlexboxLayout.class) {
            switch (baseFeatureID) {
                case FormPackage.WIDGET_FLEXBOX_LAYOUT__FLEX_DIRECTION:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__FLEX_DIRECTION;
                case FormPackage.WIDGET_FLEXBOX_LAYOUT__GAP:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__GAP;
                case FormPackage.WIDGET_FLEXBOX_LAYOUT__LABEL_FLEX:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__LABEL_FLEX;
                case FormPackage.WIDGET_FLEXBOX_LAYOUT__VALUE_FLEX:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__VALUE_FLEX;
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
        result.append(" (flexDirection: ");
        result.append(this.flexDirection);
        result.append(", gap: ");
        result.append(this.gap);
        result.append(", labelFlex: ");
        result.append(this.labelFlex);
        result.append(", valueFlex: ");
        result.append(this.valueFlex);
        result.append(", italic: ");
        result.append(this.italic);
        result.append(", bold: ");
        result.append(this.bold);
        result.append(')');
        return result.toString();
    }

} // DateTimeDescriptionStyleImpl
