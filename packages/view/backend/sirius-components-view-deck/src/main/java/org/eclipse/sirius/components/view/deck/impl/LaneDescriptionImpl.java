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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
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
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl#getOwnedCardDescriptions <em>Owned Card
 * Descriptions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LaneDescriptionImpl extends MinimalEObjectImpl.Container implements LaneDescription {
    /**
     * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSemanticCandidatesExpression()
     * @generated
     * @ordered
     */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSemanticCandidatesExpression()
     * @generated
     * @ordered
     */
    protected String semanticCandidatesExpression = SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTitleExpression() <em>Title Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getTitleExpression()
     * @generated
     * @ordered
     */
    protected static final String TITLE_EXPRESSION_EDEFAULT = "aql:self";

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
     * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EXPRESSION_EDEFAULT = "aql:self";

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
    public String getSemanticCandidatesExpression() {
        return this.semanticCandidatesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSemanticCandidatesExpression(String newSemanticCandidatesExpression) {
        String oldSemanticCandidatesExpression = this.semanticCandidatesExpression;
        this.semanticCandidatesExpression = newSemanticCandidatesExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__TITLE_EXPRESSION, oldTitleExpression, this.titleExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.LANE_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
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
            case DeckPackage.LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case DeckPackage.LANE_DESCRIPTION__TITLE_EXPRESSION:
                return this.getTitleExpression();
            case DeckPackage.LANE_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
                return this.getOwnedCardDescriptions();
            case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
                return this.getEditTool();
            case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
                return this.getCreateTool();
            case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
                return this.getCardDropTool();
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
            case DeckPackage.LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__TITLE_EXPRESSION:
                this.setTitleExpression((String) newValue);
                return;
            case DeckPackage.LANE_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
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
            case DeckPackage.LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case DeckPackage.LANE_DESCRIPTION__TITLE_EXPRESSION:
                this.setTitleExpression(TITLE_EXPRESSION_EDEFAULT);
                return;
            case DeckPackage.LANE_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
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
            case DeckPackage.LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
            case DeckPackage.LANE_DESCRIPTION__TITLE_EXPRESSION:
                return TITLE_EXPRESSION_EDEFAULT == null ? this.titleExpression != null : !TITLE_EXPRESSION_EDEFAULT.equals(this.titleExpression);
            case DeckPackage.LANE_DESCRIPTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case DeckPackage.LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS:
                return this.ownedCardDescriptions != null && !this.ownedCardDescriptions.isEmpty();
            case DeckPackage.LANE_DESCRIPTION__EDIT_TOOL:
                return this.editTool != null;
            case DeckPackage.LANE_DESCRIPTION__CREATE_TOOL:
                return this.createTool != null;
            case DeckPackage.LANE_DESCRIPTION__CARD_DROP_TOOL:
                return this.cardDropTool != null;
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
        result.append(" (semanticCandidatesExpression: ");
        result.append(this.semanticCandidatesExpression);
        result.append(", titleExpression: ");
        result.append(this.titleExpression);
        result.append(", labelExpression: ");
        result.append(this.labelExpression);
        result.append(')');
        return result.toString();
    }

} // LaneDescriptionImpl
