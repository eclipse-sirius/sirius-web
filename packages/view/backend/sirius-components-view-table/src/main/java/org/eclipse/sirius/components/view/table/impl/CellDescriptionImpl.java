/*******************************************************************************
 * Copyright (c) 2024, 2026 CEA LIST.
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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Cell Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl#getValueExpression <em>Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl#getTooltipExpression <em>Tooltip
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl#getCellWidgetDescription <em>Cell Widget
 * Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CellDescriptionImpl extends MinimalEObjectImpl.Container implements CellDescription {

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
     * The default value of the '{@link #getSelectedTargetObjectExpression() <em>Selected Target Object
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectedTargetObjectExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTED_TARGET_OBJECT_EXPRESSION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getSelectedTargetObjectExpression() <em>Selected Target Object Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectedTargetObjectExpression()
     * @generated
     * @ordered
     */
    protected String selectedTargetObjectExpression = SELECTED_TARGET_OBJECT_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EXPRESSION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected String valueExpression = VALUE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTooltipExpression() <em>Tooltip Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTooltipExpression()
     */
    protected static final String TOOLTIP_EXPRESSION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getTooltipExpression() <em>Tooltip Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTooltipExpression()
     */
    protected String tooltipExpression = TOOLTIP_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getCellWidgetDescription() <em>Cell Widget Description</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCellWidgetDescription()
     * @generated
     * @ordered
     */
    protected CellWidgetDescription cellWidgetDescription;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CellDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TablePackage.Literals.CELL_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.CELL_DESCRIPTION__NAME, oldName, this.name));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.CELL_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectedTargetObjectExpression() {
        return this.selectedTargetObjectExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectedTargetObjectExpression(String newSelectedTargetObjectExpression) {
        String oldSelectedTargetObjectExpression = this.selectedTargetObjectExpression;
        this.selectedTargetObjectExpression = newSelectedTargetObjectExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION, oldSelectedTargetObjectExpression,
                    this.selectedTargetObjectExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValueExpression() {
        return this.valueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValueExpression(String newValueExpression) {
        String oldValueExpression = this.valueExpression;
        this.valueExpression = newValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.CELL_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTooltipExpression() {
        return this.tooltipExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTooltipExpression(String newTooltipExpression) {
        String oldTooltipExpression = this.tooltipExpression;
        this.tooltipExpression = newTooltipExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.CELL_DESCRIPTION__TOOLTIP_EXPRESSION, oldTooltipExpression, this.tooltipExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CellWidgetDescription getCellWidgetDescription() {
        return this.cellWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCellWidgetDescription(CellWidgetDescription newCellWidgetDescription) {
        if (newCellWidgetDescription != this.cellWidgetDescription) {
            NotificationChain msgs = null;
            if (this.cellWidgetDescription != null)
                msgs = ((InternalEObject) this.cellWidgetDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION, null, msgs);
            if (newCellWidgetDescription != null)
                msgs = ((InternalEObject) newCellWidgetDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION, null, msgs);
            msgs = this.basicSetCellWidgetDescription(newCellWidgetDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION, newCellWidgetDescription, newCellWidgetDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCellWidgetDescription(CellWidgetDescription newCellWidgetDescription, NotificationChain msgs) {
        CellWidgetDescription oldCellWidgetDescription = this.cellWidgetDescription;
        this.cellWidgetDescription = newCellWidgetDescription;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION, oldCellWidgetDescription, newCellWidgetDescription);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION:
                return this.basicSetCellWidgetDescription(null, msgs);
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
            case TablePackage.CELL_DESCRIPTION__NAME:
                return this.getName();
            case TablePackage.CELL_DESCRIPTION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case TablePackage.CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION:
                return this.getSelectedTargetObjectExpression();
            case TablePackage.CELL_DESCRIPTION__VALUE_EXPRESSION:
                return this.getValueExpression();
            case TablePackage.CELL_DESCRIPTION__TOOLTIP_EXPRESSION:
                return this.getTooltipExpression();
            case TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION:
                return this.getCellWidgetDescription();
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
            case TablePackage.CELL_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case TablePackage.CELL_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case TablePackage.CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION:
                this.setSelectedTargetObjectExpression((String) newValue);
                return;
            case TablePackage.CELL_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression((String) newValue);
                return;
            case TablePackage.CELL_DESCRIPTION__TOOLTIP_EXPRESSION:
                this.setTooltipExpression((String) newValue);
                return;
            case TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION:
                this.setCellWidgetDescription((CellWidgetDescription) newValue);
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
            case TablePackage.CELL_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case TablePackage.CELL_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION:
                this.setSelectedTargetObjectExpression(SELECTED_TARGET_OBJECT_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.CELL_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.CELL_DESCRIPTION__TOOLTIP_EXPRESSION:
                this.setTooltipExpression(TOOLTIP_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION:
                this.setCellWidgetDescription((CellWidgetDescription) null);
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
            case TablePackage.CELL_DESCRIPTION__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case TablePackage.CELL_DESCRIPTION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case TablePackage.CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION:
                return SELECTED_TARGET_OBJECT_EXPRESSION_EDEFAULT == null ? this.selectedTargetObjectExpression != null
                        : !SELECTED_TARGET_OBJECT_EXPRESSION_EDEFAULT.equals(this.selectedTargetObjectExpression);
            case TablePackage.CELL_DESCRIPTION__VALUE_EXPRESSION:
                return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
            case TablePackage.CELL_DESCRIPTION__TOOLTIP_EXPRESSION:
                return TOOLTIP_EXPRESSION_EDEFAULT == null ? this.tooltipExpression != null : !TOOLTIP_EXPRESSION_EDEFAULT.equals(this.tooltipExpression);
            case TablePackage.CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION:
                return this.cellWidgetDescription != null;
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
        result.append(", preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(", selectedTargetObjectExpression: ");
        result.append(this.selectedTargetObjectExpression);
        result.append(", valueExpression: ");
        result.append(this.valueExpression);
        result.append(", tooltipExpression: ");
        result.append(this.tooltipExpression);
        result.append(')');
        return result.toString();
    }

} // CellDescriptionImpl
