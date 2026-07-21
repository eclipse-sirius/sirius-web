/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
	 * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
    protected static final String DOMAIN_TYPE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
    protected String domainType = DOMAIN_TYPE_EDEFAULT;

    /**
	 * The cached value of the '{@link #getOwnedCardDescriptions() <em>Owned Card Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOwnedCardDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<CardDescription> ownedCardDescriptions;

    /**
	 * The cached value of the '{@link #getEditTool() <em>Edit Tool</em>}' containment reference.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
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
	 * @see #getIsCollapsibleExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String IS_COLLAPSIBLE_EXPRESSION_EDEFAULT = "aql:true";

    /**
	 * The cached value of the '{@link #getIsCollapsibleExpression() <em>Is Collapsible Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsCollapsibleExpression()
	 * @generated
	 * @ordered
	 */
    protected String isCollapsibleExpression = IS_COLLAPSIBLE_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected LaneDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DeckPackage.Literals.LANE_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDomainType() {
		return domainType;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDomainType(String newDomainType) {
		String oldDomainType = domainType;
		domainType = newDomainType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE, oldDomainType, domainType));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<CardDescription> getOwnedCardDescriptions() {
		if (ownedCardDescriptions == null)
		{
			ownedCardDescriptions = new EObjectContainmentEList<CardDescription>(CardDescription.class, this, DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS);
		}
		return ownedCardDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EditLaneTool getEditTool() {
		return editTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetEditTool(EditLaneTool newEditTool, NotificationChain msgs) {
		EditLaneTool oldEditTool = editTool;
		editTool = newEditTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, oldEditTool, newEditTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEditTool(EditLaneTool newEditTool) {
		if (newEditTool != editTool)
		{
			NotificationChain msgs = null;
			if (editTool != null)
				msgs = ((InternalEObject)editTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, null, msgs);
			if (newEditTool != null)
				msgs = ((InternalEObject)newEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, null, msgs);
			msgs = basicSetEditTool(newEditTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__EDIT_TOOL, newEditTool, newEditTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CreateCardTool getCreateTool() {
		return createTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetCreateTool(CreateCardTool newCreateTool, NotificationChain msgs) {
		CreateCardTool oldCreateTool = createTool;
		createTool = newCreateTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, oldCreateTool, newCreateTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCreateTool(CreateCardTool newCreateTool) {
		if (newCreateTool != createTool)
		{
			NotificationChain msgs = null;
			if (createTool != null)
				msgs = ((InternalEObject)createTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, null, msgs);
			if (newCreateTool != null)
				msgs = ((InternalEObject)newCreateTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, null, msgs);
			msgs = basicSetCreateTool(newCreateTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CREATE_TOOL, newCreateTool, newCreateTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CardDropTool getCardDropTool() {
		return cardDropTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetCardDropTool(CardDropTool newCardDropTool, NotificationChain msgs) {
		CardDropTool oldCardDropTool = cardDropTool;
		cardDropTool = newCardDropTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, oldCardDropTool, newCardDropTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCardDropTool(CardDropTool newCardDropTool) {
		if (newCardDropTool != cardDropTool)
		{
			NotificationChain msgs = null;
			if (cardDropTool != null)
				msgs = ((InternalEObject)cardDropTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, null, msgs);
			if (newCardDropTool != null)
				msgs = ((InternalEObject)newCardDropTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, null, msgs);
			msgs = basicSetCardDropTool(newCardDropTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL, newCardDropTool, newCardDropTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsCollapsibleExpression() {
		return isCollapsibleExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsCollapsibleExpression(String newIsCollapsibleExpression) {
		String oldIsCollapsibleExpression = isCollapsibleExpression;
		isCollapsibleExpression = newIsCollapsibleExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION, oldIsCollapsibleExpression, isCollapsibleExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
				return ((InternalEList<?>)getOwnedCardDescriptions()).basicRemove(otherEnd, msgs);
			case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
				return basicSetEditTool(null, msgs);
			case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
				return basicSetCreateTool(null, msgs);
			case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
				return basicSetCardDropTool(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DeckPackage.LANE_DESCRIPTION__NAME:
				return getName();
			case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
				return getDomainType();
			case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
				return getOwnedCardDescriptions();
			case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
				return getEditTool();
			case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
				return getCreateTool();
			case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
				return getCardDropTool();
			case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
				return getIsCollapsibleExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case DeckPackage.LANE_DESCRIPTION__NAME:
				setName((String)newValue);
				return;
			case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
				setDomainType((String)newValue);
				return;
			case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
				getOwnedCardDescriptions().clear();
				getOwnedCardDescriptions().addAll((Collection<? extends CardDescription>)newValue);
				return;
			case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
				setEditTool((EditLaneTool)newValue);
				return;
			case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
				setCreateTool((CreateCardTool)newValue);
				return;
			case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
				setCardDropTool((CardDropTool)newValue);
				return;
			case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
				setIsCollapsibleExpression((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case DeckPackage.LANE_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
				setDomainType(DOMAIN_TYPE_EDEFAULT);
				return;
			case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
				getOwnedCardDescriptions().clear();
				return;
			case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
				setEditTool((EditLaneTool)null);
				return;
			case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
				setCreateTool((CreateCardTool)null);
				return;
			case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
				setCardDropTool((CardDropTool)null);
				return;
			case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
				setIsCollapsibleExpression(IS_COLLAPSIBLE_EXPRESSION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case DeckPackage.LANE_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DeckPackage.LANE_DESCRIPTION__DOMAIN_TYPE:
				return DOMAIN_TYPE_EDEFAULT == null ? domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(domainType);
			case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
				return ownedCardDescriptions != null && !ownedCardDescriptions.isEmpty();
			case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
				return editTool != null;
			case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
				return createTool != null;
			case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
				return cardDropTool != null;
			case DeckPackage.LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION:
				return IS_COLLAPSIBLE_EXPRESSION_EDEFAULT == null ? isCollapsibleExpression != null : !IS_COLLAPSIBLE_EXPRESSION_EDEFAULT.equals(isCollapsibleExpression);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", domainType: ");
		result.append(domainType);
		result.append(", isCollapsibleExpression: ");
		result.append(isCollapsibleExpression);
		result.append(')');
		return result.toString();
	}

} // LaneDescriptionImpl
