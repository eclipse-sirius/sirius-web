/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Tool</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl#getTargetElementDescriptions <em>Target
 * Element Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl#getIconURLsExpression <em>Icon UR Ls
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl#getDialogDescription <em>Dialog
 * Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeToolImpl extends ToolImpl implements EdgeTool {
    /**
     * The cached value of the '{@link #getTargetElementDescriptions() <em>Target Element Descriptions</em>}' reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetElementDescriptions()
     * @generated
     * @ordered
     */
    protected EList<DiagramElementDescription> targetElementDescriptions;

    /**
     * The default value of the '{@link #getIconURLsExpression() <em>Icon UR Ls Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLsExpression()
     * @generated
     * @ordered
     */
    protected static final String ICON_UR_LS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIconURLsExpression() <em>Icon UR Ls Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLsExpression()
     * @generated
     * @ordered
     */
    protected String iconURLsExpression = ICON_UR_LS_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getDialogDescription() <em>Dialog Description</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDialogDescription()
     * @generated
     * @ordered
     */
    protected DialogDescription dialogDescription;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EdgeToolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.EDGE_TOOL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DiagramElementDescription> getTargetElementDescriptions() {
        if (this.targetElementDescriptions == null) {
            this.targetElementDescriptions = new EObjectResolvingEList<>(DiagramElementDescription.class, this, DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS);
        }
        return this.targetElementDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIconURLsExpression() {
        return this.iconURLsExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIconURLsExpression(String newIconURLsExpression) {
        String oldIconURLsExpression = this.iconURLsExpression;
        this.iconURLsExpression = newIconURLsExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_TOOL__ICON_UR_LS_EXPRESSION, oldIconURLsExpression, this.iconURLsExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DialogDescription getDialogDescription() {
        return this.dialogDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDialogDescription(DialogDescription newDialogDescription, NotificationChain msgs) {
        DialogDescription oldDialogDescription = this.dialogDescription;
        this.dialogDescription = newDialogDescription;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION, oldDialogDescription, newDialogDescription);
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
    public void setDialogDescription(DialogDescription newDialogDescription) {
        if (newDialogDescription != this.dialogDescription) {
            NotificationChain msgs = null;
            if (this.dialogDescription != null)
                msgs = ((InternalEObject) this.dialogDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION, null, msgs);
            if (newDialogDescription != null)
                msgs = ((InternalEObject) newDialogDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION, null, msgs);
            msgs = this.basicSetDialogDescription(newDialogDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION, newDialogDescription, newDialogDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION:
                return this.basicSetDialogDescription(null, msgs);
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
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                return this.getTargetElementDescriptions();
            case DiagramPackage.EDGE_TOOL__ICON_UR_LS_EXPRESSION:
                return this.getIconURLsExpression();
            case DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION:
                return this.getDialogDescription();
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
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                this.getTargetElementDescriptions().clear();
                this.getTargetElementDescriptions().addAll((Collection<? extends DiagramElementDescription>) newValue);
                return;
            case DiagramPackage.EDGE_TOOL__ICON_UR_LS_EXPRESSION:
                this.setIconURLsExpression((String) newValue);
                return;
            case DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION:
                this.setDialogDescription((DialogDescription) newValue);
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
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                this.getTargetElementDescriptions().clear();
                return;
            case DiagramPackage.EDGE_TOOL__ICON_UR_LS_EXPRESSION:
                this.setIconURLsExpression(ICON_UR_LS_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION:
                this.setDialogDescription((DialogDescription) null);
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
            case DiagramPackage.EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS:
                return this.targetElementDescriptions != null && !this.targetElementDescriptions.isEmpty();
            case DiagramPackage.EDGE_TOOL__ICON_UR_LS_EXPRESSION:
                return ICON_UR_LS_EXPRESSION_EDEFAULT == null ? this.iconURLsExpression != null : !ICON_UR_LS_EXPRESSION_EDEFAULT.equals(this.iconURLsExpression);
            case DiagramPackage.EDGE_TOOL__DIALOG_DESCRIPTION:
                return this.dialogDescription != null;
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
        result.append(" (iconURLsExpression: ");
        result.append(this.iconURLsExpression);
        result.append(')');
        return result.toString();
    }

} // EdgeToolImpl
