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

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.table.RowFilterDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Row Filter Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl#getInitialStateExpression
 * <em>Initial State Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RowFilterDescriptionImpl extends MinimalEObjectImpl.Container implements RowFilterDescription {

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getId()
     */
    protected static final String ID_EDEFAULT = null;
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
     * The default value of the '{@link #getInitialStateExpression() <em>Initial State Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialStateExpression()
     */
    protected static final String INITIAL_STATE_EXPRESSION_EDEFAULT = "";
    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getId()
     */
    protected String id = ID_EDEFAULT;
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
     * The cached value of the '{@link #getInitialStateExpression() <em>Initial State Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialStateExpression()
     */
    protected String initialStateExpression = INITIAL_STATE_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RowFilterDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TablePackage.Literals.ROW_FILTER_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setId(String newId) {
        String oldId = this.id;
        this.id = newId;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_FILTER_DESCRIPTION__ID, oldId, this.id));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getInitialStateExpression() {
        return this.initialStateExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInitialStateExpression(String newInitialStateExpression) {
        String oldInitialStateExpression = this.initialStateExpression;
        this.initialStateExpression = newInitialStateExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION, oldInitialStateExpression, this.initialStateExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TablePackage.ROW_FILTER_DESCRIPTION__ID:
                return this.getId();
            case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
                return this.getInitialStateExpression();
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
            case TablePackage.ROW_FILTER_DESCRIPTION__ID:
                this.setId((String) newValue);
                return;
            case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
                this.setInitialStateExpression((String) newValue);
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
            case TablePackage.ROW_FILTER_DESCRIPTION__ID:
                this.setId(ID_EDEFAULT);
                return;
            case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
                this.setInitialStateExpression(INITIAL_STATE_EXPRESSION_EDEFAULT);
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
            case TablePackage.ROW_FILTER_DESCRIPTION__ID:
                return !Objects.equals(ID_EDEFAULT, this.id);
            case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
                return !Objects.equals(LABEL_EXPRESSION_EDEFAULT, this.labelExpression);
            case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
                return INITIAL_STATE_EXPRESSION_EDEFAULT == null ? this.initialStateExpression != null : !INITIAL_STATE_EXPRESSION_EDEFAULT.equals(this.initialStateExpression);
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

        String result = super.toString() + " (id: " +
                this.id +
                ", labelExpression: " +
                this.labelExpression +
                ", initialStateExpression: " +
                this.initialStateExpression +
                ')';
        return result;
    }

} // RowFilterDescriptionImpl
