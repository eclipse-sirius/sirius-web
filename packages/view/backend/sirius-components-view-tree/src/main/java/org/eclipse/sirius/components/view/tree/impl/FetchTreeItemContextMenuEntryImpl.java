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
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.FetchTreeItemContextMenuEntryImpl#getUrlExression <em>Url
 * Exression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.FetchTreeItemContextMenuEntryImpl#getKind <em>Kind</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FetchTreeItemContextMenuEntryImpl extends TreeItemContextMenuEntryImpl implements FetchTreeItemContextMenuEntry {
    /**
     * The default value of the '{@link #getUrlExression() <em>Url Exression</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getUrlExression()
     * @generated
     * @ordered
     */
    protected static final String URL_EXRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUrlExression() <em>Url Exression</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FetchTreeItemContextMenuEntryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TreePackage.Literals.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getUrlExression() {
        return this.urlExression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUrlExression(String newUrlExression) {
        String oldUrlExression = this.urlExression;
        this.urlExression = newUrlExression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION, oldUrlExression, this.urlExression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FetchTreeItemContextMenuEntryKind getKind() {
        return this.kind;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setKind(FetchTreeItemContextMenuEntryKind newKind) {
        FetchTreeItemContextMenuEntryKind oldKind = this.kind;
        this.kind = newKind == null ? KIND_EDEFAULT : newKind;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND, oldKind, this.kind));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
                return this.getUrlExression();
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
                return this.getKind();
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
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
                this.setUrlExression((String) newValue);
                return;
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
                this.setKind((FetchTreeItemContextMenuEntryKind) newValue);
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
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
                this.setUrlExression(URL_EXRESSION_EDEFAULT);
                return;
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
                this.setKind(KIND_EDEFAULT);
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
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION:
                return URL_EXRESSION_EDEFAULT == null ? this.urlExression != null : !URL_EXRESSION_EDEFAULT.equals(this.urlExression);
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND:
                return this.kind != KIND_EDEFAULT;
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
        result.append(" (urlExression: ");
        result.append(this.urlExression);
        result.append(", kind: ");
        result.append(this.kind);
        result.append(')');
        return result.toString();
    }

} // FetchTreeItemContextMenuEntryImpl
