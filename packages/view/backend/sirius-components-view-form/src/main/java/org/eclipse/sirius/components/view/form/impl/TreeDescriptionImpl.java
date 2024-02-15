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
     * The default value of the '{@link #getTreeItemEndIconsExpression() <em>Tree Item End Icons Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemEndIconsExpression()
     * @generated
     * @ordered
     */
    protected static final String TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemEndIconsExpression() <em>Tree Item End Icons Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
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
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBody()
     * @generated
     * @ordered
     */
    protected EList<Operation> body;

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
    public String getChildrenExpression() {
        return this.childrenExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setChildrenExpression(String newChildrenExpression) {
        String oldChildrenExpression = this.childrenExpression;
        this.childrenExpression = newChildrenExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION, oldChildrenExpression, this.childrenExpression));
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
    public String getTreeItemEndIconsExpression() {
        return this.treeItemEndIconsExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTreeItemEndIconsExpression(String newTreeItemEndIconsExpression) {
        String oldTreeItemEndIconsExpression = this.treeItemEndIconsExpression;
        this.treeItemEndIconsExpression = newTreeItemEndIconsExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION, oldTreeItemEndIconsExpression, this.treeItemEndIconsExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsCheckableExpression() {
        return this.isCheckableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsCheckableExpression(String newIsCheckableExpression) {
        String oldIsCheckableExpression = this.isCheckableExpression;
        this.isCheckableExpression = newIsCheckableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION, oldIsCheckableExpression, this.isCheckableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCheckedValueExpression() {
        return this.checkedValueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCheckedValueExpression(String newCheckedValueExpression) {
        String oldCheckedValueExpression = this.checkedValueExpression;
        this.checkedValueExpression = newCheckedValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION, oldCheckedValueExpression, this.checkedValueExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, FormPackage.TREE_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case FormPackage.TREE_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
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
            case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                return this.getChildrenExpression();
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
                return this.getTreeItemLabelExpression();
            case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
                return this.getIsTreeItemSelectableExpression();
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
                return this.getTreeItemBeginIconExpression();
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
                return this.getTreeItemEndIconsExpression();
            case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
                return this.getIsCheckableExpression();
            case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
                return this.getCheckedValueExpression();
            case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case FormPackage.TREE_DESCRIPTION__BODY:
                return this.getBody();
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
            case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                this.setChildrenExpression((String) newValue);
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
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
                this.setTreeItemEndIconsExpression((String) newValue);
                return;
            case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
                this.setIsCheckableExpression((String) newValue);
                return;
            case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
                this.setCheckedValueExpression((String) newValue);
                return;
            case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case FormPackage.TREE_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
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
            case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                this.setChildrenExpression(CHILDREN_EXPRESSION_EDEFAULT);
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
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
                this.setTreeItemEndIconsExpression(TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
                this.setIsCheckableExpression(IS_CHECKABLE_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
                this.setCheckedValueExpression(CHECKED_VALUE_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.TREE_DESCRIPTION__BODY:
                this.getBody().clear();
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
            case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                return CHILDREN_EXPRESSION_EDEFAULT == null ? this.childrenExpression != null : !CHILDREN_EXPRESSION_EDEFAULT.equals(this.childrenExpression);
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
                return TREE_ITEM_LABEL_EXPRESSION_EDEFAULT == null ? this.treeItemLabelExpression != null : !TREE_ITEM_LABEL_EXPRESSION_EDEFAULT.equals(this.treeItemLabelExpression);
            case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
                return IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT == null ? this.isTreeItemSelectableExpression != null
                        : !IS_TREE_ITEM_SELECTABLE_EXPRESSION_EDEFAULT.equals(this.isTreeItemSelectableExpression);
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
                return TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT == null ? this.treeItemBeginIconExpression != null : !TREE_ITEM_BEGIN_ICON_EXPRESSION_EDEFAULT.equals(this.treeItemBeginIconExpression);
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
                return TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT == null ? this.treeItemEndIconsExpression != null : !TREE_ITEM_END_ICONS_EXPRESSION_EDEFAULT.equals(this.treeItemEndIconsExpression);
            case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
                return IS_CHECKABLE_EXPRESSION_EDEFAULT == null ? this.isCheckableExpression != null : !IS_CHECKABLE_EXPRESSION_EDEFAULT.equals(this.isCheckableExpression);
            case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
                return CHECKED_VALUE_EXPRESSION_EDEFAULT == null ? this.checkedValueExpression != null : !CHECKED_VALUE_EXPRESSION_EDEFAULT.equals(this.checkedValueExpression);
            case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case FormPackage.TREE_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
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
        result.append(" (childrenExpression: ");
        result.append(this.childrenExpression);
        result.append(", treeItemLabelExpression: ");
        result.append(this.treeItemLabelExpression);
        result.append(", isTreeItemSelectableExpression: ");
        result.append(this.isTreeItemSelectableExpression);
        result.append(", treeItemBeginIconExpression: ");
        result.append(this.treeItemBeginIconExpression);
        result.append(", treeItemEndIconsExpression: ");
        result.append(this.treeItemEndIconsExpression);
        result.append(", isCheckableExpression: ");
        result.append(this.isCheckableExpression);
        result.append(", checkedValueExpression: ");
        result.append(this.checkedValueExpression);
        result.append(", IsEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(')');
        return result.toString();
    }

} // TreeDescriptionImpl
