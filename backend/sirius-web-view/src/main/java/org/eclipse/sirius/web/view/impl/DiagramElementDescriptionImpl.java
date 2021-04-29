/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.Mode;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Diagram Element Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getDomainType <em>Domain Type</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getCreationMode <em>Creation
 * Mode</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DiagramElementDescriptionImpl extends MinimalEObjectImpl.Container implements DiagramElementDescription {
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
     * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSemanticCandidatesExpression()
     * @generated
     * @ordered
     */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = null;

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
     * The default value of the '{@link #getCreationMode() <em>Creation Mode</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getCreationMode()
     * @generated
     * @ordered
     */
    protected static final Mode CREATION_MODE_EDEFAULT = Mode.AUTO;

    /**
     * The cached value of the '{@link #getCreationMode() <em>Creation Mode</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getCreationMode()
     * @generated
     * @ordered
     */
    protected Mode creationMode = CREATION_MODE_EDEFAULT;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramElementDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE, oldDomainType, this.domainType));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression,
                    this.semanticCandidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Mode getCreationMode() {
        return this.creationMode;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCreationMode(Mode newCreationMode) {
        Mode oldCreationMode = this.creationMode;
        this.creationMode = newCreationMode == null ? CREATION_MODE_EDEFAULT : newCreationMode;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE, oldCreationMode, this.creationMode));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
            return this.getDomainType();
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            return this.getSemanticCandidatesExpression();
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
            return this.getCreationMode();
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
            return this.getLabelExpression();
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
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
            this.setDomainType((String) newValue);
            return;
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            this.setSemanticCandidatesExpression((String) newValue);
            return;
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
            this.setCreationMode((Mode) newValue);
            return;
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
            this.setLabelExpression((String) newValue);
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
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
            this.setDomainType(DOMAIN_TYPE_EDEFAULT);
            return;
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
            this.setCreationMode(CREATION_MODE_EDEFAULT);
            return;
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
            this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
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
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
            return DOMAIN_TYPE_EDEFAULT == null ? this.domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(this.domainType);
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
            return this.creationMode != CREATION_MODE_EDEFAULT;
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
            return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
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
        result.append(" (domainType: "); //$NON-NLS-1$
        result.append(this.domainType);
        result.append(", semanticCandidatesExpression: "); //$NON-NLS-1$
        result.append(this.semanticCandidatesExpression);
        result.append(", creationMode: "); //$NON-NLS-1$
        result.append(this.creationMode);
        result.append(", labelExpression: "); //$NON-NLS-1$
        result.append(this.labelExpression);
        result.append(')');
        return result.toString();
    }

} // DiagramElementDescriptionImpl
