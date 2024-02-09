/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
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
     * The default value of the '{@link #getChildExpression() <em>Child Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getChildExpression()
     * @generated
     * @ordered
     */
    protected static final String CHILD_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChildExpression() <em>Child Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getChildExpression()
     * @generated
     * @ordered
     */
    protected String childExpression = CHILD_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTreeItemLabelExpression() <em>Tree Item Label Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String TREE_ITEM_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemLabelExpression() <em>Tree Item Label Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemLabelExpression()
     * @generated
     * @ordered
     */
    protected String treeItemLabelExpression = TREE_ITEM_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsTreeItemSelectableExpression() <em>Is Tree Item Selectable
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsTreeItemSelectableExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsTreeItemSelectableExpression() <em>Is Tree Item Selectable
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsTreeItemSelectableExpression()
     * @generated
     * @ordered
     */
    protected String isTreeItemSelectableExpression = IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTreeItemBeginIconExpression() <em>Tree Item Begin Icon Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemBeginIconExpression()
     * @generated
     * @ordered
     */
    protected static final String TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemBeginIconExpression() <em>Tree Item Begin Icon Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemBeginIconExpression()
     * @generated
     * @ordered
     */
    protected String treeItemBeginIconExpression = TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.TREE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getChildExpression() {
        return this.childExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setChildExpression(String newChildExpression) {
        String oldChildExpression = this.childExpression;
        this.childExpression = newChildExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__CHILD_EXPRESSION, oldChildExpression, this.childExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTreeItemLabelExpression() {
        return this.treeItemLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTreeItemLabelExpression(String newTreeItemLabelExpression) {
        String oldTreeItemLabelExpression = this.treeItemLabelExpression;
        this.treeItemLabelExpression = newTreeItemLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION, oldTreeItemLabelExpression, this.treeItemLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsTreeItemSelectableExpression() {
        return this.isTreeItemSelectableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsTreeItemSelectableExpression(String newIsTreeItemSelectableExpression) {
        String oldIsTreeItemSelectableExpression = this.isTreeItemSelectableExpression;
        this.isTreeItemSelectableExpression = newIsTreeItemSelectableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION, oldIsTreeItemSelectableExpression,
                    this.isTreeItemSelectableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTreeItemBeginIconExpression() {
        return this.treeItemBeginIconExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTreeItemBeginIconExpression(String newTreeItemBeginIconExpression) {
        String oldTreeItemBeginIconExpression = this.treeItemBeginIconExpression;
        this.treeItemBeginIconExpression = newTreeItemBeginIconExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION, oldTreeItemBeginIconExpression, this.treeItemBeginIconExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.TREE_DESCRIPTION__CHILD_EXPRESSION:
                return this.getChildExpression();
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
                return this.getTreeItemLabelExpression();
            case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
                return this.getIsTreeItemSelectableExpression();
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
                return this.getTreeItemBeginIconExpression();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case FormPackage.TREE_DESCRIPTION__CHILD_EXPRESSION:
                this.setChildExpression((String) newValue);
                return;
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
                this.setTreeItemLabelExpression((String) newValue);
                return;
            case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
                this.setIsTreeItemSelectableExpression((String) newValue);
                return;
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
                this.setTreeItemBeginIconExpression((String) newValue);
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
            case FormPackage.TREE_DESCRIPTION__CHILD_EXPRESSION:
                this.setChildExpression(CHILD_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
                this.setTreeItemLabelExpression(TREE_ITEM_LABEL_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
                this.setIsTreeItemSelectableExpression(IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
                this.setTreeItemBeginIconExpression(TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT);
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
            case FormPackage.TREE_DESCRIPTION__CHILD_EXPRESSION:
                return CHILD_EXPRESSION_EDEFAULT == null ? this.childExpression != null : !CHILD_EXPRESSION_EDEFAULT.equals(this.childExpression);
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
                return TREE_ITEM_LABEL_EXPRESSION_EDEFAULT == null ? this.treeItemLabelExpression != null : !TREE_ITEM_LABEL_EXPRESSION_EDEFAULT.equals(this.treeItemLabelExpression);
            case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
                return IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT == null ? this.isTreeItemSelectableExpression != null
                        : !IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT.equals(this.isTreeItemSelectableExpression);
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
                return TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT == null ? this.treeItemBeginIconExpression != null : !TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT.equals(this.treeItemBeginIconExpression);
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
        result.append(" (childExpression: ");
        result.append(this.childExpression);
        result.append(", treeItemLabelExpression: ");
        result.append(this.treeItemLabelExpression);
        result.append(", isTreeItemSelectableExpression: ");
        result.append(this.isTreeItemSelectableExpression);
        result.append(", treeItemBeginIconExpression: ");
        result.append(this.treeItemBeginIconExpression);
        result.append(')');
        return result.toString();
    }

} // TreeDescriptionImpl
