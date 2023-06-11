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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.view.DWidgetDescription;
import org.eclipse.sirius.components.view.DWidgetOutputDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>DWidget Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.DWidgetDescriptionImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DWidgetDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DWidgetDescriptionImpl#getInitialValueExpression <em>Initial Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DWidgetDescriptionImpl#getOutput <em>Output</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DWidgetDescriptionImpl#getInputs <em>Inputs</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DWidgetDescriptionImpl extends MinimalEObjectImpl.Container implements DWidgetDescription {
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

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
     * The default value of the '{@link #getInitialValueExpression() <em>Initial Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getInitialValueExpression()
     * @generated
     * @ordered
     */
    protected static final String INITIAL_VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInitialValueExpression() <em>Initial Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getInitialValueExpression()
     * @generated
     * @ordered
     */
    protected String initialValueExpression = INITIAL_VALUE_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getOutput() <em>Output</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getOutput()
     * @generated
     * @ordered
     */
    protected DWidgetOutputDescription output;

    /**
     * The cached value of the '{@link #getInputs() <em>Inputs</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getInputs()
     * @generated
     * @ordered
     */
    protected EList<DWidgetOutputDescription> inputs;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DWIDGET_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DWIDGET_DESCRIPTION__ID, oldId, this.id));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DWIDGET_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getInitialValueExpression() {
        return this.initialValueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInitialValueExpression(String newInitialValueExpression) {
        String oldInitialValueExpression = this.initialValueExpression;
        this.initialValueExpression = newInitialValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DWIDGET_DESCRIPTION__INITIAL_VALUE_EXPRESSION, oldInitialValueExpression, this.initialValueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DWidgetOutputDescription getOutput() {
        return this.output;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetOutput(DWidgetOutputDescription newOutput, NotificationChain msgs) {
        DWidgetOutputDescription oldOutput = this.output;
        this.output = newOutput;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.DWIDGET_DESCRIPTION__OUTPUT, oldOutput, newOutput);
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
    public void setOutput(DWidgetOutputDescription newOutput) {
        if (newOutput != this.output) {
            NotificationChain msgs = null;
            if (this.output != null)
                msgs = ((InternalEObject) this.output).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.DWIDGET_DESCRIPTION__OUTPUT, null, msgs);
            if (newOutput != null)
                msgs = ((InternalEObject) newOutput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.DWIDGET_DESCRIPTION__OUTPUT, null, msgs);
            msgs = this.basicSetOutput(newOutput, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DWIDGET_DESCRIPTION__OUTPUT, newOutput, newOutput));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DWidgetOutputDescription> getInputs() {
        if (this.inputs == null) {
            this.inputs = new EObjectResolvingEList<>(DWidgetOutputDescription.class, this, ViewPackage.DWIDGET_DESCRIPTION__INPUTS);
        }
        return this.inputs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.DWIDGET_DESCRIPTION__OUTPUT:
                return this.basicSetOutput(null, msgs);
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
            case ViewPackage.DWIDGET_DESCRIPTION__ID:
                return this.getId();
            case ViewPackage.DWIDGET_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case ViewPackage.DWIDGET_DESCRIPTION__INITIAL_VALUE_EXPRESSION:
                return this.getInitialValueExpression();
            case ViewPackage.DWIDGET_DESCRIPTION__OUTPUT:
                return this.getOutput();
            case ViewPackage.DWIDGET_DESCRIPTION__INPUTS:
                return this.getInputs();
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
            case ViewPackage.DWIDGET_DESCRIPTION__ID:
                this.setId((String) newValue);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__INITIAL_VALUE_EXPRESSION:
                this.setInitialValueExpression((String) newValue);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__OUTPUT:
                this.setOutput((DWidgetOutputDescription) newValue);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__INPUTS:
                this.getInputs().clear();
                this.getInputs().addAll((Collection<? extends DWidgetOutputDescription>) newValue);
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
            case ViewPackage.DWIDGET_DESCRIPTION__ID:
                this.setId(ID_EDEFAULT);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__INITIAL_VALUE_EXPRESSION:
                this.setInitialValueExpression(INITIAL_VALUE_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__OUTPUT:
                this.setOutput((DWidgetOutputDescription) null);
                return;
            case ViewPackage.DWIDGET_DESCRIPTION__INPUTS:
                this.getInputs().clear();
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
            case ViewPackage.DWIDGET_DESCRIPTION__ID:
                return ID_EDEFAULT == null ? this.id != null : !ID_EDEFAULT.equals(this.id);
            case ViewPackage.DWIDGET_DESCRIPTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case ViewPackage.DWIDGET_DESCRIPTION__INITIAL_VALUE_EXPRESSION:
                return INITIAL_VALUE_EXPRESSION_EDEFAULT == null ? this.initialValueExpression != null : !INITIAL_VALUE_EXPRESSION_EDEFAULT.equals(this.initialValueExpression);
            case ViewPackage.DWIDGET_DESCRIPTION__OUTPUT:
                return this.output != null;
            case ViewPackage.DWIDGET_DESCRIPTION__INPUTS:
                return this.inputs != null && !this.inputs.isEmpty();
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
        result.append(" (id: ");
        result.append(this.id);
        result.append(", labelExpression: ");
        result.append(this.labelExpression);
        result.append(", initialValueExpression: ");
        result.append(this.initialValueExpression);
        result.append(')');
        return result.toString();
    }

} // DWidgetDescriptionImpl
