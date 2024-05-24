/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.sirius.components.view.diagram.LabelDescription;
import org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy;
import org.eclipse.sirius.components.view.diagram.LabelTextAlign;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Label Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.LabelDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.LabelDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.LabelDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class LabelDescriptionImpl extends MinimalEObjectImpl.Container implements LabelDescription {

    /**
     * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelExpression()
     */
    protected static final String LABEL_EXPRESSION_EDEFAULT = "aql:self.name";
    /**
     * The default value of the '{@link #getOverflowStrategy() <em>Overflow Strategy</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getOverflowStrategy()
     */
    protected static final LabelOverflowStrategy OVERFLOW_STRATEGY_EDEFAULT = LabelOverflowStrategy.NONE;
    /**
     * The default value of the '{@link #getTextAlign() <em>Text Align</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTextAlign()
     */
    protected static final LabelTextAlign TEXT_ALIGN_EDEFAULT = LabelTextAlign.LEFT;
    /**
     * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelExpression()
     */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #getOverflowStrategy() <em>Overflow Strategy</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getOverflowStrategy()
     */
    protected LabelOverflowStrategy overflowStrategy = OVERFLOW_STRATEGY_EDEFAULT;
    /**
     * The cached value of the '{@link #getTextAlign() <em>Text Align</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTextAlign()
     */
    protected LabelTextAlign textAlign = TEXT_ALIGN_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LabelDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.LABEL_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LABEL_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelOverflowStrategy getOverflowStrategy() {
        return this.overflowStrategy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOverflowStrategy(LabelOverflowStrategy newOverflowStrategy) {
        LabelOverflowStrategy oldOverflowStrategy = this.overflowStrategy;
        this.overflowStrategy = newOverflowStrategy == null ? OVERFLOW_STRATEGY_EDEFAULT : newOverflowStrategy;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LABEL_DESCRIPTION__OVERFLOW_STRATEGY, oldOverflowStrategy, this.overflowStrategy));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelTextAlign getTextAlign() {
        return this.textAlign;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTextAlign(LabelTextAlign newTextAlign) {
        LabelTextAlign oldTextAlign = this.textAlign;
        this.textAlign = newTextAlign == null ? TEXT_ALIGN_EDEFAULT : newTextAlign;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.LABEL_DESCRIPTION__TEXT_ALIGN, oldTextAlign, this.textAlign));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.LABEL_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case DiagramPackage.LABEL_DESCRIPTION__OVERFLOW_STRATEGY:
                return this.getOverflowStrategy();
            case DiagramPackage.LABEL_DESCRIPTION__TEXT_ALIGN:
                return this.getTextAlign();
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
            case DiagramPackage.LABEL_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case DiagramPackage.LABEL_DESCRIPTION__OVERFLOW_STRATEGY:
                this.setOverflowStrategy((LabelOverflowStrategy) newValue);
                return;
            case DiagramPackage.LABEL_DESCRIPTION__TEXT_ALIGN:
                this.setTextAlign((LabelTextAlign) newValue);
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
            case DiagramPackage.LABEL_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.LABEL_DESCRIPTION__OVERFLOW_STRATEGY:
                this.setOverflowStrategy(OVERFLOW_STRATEGY_EDEFAULT);
                return;
            case DiagramPackage.LABEL_DESCRIPTION__TEXT_ALIGN:
                this.setTextAlign(TEXT_ALIGN_EDEFAULT);
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
            case DiagramPackage.LABEL_DESCRIPTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case DiagramPackage.LABEL_DESCRIPTION__OVERFLOW_STRATEGY:
                return this.overflowStrategy != OVERFLOW_STRATEGY_EDEFAULT;
            case DiagramPackage.LABEL_DESCRIPTION__TEXT_ALIGN:
                return this.textAlign != TEXT_ALIGN_EDEFAULT;
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

        String result = super.toString() + " (labelExpression: " +
                this.labelExpression +
                ", overflowStrategy: " +
                this.overflowStrategy +
                ", textAlign: " +
                this.textAlign +
                ')';
        return result;
    }

} // LabelDescriptionImpl
