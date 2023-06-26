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
package org.eclipse.sirius.components.view.form.impl;

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
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.PageDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Page Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl#getDomainType <em>Domain Type</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl#getGroups <em>Groups</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl#getToolbarActions <em>Toolbar
 * Actions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PageDescriptionImpl extends MinimalEObjectImpl.Container implements PageDescription {
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
     * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDomainType()
     * @generated
     * @ordered
     */
    protected static final String DOMAIN_TYPE_EDEFAULT = "";

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
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = "";

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
     * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getGroups()
     * @generated
     * @ordered
     */
    protected EList<GroupDescription> groups;

    /**
     * The cached value of the '{@link #getToolbarActions() <em>Toolbar Actions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getToolbarActions()
     * @generated
     * @ordered
     */
    protected EList<ButtonDescription> toolbarActions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PageDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.PAGE_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__NAME, oldName, this.name));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE, oldDomainType, this.domainType));
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
                    new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<GroupDescription> getGroups() {
        if (this.groups == null) {
            this.groups = new EObjectContainmentEList<>(GroupDescription.class, this, FormPackage.PAGE_DESCRIPTION__GROUPS);
        }
        return this.groups;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ButtonDescription> getToolbarActions() {
        if (this.toolbarActions == null) {
            this.toolbarActions = new EObjectContainmentEList<>(ButtonDescription.class, this, FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS);
        }
        return this.toolbarActions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case FormPackage.PAGE_DESCRIPTION__GROUPS:
                return ((InternalEList<?>) this.getGroups()).basicRemove(otherEnd, msgs);
            case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
                return ((InternalEList<?>) this.getToolbarActions()).basicRemove(otherEnd, msgs);
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
            case FormPackage.PAGE_DESCRIPTION__NAME:
                return this.getName();
            case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
                return this.getDomainType();
            case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
            case FormPackage.PAGE_DESCRIPTION__GROUPS:
                return this.getGroups();
            case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
                return this.getToolbarActions();
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
            case FormPackage.PAGE_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType((String) newValue);
                return;
            case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
                return;
            case FormPackage.PAGE_DESCRIPTION__GROUPS:
                this.getGroups().clear();
                this.getGroups().addAll((Collection<? extends GroupDescription>) newValue);
                return;
            case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
                this.getToolbarActions().clear();
                this.getToolbarActions().addAll((Collection<? extends ButtonDescription>) newValue);
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
            case FormPackage.PAGE_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType(DOMAIN_TYPE_EDEFAULT);
                return;
            case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.PAGE_DESCRIPTION__GROUPS:
                this.getGroups().clear();
                return;
            case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
                this.getToolbarActions().clear();
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
            case FormPackage.PAGE_DESCRIPTION__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
                return DOMAIN_TYPE_EDEFAULT == null ? this.domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(this.domainType);
            case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
            case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
            case FormPackage.PAGE_DESCRIPTION__GROUPS:
                return this.groups != null && !this.groups.isEmpty();
            case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
                return this.toolbarActions != null && !this.toolbarActions.isEmpty();
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
        result.append(", labelExpression: ");
        result.append(this.labelExpression);
        result.append(", domainType: ");
        result.append(this.domainType);
        result.append(", semanticCandidatesExpression: ");
        result.append(this.semanticCandidatesExpression);
        result.append(", preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(')');
        return result.toString();
    }

} // PageDescriptionImpl
