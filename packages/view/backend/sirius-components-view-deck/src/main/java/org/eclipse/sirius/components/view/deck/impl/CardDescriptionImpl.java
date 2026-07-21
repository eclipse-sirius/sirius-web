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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.DeleteCardTool;
import org.eclipse.sirius.components.view.deck.EditCardTool;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Card Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CardDescriptionImpl extends DeckElementDescriptionImpl implements CardDescription {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "New Card Description";

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
     * The default value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EXPRESSION_EDEFAULT = "aql:self";

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
	 * The cached value of the '{@link #getEditTool() <em>Edit Tool</em>}' containment reference.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getEditTool()
	 * @generated
	 * @ordered
	 */
    protected EditCardTool editTool;

    /**
     * The cached value of the '{@link #getDeleteTool() <em>Delete Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeleteTool()
     * @generated
     * @ordered
     */
    protected DeleteCardTool deleteTool;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected CardDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DeckPackage.Literals.CARD_DESCRIPTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__DOMAIN_TYPE, oldDomainType, domainType));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDescriptionExpression() {
		return descriptionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDescriptionExpression(String newDescriptionExpression) {
		String oldDescriptionExpression = descriptionExpression;
		descriptionExpression = newDescriptionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION, oldDescriptionExpression, descriptionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EditCardTool getEditTool() {
		return editTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetEditTool(EditCardTool newEditTool, NotificationChain msgs) {
		EditCardTool oldEditTool = editTool;
		editTool = newEditTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, oldEditTool, newEditTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEditTool(EditCardTool newEditTool) {
		if (newEditTool != editTool)
		{
			NotificationChain msgs = null;
			if (editTool != null)
				msgs = ((InternalEObject)editTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, null, msgs);
			if (newEditTool != null)
				msgs = ((InternalEObject)newEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, null, msgs);
			msgs = basicSetEditTool(newEditTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, newEditTool, newEditTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DeleteCardTool getDeleteTool() {
		return deleteTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDeleteTool(DeleteCardTool newDeleteTool, NotificationChain msgs) {
		DeleteCardTool oldDeleteTool = deleteTool;
		deleteTool = newDeleteTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, oldDeleteTool, newDeleteTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDeleteTool(DeleteCardTool newDeleteTool) {
		if (newDeleteTool != deleteTool)
		{
			NotificationChain msgs = null;
			if (deleteTool != null)
				msgs = ((InternalEObject)deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, null, msgs);
			if (newDeleteTool != null)
				msgs = ((InternalEObject)newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, null, msgs);
			msgs = basicSetDeleteTool(newDeleteTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, newDeleteTool, newDeleteTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
				return basicSetEditTool(null, msgs);
			case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
				return basicSetDeleteTool(null, msgs);
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
			case DeckPackage.CARD_DESCRIPTION__NAME:
				return getName();
			case DeckPackage.CARD_DESCRIPTION__DOMAIN_TYPE:
				return getDomainType();
			case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
				return getDescriptionExpression();
			case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
				return getEditTool();
			case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
				return getDeleteTool();
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
			case DeckPackage.CARD_DESCRIPTION__NAME:
				setName((String)newValue);
				return;
			case DeckPackage.CARD_DESCRIPTION__DOMAIN_TYPE:
				setDomainType((String)newValue);
				return;
			case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
				setDescriptionExpression((String)newValue);
				return;
			case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
				setEditTool((EditCardTool)newValue);
				return;
			case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
				setDeleteTool((DeleteCardTool)newValue);
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
			case DeckPackage.CARD_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DeckPackage.CARD_DESCRIPTION__DOMAIN_TYPE:
				setDomainType(DOMAIN_TYPE_EDEFAULT);
				return;
			case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
				setDescriptionExpression(DESCRIPTION_EXPRESSION_EDEFAULT);
				return;
			case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
				setEditTool((EditCardTool)null);
				return;
			case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
				setDeleteTool((DeleteCardTool)null);
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
			case DeckPackage.CARD_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DeckPackage.CARD_DESCRIPTION__DOMAIN_TYPE:
				return DOMAIN_TYPE_EDEFAULT == null ? domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(domainType);
			case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
				return DESCRIPTION_EXPRESSION_EDEFAULT == null ? descriptionExpression != null : !DESCRIPTION_EXPRESSION_EDEFAULT.equals(descriptionExpression);
			case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
				return editTool != null;
			case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
				return deleteTool != null;
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
		result.append(", descriptionExpression: ");
		result.append(descriptionExpression);
		result.append(')');
		return result.toString();
	}

} // CardDescriptionImpl
