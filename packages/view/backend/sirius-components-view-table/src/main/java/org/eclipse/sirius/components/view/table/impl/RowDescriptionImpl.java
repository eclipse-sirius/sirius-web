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
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getName()
     */
    protected String name = NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = "";
    /**
	 * The cached value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 * @ordered
	 */
    protected String semanticCandidatesExpression = SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT;
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
	 * The default value of the '{@link #getHeaderIndexLabelExpression() <em>Header Index Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getHeaderIndexLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getHeaderIndexLabelExpression() <em>Header Index Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getHeaderIndexLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String headerIndexLabelExpression = HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT;
	/**
	 * The default value of the '{@link #getInitialHeightExpression() <em>Initial Height Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInitialHeightExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String INITIAL_HEIGHT_EXPRESSION_EDEFAULT = "";
    /**
	 * The cached value of the '{@link #getInitialHeightExpression() <em>Initial Height Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInitialHeightExpression()
	 * @generated
	 * @ordered
	 */
    protected String initialHeightExpression = INITIAL_HEIGHT_EXPRESSION_EDEFAULT;
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
	 * The cached value of the '{@link #getContextMenuEntries() <em>Context Menu Entries</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getContextMenuEntries()
	 * @generated
	 * @ordered
	 */
    protected EList<RowContextMenuEntry> contextMenuEntries;
	/**
     * The default value of the '{@link #getDepthLevelExpression() <em>Depth Level Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDepthLevelExpression()
     */
    protected static final String DEPTH_LEVEL_EXPRESSION_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getDepthLevelExpression() <em>Depth Level Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDepthLevelExpression()
     */
    protected String depthLevelExpression = DEPTH_LEVEL_EXPRESSION_EDEFAULT;
	/**
     * The default value of the '{@link #getHasChildrenExpression() <em>Has Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHasChildrenExpression()
     */
    protected static final String HAS_CHILDREN_EXPRESSION_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getHasChildrenExpression() <em>Has Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHasChildrenExpression()
     */
    protected String hasChildrenExpression = HAS_CHILDREN_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected RowDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TablePackage.Literals.ROW_DESCRIPTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, semanticCandidatesExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION, oldHeaderLabelExpression, headerLabelExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION, oldHeaderIconExpression, headerIconExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION, oldHeaderIndexLabelExpression, headerIndexLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getInitialHeightExpression() {
		return initialHeightExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setInitialHeightExpression(String newInitialHeightExpression) {
		String oldInitialHeightExpression = initialHeightExpression;
		initialHeightExpression = newInitialHeightExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION, oldInitialHeightExpression, initialHeightExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION, oldIsResizableExpression, isResizableExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<RowContextMenuEntry> getContextMenuEntries() {
		if (contextMenuEntries == null)
		{
			contextMenuEntries = new EObjectContainmentEList<RowContextMenuEntry>(RowContextMenuEntry.class, this, TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES);
		}
		return contextMenuEntries;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDepthLevelExpression() {
		return depthLevelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDepthLevelExpression(String newDepthLevelExpression) {
		String oldDepthLevelExpression = depthLevelExpression;
		depthLevelExpression = newDepthLevelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION, oldDepthLevelExpression, depthLevelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getHasChildrenExpression() {
		return hasChildrenExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setHasChildrenExpression(String newHasChildrenExpression) {
		String oldHasChildrenExpression = hasChildrenExpression;
		hasChildrenExpression = newHasChildrenExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION, oldHasChildrenExpression, hasChildrenExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
				return ((InternalEList<?>)getContextMenuEntries()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TablePackage.ROW_DESCRIPTION__NAME:
				return getName();
			case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return getSemanticCandidatesExpression();
			case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				return getHeaderLabelExpression();
			case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
				return getHeaderIconExpression();
			case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				return getHeaderIndexLabelExpression();
			case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
				return getInitialHeightExpression();
			case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				return getIsResizableExpression();
			case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
				return getContextMenuEntries();
			case TablePackage.ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION:
				return getDepthLevelExpression();
			case TablePackage.ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
				return getHasChildrenExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case TablePackage.ROW_DESCRIPTION__NAME:
				setName((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				setHeaderLabelExpression((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
				setHeaderIconExpression((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				setHeaderIndexLabelExpression((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
				setInitialHeightExpression((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				setIsResizableExpression((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
				getContextMenuEntries().clear();
				getContextMenuEntries().addAll((Collection<? extends RowContextMenuEntry>)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION:
				setDepthLevelExpression((String)newValue);
				return;
			case TablePackage.ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
				setHasChildrenExpression((String)newValue);
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
			case TablePackage.ROW_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				setHeaderLabelExpression(HEADER_LABEL_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
				setHeaderIconExpression(HEADER_ICON_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				setHeaderIndexLabelExpression(HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
				setInitialHeightExpression(INITIAL_HEIGHT_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				setIsResizableExpression(IS_RESIZABLE_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
				getContextMenuEntries().clear();
				return;
			case TablePackage.ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION:
				setDepthLevelExpression(DEPTH_LEVEL_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
				setHasChildrenExpression(HAS_CHILDREN_EXPRESSION_EDEFAULT);
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
			case TablePackage.ROW_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(semanticCandidatesExpression);
			case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
				return HEADER_LABEL_EXPRESSION_EDEFAULT == null ? headerLabelExpression != null : !HEADER_LABEL_EXPRESSION_EDEFAULT.equals(headerLabelExpression);
			case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
				return HEADER_ICON_EXPRESSION_EDEFAULT == null ? headerIconExpression != null : !HEADER_ICON_EXPRESSION_EDEFAULT.equals(headerIconExpression);
			case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
				return HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT == null ? headerIndexLabelExpression != null : !HEADER_INDEX_LABEL_EXPRESSION_EDEFAULT.equals(headerIndexLabelExpression);
			case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
				return INITIAL_HEIGHT_EXPRESSION_EDEFAULT == null ? initialHeightExpression != null : !INITIAL_HEIGHT_EXPRESSION_EDEFAULT.equals(initialHeightExpression);
			case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
				return IS_RESIZABLE_EXPRESSION_EDEFAULT == null ? isResizableExpression != null : !IS_RESIZABLE_EXPRESSION_EDEFAULT.equals(isResizableExpression);
			case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
				return contextMenuEntries != null && !contextMenuEntries.isEmpty();
			case TablePackage.ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION:
				return DEPTH_LEVEL_EXPRESSION_EDEFAULT == null ? depthLevelExpression != null : !DEPTH_LEVEL_EXPRESSION_EDEFAULT.equals(depthLevelExpression);
			case TablePackage.ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
				return HAS_CHILDREN_EXPRESSION_EDEFAULT == null ? hasChildrenExpression != null : !HAS_CHILDREN_EXPRESSION_EDEFAULT.equals(hasChildrenExpression);
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
		result.append(", semanticCandidatesExpression: ");
		result.append(semanticCandidatesExpression);
		result.append(", headerLabelExpression: ");
		result.append(headerLabelExpression);
		result.append(", headerIconExpression: ");
		result.append(headerIconExpression);
		result.append(", headerIndexLabelExpression: ");
		result.append(headerIndexLabelExpression);
		result.append(", initialHeightExpression: ");
		result.append(initialHeightExpression);
		result.append(", isResizableExpression: ");
		result.append(isResizableExpression);
		result.append(", depthLevelExpression: ");
		result.append(depthLevelExpression);
		result.append(", hasChildrenExpression: ");
		result.append(hasChildrenExpression);
		result.append(')');
		return result.toString();
	}

} // RowDescriptionImpl
