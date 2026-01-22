/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.diagram.Action;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getTooltipExpression <em>Tooltip Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getIconURLsExpression <em>Icon UR Ls Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getPreconditionExpression <em>Precondition Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActionImpl extends MinimalEObjectImpl.Container implements Action {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "Action";

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
     * The default value of the '{@link #getTooltipExpression() <em>Tooltip Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTooltipExpression()
     * @generated
     * @ordered
     */
    protected static final String TOOLTIP_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTooltipExpression() <em>Tooltip Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTooltipExpression()
     * @generated
     * @ordered
     */
    protected String tooltipExpression = TOOLTIP_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIconURLsExpression() <em>Icon UR Ls Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLsExpression()
     * @generated
     * @ordered
     */
    protected static final String ICON_UR_LS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIconURLsExpression() <em>Icon UR Ls Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLsExpression()
     * @generated
     * @ordered
     */
    protected String iconURLsExpression = ICON_UR_LS_EXPRESSION_EDEFAULT;

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
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
    protected EList<Operation> body;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ActionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.ACTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getTooltipExpression() {
		return tooltipExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTooltipExpression(String newTooltipExpression) {
		String oldTooltipExpression = tooltipExpression;
		tooltipExpression = newTooltipExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__TOOLTIP_EXPRESSION, oldTooltipExpression, tooltipExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIconURLsExpression() {
		return iconURLsExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIconURLsExpression(String newIconURLsExpression) {
		String oldIconURLsExpression = iconURLsExpression;
		iconURLsExpression = newIconURLsExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION, oldIconURLsExpression, iconURLsExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, preconditionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, DiagramPackage.ACTION__BODY);
		}
		return body;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.ACTION__BODY:
				return ((InternalEList<?>)getBody()).basicRemove(otherEnd, msgs);
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
			case DiagramPackage.ACTION__NAME:
				return getName();
			case DiagramPackage.ACTION__TOOLTIP_EXPRESSION:
				return getTooltipExpression();
			case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
				return getIconURLsExpression();
			case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
				return getPreconditionExpression();
			case DiagramPackage.ACTION__BODY:
				return getBody();
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
			case DiagramPackage.ACTION__NAME:
				setName((String)newValue);
				return;
			case DiagramPackage.ACTION__TOOLTIP_EXPRESSION:
				setTooltipExpression((String)newValue);
				return;
			case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
				setIconURLsExpression((String)newValue);
				return;
			case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression((String)newValue);
				return;
			case DiagramPackage.ACTION__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends Operation>)newValue);
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
			case DiagramPackage.ACTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DiagramPackage.ACTION__TOOLTIP_EXPRESSION:
				setTooltipExpression(TOOLTIP_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
				setIconURLsExpression(ICON_UR_LS_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.ACTION__BODY:
				getBody().clear();
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
			case DiagramPackage.ACTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DiagramPackage.ACTION__TOOLTIP_EXPRESSION:
				return TOOLTIP_EXPRESSION_EDEFAULT == null ? tooltipExpression != null : !TOOLTIP_EXPRESSION_EDEFAULT.equals(tooltipExpression);
			case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
				return ICON_UR_LS_EXPRESSION_EDEFAULT == null ? iconURLsExpression != null : !ICON_UR_LS_EXPRESSION_EDEFAULT.equals(iconURLsExpression);
			case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
				return PRECONDITION_EXPRESSION_EDEFAULT == null ? preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(preconditionExpression);
			case DiagramPackage.ACTION__BODY:
				return body != null && !body.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", tooltipExpression: ");
		result.append(tooltipExpression);
		result.append(", iconURLsExpression: ");
		result.append(iconURLsExpression);
		result.append(", preconditionExpression: ");
		result.append(preconditionExpression);
		result.append(')');
		return result.toString();
	}

} // ActionImpl
