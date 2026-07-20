/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getFlexDirection <em>Flex Direction</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getIsEnabledExpression <em>Is Enabled Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getBorderStyle <em>Border Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl#getConditionalBorderStyles <em>Conditional Border Styles</em>}</li>
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
	 * The default value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFlexDirection()
	 * @generated
	 * @ordered
	 */
    protected static final FlexDirection FLEX_DIRECTION_EDEFAULT = FlexDirection.ROW;

    /**
	 * The cached value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
	 * The cached value of the '{@link #getConditionalBorderStyles() <em>Conditional Border Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalBorderStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalContainerBorderStyle> conditionalBorderStyles;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected FlexboxContainerDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<FormElementDescription> getChildren() {
		if (children == null)
		{
			children = new EObjectContainmentEList<FormElementDescription>(FormElementDescription.class, this, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN);
		}
		return children;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public FlexDirection getFlexDirection() {
		return flexDirection;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setFlexDirection(FlexDirection newFlexDirection) {
		FlexDirection oldFlexDirection = flexDirection;
		flexDirection = newFlexDirection == null ? FLEX_DIRECTION_EDEFAULT : newFlexDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION, oldFlexDirection, flexDirection));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsEnabledExpression() {
		return isEnabledExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsEnabledExpression(String newIsEnabledExpression) {
		String oldIsEnabledExpression = isEnabledExpression;
		isEnabledExpression = newIsEnabledExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ContainerBorderStyle getBorderStyle() {
		return borderStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderStyle(ContainerBorderStyle newBorderStyle) {
		if (newBorderStyle != borderStyle)
		{
			NotificationChain msgs = null;
			if (borderStyle != null)
				msgs = ((InternalEObject)borderStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, null, msgs);
			if (newBorderStyle != null)
				msgs = ((InternalEObject)newBorderStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, null, msgs);
			msgs = basicSetBorderStyle(newBorderStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, newBorderStyle, newBorderStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetBorderStyle(ContainerBorderStyle newBorderStyle, NotificationChain msgs) {
		ContainerBorderStyle oldBorderStyle = borderStyle;
		borderStyle = newBorderStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, oldBorderStyle, newBorderStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalContainerBorderStyle> getConditionalBorderStyles() {
		if (conditionalBorderStyles == null)
		{
			conditionalBorderStyles = new EObjectContainmentEList<ConditionalContainerBorderStyle>(ConditionalContainerBorderStyle.class, this, FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES);
		}
		return conditionalBorderStyles;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
				return basicSetBorderStyle(null, msgs);
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				return ((InternalEList<?>)getConditionalBorderStyles()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
				return getChildren();
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
				return getFlexDirection();
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return getIsEnabledExpression();
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
				return getBorderStyle();
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				return getConditionalBorderStyles();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends FormElementDescription>)newValue);
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
				setFlexDirection((FlexDirection)newValue);
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression((String)newValue);
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
				setBorderStyle((ContainerBorderStyle)newValue);
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				getConditionalBorderStyles().clear();
				getConditionalBorderStyles().addAll((Collection<? extends ConditionalContainerBorderStyle>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
				getChildren().clear();
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
				setFlexDirection(FLEX_DIRECTION_EDEFAULT);
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
				setBorderStyle((ContainerBorderStyle)null);
				return;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				getConditionalBorderStyles().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
				return children != null && !children.isEmpty();
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
				return flexDirection != FLEX_DIRECTION_EDEFAULT;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return IS_ENABLED_EXPRESSION_EDEFAULT == null ? isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(isEnabledExpression);
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
				return borderStyle != null;
			case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				return conditionalBorderStyles != null && !conditionalBorderStyles.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (flexDirection: ");
		result.append(flexDirection);
		result.append(", IsEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} // FlexboxContainerDescriptionImpl
