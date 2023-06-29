/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.WidgetDescription;

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
     * The default value of the '{@link #getDisplayMode() <em>Display Mode</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getDisplayMode()
     * @generated
     * @ordered
     */
    protected static final GroupDisplayMode DISPLAY_MODE_EDEFAULT = GroupDisplayMode.LIST;

    /**
     * The cached value of the '{@link #getDisplayMode() <em>Display Mode</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDisplayMode()
     * @generated
     * @ordered
     */
    protected GroupDisplayMode displayMode = DISPLAY_MODE_EDEFAULT;

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
     * The cached value of the '{@link #getToolbarActions() <em>Toolbar Actions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getToolbarActions()
     * @generated
     * @ordered
     */
    protected EList<ButtonDescription> toolbarActions;

    /**
     * The cached value of the '{@link #getWidgets() <em>Widgets</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getWidgets()
     */
    protected EList<WidgetDescription> widgets;

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
     * The cached value of the '{@link #getConditionalBorderStyles() <em>Conditional Border Styles</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getConditionalBorderStyles()
     */
    protected EList<ConditionalContainerBorderStyle> conditionalBorderStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected GroupDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.GROUP_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__NAME, oldName, this.name));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public GroupDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDisplayMode(GroupDisplayMode newDisplayMode) {
        GroupDisplayMode oldDisplayMode = this.displayMode;
        this.displayMode = newDisplayMode == null ? DISPLAY_MODE_EDEFAULT : newDisplayMode;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE, oldDisplayMode, this.displayMode));
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
                    new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ButtonDescription> getToolbarActions() {
        if (this.toolbarActions == null) {
            this.toolbarActions = new EObjectContainmentEList<>(ButtonDescription.class, this, FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS);
        }
        return this.toolbarActions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<WidgetDescription> getWidgets() {
        if (this.widgets == null) {
            this.widgets = new EObjectContainmentEList<>(WidgetDescription.class, this, FormPackage.GROUP_DESCRIPTION__WIDGETS);
        }
        return this.widgets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ContainerBorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderStyle(ContainerBorderStyle newBorderStyle) {
        if (newBorderStyle != this.borderStyle) {
            NotificationChain msgs = null;
            if (this.borderStyle != null)
                msgs = ((InternalEObject) this.borderStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, null, msgs);
            if (newBorderStyle != null)
                msgs = ((InternalEObject) newBorderStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, null, msgs);
            msgs = this.basicSetBorderStyle(newBorderStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, newBorderStyle, newBorderStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBorderStyle(ContainerBorderStyle newBorderStyle, NotificationChain msgs) {
        ContainerBorderStyle oldBorderStyle = this.borderStyle;
        this.borderStyle = newBorderStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.GROUP_DESCRIPTION__BORDER_STYLE, oldBorderStyle, newBorderStyle);
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
    public EList<ConditionalContainerBorderStyle> getConditionalBorderStyles() {
        if (this.conditionalBorderStyles == null) {
            this.conditionalBorderStyles = new EObjectContainmentEList<>(ConditionalContainerBorderStyle.class, this,
                    FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES);
        }
        return this.conditionalBorderStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
                return ((InternalEList<?>) this.getToolbarActions()).basicRemove(otherEnd, msgs);
            case FormPackage.GROUP_DESCRIPTION__WIDGETS:
                return ((InternalEList<?>) this.getWidgets()).basicRemove(otherEnd, msgs);
            case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
                return this.basicSetBorderStyle(null, msgs);
            case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                return ((InternalEList<?>) this.getConditionalBorderStyles()).basicRemove(otherEnd, msgs);
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
            case FormPackage.GROUP_DESCRIPTION__NAME:
                return this.getName();
            case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
                return this.getDisplayMode();
            case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
                return this.getToolbarActions();
            case FormPackage.GROUP_DESCRIPTION__WIDGETS:
                return this.getWidgets();
            case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
                return this.getBorderStyle();
            case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                return this.getConditionalBorderStyles();
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
            case FormPackage.GROUP_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
                this.setDisplayMode((GroupDisplayMode) newValue);
                return;
            case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
                this.getToolbarActions().clear();
                this.getToolbarActions().addAll((Collection<? extends ButtonDescription>) newValue);
                return;
            case FormPackage.GROUP_DESCRIPTION__WIDGETS:
                this.getWidgets().clear();
                this.getWidgets().addAll((Collection<? extends WidgetDescription>) newValue);
                return;
            case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
                this.setBorderStyle((ContainerBorderStyle) newValue);
                return;
            case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                this.getConditionalBorderStyles().clear();
                this.getConditionalBorderStyles().addAll((Collection<? extends ConditionalContainerBorderStyle>) newValue);
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
            case FormPackage.GROUP_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
                this.setDisplayMode(DISPLAY_MODE_EDEFAULT);
                return;
            case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
                this.getToolbarActions().clear();
                return;
            case FormPackage.GROUP_DESCRIPTION__WIDGETS:
                this.getWidgets().clear();
                return;
            case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
                this.setBorderStyle(null);
                return;
            case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                this.getConditionalBorderStyles().clear();
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
            case FormPackage.GROUP_DESCRIPTION__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case FormPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
                return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
            case FormPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
                return this.displayMode != DISPLAY_MODE_EDEFAULT;
            case FormPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
            case FormPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
                return this.toolbarActions != null && !this.toolbarActions.isEmpty();
            case FormPackage.GROUP_DESCRIPTION__WIDGETS:
                return this.widgets != null && !this.widgets.isEmpty();
            case FormPackage.GROUP_DESCRIPTION__BORDER_STYLE:
                return this.borderStyle != null;
            case FormPackage.GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                return this.conditionalBorderStyles != null && !this.conditionalBorderStyles.isEmpty();
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
        result.append(", displayMode: ");
        result.append(this.displayMode);
        result.append(", semanticCandidatesExpression: ");
        result.append(this.semanticCandidatesExpression);
        result.append(')');
        return result.toString();
    }

} // GroupDescriptionImpl
