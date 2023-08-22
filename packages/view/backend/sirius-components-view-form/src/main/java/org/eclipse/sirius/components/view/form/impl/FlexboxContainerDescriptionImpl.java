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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle;
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Flexbox Container Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getChildren
 * <em>Children</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getFlexDirection <em>Flex
 * Direction</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getIsEnabledExpression <em>Is
 * Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getBorderStyle <em>Border
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getConditionalBorderStyles
 * <em>Conditional Border Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FlexboxContainerDescriptionImpl extends WidgetDescriptionImpl implements FlexboxContainerDescription {
    /**
     * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildren()
     * @generated
     * @ordered
     */
    protected EList<FormElementDescription> children;

    /**
     * The default value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated
     * @ordered
     */
    protected static final FlexDirection FLEX_DIRECTION_EDEFAULT = FlexDirection.ROW;

    /**
     * The cached value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated
     * @ordered
     */
    protected FlexDirection flexDirection = FLEX_DIRECTION_EDEFAULT;

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
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getBorderStyle() <em>Border Style</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderStyle()
     */
    protected ContainerBorderStyle borderStyle;

    /**
     * The cached value of the '{@link #getConditionalBorderStyles() <em>Conditional Border Styles</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalBorderStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalContainerBorderStyle> conditionalBorderStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FlexboxContainerDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<FormElementDescription> getChildren() {
        if (this.children == null) {
            this.children = new EObjectContainmentEList<>(FormElementDescription.class, this, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN);
        }
        return this.children;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FlexDirection getFlexDirection() {
        return this.flexDirection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFlexDirection(FlexDirection newFlexDirection) {
        FlexDirection oldFlexDirection = this.flexDirection;
        this.flexDirection = newFlexDirection == null ? FLEX_DIRECTION_EDEFAULT : newFlexDirection;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION, oldFlexDirection, this.flexDirection));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ContainerBorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderStyle(ContainerBorderStyle newBorderStyle) {
        if (newBorderStyle != this.borderStyle) {
            NotificationChain msgs = null;
            if (this.borderStyle != null)
                msgs = ((InternalEObject) this.borderStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, null, msgs);
            if (newBorderStyle != null)
                msgs = ((InternalEObject) newBorderStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, null, msgs);
            msgs = this.basicSetBorderStyle(newBorderStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, newBorderStyle, newBorderStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBorderStyle(ContainerBorderStyle newBorderStyle, NotificationChain msgs) {
        ContainerBorderStyle oldBorderStyle = this.borderStyle;
        this.borderStyle = newBorderStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, oldBorderStyle, newBorderStyle);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalContainerBorderStyle> getConditionalBorderStyles() {
        if (this.conditionalBorderStyles == null) {
            this.conditionalBorderStyles = new EObjectContainmentEList<>(ConditionalContainerBorderStyle.class, this, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES);
        }
        return this.conditionalBorderStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                return ((InternalEList<?>) this.getChildren()).basicRemove(otherEnd, msgs);
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
                return this.basicSetBorderStyle(null, msgs);
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                return ((InternalEList<?>) this.getConditionalBorderStyles()).basicRemove(otherEnd, msgs);
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                return this.getChildren();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                return this.getFlexDirection();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
                return this.getBorderStyle();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                return this.getConditionalBorderStyles();
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                this.getChildren().clear();
                this.getChildren().addAll((Collection<? extends FormElementDescription>) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                this.setFlexDirection((FlexDirection) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
                this.setBorderStyle((ContainerBorderStyle) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                this.getConditionalBorderStyles().clear();
                this.getConditionalBorderStyles().addAll((Collection<? extends ConditionalContainerBorderStyle>) newValue);
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                this.getChildren().clear();
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                this.setFlexDirection(FLEX_DIRECTION_EDEFAULT);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
                this.setBorderStyle((ContainerBorderStyle) null);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                this.getConditionalBorderStyles().clear();
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
                return this.children != null && !this.children.isEmpty();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
                return this.flexDirection != FLEX_DIRECTION_EDEFAULT;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
                return this.borderStyle != null;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                return this.conditionalBorderStyles != null && !this.conditionalBorderStyles.isEmpty();
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
        result.append(" (flexDirection: ");
        result.append(this.flexDirection);
        result.append(", IsEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(')');
        return result.toString();
    }

} // FlexboxContainerDescriptionImpl
