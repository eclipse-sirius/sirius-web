/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Tool</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl#getDialogDescription <em>Dialog Description</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl#getIconURLsExpression <em>Icon UR Ls Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl#getElementsToSelectExpression <em>Elements To Select Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl#isWithImpactAnalysis <em>With Impact Analysis</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeToolImpl extends ToolImpl implements NodeTool {

    /**
     * The cached value of the '{@link #getDialogDescription() <em>Dialog Description</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDialogDescription()
     */
    protected DialogDescription dialogDescription;

    /**
     * The default value of the '{@link #getIconURLsExpression() <em>Icon UR Ls Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIconURLsExpression()
     */
    protected static final String ICON_UR_LS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIconURLsExpression() <em>Icon UR Ls Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIconURLsExpression()
     */
    protected String iconURLsExpression = ICON_UR_LS_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getElementsToSelectExpression() <em>Elements To Select Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getElementsToSelectExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String ELEMENTS_TO_SELECT_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getElementsToSelectExpression() <em>Elements To Select Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getElementsToSelectExpression()
	 * @generated
	 * @ordered
	 */
    protected String elementsToSelectExpression = ELEMENTS_TO_SELECT_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #isWithImpactAnalysis() <em>With Impact Analysis</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isWithImpactAnalysis()
     */
    protected static final boolean WITH_IMPACT_ANALYSIS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isWithImpactAnalysis() <em>With Impact Analysis</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isWithImpactAnalysis()
     */
    protected boolean withImpactAnalysis = WITH_IMPACT_ANALYSIS_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected NodeToolImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.NODE_TOOL;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DialogDescription getDialogDescription() {
		return dialogDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDialogDescription(DialogDescription newDialogDescription) {
		if (newDialogDescription != dialogDescription)
		{
			NotificationChain msgs = null;
			if (dialogDescription != null)
				msgs = ((InternalEObject)dialogDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION, null, msgs);
			if (newDialogDescription != null)
				msgs = ((InternalEObject)newDialogDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION, null, msgs);
			msgs = basicSetDialogDescription(newDialogDescription, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION, newDialogDescription, newDialogDescription));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDialogDescription(DialogDescription newDialogDescription, NotificationChain msgs) {
		DialogDescription oldDialogDescription = dialogDescription;
		dialogDescription = newDialogDescription;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION, oldDialogDescription, newDialogDescription);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_TOOL__ICON_UR_LS_EXPRESSION, oldIconURLsExpression, iconURLsExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getElementsToSelectExpression() {
		return elementsToSelectExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setElementsToSelectExpression(String newElementsToSelectExpression) {
		String oldElementsToSelectExpression = elementsToSelectExpression;
		elementsToSelectExpression = newElementsToSelectExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_TOOL__ELEMENTS_TO_SELECT_EXPRESSION, oldElementsToSelectExpression, elementsToSelectExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isWithImpactAnalysis() {
		return withImpactAnalysis;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setWithImpactAnalysis(boolean newWithImpactAnalysis) {
		boolean oldWithImpactAnalysis = withImpactAnalysis;
		withImpactAnalysis = newWithImpactAnalysis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_TOOL__WITH_IMPACT_ANALYSIS, oldWithImpactAnalysis, withImpactAnalysis));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION:
				return basicSetDialogDescription(null, msgs);
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
			case DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION:
				return getDialogDescription();
			case DiagramPackage.NODE_TOOL__ICON_UR_LS_EXPRESSION:
				return getIconURLsExpression();
			case DiagramPackage.NODE_TOOL__ELEMENTS_TO_SELECT_EXPRESSION:
				return getElementsToSelectExpression();
			case DiagramPackage.NODE_TOOL__WITH_IMPACT_ANALYSIS:
				return isWithImpactAnalysis();
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
			case DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION:
				setDialogDescription((DialogDescription)newValue);
				return;
			case DiagramPackage.NODE_TOOL__ICON_UR_LS_EXPRESSION:
				setIconURLsExpression((String)newValue);
				return;
			case DiagramPackage.NODE_TOOL__ELEMENTS_TO_SELECT_EXPRESSION:
				setElementsToSelectExpression((String)newValue);
				return;
			case DiagramPackage.NODE_TOOL__WITH_IMPACT_ANALYSIS:
				setWithImpactAnalysis((Boolean)newValue);
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
			case DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION:
				setDialogDescription((DialogDescription)null);
				return;
			case DiagramPackage.NODE_TOOL__ICON_UR_LS_EXPRESSION:
				setIconURLsExpression(ICON_UR_LS_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.NODE_TOOL__ELEMENTS_TO_SELECT_EXPRESSION:
				setElementsToSelectExpression(ELEMENTS_TO_SELECT_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.NODE_TOOL__WITH_IMPACT_ANALYSIS:
				setWithImpactAnalysis(WITH_IMPACT_ANALYSIS_EDEFAULT);
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
			case DiagramPackage.NODE_TOOL__DIALOG_DESCRIPTION:
				return dialogDescription != null;
			case DiagramPackage.NODE_TOOL__ICON_UR_LS_EXPRESSION:
				return ICON_UR_LS_EXPRESSION_EDEFAULT == null ? iconURLsExpression != null : !ICON_UR_LS_EXPRESSION_EDEFAULT.equals(iconURLsExpression);
			case DiagramPackage.NODE_TOOL__ELEMENTS_TO_SELECT_EXPRESSION:
				return ELEMENTS_TO_SELECT_EXPRESSION_EDEFAULT == null ? elementsToSelectExpression != null : !ELEMENTS_TO_SELECT_EXPRESSION_EDEFAULT.equals(elementsToSelectExpression);
			case DiagramPackage.NODE_TOOL__WITH_IMPACT_ANALYSIS:
				return withImpactAnalysis != WITH_IMPACT_ANALYSIS_EDEFAULT;
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
		result.append(" (iconURLsExpression: ");
		result.append(iconURLsExpression);
		result.append(", elementsToSelectExpression: ");
		result.append(elementsToSelectExpression);
		result.append(", withImpactAnalysis: ");
		result.append(withImpactAnalysis);
		result.append(')');
		return result.toString();
	}

} // NodeToolImpl
