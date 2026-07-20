/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
	 * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
    protected static final String DOMAIN_TYPE_EDEFAULT = "";

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
	 * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = "aql:self";

    /**
	 * The cached value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
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
	 * @generated
	 */
    protected PageDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.PAGE_DESCRIPTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLabelExpression() {
		return labelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE, oldDomainType, domainType));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSemanticCandidatesExpression() {
		return semanticCandidatesExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSemanticCandidatesExpression(String newSemanticCandidatesExpression) {
		String oldSemanticCandidatesExpression = semanticCandidatesExpression;
		semanticCandidatesExpression = newSemanticCandidatesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, semanticCandidatesExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getPreconditionExpression() {
		return preconditionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPreconditionExpression(String newPreconditionExpression) {
		String oldPreconditionExpression = preconditionExpression;
		preconditionExpression = newPreconditionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION, oldPreconditionExpression, preconditionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<GroupDescription> getGroups() {
		if (groups == null)
		{
			groups = new EObjectContainmentEList<GroupDescription>(GroupDescription.class, this, FormPackage.PAGE_DESCRIPTION__GROUPS);
		}
		return groups;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ButtonDescription> getToolbarActions() {
		if (toolbarActions == null)
		{
			toolbarActions = new EObjectContainmentEList<ButtonDescription>(ButtonDescription.class, this, FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS);
		}
		return toolbarActions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.PAGE_DESCRIPTION__GROUPS:
				return ((InternalEList<?>)getGroups()).basicRemove(otherEnd, msgs);
			case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
				return ((InternalEList<?>)getToolbarActions()).basicRemove(otherEnd, msgs);
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
			case FormPackage.PAGE_DESCRIPTION__NAME:
				return getName();
			case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
				return getLabelExpression();
			case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
				return getDomainType();
			case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return getSemanticCandidatesExpression();
			case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
				return getPreconditionExpression();
			case FormPackage.PAGE_DESCRIPTION__GROUPS:
				return getGroups();
			case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
				return getToolbarActions();
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
			case FormPackage.PAGE_DESCRIPTION__NAME:
				setName((String)newValue);
				return;
			case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
				setDomainType((String)newValue);
				return;
			case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression((String)newValue);
				return;
			case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression((String)newValue);
				return;
			case FormPackage.PAGE_DESCRIPTION__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection<? extends GroupDescription>)newValue);
				return;
			case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
				getToolbarActions().clear();
				getToolbarActions().addAll((Collection<? extends ButtonDescription>)newValue);
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
			case FormPackage.PAGE_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
				setDomainType(DOMAIN_TYPE_EDEFAULT);
				return;
			case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
				setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.PAGE_DESCRIPTION__GROUPS:
				getGroups().clear();
				return;
			case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
				getToolbarActions().clear();
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
			case FormPackage.PAGE_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FormPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case FormPackage.PAGE_DESCRIPTION__DOMAIN_TYPE:
				return DOMAIN_TYPE_EDEFAULT == null ? domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(domainType);
			case FormPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(semanticCandidatesExpression);
			case FormPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
				return PRECONDITION_EXPRESSION_EDEFAULT == null ? preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(preconditionExpression);
			case FormPackage.PAGE_DESCRIPTION__GROUPS:
				return groups != null && !groups.isEmpty();
			case FormPackage.PAGE_DESCRIPTION__TOOLBAR_ACTIONS:
				return toolbarActions != null && !toolbarActions.isEmpty();
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
		result.append(", labelExpression: ");
		result.append(labelExpression);
		result.append(", domainType: ");
		result.append(domainType);
		result.append(", semanticCandidatesExpression: ");
		result.append(semanticCandidatesExpression);
		result.append(", preconditionExpression: ");
		result.append(preconditionExpression);
		result.append(')');
		return result.toString();
	}

} // PageDescriptionImpl
