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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>If Tree Item Label Element
 * Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.IfTreeItemLabelElementDescriptionImpl#getPredicateExpression <em>Predicate Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.IfTreeItemLabelElementDescriptionImpl#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IfTreeItemLabelElementDescriptionImpl extends TreeItemLabelElementDescriptionImpl implements IfTreeItemLabelElementDescription {

    /**
     * The default value of the '{@link #getPredicateExpression() <em>Predicate Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPredicateExpression()
     * @generated
     * @ordered
     */
    protected static final String PREDICATE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPredicateExpression() <em>Predicate Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPredicateExpression()
     * @generated
     * @ordered
     */
    protected String predicateExpression = PREDICATE_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildren()
     * @generated
     * @ordered
     */
    protected EList<TreeItemLabelElementDescription> children;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected IfTreeItemLabelElementDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TreePackage.Literals.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getPredicateExpression() {
		return predicateExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPredicateExpression(String newPredicateExpression) {
		String oldPredicateExpression = predicateExpression;
		predicateExpression = newPredicateExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__PREDICATE_EXPRESSION, oldPredicateExpression, predicateExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<TreeItemLabelElementDescription> getChildren() {
		if (children == null)
		{
			children = new EObjectContainmentEList<TreeItemLabelElementDescription>(TreeItemLabelElementDescription.class, this, TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN);
		}
		return children;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
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
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__PREDICATE_EXPRESSION:
				return getPredicateExpression();
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
				return getChildren();
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
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__PREDICATE_EXPRESSION:
				setPredicateExpression((String)newValue);
				return;
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends TreeItemLabelElementDescription>)newValue);
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
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__PREDICATE_EXPRESSION:
				setPredicateExpression(PREDICATE_EXPRESSION_EDEFAULT);
				return;
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
				getChildren().clear();
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
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__PREDICATE_EXPRESSION:
				return PREDICATE_EXPRESSION_EDEFAULT == null ? predicateExpression != null : !PREDICATE_EXPRESSION_EDEFAULT.equals(predicateExpression);
			case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
				return children != null && !children.isEmpty();
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
		result.append(" (predicateExpression: ");
		result.append(predicateExpression);
		result.append(')');
		return result.toString();
	}

} // IfTreeItemLabelElementDescriptionImpl
