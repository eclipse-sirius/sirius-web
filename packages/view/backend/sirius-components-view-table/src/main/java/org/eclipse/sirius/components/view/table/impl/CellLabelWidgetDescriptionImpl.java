/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Cell Label Widget Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.CellLabelWidgetDescriptionImpl#getIconExpression <em>Icon
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CellLabelWidgetDescriptionImpl extends MinimalEObjectImpl.Container implements CellLabelWidgetDescription {

    /**
     * The default value of the '{@link #getIconExpression() <em>Icon Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getIconExpression()
     * @generated
     * @ordered
     */
    protected static final String ICON_EXPRESSION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getIconExpression() <em>Icon Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getIconExpression()
     * @generated
     * @ordered
     */
    protected String iconExpression = ICON_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CellLabelWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TablePackage.Literals.CELL_LABEL_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIconExpression() {
        return this.iconExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIconExpression(String newIconExpression) {
        String oldIconExpression = this.iconExpression;
        this.iconExpression = newIconExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION, oldIconExpression, this.iconExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TablePackage.CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION:
                return this.getIconExpression();
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
            case TablePackage.CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION:
                this.setIconExpression((String) newValue);
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
            case TablePackage.CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION:
                this.setIconExpression(ICON_EXPRESSION_EDEFAULT);
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
            case TablePackage.CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION:
                return ICON_EXPRESSION_EDEFAULT == null ? this.iconExpression != null : !ICON_EXPRESSION_EDEFAULT.equals(this.iconExpression);
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
        result.append(" (iconExpression: ");
        result.append(this.iconExpression);
        result.append(')');
        return result.toString();
    }

} // CellLabelWidgetDescriptionImpl
