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
import org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle;
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Group Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl#getDisplayMode <em>Display
 * Mode</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl#getToolbarActions <em>Toolbar
 * Actions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl#getWidgets <em>Widgets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupDescriptionImpl extends MinimalEObjectImpl.Container implements GroupDescription {
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
	 * The default value of the '{@link #getDisplayMode() <em>Display Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDisplayMode()
	 * @generated
	 * @ordered
	 */
    protected static final GroupDisplayMode DISPLAY_MODE_EDEFAULT = GroupDisplayMode.LIST;

    /**
	 * The cached value of the '{@link #getDisplayMode() <em>Display Mode</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDisplayMode()
	 * @generated
	 * @ordered
	 */
    protected GroupDisplayMode displayMode = DISPLAY_MODE_EDEFAULT;

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
     * The cached value of the '{@link #getToolbarActions() <em>Toolbar Actions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getToolbarActions()
     * @generated
     * @ordered
     */
    protected EList<ButtonDescription> toolbarActions;

    /**
     * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildren()
     * @generated
     * @ordered
     */
    protected EList<FormElementDescription> children;

    /**
     * The cached value of the '{@link #getBorderStyle() <em>Border Style</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderStyle()
     */
    protected ContainerBorderStyle borderStyle;

    /**
	 * The cached value of the '{@link #getConditionalBorderStyles() <em>Conditional Border Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalBorderStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalContainerBorderStyle> conditionalBorderStyles;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected GroupDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.GROUP_DESCRIPTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public GroupDisplayMode getDisplayMode() {
		return displayMode;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDisplayMode(GroupDisplayMode newDisplayMode) {
		GroupDisplayMode oldDisplayMode = displayMode;
		displayMode = newDisplayMode == null ? DISPLAY_MODE_EDEFAULT : newDisplayMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE, oldDisplayMode, displayMode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, semanticCandidatesExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ButtonDescription> getToolbarActions() {
		if (toolbarActions == null)
		{
			toolbarActions = new EObjectContainmentEList<ButtonDescription>(ButtonDescription.class, this, FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS);
		}
		return toolbarActions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<FormElementDescription> getChildren() {
		if (children == null)
		{
			children = new EObjectContainmentEList<FormElementDescription>(FormElementDescription.class, this, FormPackage.GROUP_DESCRIPTION__CHILDREN);
		}
		return children;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ContainerBorderStyle getBorderStyle() {
		return borderStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderStyle(ContainerBorderStyle newBorderStyle) {
		if (newBorderStyle != borderStyle)
		{
			NotificationChain msgs = null;
			if (borderStyle != null)
				msgs = ((InternalEObject)borderStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, null, msgs);
			if (newBorderStyle != null)
				msgs = ((InternalEObject)newBorderStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, null, msgs);
			msgs = basicSetBorderStyle(newBorderStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, newBorderStyle, newBorderStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetBorderStyle(ContainerBorderStyle newBorderStyle, NotificationChain msgs) {
		ContainerBorderStyle oldBorderStyle = borderStyle;
		borderStyle = newBorderStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, oldBorderStyle, newBorderStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalContainerBorderStyle> getConditionalBorderStyles() {
		if (conditionalBorderStyles == null)
		{
			conditionalBorderStyles = new EObjectContainmentEList<ConditionalContainerBorderStyle>(ConditionalContainerBorderStyle.class, this, FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES);
		}
		return conditionalBorderStyles;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
				return ((InternalEList<?>)getToolbarActions()).basicRemove(otherEnd, msgs);
			case FormPackage.GROUP_DESCRIPTION__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
				return basicSetBorderStyle(null, msgs);
			case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				return ((InternalEList<?>)getConditionalBorderStyles()).basicRemove(otherEnd, msgs);
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
			case FormPackage.GROUP_DESCRIPTION__NAME:
				return getName();
			case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
				return getLabelExpression();
			case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
				return getDisplayMode();
			case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return getSemanticCandidatesExpression();
			case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
				return getToolbarActions();
			case FormPackage.GROUP_DESCRIPTION__CHILDREN:
				return getChildren();
			case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
				return getBorderStyle();
			case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				return getConditionalBorderStyles();
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
			case FormPackage.GROUP_DESCRIPTION__NAME:
				setName((String)newValue);
				return;
			case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
				setDisplayMode((GroupDisplayMode)newValue);
				return;
			case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression((String)newValue);
				return;
			case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
				getToolbarActions().clear();
				getToolbarActions().addAll((Collection<? extends ButtonDescription>)newValue);
				return;
			case FormPackage.GROUP_DESCRIPTION__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends FormElementDescription>)newValue);
				return;
			case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
				setBorderStyle((ContainerBorderStyle)newValue);
				return;
			case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				getConditionalBorderStyles().clear();
				getConditionalBorderStyles().addAll((Collection<? extends ConditionalContainerBorderStyle>)newValue);
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
			case FormPackage.GROUP_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
				setDisplayMode(DISPLAY_MODE_EDEFAULT);
				return;
			case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
				getToolbarActions().clear();
				return;
			case FormPackage.GROUP_DESCRIPTION__CHILDREN:
				getChildren().clear();
				return;
			case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
				setBorderStyle((ContainerBorderStyle)null);
				return;
			case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				getConditionalBorderStyles().clear();
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
			case FormPackage.GROUP_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
				return displayMode != DISPLAY_MODE_EDEFAULT;
			case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
				return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(semanticCandidatesExpression);
			case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
				return toolbarActions != null && !toolbarActions.isEmpty();
			case FormPackage.GROUP_DESCRIPTION__CHILDREN:
				return children != null && !children.isEmpty();
			case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
				return borderStyle != null;
			case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
				return conditionalBorderStyles != null && !conditionalBorderStyles.isEmpty();
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
		result.append(", displayMode: ");
		result.append(displayMode);
		result.append(", semanticCandidatesExpression: ");
		result.append(semanticCandidatesExpression);
		result.append(')');
		return result.toString();
	}

} // GroupDescriptionImpl
