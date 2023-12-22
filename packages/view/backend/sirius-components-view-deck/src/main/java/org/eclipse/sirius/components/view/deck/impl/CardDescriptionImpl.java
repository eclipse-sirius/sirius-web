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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
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
public class CardDescriptionImpl extends MinimalEObjectImpl.Container implements CardDescription {
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
     * The cached value of the '{@link #getEditTool() <em>Edit Tool</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
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
     *
     * @generated
     */
    protected CardDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeckPackage.Literals.CARD_DESCRIPTION;
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
                    new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__TITLE_EXPRESSION, oldTitleExpression, this.titleExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION, oldDescriptionExpression, this.descriptionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EditCardTool getEditTool() {
        return this.editTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetEditTool(EditCardTool newEditTool, NotificationChain msgs) {
        EditCardTool oldEditTool = this.editTool;
        this.editTool = newEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, oldEditTool, newEditTool);
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
    public void setEditTool(EditCardTool newEditTool) {
        if (newEditTool != this.editTool) {
            NotificationChain msgs = null;
            if (this.editTool != null)
                msgs = ((InternalEObject) this.editTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, null, msgs);
            if (newEditTool != null)
                msgs = ((InternalEObject) newEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, null, msgs);
            msgs = this.basicSetEditTool(newEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__EDIT_TOOL, newEditTool, newEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteCardTool getDeleteTool() {
        return this.deleteTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDeleteTool(DeleteCardTool newDeleteTool, NotificationChain msgs) {
        DeleteCardTool oldDeleteTool = this.deleteTool;
        this.deleteTool = newDeleteTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, oldDeleteTool, newDeleteTool);
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
    public void setDeleteTool(DeleteCardTool newDeleteTool) {
        if (newDeleteTool != this.deleteTool) {
            NotificationChain msgs = null;
            if (this.deleteTool != null)
                msgs = ((InternalEObject) this.deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, null, msgs);
            if (newDeleteTool != null)
                msgs = ((InternalEObject) newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, null, msgs);
            msgs = this.basicSetDeleteTool(newDeleteTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CARD_DESCRIPTION__DELETE_TOOL, newDeleteTool, newDeleteTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
                return this.basicSetEditTool(null, msgs);
            case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
                return this.basicSetDeleteTool(null, msgs);
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
            case DeckPackage.CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case DeckPackage.CARD_DESCRIPTION__TITLE_EXPRESSION:
                return this.getTitleExpression();
            case DeckPackage.CARD_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return this.getDescriptionExpression();
            case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
                return this.getEditTool();
            case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
                return this.getDeleteTool();
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
            case DeckPackage.CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case DeckPackage.CARD_DESCRIPTION__TITLE_EXPRESSION:
                this.setTitleExpression((String) newValue);
                return;
            case DeckPackage.CARD_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression((String) newValue);
                return;
            case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
                this.setEditTool((EditCardTool) newValue);
                return;
            case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
                this.setDeleteTool((DeleteCardTool) newValue);
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
            case DeckPackage.CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case DeckPackage.CARD_DESCRIPTION__TITLE_EXPRESSION:
                this.setTitleExpression(TITLE_EXPRESSION_EDEFAULT);
                return;
            case DeckPackage.CARD_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression(DESCRIPTION_EXPRESSION_EDEFAULT);
                return;
            case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
                this.setEditTool((EditCardTool) null);
                return;
            case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
                this.setDeleteTool((DeleteCardTool) null);
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
            case DeckPackage.CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
            case DeckPackage.CARD_DESCRIPTION__TITLE_EXPRESSION:
                return TITLE_EXPRESSION_EDEFAULT == null ? this.titleExpression != null : !TITLE_EXPRESSION_EDEFAULT.equals(this.titleExpression);
            case DeckPackage.CARD_DESCRIPTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case DeckPackage.CARD_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return DESCRIPTION_EXPRESSION_EDEFAULT == null ? this.descriptionExpression != null : !DESCRIPTION_EXPRESSION_EDEFAULT.equals(this.descriptionExpression);
            case DeckPackage.CARD_DESCRIPTION__EDIT_TOOL:
                return this.editTool != null;
            case DeckPackage.CARD_DESCRIPTION__DELETE_TOOL:
                return this.deleteTool != null;
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
        result.append(", descriptionExpression: ");
        result.append(this.descriptionExpression);
        result.append(')');
        return result.toString();
    }

} // CardDescriptionImpl
