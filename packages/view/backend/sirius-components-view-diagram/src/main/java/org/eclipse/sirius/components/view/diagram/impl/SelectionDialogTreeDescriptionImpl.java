/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Selection Dialog Tree Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogTreeDescriptionImpl#getElementsExpression
 * <em>Elements Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogTreeDescriptionImpl#getChildrenExpression
 * <em>Children Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogTreeDescriptionImpl#getIsSelectableExpression
 * <em>Is Selectable Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SelectionDialogTreeDescriptionImpl extends MinimalEObjectImpl.Container implements SelectionDialogTreeDescription {
    /**
     * The default value of the '{@link #getElementsExpression() <em>Elements Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElementsExpression()
     * @generated
     * @ordered
     */
    protected static final String ELEMENTS_EXPRESSION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getElementsExpression() <em>Elements Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElementsExpression()
     * @generated
     * @ordered
     */
    protected String elementsExpression = ELEMENTS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildrenExpression()
     * @generated
     * @ordered
     */
    protected static final String CHILDREN_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildrenExpression()
     * @generated
     * @ordered
     */
    protected String childrenExpression = CHILDREN_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsSelectableExpression() <em>Is Selectable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsSelectableExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_SELECTABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsSelectableExpression() <em>Is Selectable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsSelectableExpression()
     * @generated
     * @ordered
     */
    protected String isSelectableExpression = IS_SELECTABLE_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SelectionDialogTreeDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.SELECTION_DIALOG_TREE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getElementsExpression() {
        return this.elementsExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setElementsExpression(String newElementsExpression) {
        String oldElementsExpression = this.elementsExpression;
        this.elementsExpression = newElementsExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__ELEMENTS_EXPRESSION, oldElementsExpression, this.elementsExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getChildrenExpression() {
        return this.childrenExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setChildrenExpression(String newChildrenExpression) {
        String oldChildrenExpression = this.childrenExpression;
        this.childrenExpression = newChildrenExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__CHILDREN_EXPRESSION, oldChildrenExpression, this.childrenExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsSelectableExpression() {
        return this.isSelectableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsSelectableExpression(String newIsSelectableExpression) {
        String oldIsSelectableExpression = this.isSelectableExpression;
        this.isSelectableExpression = newIsSelectableExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__IS_SELECTABLE_EXPRESSION, oldIsSelectableExpression, this.isSelectableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                return this.getElementsExpression();
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                return this.getChildrenExpression();
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__IS_SELECTABLE_EXPRESSION:
                return this.getIsSelectableExpression();
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
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                this.setElementsExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                this.setChildrenExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__IS_SELECTABLE_EXPRESSION:
                this.setIsSelectableExpression((String) newValue);
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
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                this.setElementsExpression(ELEMENTS_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                this.setChildrenExpression(CHILDREN_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__IS_SELECTABLE_EXPRESSION:
                this.setIsSelectableExpression(IS_SELECTABLE_EXPRESSION_EDEFAULT);
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
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                return ELEMENTS_EXPRESSION_EDEFAULT == null ? this.elementsExpression != null : !ELEMENTS_EXPRESSION_EDEFAULT.equals(this.elementsExpression);
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                return CHILDREN_EXPRESSION_EDEFAULT == null ? this.childrenExpression != null : !CHILDREN_EXPRESSION_EDEFAULT.equals(this.childrenExpression);
            case DiagramPackage.SELECTION_DIALOG_TREE_DESCRIPTION__IS_SELECTABLE_EXPRESSION:
                return IS_SELECTABLE_EXPRESSION_EDEFAULT == null ? this.isSelectableExpression != null : !IS_SELECTABLE_EXPRESSION_EDEFAULT.equals(this.isSelectableExpression);
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
        result.append(" (elementsExpression: ");
        result.append(this.elementsExpression);
        result.append(", childrenExpression: ");
        result.append(this.childrenExpression);
        result.append(", isSelectableExpression: ");
        result.append(this.isSelectableExpression);
        result.append(')');
        return result.toString();
    }

} // SelectionDialogTreeDescriptionImpl
