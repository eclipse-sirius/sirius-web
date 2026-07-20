/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.diagram.DecoratorDescription;
import org.eclipse.sirius.components.view.diagram.DecoratorPosition;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Decorator Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getPreconditionExpression <em>Precondition Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getIconURLExpression <em>Icon URL Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DecoratorDescriptionImpl extends MinimalEObjectImpl.Container implements DecoratorDescription {
    /**
	 * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected static final String ICON_URL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected String iconURLExpression = ICON_URL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
    protected static final DecoratorPosition POSITION_EDEFAULT = DecoratorPosition.NORTH;

    /**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
    protected DecoratorPosition position = POSITION_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected DecoratorDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.DECORATOR_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLabelExpression() {
		return labelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getPreconditionExpression() {
		return preconditionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPreconditionExpression(String newPreconditionExpression) {
		String oldPreconditionExpression = preconditionExpression;
		preconditionExpression = newPreconditionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, preconditionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIconURLExpression() {
		return iconURLExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIconURLExpression(String newIconURLExpression) {
		String oldIconURLExpression = iconURLExpression;
		iconURLExpression = newIconURLExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION, oldIconURLExpression, iconURLExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DecoratorPosition getPosition() {
		return position;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPosition(DecoratorPosition newPosition) {
		DecoratorPosition oldPosition = position;
		position = newPosition == null ? POSITION_EDEFAULT : newPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__POSITION, oldPosition, position));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
				return getLabelExpression();
			case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
				return getPreconditionExpression();
			case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
				return getIconURLExpression();
			case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
				return getPosition();
			case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression((String)newValue);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
				setIconURLExpression((String)newValue);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
				setPosition((DecoratorPosition)newValue);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
				setName((String)newValue);
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
			case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
				setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
				setPosition(POSITION_EDEFAULT);
				return;
			case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
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
			case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
				return PRECONDITION_EXPRESSION_EDEFAULT == null ? preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(preconditionExpression);
			case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
				return ICON_URL_EXPRESSION_EDEFAULT == null ? iconURLExpression != null : !ICON_URL_EXPRESSION_EDEFAULT.equals(iconURLExpression);
			case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
				return position != POSITION_EDEFAULT;
			case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (labelExpression: ");
		result.append(labelExpression);
		result.append(", preconditionExpression: ");
		result.append(preconditionExpression);
		result.append(", iconURLExpression: ");
		result.append(iconURLExpression);
		result.append(", position: ");
		result.append(position);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // DecoratorDescriptionImpl
