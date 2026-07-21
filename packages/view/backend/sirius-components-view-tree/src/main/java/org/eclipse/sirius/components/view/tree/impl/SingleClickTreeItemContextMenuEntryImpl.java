/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.view.tree.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Single Click Tree Item Context Menu
 * Entry</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.SingleClickTreeItemContextMenuEntryImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.SingleClickTreeItemContextMenuEntryImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.SingleClickTreeItemContextMenuEntryImpl#getIconURLExpression <em>Icon URL Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.SingleClickTreeItemContextMenuEntryImpl#isWithImpactAnalysis <em>With Impact Analysis</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SingleClickTreeItemContextMenuEntryImpl extends TreeItemContextMenuEntryImpl implements SingleClickTreeItemContextMenuEntry {

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
     * The default value of the '{@link #isWithImpactAnalysis() <em>With Impact Analysis</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isWithImpactAnalysis()
     * @generated
     * @ordered
     */
    protected static final boolean WITH_IMPACT_ANALYSIS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isWithImpactAnalysis() <em>With Impact Analysis</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isWithImpactAnalysis()
     * @generated
     * @ordered
     */
    protected boolean withImpactAnalysis = WITH_IMPACT_ANALYSIS_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected SingleClickTreeItemContextMenuEntryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY);
		}
		return body;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION, oldIconURLExpression, iconURLExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS, oldWithImpactAnalysis, withImpactAnalysis));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY:
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
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY:
				return getBody();
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				return getLabelExpression();
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				return getIconURLExpression();
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
				return isWithImpactAnalysis();
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
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends Operation>)newValue);
				return;
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				setIconURLExpression((String)newValue);
				return;
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
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
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY:
				getBody().clear();
				return;
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
				return;
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
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
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY:
				return body != null && !body.isEmpty();
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				return ICON_URL_EXPRESSION_EDEFAULT == null ? iconURLExpression != null : !ICON_URL_EXPRESSION_EDEFAULT.equals(iconURLExpression);
			case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
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
		result.append(" (labelExpression: ");
		result.append(labelExpression);
		result.append(", iconURLExpression: ");
		result.append(iconURLExpression);
		result.append(", withImpactAnalysis: ");
		result.append(withImpactAnalysis);
		result.append(')');
		return result.toString();
	}

} // SingleClickTreeItemContextMenuEntryImpl
