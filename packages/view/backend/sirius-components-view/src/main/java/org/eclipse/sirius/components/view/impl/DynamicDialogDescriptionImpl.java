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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.DValidationMessageDescription;
import org.eclipse.sirius.components.view.DWidgetDescription;
import org.eclipse.sirius.components.view.DynamicDialogDescription;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Dynamic Dialog Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogDescriptionImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogDescriptionImpl#getTitleExpression <em>Title
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogDescriptionImpl#getDescriptionExpression
 * <em>Description Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogDescriptionImpl#getWidgetDescriptions <em>Widget
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogDescriptionImpl#getIsValidExpression <em>Is Valid
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogDescriptionImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogDescriptionImpl#getValidationMessages <em>Validation
 * Messages</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DynamicDialogDescriptionImpl extends MinimalEObjectImpl.Container implements DynamicDialogDescription {
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
     * The default value of the '{@link #getTitleExpression() <em>Title Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getTitleExpression()
     * @generated
     * @ordered
     */
    protected static final String TITLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTitleExpression() <em>Title Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getTitleExpression()
     * @generated
     * @ordered
     */
    protected String titleExpression = TITLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected String descriptionExpression = DESCRIPTION_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getWidgetDescriptions() <em>Widget Descriptions</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWidgetDescriptions()
     * @generated
     * @ordered
     */
    protected EList<DWidgetDescription> widgetDescriptions;

    /**
     * The default value of the '{@link #getIsValidExpression() <em>Is Valid Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsValidExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_VALID_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsValidExpression() <em>Is Valid Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsValidExpression()
     * @generated
     * @ordered
     */
    protected String isValidExpression = IS_VALID_EXPRESSION_EDEFAULT;

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
     * The cached value of the '{@link #getValidationMessages() <em>Validation Messages</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getValidationMessages()
     * @generated
     * @ordered
     */
    protected EList<DValidationMessageDescription> validationMessages;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DynamicDialogDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__ID, oldId, this.id));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTitleExpression() {
        return this.titleExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTitleExpression(String newTitleExpression) {
        String oldTitleExpression = this.titleExpression;
        this.titleExpression = newTitleExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION, oldTitleExpression, this.titleExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDescriptionExpression() {
        return this.descriptionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDescriptionExpression(String newDescriptionExpression) {
        String oldDescriptionExpression = this.descriptionExpression;
        this.descriptionExpression = newDescriptionExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION, oldDescriptionExpression, this.descriptionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DWidgetDescription> getWidgetDescriptions() {
        if (this.widgetDescriptions == null) {
            this.widgetDescriptions = new EObjectContainmentEList<>(DWidgetDescription.class, this, ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS);
        }
        return this.widgetDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsValidExpression() {
        return this.isValidExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsValidExpression(String newIsValidExpression) {
        String oldIsValidExpression = this.isValidExpression;
        this.isValidExpression = newIsValidExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION, oldIsValidExpression, this.isValidExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DValidationMessageDescription> getValidationMessages() {
        if (this.validationMessages == null) {
            this.validationMessages = new EObjectContainmentEList<>(DValidationMessageDescription.class, this,
                    ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES);
        }
        return this.validationMessages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS:
                return ((InternalEList<?>) this.getWidgetDescriptions()).basicRemove(otherEnd, msgs);
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES:
                return ((InternalEList<?>) this.getValidationMessages()).basicRemove(otherEnd, msgs);
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
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__ID:
                return this.getId();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION:
                return this.getTitleExpression();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return this.getDescriptionExpression();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS:
                return this.getWidgetDescriptions();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION:
                return this.getIsValidExpression();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__BODY:
                return this.getBody();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES:
                return this.getValidationMessages();
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
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__ID:
                this.setId((String) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION:
                this.setTitleExpression((String) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression((String) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS:
                this.getWidgetDescriptions().clear();
                this.getWidgetDescriptions().addAll((Collection<? extends DWidgetDescription>) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION:
                this.setIsValidExpression((String) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES:
                this.getValidationMessages().clear();
                this.getValidationMessages().addAll((Collection<? extends DValidationMessageDescription>) newValue);
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
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__ID:
                this.setId(ID_EDEFAULT);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION:
                this.setTitleExpression(TITLE_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression(DESCRIPTION_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS:
                this.getWidgetDescriptions().clear();
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION:
                this.setIsValidExpression(IS_VALID_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__BODY:
                this.getBody().clear();
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES:
                this.getValidationMessages().clear();
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
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__ID:
                return ID_EDEFAULT == null ? this.id != null : !ID_EDEFAULT.equals(this.id);
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION:
                return TITLE_EXPRESSION_EDEFAULT == null ? this.titleExpression != null : !TITLE_EXPRESSION_EDEFAULT.equals(this.titleExpression);
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return DESCRIPTION_EXPRESSION_EDEFAULT == null ? this.descriptionExpression != null : !DESCRIPTION_EXPRESSION_EDEFAULT.equals(this.descriptionExpression);
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS:
                return this.widgetDescriptions != null && !this.widgetDescriptions.isEmpty();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION:
                return IS_VALID_EXPRESSION_EDEFAULT == null ? this.isValidExpression != null : !IS_VALID_EXPRESSION_EDEFAULT.equals(this.isValidExpression);
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES:
                return this.validationMessages != null && !this.validationMessages.isEmpty();
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
        result.append(", titleExpression: ");
        result.append(this.titleExpression);
        result.append(", descriptionExpression: ");
        result.append(this.descriptionExpression);
        result.append(", isValidExpression: ");
        result.append(this.isValidExpression);
        result.append(')');
        return result.toString();
    }

} // DynamicDialogDescriptionImpl
