/**
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view.impl;

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
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.GroupDisplayMode;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.WidgetDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Group Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.GroupDescriptionImpl#getToolbarActions <em>Toolbar
 * Actions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.GroupDescriptionImpl#getWidgets <em>Widgets</em>}</li>
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
     * @see #getWidgets()
     * @generated
     * @ordered
     */
    protected EList<WidgetDescription> widgets;

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
        return ViewPackage.Literals.GROUP_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.GROUP_DESCRIPTION__NAME, oldName, this.name));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.GROUP_DESCRIPTION__DISPLAY_MODE, oldDisplayMode, this.displayMode));
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
                    new ENotificationImpl(this, Notification.SET, ViewPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ButtonDescription> getToolbarActions() {
        if (this.toolbarActions == null) {
            this.toolbarActions = new EObjectContainmentEList<>(ButtonDescription.class, this, ViewPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS);
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
            this.widgets = new EObjectContainmentEList<>(WidgetDescription.class, this, ViewPackage.GROUP_DESCRIPTION__WIDGETS);
        }
        return this.widgets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ViewPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
            return ((InternalEList<?>) this.getToolbarActions()).basicRemove(otherEnd, msgs);
        case ViewPackage.GROUP_DESCRIPTION__WIDGETS:
            return ((InternalEList<?>) this.getWidgets()).basicRemove(otherEnd, msgs);
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
        case ViewPackage.GROUP_DESCRIPTION__NAME:
            return this.getName();
        case ViewPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
            return this.getLabelExpression();
        case ViewPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
            return this.getDisplayMode();
        case ViewPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            return this.getSemanticCandidatesExpression();
        case ViewPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
            return this.getToolbarActions();
        case ViewPackage.GROUP_DESCRIPTION__WIDGETS:
            return this.getWidgets();
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
        case ViewPackage.GROUP_DESCRIPTION__NAME:
            this.setName((String) newValue);
            return;
        case ViewPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
            this.setLabelExpression((String) newValue);
            return;
        case ViewPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
            this.setDisplayMode((GroupDisplayMode) newValue);
            return;
        case ViewPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            this.setSemanticCandidatesExpression((String) newValue);
            return;
        case ViewPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
            this.getToolbarActions().clear();
            this.getToolbarActions().addAll((Collection<? extends ButtonDescription>) newValue);
            return;
        case ViewPackage.GROUP_DESCRIPTION__WIDGETS:
            this.getWidgets().clear();
            this.getWidgets().addAll((Collection<? extends WidgetDescription>) newValue);
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
        case ViewPackage.GROUP_DESCRIPTION__NAME:
            this.setName(NAME_EDEFAULT);
            return;
        case ViewPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
            this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
            this.setDisplayMode(DISPLAY_MODE_EDEFAULT);
            return;
        case ViewPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
            this.getToolbarActions().clear();
            return;
        case ViewPackage.GROUP_DESCRIPTION__WIDGETS:
            this.getWidgets().clear();
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
        case ViewPackage.GROUP_DESCRIPTION__NAME:
            return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
        case ViewPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
            return LABEL_EXPRESSION_EDEFAULT == null ? this.labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(this.labelExpression);
        case ViewPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
            return this.displayMode != DISPLAY_MODE_EDEFAULT;
        case ViewPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
        case ViewPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
            return this.toolbarActions != null && !this.toolbarActions.isEmpty();
        case ViewPackage.GROUP_DESCRIPTION__WIDGETS:
            return this.widgets != null && !this.widgets.isEmpty();
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
