/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramToolbar;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Toolbar</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramToolbarImpl#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramToolbarImpl#isExpandedByDefault <em>Expanded By
 * Default</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramToolbarImpl extends MinimalEObjectImpl.Container implements DiagramToolbar {
    /**
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #isExpandedByDefault() <em>Expanded By Default</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isExpandedByDefault()
     * @generated
     * @ordered
     */
    protected static final boolean EXPANDED_BY_DEFAULT_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isExpandedByDefault() <em>Expanded By Default</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isExpandedByDefault()
     * @generated
     * @ordered
     */
    protected boolean expandedByDefault = EXPANDED_BY_DEFAULT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramToolbarImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.DIAGRAM_TOOLBAR;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_TOOLBAR__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isExpandedByDefault() {
        return this.expandedByDefault;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setExpandedByDefault(boolean newExpandedByDefault) {
        boolean oldExpandedByDefault = this.expandedByDefault;
        this.expandedByDefault = newExpandedByDefault;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_TOOLBAR__EXPANDED_BY_DEFAULT, oldExpandedByDefault, this.expandedByDefault));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.DIAGRAM_TOOLBAR__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case DiagramPackage.DIAGRAM_TOOLBAR__EXPANDED_BY_DEFAULT:
                return this.isExpandedByDefault();
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
            case DiagramPackage.DIAGRAM_TOOLBAR__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case DiagramPackage.DIAGRAM_TOOLBAR__EXPANDED_BY_DEFAULT:
                this.setExpandedByDefault((Boolean) newValue);
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
            case DiagramPackage.DIAGRAM_TOOLBAR__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.DIAGRAM_TOOLBAR__EXPANDED_BY_DEFAULT:
                this.setExpandedByDefault(EXPANDED_BY_DEFAULT_EDEFAULT);
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
            case DiagramPackage.DIAGRAM_TOOLBAR__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case DiagramPackage.DIAGRAM_TOOLBAR__EXPANDED_BY_DEFAULT:
                return this.expandedByDefault != EXPANDED_BY_DEFAULT_EDEFAULT;
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
        result.append(" (preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(", expandedByDefault: ");
        result.append(this.expandedByDefault);
        result.append(')');
        return result.toString();
    }

} // DiagramToolbarImpl
