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
import org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>For Tree Item Label Element
 * Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.ForTreeItemLabelElementDescriptionImpl#getIterator
 * <em>Iterator</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.ForTreeItemLabelElementDescriptionImpl#getIterableExpression
 * <em>Iterable Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.ForTreeItemLabelElementDescriptionImpl#getChildren
 * <em>Children</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForTreeItemLabelElementDescriptionImpl extends TreeItemLabelElementDescriptionImpl implements ForTreeItemLabelElementDescription {
    /**
     * The default value of the '{@link #getIterator() <em>Iterator</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getIterator()
     * @generated
     * @ordered
     */
    protected static final String ITERATOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIterator() <em>Iterator</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getIterator()
     * @generated
     * @ordered
     */
    protected String iterator = ITERATOR_EDEFAULT;

    /**
     * The default value of the '{@link #getIterableExpression() <em>Iterable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIterableExpression()
     * @generated
     * @ordered
     */
    protected static final String ITERABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIterableExpression() <em>Iterable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIterableExpression()
     * @generated
     * @ordered
     */
    protected String iterableExpression = ITERABLE_EXPRESSION_EDEFAULT;

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
     *
     * @generated
     */
    protected ForTreeItemLabelElementDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TreePackage.Literals.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIterator() {
        return this.iterator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIterator(String newIterator) {
        String oldIterator = this.iterator;
        this.iterator = newIterator;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERATOR, oldIterator, this.iterator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIterableExpression() {
        return this.iterableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIterableExpression(String newIterableExpression) {
        String oldIterableExpression = this.iterableExpression;
        this.iterableExpression = newIterableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERABLE_EXPRESSION, oldIterableExpression, this.iterableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TreeItemLabelElementDescription> getChildren() {
        if (this.children == null) {
            this.children = new EObjectContainmentEList<>(TreeItemLabelElementDescription.class, this, TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN);
        }
        return this.children;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
                return ((InternalEList<?>) this.getChildren()).basicRemove(otherEnd, msgs);
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
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERATOR:
                return this.getIterator();
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERABLE_EXPRESSION:
                return this.getIterableExpression();
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
                return this.getChildren();
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
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERATOR:
                this.setIterator((String) newValue);
                return;
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERABLE_EXPRESSION:
                this.setIterableExpression((String) newValue);
                return;
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
                this.getChildren().clear();
                this.getChildren().addAll((Collection<? extends TreeItemLabelElementDescription>) newValue);
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
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERATOR:
                this.setIterator(ITERATOR_EDEFAULT);
                return;
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERABLE_EXPRESSION:
                this.setIterableExpression(ITERABLE_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
                this.getChildren().clear();
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
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERATOR:
                return ITERATOR_EDEFAULT == null ? this.iterator != null : !ITERATOR_EDEFAULT.equals(this.iterator);
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERABLE_EXPRESSION:
                return ITERABLE_EXPRESSION_EDEFAULT == null ? this.iterableExpression != null : !ITERABLE_EXPRESSION_EDEFAULT.equals(this.iterableExpression);
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN:
                return this.children != null && !this.children.isEmpty();
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
        result.append(" (iterator: ");
        result.append(this.iterator);
        result.append(", iterableExpression: ");
        result.append(this.iterableExpression);
        result.append(')');
        return result.toString();
    }

} // ForTreeItemLabelElementDescriptionImpl
