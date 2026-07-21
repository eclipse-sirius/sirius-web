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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Fetch Tree Item Context Menu Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.FetchTreeItemContextMenuEntryImpl#getUrlExression <em>Url Exression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.FetchTreeItemContextMenuEntryImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.FetchTreeItemContextMenuEntryImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.FetchTreeItemContextMenuEntryImpl#getIconURLExpression <em>Icon URL Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FetchTreeItemContextMenuEntryImpl extends TreeItemContextMenuEntryImpl implements FetchTreeItemContextMenuEntry {
    /**
	 * The default value of the '{@link #getUrlExression() <em>Url Exression</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUrlExression()
	 * @generated
	 * @ordered
	 */
    protected static final String URL_EXRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getUrlExression() <em>Url Exression</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUrlExression()
	 * @generated
	 * @ordered
	 */
    protected String urlExression = URL_EXRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getKind() <em>Kind</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getKind()
     * @generated
     * @ordered
     */
    protected static final FetchTreeItemContextMenuEntryKind KIND_EDEFAULT = FetchTreeItemContextMenuEntryKind.DOWNLOAD;

    /**
     * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getKind()
     * @generated
     * @ordered
     */
    protected FetchTreeItemContextMenuEntryKind kind = KIND_EDEFAULT;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected FetchTreeItemContextMenuEntryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TreePackage.Literals.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getUrlExression() {
		return urlExression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setUrlExression(String newUrlExression) {
		String oldUrlExression = urlExression;
		urlExression = newUrlExression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION, oldUrlExression, urlExression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public FetchTreeItemContextMenuEntryKind getKind() {
		return kind;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setKind(FetchTreeItemContextMenuEntryKind newKind) {
		FetchTreeItemContextMenuEntryKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND, oldKind, kind));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION, oldIconURLExpression, iconURLExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
				return getUrlExression();
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
				return getKind();
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				return getLabelExpression();
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				return getIconURLExpression();
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
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
				setUrlExression((String)newValue);
				return;
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
				setKind((FetchTreeItemContextMenuEntryKind)newValue);
				return;
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				setIconURLExpression((String)newValue);
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
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
				setUrlExression(URL_EXRESSION_EDEFAULT);
				return;
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
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
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
				return URL_EXRESSION_EDEFAULT == null ? urlExression != null : !URL_EXRESSION_EDEFAULT.equals(urlExression);
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
				return kind != KIND_EDEFAULT;
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
				return ICON_URL_EXPRESSION_EDEFAULT == null ? iconURLExpression != null : !ICON_URL_EXPRESSION_EDEFAULT.equals(iconURLExpression);
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
		result.append(" (urlExression: ");
		result.append(urlExression);
		result.append(", kind: ");
		result.append(kind);
		result.append(", labelExpression: ");
		result.append(labelExpression);
		result.append(", iconURLExpression: ");
		result.append(iconURLExpression);
		result.append(')');
		return result.toString();
	}

} // FetchTreeItemContextMenuEntryImpl
