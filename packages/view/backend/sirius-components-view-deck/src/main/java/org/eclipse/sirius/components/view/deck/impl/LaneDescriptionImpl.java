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
package org.eclipse.sirius.components.view.deck.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.CardDropTool;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.EditLaneTool;
import org.eclipse.sirius.components.view.deck.LaneDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Lane Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getOwnedCardDescriptions <em>Owned Card
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getEditTool <em>Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getCreateTool <em>Create Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getCardDropTool <em>Card Drop
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getIsCollapsibleExpression <em>Is
 * Collapsible Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LaneDescriptionImpl extends DeckElementDescriptionImpl implements LaneDescription {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "New Lane Description";

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
     * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDomainType()
     * @generated
     * @ordered
     */
    protected static final String DOMAIN_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDomainType()
     * @generated
     * @ordered
     */
    protected String domainType = DOMAIN_TYPE_EDEFAULT;

    /**
     * The cached value of the '{@link #getOwnedCardDescriptions() <em>Owned Card Descriptions</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOwnedCardDescriptions()
     * @generated
     * @ordered
     */
    protected EList<CardDescription> ownedCardDescriptions;

    /**
     * The cached value of the '{@link #getEditTool() <em>Edit Tool</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getEditTool()
     * @generated
     * @ordered
     */
    protected EditLaneTool editTool;

    /**
     * The cached value of the '{@link #getCreateTool() <em>Create Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCreateTool()
     * @generated
     * @ordered
     */
    protected CreateCardTool createTool;

    /**
     * The cached value of the '{@link #getCardDropTool() <em>Card Drop Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCardDropTool()
     * @generated
     * @ordered
     */
    protected CardDropTool cardDropTool;

    /**
     * The default value of the '{@link #getIsCollapsibleExpression() <em>Is Collapsible Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsCollapsibleExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_COLLAPSIBLE_EXPRESSION_EDEFAULT = "aql:true";

    /**
     * The cached value of the '{@link #getIsCollapsibleExpression() <em>Is Collapsible Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsCollapsibleExpression()
     * @generated
     * @ordered
     */
    protected String isCollapsibleExpression = IS_COLLAPSIBLE_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LaneDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeckPackage.Literals.LANE_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDomainType() {
        return this.domainType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDomainType(String newDomainType) {
        String oldDomainType = this.domainType;
        this.domainType = newDomainType;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE, oldDomainType, this.domainType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<CardDescription> getOwnedCardDescriptions() {
        if (this.ownedCardDescriptions == null) {
            this.ownedCardDescriptions = new EObjectContainmentEList<>(CardDescription.class, this, DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS);
        }
        return this.ownedCardDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EditLaneTool getEditTool() {
        return this.editTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetEditTool(EditLaneTool newEditTool, NotificationChain msgs) {
        EditLaneTool oldEditTool = this.editTool;
        this.editTool = newEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, oldEditTool, newEditTool);
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
    public void setEditTool(EditLaneTool newEditTool) {
        if (newEditTool != this.editTool) {
            NotificationChain msgs = null;
            if (this.editTool != null)
                msgs = ((InternalEObject) this.editTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, null, msgs);
            if (newEditTool != null)
                msgs = ((InternalEObject) newEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, null, msgs);
            msgs = this.basicSetEditTool(newEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, newEditTool, newEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateCardTool getCreateTool() {
        return this.createTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCreateTool(CreateCardTool newCreateTool, NotificationChain msgs) {
        CreateCardTool oldCreateTool = this.createTool;
        this.createTool = newCreateTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, oldCreateTool, newCreateTool);
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
    public void setCreateTool(CreateCardTool newCreateTool) {
        if (newCreateTool != this.createTool) {
            NotificationChain msgs = null;
            if (this.createTool != null)
                msgs = ((InternalEObject) this.createTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, null, msgs);
            if (newCreateTool != null)
                msgs = ((InternalEObject) newCreateTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, null, msgs);
            msgs = this.basicSetCreateTool(newCreateTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, newCreateTool, newCreateTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CardDropTool getCardDropTool() {
        return this.cardDropTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCardDropTool(CardDropTool newCardDropTool, NotificationChain msgs) {
        CardDropTool oldCardDropTool = this.cardDropTool;
        this.cardDropTool = newCardDropTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, oldCardDropTool, newCardDropTool);
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
    public void setCardDropTool(CardDropTool newCardDropTool) {
        if (newCardDropTool != this.cardDropTool) {
            NotificationChain msgs = null;
            if (this.cardDropTool != null)
                msgs = ((InternalEObject) this.cardDropTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, null, msgs);
            if (newCardDropTool != null)
                msgs = ((InternalEObject) newCardDropTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, null, msgs);
            msgs = this.basicSetCardDropTool(newCardDropTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, newCardDropTool, newCardDropTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsCollapsibleExpression() {
        return this.isCollapsibleExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsCollapsibleExpression(String newIsCollapsibleExpression) {
        String oldIsCollapsibleExpression = this.isCollapsibleExpression;
        this.isCollapsibleExpression = newIsCollapsibleExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION, oldIsCollapsibleExpression, this.isCollapsibleExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
                return ((InternalEList<?>) this.getOwnedCardDescriptions()).basicRemove(otherEnd, msgs);
            case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
                return this.basicSetEditTool(null, msgs);
            case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
                return this.basicSetCreateTool(null, msgs);
            case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
                return this.basicSetCardDropTool(null, msgs);
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
            case DeckPackage.LANE_DESCRIPTION__NAME:
                return this.getName();
            case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
                return this.getDomainType();
            case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
                return this.getOwnedCardDescriptions();
            case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
                return this.getEditTool();
            case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
                return this.getCreateTool();
            case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
                return this.getCardDropTool();
            case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
                return this.getIsCollapsibleExpression();
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
            case DeckPackage.LANE_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType((String) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
                this.getOwnedCardDescriptions().clear();
                this.getOwnedCardDescriptions().addAll((Collection<? extends CardDescription>) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
                this.setEditTool((EditLaneTool) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
                this.setCreateTool((CreateCardTool) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
                this.setCardDropTool((CardDropTool) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
                this.setIsCollapsibleExpression((String) newValue);
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
            case DeckPackage.LANE_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType(DOMAIN_TYPE_EDEFAULT);
                return;
            case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
                this.getOwnedCardDescriptions().clear();
                return;
            case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
                this.setEditTool((EditLaneTool) null);
                return;
            case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
                this.setCreateTool((CreateCardTool) null);
                return;
            case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
                this.setCardDropTool((CardDropTool) null);
                return;
            case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
                this.setIsCollapsibleExpression(IS_COLLAPSIBLE_EXPRESSION_EDEFAULT);
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
            case DeckPackage.LANE_DESCRIPTION__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
                return DOMAIN_TYPE_EDEFAULT == null ? this.domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(this.domainType);
            case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
                return this.ownedCardDescriptions != null && !this.ownedCardDescriptions.isEmpty();
            case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
                return this.editTool != null;
            case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
                return this.createTool != null;
            case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
                return this.cardDropTool != null;
            case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
                return IS_COLLAPSIBLE_EXPRESSION_EDEFAULT == null ? this.isCollapsibleExpression != null : !IS_COLLAPSIBLE_EXPRESSION_EDEFAULT.equals(this.isCollapsibleExpression);
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
        result.append(", domainType: ");
        result.append(this.domainType);
        result.append(", isCollapsibleExpression: ");
        result.append(this.isCollapsibleExpression);
        result.append(')');
        return result.toString();
    }

} // LaneDescriptionImpl
