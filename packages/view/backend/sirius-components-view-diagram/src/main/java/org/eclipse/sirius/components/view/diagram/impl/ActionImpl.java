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
package org.eclipse.sirius.components.view.diagram.impl;

import java.util.Collection;

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
import org.eclipse.sirius.components.view.diagram.Action;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getIconURLsExpression <em>Icon UR Ls
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getPreconditionExpression <em>Precondition
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ActionImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActionImpl extends MinimalEObjectImpl.Container implements Action {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "Action";

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getLabelExpression()
     * @generated
     * @ordered
     */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

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
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBody()
     * @generated
     * @ordered
     */
    protected EList<Operation> body;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.ACTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__NAME, oldName, this.name));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION, oldIconURLsExpression, this.iconURLsExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ACTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, DiagramPackage.ACTION__BODY);
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
            case DiagramPackage.ACTION__BODY:
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
            case DiagramPackage.ACTION__NAME:
                return this.getName();
            case DiagramPackage.ACTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
                return this.getIconURLsExpression();
            case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case DiagramPackage.ACTION__BODY:
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
            case DiagramPackage.ACTION__NAME:
                this.setName((String) newValue);
                return;
            case DiagramPackage.ACTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
                this.setIconURLsExpression((String) newValue);
                return;
            case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case DiagramPackage.ACTION__BODY:
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
            case DiagramPackage.ACTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case DiagramPackage.ACTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
                this.setIconURLsExpression(ICON_UR_LS_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.ACTION__BODY:
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
            case DiagramPackage.ACTION__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case DiagramPackage.ACTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case DiagramPackage.ACTION__ICON_UR_LS_EXPRESSION:
                return ICON_UR_LS_EXPRESSION_EDEFAULT == null ? this.iconURLsExpression != null : !ICON_UR_LS_EXPRESSION_EDEFAULT.equals(this.iconURLsExpression);
            case DiagramPackage.ACTION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case DiagramPackage.ACTION__BODY:
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (name: ");
        result.append(this.name);
        result.append(", labelExpression: ");
        result.append(this.labelExpression);
        result.append(", iconURLsExpression: ");
        result.append(this.iconURLsExpression);
        result.append(", preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(')');
        return result.toString();
    }

} // ActionImpl
