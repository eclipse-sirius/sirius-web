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
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.table.RowContextMenuEntry;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Row Context Menu Entry</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl#getIconURLExpression <em>Icon URL
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RowContextMenuEntryImpl extends MinimalEObjectImpl.Container implements RowContextMenuEntry {

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
     * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelExpression()
     */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;
    /**
     * The default value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIconURLExpression()
     */
    protected static final String ICON_URL_EXPRESSION_EDEFAULT = null;
    /**
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPreconditionExpression()
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = null;
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
     * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelExpression()
     */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIconURLExpression()
     */
    protected String iconURLExpression = ICON_URL_EXPRESSION_EDEFAULT;
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
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBody()
     */
    protected EList<Operation> body;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RowContextMenuEntryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TablePackage.Literals.ROW_CONTEXT_MENU_ENTRY;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_CONTEXT_MENU_ENTRY__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelExpression() {
        return this.labelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelExpression(String newLabelExpression) {
        String oldLabelExpression = this.labelExpression;
        this.labelExpression = newLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIconURLExpression() {
        return this.iconURLExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIconURLExpression(String newIconURLExpression) {
        String oldIconURLExpression = this.iconURLExpression;
        this.iconURLExpression = newIconURLExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION, oldIconURLExpression, this.iconURLExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, TablePackage.ROW_CONTEXT_MENU_ENTRY__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
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
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__NAME:
                return this.getName();
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                return this.getIconURLExpression();
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__BODY:
                return this.getBody();
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
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__NAME:
                this.setName((String) newValue);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                this.setIconURLExpression((String) newValue);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
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
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                this.setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__BODY:
                this.getBody().clear();
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
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__NAME:
                return !Objects.equals(NAME_EDEFAULT, this.name);
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
                return !Objects.equals(LABEL_EXPRESSION_EDEFAULT, this.labelExpression);
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
                return !Objects.equals(ICON_URL_EXPRESSION_EDEFAULT, this.iconURLExpression);
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
                return !Objects.equals(PRECONDITION_EXPRESSION_EDEFAULT, this.preconditionExpression);
            case TablePackage.ROW_CONTEXT_MENU_ENTRY__BODY:
                return this.body != null && !this.body.isEmpty();
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
                ", labelExpression: " +
                this.labelExpression +
                ", iconURLExpression: " +
                this.iconURLExpression +
                ", preconditionExpression: " +
                this.preconditionExpression +
                ')';
        return result;
    }

} // RowContextMenuEntryImpl
