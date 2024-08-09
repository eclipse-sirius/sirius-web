/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Selection Dialog Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionCandidatesExpression
 * <em>Selection Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionMessage
 * <em>Selection Message</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#isDisplayedAsTree
 * <em>Displayed As Tree</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#isExpandedAtOpening
 * <em>Expanded At Opening</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SelectionDialogDescriptionImpl extends DialogDescriptionImpl implements SelectionDialogDescription {
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
     * The default value of the '{@link #isDisplayedAsTree() <em>Displayed As Tree</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #isDisplayedAsTree()
     * @generated
     * @ordered
     */
    protected static final boolean DISPLAYED_AS_TREE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDisplayedAsTree() <em>Displayed As Tree</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #isDisplayedAsTree()
     * @generated
     * @ordered
     */
    protected boolean displayedAsTree = DISPLAYED_AS_TREE_EDEFAULT;

    /**
     * The default value of the '{@link #isExpandedAtOpening() <em>Expanded At Opening</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isExpandedAtOpening()
     * @generated
     * @ordered
     */
    protected static final boolean EXPANDED_AT_OPENING_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isExpandedAtOpening() <em>Expanded At Opening</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isExpandedAtOpening()
     * @generated
     * @ordered
     */
    protected boolean expandedAtOpening = EXPANDED_AT_OPENING_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SelectionDialogDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION, oldSelectionCandidatesExpression,
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE, oldSelectionMessage, this.selectionMessage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isDisplayedAsTree() {
        return this.displayedAsTree;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDisplayedAsTree(boolean newDisplayedAsTree) {
        boolean oldDisplayedAsTree = this.displayedAsTree;
        this.displayedAsTree = newDisplayedAsTree;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DISPLAYED_AS_TREE, oldDisplayedAsTree, this.displayedAsTree));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isExpandedAtOpening() {
        return this.expandedAtOpening;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setExpandedAtOpening(boolean newExpandedAtOpening) {
        boolean oldExpandedAtOpening = this.expandedAtOpening;
        this.expandedAtOpening = newExpandedAtOpening;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__EXPANDED_AT_OPENING, oldExpandedAtOpening, this.expandedAtOpening));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                return this.getSelectionCandidatesExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                return this.getSelectionMessage();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DISPLAYED_AS_TREE:
                return this.isDisplayedAsTree();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__EXPANDED_AT_OPENING:
                return this.isExpandedAtOpening();
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                this.setSelectionCandidatesExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                this.setSelectionMessage((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DISPLAYED_AS_TREE:
                this.setDisplayedAsTree((Boolean) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__EXPANDED_AT_OPENING:
                this.setExpandedAtOpening((Boolean) newValue);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                this.setSelectionCandidatesExpression(SELECTION_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                this.setSelectionMessage(SELECTION_MESSAGE_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DISPLAYED_AS_TREE:
                this.setDisplayedAsTree(DISPLAYED_AS_TREE_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__EXPANDED_AT_OPENING:
                this.setExpandedAtOpening(EXPANDED_AT_OPENING_EDEFAULT);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION:
                return SELECTION_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.selectionCandidatesExpression != null
                        : !SELECTION_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.selectionCandidatesExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                return SELECTION_MESSAGE_EDEFAULT == null ? this.selectionMessage != null : !SELECTION_MESSAGE_EDEFAULT.equals(this.selectionMessage);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DISPLAYED_AS_TREE:
                return this.displayedAsTree != DISPLAYED_AS_TREE_EDEFAULT;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__EXPANDED_AT_OPENING:
                return this.expandedAtOpening != EXPANDED_AT_OPENING_EDEFAULT;
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
        result.append(", displayedAsTree: ");
        result.append(this.displayedAsTree);
        result.append(", expandedAtOpening: ");
        result.append(this.expandedAtOpening);
        result.append(')');
        return result.toString();
    }

} // SelectionDialogDescriptionImpl
