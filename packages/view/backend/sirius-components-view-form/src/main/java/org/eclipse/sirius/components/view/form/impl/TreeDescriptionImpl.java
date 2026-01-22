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
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.TreeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Tree Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.TreeDescriptionImpl#getChildExpression <em>Child
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.TreeDescriptionImpl#getIsTreeItemSelectableExpression <em>Is
 * Tree Item Selectable Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TreeDescriptionImpl extends WidgetDescriptionImpl implements TreeDescription {
    /**
     * The default value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildrenExpression()
     * @generated
     * @ordered
     */
    protected static final String CHILDREN_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildrenExpression()
     * @generated
     * @ordered
     */
    protected String childrenExpression = CHILDREN_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getTreeItemLabelExpression() <em>Tree Item Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTreeItemLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String TREE_ITEM_LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getTreeItemLabelExpression() <em>Tree Item Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTreeItemLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String treeItemLabelExpression = TREE_ITEM_LABEL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getIsTreeItemSelectableExpression() <em>Is Tree Item Selectable Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsTreeItemSelectableExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getIsTreeItemSelectableExpression() <em>Is Tree Item Selectable Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsTreeItemSelectableExpression()
	 * @generated
	 * @ordered
	 */
    protected String isTreeItemSelectableExpression = IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getTreeItemBeginIconExpression() <em>Tree Item Begin Icon Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTreeItemBeginIconExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getTreeItemBeginIconExpression() <em>Tree Item Begin Icon Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTreeItemBeginIconExpression()
	 * @generated
	 * @ordered
	 */
    protected String treeItemBeginIconExpression = TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getTreeItemEndIconsExpression() <em>Tree Item End Icons Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTreeItemEndIconsExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getTreeItemEndIconsExpression() <em>Tree Item End Icons Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTreeItemEndIconsExpression()
	 * @generated
	 * @ordered
	 */
    protected String treeItemEndIconsExpression = TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsCheckableExpression() <em>Is Checkable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsCheckableExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_CHECKABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsCheckableExpression() <em>Is Checkable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsCheckableExpression()
     * @generated
     * @ordered
     */
    protected String isCheckableExpression = IS_CHECKABLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getCheckedValueExpression() <em>Checked Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCheckedValueExpression()
     * @generated
     * @ordered
     */
    protected static final String CHECKED_VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCheckedValueExpression() <em>Checked Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCheckedValueExpression()
     * @generated
     * @ordered
     */
    protected String checkedValueExpression = CHECKED_VALUE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

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
    protected TreeDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.TREE_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getChildrenExpression() {
		return childrenExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setChildrenExpression(String newChildrenExpression) {
		String oldChildrenExpression = childrenExpression;
		childrenExpression = newChildrenExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION, oldChildrenExpression, childrenExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getTreeItemLabelExpression() {
		return treeItemLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTreeItemLabelExpression(String newTreeItemLabelExpression) {
		String oldTreeItemLabelExpression = treeItemLabelExpression;
		treeItemLabelExpression = newTreeItemLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION, oldTreeItemLabelExpression, treeItemLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsTreeItemSelectableExpression() {
		return isTreeItemSelectableExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsTreeItemSelectableExpression(String newIsTreeItemSelectableExpression) {
		String oldIsTreeItemSelectableExpression = isTreeItemSelectableExpression;
		isTreeItemSelectableExpression = newIsTreeItemSelectableExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION, oldIsTreeItemSelectableExpression, isTreeItemSelectableExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getTreeItemBeginIconExpression() {
		return treeItemBeginIconExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTreeItemBeginIconExpression(String newTreeItemBeginIconExpression) {
		String oldTreeItemBeginIconExpression = treeItemBeginIconExpression;
		treeItemBeginIconExpression = newTreeItemBeginIconExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION, oldTreeItemBeginIconExpression, treeItemBeginIconExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getTreeItemEndIconsExpression() {
		return treeItemEndIconsExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTreeItemEndIconsExpression(String newTreeItemEndIconsExpression) {
		String oldTreeItemEndIconsExpression = treeItemEndIconsExpression;
		treeItemEndIconsExpression = newTreeItemEndIconsExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION, oldTreeItemEndIconsExpression, treeItemEndIconsExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsCheckableExpression() {
		return isCheckableExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsCheckableExpression(String newIsCheckableExpression) {
		String oldIsCheckableExpression = isCheckableExpression;
		isCheckableExpression = newIsCheckableExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION, oldIsCheckableExpression, isCheckableExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getCheckedValueExpression() {
		return checkedValueExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCheckedValueExpression(String newCheckedValueExpression) {
		String oldCheckedValueExpression = checkedValueExpression;
		checkedValueExpression = newCheckedValueExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION, oldCheckedValueExpression, checkedValueExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, FormPackage.TREE_DESCRIPTION__BODY);
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
			case FormPackage.TREE_DESCRIPTION__BODY:
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
			case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
				return getChildrenExpression();
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
				return getTreeItemLabelExpression();
			case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
				return getIsTreeItemSelectableExpression();
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
				return getTreeItemBeginIconExpression();
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
				return getTreeItemEndIconsExpression();
			case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
				return getIsCheckableExpression();
			case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
				return getCheckedValueExpression();
			case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return getIsEnabledExpression();
			case FormPackage.TREE_DESCRIPTION__BODY:
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
			case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
				setChildrenExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
				setTreeItemLabelExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
				setIsTreeItemSelectableExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
				setTreeItemBeginIconExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
				setTreeItemEndIconsExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
				setIsCheckableExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
				setCheckedValueExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression((String)newValue);
				return;
			case FormPackage.TREE_DESCRIPTION__BODY:
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
			case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
				setChildrenExpression(CHILDREN_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
				setTreeItemLabelExpression(TREE_ITEM_LABEL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
				setIsTreeItemSelectableExpression(IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
				setTreeItemBeginIconExpression(TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
				setTreeItemEndIconsExpression(TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
				setIsCheckableExpression(IS_CHECKABLE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
				setCheckedValueExpression(CHECKED_VALUE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.TREE_DESCRIPTION__BODY:
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
			case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
				return CHILDREN_EXPRESSION_EDEFAULT == null ? childrenExpression != null : !CHILDREN_EXPRESSION_EDEFAULT.equals(childrenExpression);
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
				return TREE_ITEM_LABEL_EXPRESSION_EDEFAULT == null ? treeItemLabelExpression != null : !TREE_ITEM_LABEL_EXPRESSION_EDEFAULT.equals(treeItemLabelExpression);
			case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
				return IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT == null ? isTreeItemSelectableExpression != null : !IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT.equals(isTreeItemSelectableExpression);
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
				return TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT == null ? treeItemBeginIconExpression != null : !TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT.equals(treeItemBeginIconExpression);
			case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
				return TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT == null ? treeItemEndIconsExpression != null : !TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT.equals(treeItemEndIconsExpression);
			case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
				return IS_CHECKABLE_EXPRESSION_EDEFAULT == null ? isCheckableExpression != null : !IS_CHECKABLE_EXPRESSION_EDEFAULT.equals(isCheckableExpression);
			case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
				return CHECKED_VALUE_EXPRESSION_EDEFAULT == null ? checkedValueExpression != null : !CHECKED_VALUE_EXPRESSION_EDEFAULT.equals(checkedValueExpression);
			case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return IS_ENABLED_EXPRESSION_EDEFAULT == null ? isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(isEnabledExpression);
			case FormPackage.TREE_DESCRIPTION__BODY:
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
		result.append(" (childrenExpression: ");
		result.append(childrenExpression);
		result.append(", treeItemLabelExpression: ");
		result.append(treeItemLabelExpression);
		result.append(", isTreeItemSelectableExpression: ");
		result.append(isTreeItemSelectableExpression);
		result.append(", treeItemBeginIconExpression: ");
		result.append(treeItemBeginIconExpression);
		result.append(", treeItemEndIconsExpression: ");
		result.append(treeItemEndIconsExpression);
		result.append(", isCheckableExpression: ");
		result.append(isCheckableExpression);
		result.append(", checkedValueExpression: ");
		result.append(checkedValueExpression);
		result.append(", IsEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} // TreeDescriptionImpl
