/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetFlexboxLayout;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Checkbox Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl#getFlexDirection <em>Flex
 * Direction</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl#getGap <em>Gap</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl#getLabelFlex <em>Label
 * Flex</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl#getValueFlex <em>Value
 * Flex</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CheckboxDescriptionStyleImpl extends WidgetDescriptionStyleImpl implements CheckboxDescriptionStyle {

    /**
     * The default value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated NOT
     * @ordered
     */
    protected static final String FLEX_DIRECTION_EDEFAULT = "row-reverse";

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
     * @generated NOT
     * @ordered
     */
    protected static final String LABEL_FLEX_EDEFAULT = "1 1 auto";

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
     * The cached value of the '{@link #getColor() <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getColor()
     */
    protected UserColor color;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CheckboxDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.CHECKBOX_DESCRIPTION_STYLE;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION, oldFlexDirection, this.flexDirection));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CHECKBOX_DESCRIPTION_STYLE__GAP, oldGap, this.gap));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX, oldLabelFlex, this.labelFlex));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX, oldValueFlex, this.valueFlex));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getColor() {
        if (this.color != null && this.color.eIsProxy()) {
            InternalEObject oldColor = (InternalEObject) this.color;
            this.color = (UserColor) this.eResolveProxy(oldColor);
            if (this.color != oldColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR, oldColor, this.color));
            }
        }
        return this.color;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetColor() {
        return this.color;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setColor(UserColor newColor) {
        UserColor oldColor = this.color;
        this.color = newColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR, oldColor, this.color));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION:
                return this.getFlexDirection();
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__GAP:
                return this.getGap();
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX:
                return this.getLabelFlex();
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX:
                return this.getValueFlex();
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                if (resolve)
                    return this.getColor();
                return this.basicGetColor();
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
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION:
                this.setFlexDirection((String) newValue);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__GAP:
                this.setGap((String) newValue);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX:
                this.setLabelFlex((String) newValue);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX:
                this.setValueFlex((String) newValue);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                this.setColor((UserColor) newValue);
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
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION:
                this.setFlexDirection(FLEX_DIRECTION_EDEFAULT);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__GAP:
                this.setGap(GAP_EDEFAULT);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX:
                this.setLabelFlex(LABEL_FLEX_EDEFAULT);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX:
                this.setValueFlex(VALUE_FLEX_EDEFAULT);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                this.setColor(null);
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
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION:
                return FLEX_DIRECTION_EDEFAULT == null ? this.flexDirection != null : !FLEX_DIRECTION_EDEFAULT.equals(this.flexDirection);
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__GAP:
                return GAP_EDEFAULT == null ? this.gap != null : !GAP_EDEFAULT.equals(this.gap);
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX:
                return LABEL_FLEX_EDEFAULT == null ? this.labelFlex != null : !LABEL_FLEX_EDEFAULT.equals(this.labelFlex);
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX:
                return VALUE_FLEX_EDEFAULT == null ? this.valueFlex != null : !VALUE_FLEX_EDEFAULT.equals(this.valueFlex);
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                return this.color != null;
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
                case FormPackage.CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION:
                    return FormPackage.WIDGET_FLEXBOX_LAYOUT__FLEX_DIRECTION;
                case FormPackage.CHECKBOX_DESCRIPTION_STYLE__GAP:
                    return FormPackage.WIDGET_FLEXBOX_LAYOUT__GAP;
                case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX:
                    return FormPackage.WIDGET_FLEXBOX_LAYOUT__LABEL_FLEX;
                case FormPackage.CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX:
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
                    return FormPackage.CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION;
                case FormPackage.WIDGET_FLEXBOX_LAYOUT__GAP:
                    return FormPackage.CHECKBOX_DESCRIPTION_STYLE__GAP;
                case FormPackage.WIDGET_FLEXBOX_LAYOUT__LABEL_FLEX:
                    return FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX;
                case FormPackage.WIDGET_FLEXBOX_LAYOUT__VALUE_FLEX:
                    return FormPackage.CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX;
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

        String result = super.toString() + " (flexDirection: "
                + this.flexDirection
                + ", gap: "
                + this.gap
                + ", labelFlex: "
                + this.labelFlex
                + ", valueFlex: "
                + this.valueFlex
                + ')';
        return result;
    }

} // CheckboxDescriptionStyleImpl
