/**
 * Copyright (c) 2021, 2022 Obeo.
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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.ListDescription;
import org.eclipse.sirius.components.view.ListDescriptionStyle;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>List Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl#getValueExpression <em>Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl#getDisplayExpression <em>Display
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl#getIsDeletableExpression <em>Is Deletable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ListDescriptionImpl extends WidgetDescriptionImpl implements ListDescription {
    /**
     * The default value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected String valueExpression = VALUE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDisplayExpression() <em>Display Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDisplayExpression()
     * @generated
     * @ordered
     */
    protected static final String DISPLAY_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDisplayExpression() <em>Display Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDisplayExpression()
     * @generated
     * @ordered
     */
    protected String displayExpression = DISPLAY_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsDeletableExpression() <em>Is Deletable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsDeletableExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_DELETABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsDeletableExpression() <em>Is Deletable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsDeletableExpression()
     * @generated
     * @ordered
     */
    protected String isDeletableExpression = IS_DELETABLE_EXPRESSION_EDEFAULT;

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
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected ListDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalListDescriptionStyle> conditionalStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ListDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.LIST_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValueExpression() {
        return this.valueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValueExpression(String newValueExpression) {
        String oldValueExpression = this.valueExpression;
        this.valueExpression = newValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LIST_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDisplayExpression() {
        return this.displayExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDisplayExpression(String newDisplayExpression) {
        String oldDisplayExpression = this.displayExpression;
        this.displayExpression = newDisplayExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LIST_DESCRIPTION__DISPLAY_EXPRESSION, oldDisplayExpression, this.displayExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsDeletableExpression() {
        return this.isDeletableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsDeletableExpression(String newIsDeletableExpression) {
        String oldIsDeletableExpression = this.isDeletableExpression;
        this.isDeletableExpression = newIsDeletableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION, oldIsDeletableExpression, this.isDeletableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, ViewPackage.LIST_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(ListDescriptionStyle newStyle, NotificationChain msgs) {
        ListDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.LIST_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(ListDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.LIST_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.LIST_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LIST_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalListDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalListDescriptionStyle.class, this, ViewPackage.LIST_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ViewPackage.LIST_DESCRIPTION__BODY:
            return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
        case ViewPackage.LIST_DESCRIPTION__STYLE:
            return this.basicSetStyle(null, msgs);
        case ViewPackage.LIST_DESCRIPTION__CONDITIONAL_STYLES:
            return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
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
        case ViewPackage.LIST_DESCRIPTION__VALUE_EXPRESSION:
            return this.getValueExpression();
        case ViewPackage.LIST_DESCRIPTION__DISPLAY_EXPRESSION:
            return this.getDisplayExpression();
        case ViewPackage.LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION:
            return this.getIsDeletableExpression();
        case ViewPackage.LIST_DESCRIPTION__BODY:
            return this.getBody();
        case ViewPackage.LIST_DESCRIPTION__STYLE:
            return this.getStyle();
        case ViewPackage.LIST_DESCRIPTION__CONDITIONAL_STYLES:
            return this.getConditionalStyles();
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
        case ViewPackage.LIST_DESCRIPTION__VALUE_EXPRESSION:
            this.setValueExpression((String) newValue);
            return;
        case ViewPackage.LIST_DESCRIPTION__DISPLAY_EXPRESSION:
            this.setDisplayExpression((String) newValue);
            return;
        case ViewPackage.LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION:
            this.setIsDeletableExpression((String) newValue);
            return;
        case ViewPackage.LIST_DESCRIPTION__BODY:
            this.getBody().clear();
            this.getBody().addAll((Collection<? extends Operation>) newValue);
            return;
        case ViewPackage.LIST_DESCRIPTION__STYLE:
            this.setStyle((ListDescriptionStyle) newValue);
            return;
        case ViewPackage.LIST_DESCRIPTION__CONDITIONAL_STYLES:
            this.getConditionalStyles().clear();
            this.getConditionalStyles().addAll((Collection<? extends ConditionalListDescriptionStyle>) newValue);
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
        case ViewPackage.LIST_DESCRIPTION__VALUE_EXPRESSION:
            this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.LIST_DESCRIPTION__DISPLAY_EXPRESSION:
            this.setDisplayExpression(DISPLAY_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION:
            this.setIsDeletableExpression(IS_DELETABLE_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.LIST_DESCRIPTION__BODY:
            this.getBody().clear();
            return;
        case ViewPackage.LIST_DESCRIPTION__STYLE:
            this.setStyle((ListDescriptionStyle) null);
            return;
        case ViewPackage.LIST_DESCRIPTION__CONDITIONAL_STYLES:
            this.getConditionalStyles().clear();
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
        case ViewPackage.LIST_DESCRIPTION__VALUE_EXPRESSION:
            return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
        case ViewPackage.LIST_DESCRIPTION__DISPLAY_EXPRESSION:
            return DISPLAY_EXPRESSION_EDEFAULT == null ? this.displayExpression != null : !DISPLAY_EXPRESSION_EDEFAULT.equals(this.displayExpression);
        case ViewPackage.LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION:
            return IS_DELETABLE_EXPRESSION_EDEFAULT == null ? this.isDeletableExpression != null : !IS_DELETABLE_EXPRESSION_EDEFAULT.equals(this.isDeletableExpression);
        case ViewPackage.LIST_DESCRIPTION__BODY:
            return this.body != null && !this.body.isEmpty();
        case ViewPackage.LIST_DESCRIPTION__STYLE:
            return this.style != null;
        case ViewPackage.LIST_DESCRIPTION__CONDITIONAL_STYLES:
            return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
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
        result.append(" (valueExpression: "); //$NON-NLS-1$
        result.append(this.valueExpression);
        result.append(", displayExpression: "); //$NON-NLS-1$
        result.append(this.displayExpression);
        result.append(", isDeletableExpression: "); //$NON-NLS-1$
        result.append(this.isDeletableExpression);
        result.append(')');
        return result.toString();
    }

} // ListDescriptionImpl
