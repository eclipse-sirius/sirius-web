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
package org.eclipse.sirius.components.view.diagram.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.OutsideLabelStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Outside Label Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.OutsideLabelDescriptionImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.OutsideLabelDescriptionImpl#getStyle <em>Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.OutsideLabelDescriptionImpl#getConditionalStyles <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OutsideLabelDescriptionImpl extends LabelDescriptionImpl implements OutsideLabelDescription {

    /**
	 * The default value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
    protected static final OutsideLabelPosition POSITION_EDEFAULT = OutsideLabelPosition.BOTTOM_CENTER;

    /**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
    protected OutsideLabelPosition position = POSITION_EDEFAULT;

    /**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
    protected OutsideLabelStyle style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalOutsideLabelStyle> conditionalStyles;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected OutsideLabelDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.OUTSIDE_LABEL_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OutsideLabelPosition getPosition() {
		return position;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPosition(OutsideLabelPosition newPosition) {
		OutsideLabelPosition oldPosition = position;
		position = newPosition == null ? POSITION_EDEFAULT : newPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__POSITION, oldPosition, position));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OutsideLabelStyle getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(OutsideLabelStyle newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(OutsideLabelStyle newStyle, NotificationChain msgs) {
		OutsideLabelStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalOutsideLabelStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalOutsideLabelStyle>(ConditionalOutsideLabelStyle.class, this, DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES);
		}
		return conditionalStyles;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES:
				return ((InternalEList<?>)getConditionalStyles()).basicRemove(otherEnd, msgs);
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
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__POSITION:
				return getPosition();
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE:
				return getStyle();
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES:
				return getConditionalStyles();
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
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__POSITION:
				setPosition((OutsideLabelPosition)newValue);
				return;
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE:
				setStyle((OutsideLabelStyle)newValue);
				return;
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalOutsideLabelStyle>)newValue);
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
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__POSITION:
				setPosition(POSITION_EDEFAULT);
				return;
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE:
				setStyle((OutsideLabelStyle)null);
				return;
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
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
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__POSITION:
				return position != POSITION_EDEFAULT;
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__STYLE:
				return style != null;
			case DiagramPackage.OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
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
		result.append(" (position: ");
		result.append(position);
		result.append(')');
		return result.toString();
	}

} // OutsideLabelDescriptionImpl
