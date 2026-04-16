/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.view.diagram.DecoratorDescription;
import org.eclipse.sirius.components.view.diagram.DecoratorPosition;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Decorator Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getIconURLExpression <em>Icon URL
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getPosition
 * <em>Position</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DecoratorDescriptionImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DecoratorDescriptionImpl extends MinimalEObjectImpl.Container implements DecoratorDescription {
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
     * The default value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected static final String ICON_URL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected String iconURLExpression = ICON_URL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getPosition() <em>Position</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getPosition()
     * @generated
     * @ordered
     */
    protected static final DecoratorPosition POSITION_EDEFAULT = DecoratorPosition.NORTH;

    /**
     * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getPosition()
     * @generated
     * @ordered
     */
    protected DecoratorPosition position = POSITION_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DecoratorDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.DECORATOR_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIconURLExpression() {
        return this.iconURLExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIconURLExpression(String newIconURLExpression) {
        String oldIconURLExpression = this.iconURLExpression;
        this.iconURLExpression = newIconURLExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION, oldIconURLExpression, this.iconURLExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DecoratorPosition getPosition() {
        return this.position;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPosition(DecoratorPosition newPosition) {
        DecoratorPosition oldPosition = this.position;
        this.position = newPosition == null ? POSITION_EDEFAULT : newPosition;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__POSITION, oldPosition, this.position));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DECORATOR_DESCRIPTION__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
                return this.getIconURLExpression();
            case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
                return this.getPosition();
            case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
                return this.getName();
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
            case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
                this.setIconURLExpression((String) newValue);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
                this.setPosition((DecoratorPosition) newValue);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
                this.setName((String) newValue);
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
            case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
                this.setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
                this.setPosition(POSITION_EDEFAULT);
                return;
            case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
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
            case DiagramPackage.DECORATOR_DESCRIPTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case DiagramPackage.DECORATOR_DESCRIPTION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case DiagramPackage.DECORATOR_DESCRIPTION__ICON_URL_EXPRESSION:
                return ICON_URL_EXPRESSION_EDEFAULT == null ? this.iconURLExpression != null : !ICON_URL_EXPRESSION_EDEFAULT.equals(this.iconURLExpression);
            case DiagramPackage.DECORATOR_DESCRIPTION__POSITION:
                return this.position != POSITION_EDEFAULT;
            case DiagramPackage.DECORATOR_DESCRIPTION__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
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
        result.append(" (labelExpression: ");
        result.append(this.labelExpression);
        result.append(", preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(", iconURLExpression: ");
        result.append(this.iconURLExpression);
        result.append(", position: ");
        result.append(this.position);
        result.append(", name: ");
        result.append(this.name);
        result.append(')');
        return result.toString();
    }

} // DecoratorDescriptionImpl
