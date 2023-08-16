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
import org.eclipse.sirius.components.view.form.AlignItems;
import org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle;
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.JustifyContent;

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
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getFlexboxJustifyContent
 * <em>Flexbox Justify Content</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getFlexboxAlignItems
 * <em>Flexbox Align Items</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getMargin
 * <em>Margin</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getPadding
 * <em>Padding</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getGap <em>Gap</em>}</li>
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
     * The default value of the '{@link #getFlexboxJustifyContent() <em>Flexbox Justify Content</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFlexboxJustifyContent()
     * @generated
     * @ordered
     */
    protected static final JustifyContent FLEXBOX_JUSTIFY_CONTENT_EDEFAULT = JustifyContent.STRETCH;

    /**
     * The cached value of the '{@link #getFlexboxJustifyContent() <em>Flexbox Justify Content</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFlexboxJustifyContent()
     * @generated
     * @ordered
     */
    protected JustifyContent flexboxJustifyContent = FLEXBOX_JUSTIFY_CONTENT_EDEFAULT;

    /**
     * The default value of the '{@link #getFlexboxAlignItems() <em>Flexbox Align Items</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFlexboxAlignItems()
     * @generated
     * @ordered
     */
    protected static final AlignItems FLEXBOX_ALIGN_ITEMS_EDEFAULT = AlignItems.STRETCH;

    /**
     * The cached value of the '{@link #getFlexboxAlignItems() <em>Flexbox Align Items</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFlexboxAlignItems()
     * @generated
     * @ordered
     */
    protected AlignItems flexboxAlignItems = FLEXBOX_ALIGN_ITEMS_EDEFAULT;

    /**
     * The default value of the '{@link #getMargin() <em>Margin</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getMargin()
     * @generated
     * @ordered
     */
    protected static final String MARGIN_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMargin() <em>Margin</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getMargin()
     * @generated
     * @ordered
     */
    protected String margin = MARGIN_EDEFAULT;

    /**
     * The default value of the '{@link #getPadding() <em>Padding</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getPadding()
     * @generated
     * @ordered
     */
    protected static final String PADDING_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPadding() <em>Padding</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getPadding()
     * @generated
     * @ordered
     */
    protected String padding = PADDING_EDEFAULT;

    /**
     * The default value of the '{@link #getGap() <em>Gap</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getGap()
     * @generated
     * @ordered
     */
    protected static final String GAP_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGap() <em>Gap</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGap()
     * @generated
     * @ordered
     */
    protected String gap = GAP_EDEFAULT;

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
            this.conditionalBorderStyles = new EObjectContainmentEList<>(ConditionalContainerBorderStyle.class, this,
                    FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES);
        }
        return this.conditionalBorderStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public JustifyContent getFlexboxJustifyContent() {
        return this.flexboxJustifyContent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFlexboxJustifyContent(JustifyContent newFlexboxJustifyContent) {
        JustifyContent oldFlexboxJustifyContent = this.flexboxJustifyContent;
        this.flexboxJustifyContent = newFlexboxJustifyContent == null ? FLEXBOX_JUSTIFY_CONTENT_EDEFAULT : newFlexboxJustifyContent;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_JUSTIFY_CONTENT, oldFlexboxJustifyContent, this.flexboxJustifyContent));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public AlignItems getFlexboxAlignItems() {
        return this.flexboxAlignItems;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFlexboxAlignItems(AlignItems newFlexboxAlignItems) {
        AlignItems oldFlexboxAlignItems = this.flexboxAlignItems;
        this.flexboxAlignItems = newFlexboxAlignItems == null ? FLEXBOX_ALIGN_ITEMS_EDEFAULT : newFlexboxAlignItems;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_ALIGN_ITEMS, oldFlexboxAlignItems, this.flexboxAlignItems));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getMargin() {
        return this.margin;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMargin(String newMargin) {
        String oldMargin = this.margin;
        this.margin = newMargin;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__MARGIN, oldMargin, this.margin));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getPadding() {
        return this.padding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPadding(String newPadding) {
        String oldPadding = this.padding;
        this.padding = newPadding;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__PADDING, oldPadding, this.padding));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__GAP, oldGap, this.gap));
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_JUSTIFY_CONTENT:
                return this.getFlexboxJustifyContent();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_ALIGN_ITEMS:
                return this.getFlexboxAlignItems();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__MARGIN:
                return this.getMargin();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__PADDING:
                return this.getPadding();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__GAP:
                return this.getGap();
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_JUSTIFY_CONTENT:
                this.setFlexboxJustifyContent((JustifyContent) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_ALIGN_ITEMS:
                this.setFlexboxAlignItems((AlignItems) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__MARGIN:
                this.setMargin((String) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__PADDING:
                this.setPadding((String) newValue);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__GAP:
                this.setGap((String) newValue);
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_JUSTIFY_CONTENT:
                this.setFlexboxJustifyContent(FLEXBOX_JUSTIFY_CONTENT_EDEFAULT);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_ALIGN_ITEMS:
                this.setFlexboxAlignItems(FLEXBOX_ALIGN_ITEMS_EDEFAULT);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__MARGIN:
                this.setMargin(MARGIN_EDEFAULT);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__PADDING:
                this.setPadding(PADDING_EDEFAULT);
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__GAP:
                this.setGap(GAP_EDEFAULT);
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
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_JUSTIFY_CONTENT:
                return this.flexboxJustifyContent != FLEXBOX_JUSTIFY_CONTENT_EDEFAULT;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_ALIGN_ITEMS:
                return this.flexboxAlignItems != FLEXBOX_ALIGN_ITEMS_EDEFAULT;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__MARGIN:
                return MARGIN_EDEFAULT == null ? this.margin != null : !MARGIN_EDEFAULT.equals(this.margin);
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__PADDING:
                return PADDING_EDEFAULT == null ? this.padding != null : !PADDING_EDEFAULT.equals(this.padding);
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__GAP:
                return GAP_EDEFAULT == null ? this.gap != null : !GAP_EDEFAULT.equals(this.gap);
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
        result.append(", flexboxJustifyContent: ");
        result.append(this.flexboxJustifyContent);
        result.append(", flexboxAlignItems: ");
        result.append(this.flexboxAlignItems);
        result.append(", margin: ");
        result.append(this.margin);
        result.append(", padding: ");
        result.append(this.padding);
        result.append(", gap: ");
        result.append(this.gap);
        result.append(')');
        return result.toString();
    }

} // FlexboxContainerDescriptionImpl
