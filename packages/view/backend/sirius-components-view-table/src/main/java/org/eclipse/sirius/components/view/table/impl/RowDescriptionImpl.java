/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table.impl;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.table.RowContextMenuEntry;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Row Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl#getIconExpression <em>Icon
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RowDescriptionImpl extends MinimalEObjectImpl.Container implements RowDescription {

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getName()
     */
    protected static final String NAME_EDEFAULT = null;
    /**
     * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSemanticCandidatesExpression()
     */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = "";
    /**
     * The default value of the '{@link #getHeaderLabelExpression() <em>Header Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderLabelExpression()
     */
    protected static final String HEADER_LABEL_EXPRESSION_EDEFAULT = "";
    /**
     * The default value of the '{@link #getHeaderIconExpression() <em>Header Icon Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderIconExpression()
     */
    protected static final String HEADER_ICON_EXPRESSION_EDEFAULT = "";
    /**
     * The default value of the '{@link #getHeaderIndexLabelExpression() <em>Header Index Label Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderIndexLabelExpression()
     */
    protected static final String HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT = null;
    /**
     * The default value of the '{@link #getInitialHeightExpression() <em>Initial Height Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialHeightExpression()
     */
    protected static final String INITIAL_HEIGHT_EXPRESSION_EDEFAULT = "";
    /**
     * The default value of the '{@link #getIsResizableExpression() <em>Is Resizable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsResizableExpression()
     */
    protected static final String IS_RESIZABLE_EXPRESSION_EDEFAULT = "";
    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getName()
     */
    protected String name = NAME_EDEFAULT;
    /**
     * The cached value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSemanticCandidatesExpression()
     */
    protected String semanticCandidatesExpression = SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getHeaderLabelExpression() <em>Header Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderLabelExpression()
     */
    protected String headerLabelExpression = HEADER_LABEL_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getHeaderIconExpression() <em>Header Icon Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderIconExpression()
     */
    protected String headerIconExpression = HEADER_ICON_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getHeaderIndexLabelExpression() <em>Header Index Label Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderIndexLabelExpression()
     */
    protected String headerIndexLabelExpression = HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getInitialHeightExpression() <em>Initial Height Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialHeightExpression()
     */
    protected String initialHeightExpression = INITIAL_HEIGHT_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getIsResizableExpression() <em>Is Resizable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsResizableExpression()
     */
    protected String isResizableExpression = IS_RESIZABLE_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getContextMenuEntries() <em>Context Menu Entries</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getContextMenuEntries()
     */
    protected EList<RowContextMenuEntry> contextMenuEntries;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RowDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TablePackage.Literals.ROW_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSemanticCandidatesExpression() {
        return this.semanticCandidatesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSemanticCandidatesExpression(String newSemanticCandidatesExpression) {
        String oldSemanticCandidatesExpression = this.semanticCandidatesExpression;
        this.semanticCandidatesExpression = newSemanticCandidatesExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getHeaderLabelExpression() {
        return this.headerLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHeaderLabelExpression(String newHeaderLabelExpression) {
        String oldHeaderLabelExpression = this.headerLabelExpression;
        this.headerLabelExpression = newHeaderLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION, oldHeaderLabelExpression, this.headerLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getHeaderIconExpression() {
        return this.headerIconExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHeaderIconExpression(String newHeaderIconExpression) {
        String oldHeaderIconExpression = this.headerIconExpression;
        this.headerIconExpression = newHeaderIconExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION, oldHeaderIconExpression, this.headerIconExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getHeaderIndexLabelExpression() {
        return this.headerIndexLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHeaderIndexLabelExpression(String newHeaderIndexLabelExpression) {
        String oldHeaderIndexLabelExpression = this.headerIndexLabelExpression;
        this.headerIndexLabelExpression = newHeaderIndexLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION, oldHeaderIndexLabelExpression, this.headerIndexLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getInitialHeightExpression() {
        return this.initialHeightExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInitialHeightExpression(String newInitialHeightExpression) {
        String oldInitialHeightExpression = this.initialHeightExpression;
        this.initialHeightExpression = newInitialHeightExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION, oldInitialHeightExpression, this.initialHeightExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsResizableExpression() {
        return this.isResizableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsResizableExpression(String newIsResizableExpression) {
        String oldIsResizableExpression = this.isResizableExpression;
        this.isResizableExpression = newIsResizableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION, oldIsResizableExpression, this.isResizableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<RowContextMenuEntry> getContextMenuEntries() {
        if (this.contextMenuEntries == null) {
            this.contextMenuEntries = new EObjectContainmentEList<>(RowContextMenuEntry.class, this, TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES);
        }
        return this.contextMenuEntries;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                return ((InternalEList<?>) this.getContextMenuEntries()).basicRemove(otherEnd, msgs);
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
            case TablePackage.ROW_DESCRIPTION__NAME:
                return this.getName();
            case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                return this.getHeaderLabelExpression();
            case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
                return this.getHeaderIconExpression();
            case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                return this.getHeaderIndexLabelExpression();
            case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
                return this.getInitialHeightExpression();
            case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                return this.getIsResizableExpression();
            case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                return this.getContextMenuEntries();
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
            case TablePackage.ROW_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                this.setHeaderLabelExpression((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
                this.setHeaderIconExpression((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                this.setHeaderIndexLabelExpression((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
                this.setInitialHeightExpression((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                this.setIsResizableExpression((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                this.getContextMenuEntries().clear();
                this.getContextMenuEntries().addAll((Collection<? extends RowContextMenuEntry>) newValue);
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
            case TablePackage.ROW_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                this.setHeaderLabelExpression(HEADER_LABEL_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
                this.setHeaderIconExpression(HEADER_ICON_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                this.setHeaderIndexLabelExpression(HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
                this.setInitialHeightExpression(INITIAL_HEIGHT_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                this.setIsResizableExpression(IS_RESIZABLE_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                this.getContextMenuEntries().clear();
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
            case TablePackage.ROW_DESCRIPTION__NAME:
                return !Objects.equals(NAME_EDEFAULT, this.name);
            case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
            case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                return HEADER_LABEL_EXPRESSION_EDEFAULT == null ? this.headerLabelExpression != null : !HEADER_LABEL_EXPRESSION_EDEFAULT.equals(this.headerLabelExpression);
            case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
                return HEADER_ICON_EXPRESSION_EDEFAULT == null ? this.headerIconExpression != null : !HEADER_ICON_EXPRESSION_EDEFAULT.equals(this.headerIconExpression);
            case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                return !Objects.equals(HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT, this.headerIndexLabelExpression);
            case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
                return INITIAL_HEIGHT_EXPRESSION_EDEFAULT == null ? this.initialHeightExpression != null : !INITIAL_HEIGHT_EXPRESSION_EDEFAULT.equals(this.initialHeightExpression);
            case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                return IS_RESIZABLE_EXPRESSION_EDEFAULT == null ? this.isResizableExpression != null : !IS_RESIZABLE_EXPRESSION_EDEFAULT.equals(this.isResizableExpression);
            case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                return this.contextMenuEntries != null && !this.contextMenuEntries.isEmpty();
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

        String result = super.toString() + " (name: " +
                this.name +
                ", semanticCandidatesExpression: " +
                this.semanticCandidatesExpression +
                ", headerLabelExpression: " +
                this.headerLabelExpression +
                ", headerIconExpression: " +
                this.headerIconExpression +
                ", headerIndexLabelExpression: " +
                this.headerIndexLabelExpression +
                ", initialHeightExpression: " +
                this.initialHeightExpression +
                ", isResizableExpression: " +
                this.isResizableExpression +
                ')';
        return result;
    }

} // RowDescriptionImpl
