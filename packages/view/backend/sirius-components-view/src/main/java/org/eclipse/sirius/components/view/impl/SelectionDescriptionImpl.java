/**
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.SelectionDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Selection Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.SelectionDescriptionImpl#getSelectionCandidatesExpression
 * <em>Selection Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.SelectionDescriptionImpl#getSelectionMessage <em>Selection
 * Message</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SelectionDescriptionImpl extends MinimalEObjectImpl.Container implements SelectionDescription {
    /**
     * The default value of the '{@link #getSelectionCandidatesExpression() <em>Selection Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionCandidatesExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_CANDIDATES_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getSelectionCandidatesExpression() <em>Selection Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionCandidatesExpression()
     * @generated
     * @ordered
     */
    protected String selectionCandidatesExpression = SELECTION_CANDIDATES_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectionMessage() <em>Selection Message</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionMessage()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_MESSAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectionMessage() <em>Selection Message</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionMessage()
     * @generated
     * @ordered
     */
    protected String selectionMessage = SELECTION_MESSAGE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SelectionDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.SELECTION_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectionCandidatesExpression() {
        return this.selectionCandidatesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectionCandidatesExpression(String newSelectionCandidatesExpression) {
        String oldSelectionCandidatesExpression = this.selectionCandidatesExpression;
        this.selectionCandidatesExpression = newSelectionCandidatesExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION, oldSelectionCandidatesExpression,
                    this.selectionCandidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectionMessage() {
        return this.selectionMessage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectionMessage(String newSelectionMessage) {
        String oldSelectionMessage = this.selectionMessage;
        this.selectionMessage = newSelectionMessage;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.SELECTION_DESCRIPTION__SELECTION_MESSAGE, oldSelectionMessage, this.selectionMessage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                return this.getSelectionCandidatesExpression();
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_MESSAGE:
                return this.getSelectionMessage();
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
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                this.setSelectionCandidatesExpression((String) newValue);
                return;
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_MESSAGE:
                this.setSelectionMessage((String) newValue);
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
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                this.setSelectionCandidatesExpression(SELECTION_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_MESSAGE:
                this.setSelectionMessage(SELECTION_MESSAGE_EDEFAULT);
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
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                return SELECTION_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.selectionCandidatesExpression != null
                        : !SELECTION_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.selectionCandidatesExpression);
            case ViewPackage.SELECTION_DESCRIPTION__SELECTION_MESSAGE:
                return SELECTION_MESSAGE_EDEFAULT == null ? this.selectionMessage != null : !SELECTION_MESSAGE_EDEFAULT.equals(this.selectionMessage);
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
        result.append(" (selectionCandidatesExpression: ");
        result.append(this.selectionCandidatesExpression);
        result.append(", selectionMessage: ");
        result.append(this.selectionMessage);
        result.append(')');
        return result.toString();
    }

} // SelectionDescriptionImpl
