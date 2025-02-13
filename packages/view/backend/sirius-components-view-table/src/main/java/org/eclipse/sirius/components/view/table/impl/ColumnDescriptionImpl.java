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

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Column Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl#getHeaderExpression <em>Header
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl#getIconExpression <em>Icon
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl#getInitialWidthExpression <em>Initial
 * Width Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl#getIsResizableExpression <em>Is
 * Resizable Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl#getFilterWidgetExpression <em>Filter
 * Widget Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ColumnDescriptionImpl extends MinimalEObjectImpl.Container implements ColumnDescription {

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
     * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDomainType()
     */
    protected static final String DOMAIN_TYPE_EDEFAULT = "";
    /**
     * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSemanticCandidatesExpression()
     */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = null;
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
     * The default value of the '{@link #getHeaderIndexLabelExpression() <em>Header Index Label Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderIndexLabelExpression()
     */
    protected static final String HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT = "";
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
     * The default value of the '{@link #getInitialWidthExpression() <em>Initial Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialWidthExpression()
     */
    protected static final String INITIAL_WIDTH_EXPRESSION_EDEFAULT = "";
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
     * The default value of the '{@link #getFilterWidgetExpression() <em>Filter Widget Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getFilterWidgetExpression()
     */
    protected static final String FILTER_WIDGET_EXPRESSION_EDEFAULT = "";
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
     * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDomainType()
     */
    protected String domainType = DOMAIN_TYPE_EDEFAULT;
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
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPreconditionExpression()
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getInitialWidthExpression() <em>Initial Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialWidthExpression()
     */
    protected String initialWidthExpression = INITIAL_WIDTH_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getFilterWidgetExpression() <em>Filter Widget Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getFilterWidgetExpression()
     */
    protected String filterWidgetExpression = FILTER_WIDGET_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ColumnDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TablePackage.Literals.COLUMN_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDomainType() {
        return this.domainType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDomainType(String newDomainType) {
        String oldDomainType = this.domainType;
        this.domainType = newDomainType;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE, oldDomainType, this.domainType));
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
                    new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION, oldHeaderIndexLabelExpression, this.headerIndexLabelExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION, oldHeaderLabelExpression, this.headerLabelExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION, oldHeaderIconExpression, this.headerIconExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getInitialWidthExpression() {
        return this.initialWidthExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInitialWidthExpression(String newInitialWidthExpression) {
        String oldInitialWidthExpression = this.initialWidthExpression;
        this.initialWidthExpression = newInitialWidthExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION, oldInitialWidthExpression, this.initialWidthExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION, oldIsResizableExpression, this.isResizableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getFilterWidgetExpression() {
        return this.filterWidgetExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFilterWidgetExpression(String newFilterWidgetExpression) {
        String oldFilterWidgetExpression = this.filterWidgetExpression;
        this.filterWidgetExpression = newFilterWidgetExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION, oldFilterWidgetExpression, this.filterWidgetExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TablePackage.COLUMN_DESCRIPTION__NAME:
                return this.getName();
            case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
                return this.getDomainType();
            case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                return this.getHeaderIndexLabelExpression();
            case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                return this.getHeaderLabelExpression();
            case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
                return this.getHeaderIconExpression();
            case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
                return this.getInitialWidthExpression();
            case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                return this.getIsResizableExpression();
            case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
                return this.getFilterWidgetExpression();
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
            case TablePackage.COLUMN_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                this.setHeaderIndexLabelExpression((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                this.setHeaderLabelExpression((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
                this.setHeaderIconExpression((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
                this.setInitialWidthExpression((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                this.setIsResizableExpression((String) newValue);
                return;
            case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
                this.setFilterWidgetExpression((String) newValue);
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
            case TablePackage.COLUMN_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType(DOMAIN_TYPE_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                this.setHeaderIndexLabelExpression(HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                this.setHeaderLabelExpression(HEADER_LABEL_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
                this.setHeaderIconExpression(HEADER_ICON_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
                this.setInitialWidthExpression(INITIAL_WIDTH_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                this.setIsResizableExpression(IS_RESIZABLE_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
                this.setFilterWidgetExpression(FILTER_WIDGET_EXPRESSION_EDEFAULT);
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
            case TablePackage.COLUMN_DESCRIPTION__NAME:
                return !Objects.equals(NAME_EDEFAULT, this.name);
            case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
                return DOMAIN_TYPE_EDEFAULT == null ? this.domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(this.domainType);
            case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return !Objects.equals(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT, this.semanticCandidatesExpression);
            case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
                return HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT == null ? this.headerIndexLabelExpression != null : !HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT.equals(this.headerIndexLabelExpression);
            case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
                return HEADER_LABEL_EXPRESSION_EDEFAULT == null ? this.headerLabelExpression != null : !HEADER_LABEL_EXPRESSION_EDEFAULT.equals(this.headerLabelExpression);
            case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
                return HEADER_ICON_EXPRESSION_EDEFAULT == null ? this.headerIconExpression != null : !HEADER_ICON_EXPRESSION_EDEFAULT.equals(this.headerIconExpression);
            case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
                return INITIAL_WIDTH_EXPRESSION_EDEFAULT == null ? this.initialWidthExpression != null : !INITIAL_WIDTH_EXPRESSION_EDEFAULT.equals(this.initialWidthExpression);
            case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                return IS_RESIZABLE_EXPRESSION_EDEFAULT == null ? this.isResizableExpression != null : !IS_RESIZABLE_EXPRESSION_EDEFAULT.equals(this.isResizableExpression);
            case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
                return FILTER_WIDGET_EXPRESSION_EDEFAULT == null ? this.filterWidgetExpression != null : !FILTER_WIDGET_EXPRESSION_EDEFAULT.equals(this.filterWidgetExpression);
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
                ", domainType: " +
                this.domainType +
                ", semanticCandidatesExpression: " +
                this.semanticCandidatesExpression +
                ", preconditionExpression: " +
                this.preconditionExpression +
                ", headerIndexLabelExpression: " +
                this.headerIndexLabelExpression +
                ", headerLabelExpression: " +
                this.headerLabelExpression +
                ", headerIconExpression: " +
                this.headerIconExpression +
                ", initialWidthExpression: " +
                this.initialWidthExpression +
                ", isResizableExpression: " +
                this.isResizableExpression +
                ", filterWidgetExpression: " +
                this.filterWidgetExpression +
                ')';
        return result;
    }

} // ColumnDescriptionImpl
