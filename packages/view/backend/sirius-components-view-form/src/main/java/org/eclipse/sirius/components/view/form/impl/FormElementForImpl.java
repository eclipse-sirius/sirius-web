/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.form.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Element For</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FormElementForImpl#getIterator <em>Iterator</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FormElementForImpl#getIterableExpression <em>Iterable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FormElementForImpl#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FormElementForImpl extends FormElementDescriptionImpl implements FormElementFor {
    /**
     * The default value of the '{@link #getIterator() <em>Iterator</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getIterator()
     * @generated
     * @ordered
     */
    protected static final String ITERATOR_EDEFAULT = "it";

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
    protected EList<FormElementDescription> children;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FormElementForImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.FORM_ELEMENT_FOR;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FORM_ELEMENT_FOR__ITERATOR, oldIterator, this.iterator));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.FORM_ELEMENT_FOR__ITERABLE_EXPRESSION, oldIterableExpression, this.iterableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<FormElementDescription> getChildren() {
        if (this.children == null) {
            this.children = new EObjectContainmentEList<>(FormElementDescription.class, this, FormPackage.FORM_ELEMENT_FOR__CHILDREN);
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
            case FormPackage.FORM_ELEMENT_FOR__CHILDREN:
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
            case FormPackage.FORM_ELEMENT_FOR__ITERATOR:
                return this.getIterator();
            case FormPackage.FORM_ELEMENT_FOR__ITERABLE_EXPRESSION:
                return this.getIterableExpression();
            case FormPackage.FORM_ELEMENT_FOR__CHILDREN:
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
            case FormPackage.FORM_ELEMENT_FOR__ITERATOR:
                this.setIterator((String) newValue);
                return;
            case FormPackage.FORM_ELEMENT_FOR__ITERABLE_EXPRESSION:
                this.setIterableExpression((String) newValue);
                return;
            case FormPackage.FORM_ELEMENT_FOR__CHILDREN:
                this.getChildren().clear();
                this.getChildren().addAll((Collection<? extends FormElementDescription>) newValue);
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
            case FormPackage.FORM_ELEMENT_FOR__ITERATOR:
                this.setIterator(ITERATOR_EDEFAULT);
                return;
            case FormPackage.FORM_ELEMENT_FOR__ITERABLE_EXPRESSION:
                this.setIterableExpression(ITERABLE_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.FORM_ELEMENT_FOR__CHILDREN:
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
            case FormPackage.FORM_ELEMENT_FOR__ITERATOR:
                return ITERATOR_EDEFAULT == null ? this.iterator != null : !ITERATOR_EDEFAULT.equals(this.iterator);
            case FormPackage.FORM_ELEMENT_FOR__ITERABLE_EXPRESSION:
                return ITERABLE_EXPRESSION_EDEFAULT == null ? this.iterableExpression != null : !ITERABLE_EXPRESSION_EDEFAULT.equals(this.iterableExpression);
            case FormPackage.FORM_ELEMENT_FOR__CHILDREN:
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

} // FormElementForImpl
