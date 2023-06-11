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
import org.eclipse.sirius.components.view.DValidationMessageDescription;
import org.eclipse.sirius.components.view.DValidationMessageSeverity;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>DValidation Message Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.DValidationMessageDescriptionImpl#getPreCondition <em>Pre
 * Condition</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DValidationMessageDescriptionImpl#getMessageExpression <em>Message
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DValidationMessageDescriptionImpl#getSeverity
 * <em>Severity</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DValidationMessageDescriptionImpl#isBlocksApplyDialog <em>Blocks
 * Apply Dialog</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DValidationMessageDescriptionImpl extends MinimalEObjectImpl.Container implements DValidationMessageDescription {
    /**
     * The default value of the '{@link #getPreCondition() <em>Pre Condition</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPreCondition()
     * @generated
     * @ordered
     */
    protected static final String PRE_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPreCondition() <em>Pre Condition</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPreCondition()
     * @generated
     * @ordered
     */
    protected String preCondition = PRE_CONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getMessageExpression() <em>Message Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMessageExpression()
     * @generated
     * @ordered
     */
    protected static final String MESSAGE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMessageExpression() <em>Message Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMessageExpression()
     * @generated
     * @ordered
     */
    protected String messageExpression = MESSAGE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getSeverity()
     * @generated
     * @ordered
     */
    protected static final DValidationMessageSeverity SEVERITY_EDEFAULT = DValidationMessageSeverity.INFO;

    /**
     * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getSeverity()
     * @generated
     * @ordered
     */
    protected DValidationMessageSeverity severity = SEVERITY_EDEFAULT;

    /**
     * The default value of the '{@link #isBlocksApplyDialog() <em>Blocks Apply Dialog</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isBlocksApplyDialog()
     * @generated
     * @ordered
     */
    protected static final boolean BLOCKS_APPLY_DIALOG_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBlocksApplyDialog() <em>Blocks Apply Dialog</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isBlocksApplyDialog()
     * @generated
     * @ordered
     */
    protected boolean blocksApplyDialog = BLOCKS_APPLY_DIALOG_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DValidationMessageDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DVALIDATION_MESSAGE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getPreCondition() {
        return this.preCondition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPreCondition(String newPreCondition) {
        String oldPreCondition = this.preCondition;
        this.preCondition = newPreCondition;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__PRE_CONDITION, oldPreCondition, this.preCondition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getMessageExpression() {
        return this.messageExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMessageExpression(String newMessageExpression) {
        String oldMessageExpression = this.messageExpression;
        this.messageExpression = newMessageExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__MESSAGE_EXPRESSION, oldMessageExpression, this.messageExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DValidationMessageSeverity getSeverity() {
        return this.severity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSeverity(DValidationMessageSeverity newSeverity) {
        DValidationMessageSeverity oldSeverity = this.severity;
        this.severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__SEVERITY, oldSeverity, this.severity));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isBlocksApplyDialog() {
        return this.blocksApplyDialog;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBlocksApplyDialog(boolean newBlocksApplyDialog) {
        boolean oldBlocksApplyDialog = this.blocksApplyDialog;
        this.blocksApplyDialog = newBlocksApplyDialog;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__BLOCKS_APPLY_DIALOG, oldBlocksApplyDialog, this.blocksApplyDialog));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__PRE_CONDITION:
                return this.getPreCondition();
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__MESSAGE_EXPRESSION:
                return this.getMessageExpression();
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__SEVERITY:
                return this.getSeverity();
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__BLOCKS_APPLY_DIALOG:
                return this.isBlocksApplyDialog();
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
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__PRE_CONDITION:
                this.setPreCondition((String) newValue);
                return;
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__MESSAGE_EXPRESSION:
                this.setMessageExpression((String) newValue);
                return;
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__SEVERITY:
                this.setSeverity((DValidationMessageSeverity) newValue);
                return;
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__BLOCKS_APPLY_DIALOG:
                this.setBlocksApplyDialog((Boolean) newValue);
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
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__PRE_CONDITION:
                this.setPreCondition(PRE_CONDITION_EDEFAULT);
                return;
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__MESSAGE_EXPRESSION:
                this.setMessageExpression(MESSAGE_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__SEVERITY:
                this.setSeverity(SEVERITY_EDEFAULT);
                return;
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__BLOCKS_APPLY_DIALOG:
                this.setBlocksApplyDialog(BLOCKS_APPLY_DIALOG_EDEFAULT);
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
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__PRE_CONDITION:
                return PRE_CONDITION_EDEFAULT == null ? this.preCondition != null : !PRE_CONDITION_EDEFAULT.equals(this.preCondition);
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__MESSAGE_EXPRESSION:
                return MESSAGE_EXPRESSION_EDEFAULT == null ? this.messageExpression != null : !MESSAGE_EXPRESSION_EDEFAULT.equals(this.messageExpression);
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__SEVERITY:
                return this.severity != SEVERITY_EDEFAULT;
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION__BLOCKS_APPLY_DIALOG:
                return this.blocksApplyDialog != BLOCKS_APPLY_DIALOG_EDEFAULT;
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
        result.append(" (preCondition: ");
        result.append(this.preCondition);
        result.append(", messageExpression: ");
        result.append(this.messageExpression);
        result.append(", severity: ");
        result.append(this.severity);
        result.append(", blocksApplyDialog: ");
        result.append(this.blocksApplyDialog);
        result.append(')');
        return result.toString();
    }

} // DValidationMessageDescriptionImpl
