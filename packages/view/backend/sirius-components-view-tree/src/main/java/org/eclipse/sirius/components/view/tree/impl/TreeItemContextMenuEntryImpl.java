/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Item Context Menu Entry</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemContextMenuEntryImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemContextMenuEntryImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemContextMenuEntryImpl#getIconURLExpression <em>Icon
 * URL Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemContextMenuEntryImpl#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TreeItemContextMenuEntryImpl extends MinimalEObjectImpl.Container implements TreeItemContextMenuEntry {

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
     * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeItemContextMenuEntryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TreePackage.Literals.TREE_ITEM_CONTEXT_MENU_ENTRY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelExpression() {
        return this.labelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelExpression(String newLabelExpression) {
        String oldLabelExpression = this.labelExpression;
        this.labelExpression = newLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIconURLExpression() {
        return this.iconURLExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIconURLExpression(String newIconURLExpression) {
        String oldIconURLExpression = this.iconURLExpression;
        this.iconURLExpression = newIconURLExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION, oldIconURLExpression, this.iconURLExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getPreconditionExpression() {
        return this.preconditionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPreconditionExpression(String newPreconditionExpression) {
        String oldPreconditionExpression = this.preconditionExpression;
        this.preconditionExpression = newPreconditionExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
                return this.getName();
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                return this.getIconURLExpression();
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
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
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
                this.setName((String) newValue);
                return;
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                this.setIconURLExpression((String) newValue);
                return;
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
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
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                this.setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
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
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                return ICON_URL_EXPRESSION_EDEFAULT == null ? this.iconURLExpression != null : !ICON_URL_EXPRESSION_EDEFAULT.equals(this.iconURLExpression);
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
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
        result.append(" (name: ");
        result.append(this.name);
        result.append(", labelExpression: ");
        result.append(this.labelExpression);
        result.append(", iconURLExpression: ");
        result.append(this.iconURLExpression);
        result.append(", preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(')');
        return result.toString();
    }

} // TreeItemContextMenuEntryImpl
