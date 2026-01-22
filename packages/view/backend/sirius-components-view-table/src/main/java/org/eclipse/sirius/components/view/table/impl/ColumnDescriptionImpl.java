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
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getName()
     */
    protected String name = NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
    protected static final String DOMAIN_TYPE_EDEFAULT = "";
    /**
	 * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
    protected String domainType = DOMAIN_TYPE_EDEFAULT;
	/**
	 * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 * @ordered
	 */
    protected String semanticCandidatesExpression = SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPreconditionExpression()
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;
	/**
	 * The default value of the '{@link #getHeaderIndexLabelExpression() <em>Header Index Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getHeaderIndexLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT = "";
    /**
	 * The cached value of the '{@link #getHeaderIndexLabelExpression() <em>Header Index Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getHeaderIndexLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String headerIndexLabelExpression = HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getHeaderLabelExpression() <em>Header Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderLabelExpression()
     */
    protected String headerLabelExpression = HEADER_LABEL_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getHeaderIconExpression() <em>Header Icon Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHeaderIconExpression()
     */
    protected String headerIconExpression = HEADER_ICON_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getInitialWidthExpression() <em>Initial Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialWidthExpression()
     */
    protected String initialWidthExpression = INITIAL_WIDTH_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getIsResizableExpression() <em>Is Resizable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsResizableExpression()
     */
    protected String isResizableExpression = IS_RESIZABLE_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getFilterWidgetExpression() <em>Filter Widget Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getFilterWidgetExpression()
     */
    protected String filterWidgetExpression = FILTER_WIDGET_EXPRESSION_EDEFAULT;
	/**
     * The default value of the '{@link #getIsSortableExpression() <em>Is Sortable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsSortableExpression()
     */
    protected static final String IS_SORTABLE_EXPRESSION_EDEFAULT = "";
    /**
     * The cached value of the '{@link #getIsSortableExpression() <em>Is Sortable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsSortableExpression()
     */
    protected String isSortableExpression = IS_SORTABLE_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ColumnDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TablePackage.Literals.COLUMN_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDomainType() {
		return domainType;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDomainType(String newDomainType) {
		String oldDomainType = domainType;
		domainType = newDomainType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE, oldDomainType, domainType));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSemanticCandidatesExpression() {
		return semanticCandidatesExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSemanticCandidatesExpression(String newSemanticCandidatesExpression) {
		String oldSemanticCandidatesExpression = semanticCandidatesExpression;
		semanticCandidatesExpression = newSemanticCandidatesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, semanticCandidatesExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getPreconditionExpression() {
		return preconditionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPreconditionExpression(String newPreconditionExpression) {
		String oldPreconditionExpression = preconditionExpression;
		preconditionExpression = newPreconditionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, preconditionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getHeaderIndexLabelExpression() {
		return headerIndexLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setHeaderIndexLabelExpression(String newHeaderIndexLabelExpression) {
		String oldHeaderIndexLabelExpression = headerIndexLabelExpression;
		headerIndexLabelExpression = newHeaderIndexLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION, oldHeaderIndexLabelExpression, headerIndexLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getHeaderLabelExpression() {
		return headerLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setHeaderLabelExpression(String newHeaderLabelExpression) {
		String oldHeaderLabelExpression = headerLabelExpression;
		headerLabelExpression = newHeaderLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION, oldHeaderLabelExpression, headerLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getHeaderIconExpression() {
		return headerIconExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setHeaderIconExpression(String newHeaderIconExpression) {
		String oldHeaderIconExpression = headerIconExpression;
		headerIconExpression = newHeaderIconExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION, oldHeaderIconExpression, headerIconExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getInitialWidthExpression() {
		return initialWidthExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setInitialWidthExpression(String newInitialWidthExpression) {
		String oldInitialWidthExpression = initialWidthExpression;
		initialWidthExpression = newInitialWidthExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION, oldInitialWidthExpression, initialWidthExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsResizableExpression() {
		return isResizableExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsResizableExpression(String newIsResizableExpression) {
		String oldIsResizableExpression = isResizableExpression;
		isResizableExpression = newIsResizableExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION, oldIsResizableExpression, isResizableExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getFilterWidgetExpression() {
		return filterWidgetExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setFilterWidgetExpression(String newFilterWidgetExpression) {
		String oldFilterWidgetExpression = filterWidgetExpression;
		filterWidgetExpression = newFilterWidgetExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION, oldFilterWidgetExpression, filterWidgetExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsSortableExpression() {
		return isSortableExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsSortableExpression(String newIsSortableExpression) {
		String oldIsSortableExpression = isSortableExpression;
		isSortableExpression = newIsSortableExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION, oldIsSortableExpression, isSortableExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TablePackage.COLUMN_DESCRIPTION__NAME:
				return getName();
			case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
				return getDomainType();
			case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return getSemanticCandidatesExpression();
			case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
				return getPreconditionExpression();
			case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				return getHeaderIndexLabelExpression();
			case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				return getHeaderLabelExpression();
			case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
				return getHeaderIconExpression();
			case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
				return getInitialWidthExpression();
			case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				return getIsResizableExpression();
			case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
				return getFilterWidgetExpression();
			case TablePackage.COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION:
				return getIsSortableExpression();
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
			case TablePackage.COLUMN_DESCRIPTION__NAME:
				setName((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
				setDomainType((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				setHeaderIndexLabelExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				setHeaderLabelExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
				setHeaderIconExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
				setInitialWidthExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				setIsResizableExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
				setFilterWidgetExpression((String)newValue);
				return;
			case TablePackage.COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION:
				setIsSortableExpression((String)newValue);
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
			case TablePackage.COLUMN_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
				setDomainType(DOMAIN_TYPE_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				setHeaderIndexLabelExpression(HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				setHeaderLabelExpression(HEADER_LABEL_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
				setHeaderIconExpression(HEADER_ICON_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
				setInitialWidthExpression(INITIAL_WIDTH_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				setIsResizableExpression(IS_RESIZABLE_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
				setFilterWidgetExpression(FILTER_WIDGET_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION:
				setIsSortableExpression(IS_SORTABLE_EXPRESSION_EDEFAULT);
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
			case TablePackage.COLUMN_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
				return DOMAIN_TYPE_EDEFAULT == null ? domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(domainType);
			case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(semanticCandidatesExpression);
			case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
				return PRECONDITION_EXPRESSION_EDEFAULT == null ? preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(preconditionExpression);
			case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				return HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT == null ? headerIndexLabelExpression != null : !HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT.equals(headerIndexLabelExpression);
			case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				return HEADER_LABEL_EXPRESSION_EDEFAULT == null ? headerLabelExpression != null : !HEADER_LABEL_EXPRESSION_EDEFAULT.equals(headerLabelExpression);
			case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
				return HEADER_ICON_EXPRESSION_EDEFAULT == null ? headerIconExpression != null : !HEADER_ICON_EXPRESSION_EDEFAULT.equals(headerIconExpression);
			case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
				return INITIAL_WIDTH_EXPRESSION_EDEFAULT == null ? initialWidthExpression != null : !INITIAL_WIDTH_EXPRESSION_EDEFAULT.equals(initialWidthExpression);
			case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				return IS_RESIZABLE_EXPRESSION_EDEFAULT == null ? isResizableExpression != null : !IS_RESIZABLE_EXPRESSION_EDEFAULT.equals(isResizableExpression);
			case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
				return FILTER_WIDGET_EXPRESSION_EDEFAULT == null ? filterWidgetExpression != null : !FILTER_WIDGET_EXPRESSION_EDEFAULT.equals(filterWidgetExpression);
			case TablePackage.COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION:
				return IS_SORTABLE_EXPRESSION_EDEFAULT == null ? isSortableExpression != null : !IS_SORTABLE_EXPRESSION_EDEFAULT.equals(isSortableExpression);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", domainType: ");
		result.append(domainType);
		result.append(", semanticCandidatesExpression: ");
		result.append(semanticCandidatesExpression);
		result.append(", preconditionExpression: ");
		result.append(preconditionExpression);
		result.append(", headerIndexLabelExpression: ");
		result.append(headerIndexLabelExpression);
		result.append(", headerLabelExpression: ");
		result.append(headerLabelExpression);
		result.append(", headerIconExpression: ");
		result.append(headerIconExpression);
		result.append(", initialWidthExpression: ");
		result.append(initialWidthExpression);
		result.append(", isResizableExpression: ");
		result.append(isResizableExpression);
		result.append(", filterWidgetExpression: ");
		result.append(filterWidgetExpression);
		result.append(", isSortableExpression: ");
		result.append(isSortableExpression);
		result.append(')');
		return result.toString();
	}

} // ColumnDescriptionImpl
