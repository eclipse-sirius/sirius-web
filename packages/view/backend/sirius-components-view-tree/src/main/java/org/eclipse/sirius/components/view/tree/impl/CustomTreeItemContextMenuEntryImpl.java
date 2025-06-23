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
package org.eclipse.sirius.components.view.tree.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Custom Tree Item Context Menu Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.CustomTreeItemContextMenuEntryImpl#getContributionId
 * <em>Contribution Id</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.CustomTreeItemContextMenuEntryImpl#isWithImpactAnalysis
 * <em>With Impact Analysis</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomTreeItemContextMenuEntryImpl extends TreeItemContextMenuEntryImpl implements CustomTreeItemContextMenuEntry {
    /**
     * The default value of the '{@link #getContributionId() <em>Contribution Id</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getContributionId()
     * @generated
     * @ordered
     */
    protected static final String CONTRIBUTION_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getContributionId() <em>Contribution Id</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getContributionId()
     * @generated
     * @ordered
     */
    protected String contributionId = CONTRIBUTION_ID_EDEFAULT;

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
     *
     * @generated
     */
    protected CustomTreeItemContextMenuEntryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TreePackage.Literals.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getContributionId() {
        return this.contributionId;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setContributionId(String newContributionId) {
        String oldContributionId = this.contributionId;
        this.contributionId = newContributionId;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__CONTRIBUTION_ID, oldContributionId, this.contributionId));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isWithImpactAnalysis() {
        return this.withImpactAnalysis;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWithImpactAnalysis(boolean newWithImpactAnalysis) {
        boolean oldWithImpactAnalysis = this.withImpactAnalysis;
        this.withImpactAnalysis = newWithImpactAnalysis;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS, oldWithImpactAnalysis, this.withImpactAnalysis));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__CONTRIBUTION_ID:
                return this.getContributionId();
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
                return this.isWithImpactAnalysis();
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
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__CONTRIBUTION_ID:
                this.setContributionId((String) newValue);
                return;
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
                this.setWithImpactAnalysis((Boolean) newValue);
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
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__CONTRIBUTION_ID:
                this.setContributionId(CONTRIBUTION_ID_EDEFAULT);
                return;
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
                this.setWithImpactAnalysis(WITH_IMPACT_ANALYSIS_EDEFAULT);
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
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__CONTRIBUTION_ID:
                return CONTRIBUTION_ID_EDEFAULT == null ? this.contributionId != null : !CONTRIBUTION_ID_EDEFAULT.equals(this.contributionId);
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
                return this.withImpactAnalysis != WITH_IMPACT_ANALYSIS_EDEFAULT;
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
        result.append(" (contributionId: ");
        result.append(this.contributionId);
        result.append(", withImpactAnalysis: ");
        result.append(this.withImpactAnalysis);
        result.append(')');
        return result.toString();
    }

} // CustomTreeItemContextMenuEntryImpl
