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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
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
     * The default value of the '{@link #getPaginationPredicateExpression() <em>Pagination Predicate Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPaginationPredicateExpression()
     */
    protected static final String PAGINATION_PREDICATE_EXPRESSION_EDEFAULT = "";
    /**
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPreconditionExpression()
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = "";
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
     * The cached value of the '{@link #getPaginationPredicateExpression() <em>Pagination Predicate Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPaginationPredicateExpression()
     */
    protected String paginationPredicateExpression = PAGINATION_PREDICATE_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPreconditionExpression()
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;
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
    public String getPaginationPredicateExpression() {
        return this.paginationPredicateExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPaginationPredicateExpression(String newPaginationPredicateExpression) {
        String oldPaginationPredicateExpression = this.paginationPredicateExpression;
        this.paginationPredicateExpression = newPaginationPredicateExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__PAGINATION_PREDICATE_EXPRESSION, oldPaginationPredicateExpression, this.paginationPredicateExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
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
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TablePackage.ROW_DESCRIPTION__NAME:
                return this.getName();
            case TablePackage.ROW_DESCRIPTION__PAGINATION_PREDICATE_EXPRESSION:
                return this.getPaginationPredicateExpression();
            case TablePackage.ROW_DESCRIPTION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
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
            case TablePackage.ROW_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__PAGINATION_PREDICATE_EXPRESSION:
                this.setPaginationPredicateExpression((String) newValue);
                return;
            case TablePackage.ROW_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
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
            case TablePackage.ROW_DESCRIPTION__PAGINATION_PREDICATE_EXPRESSION:
                this.setPaginationPredicateExpression(PAGINATION_PREDICATE_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
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
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case TablePackage.ROW_DESCRIPTION__PAGINATION_PREDICATE_EXPRESSION:
                return PAGINATION_PREDICATE_EXPRESSION_EDEFAULT == null ? this.paginationPredicateExpression != null
                        : !PAGINATION_PREDICATE_EXPRESSION_EDEFAULT.equals(this.paginationPredicateExpression);
            case TablePackage.ROW_DESCRIPTION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                return HEADER_LABEL_EXPRESSION_EDEFAULT == null ? this.headerLabelExpression != null : !HEADER_LABEL_EXPRESSION_EDEFAULT.equals(this.headerLabelExpression);
            case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
                return HEADER_ICON_EXPRESSION_EDEFAULT == null ? this.headerIconExpression != null : !HEADER_ICON_EXPRESSION_EDEFAULT.equals(this.headerIconExpression);
            case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                return HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT == null ? this.headerIndexLabelExpression != null : !HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT.equals(this.headerIndexLabelExpression);
            case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
                return INITIAL_HEIGHT_EXPRESSION_EDEFAULT == null ? this.initialHeightExpression != null : !INITIAL_HEIGHT_EXPRESSION_EDEFAULT.equals(this.initialHeightExpression);
            case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                return IS_RESIZABLE_EXPRESSION_EDEFAULT == null ? this.isResizableExpression != null : !IS_RESIZABLE_EXPRESSION_EDEFAULT.equals(this.isResizableExpression);
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
        result.append(", paginationPredicateExpression: ");
        result.append(this.paginationPredicateExpression);
        result.append(", preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(", headerLabelExpression: ");
        result.append(this.headerLabelExpression);
        result.append(", headerIconExpression: ");
        result.append(this.headerIconExpression);
        result.append(", headerIndexLabelExpression: ");
        result.append(this.headerIndexLabelExpression);
        result.append(", initialHeightExpression: ");
        result.append(this.initialHeightExpression);
        result.append(", isResizableExpression: ");
        result.append(this.isResizableExpression);
        result.append(')');
        return result.toString();
    }

} // RowDescriptionImpl
