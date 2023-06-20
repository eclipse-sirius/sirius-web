/**
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.diagram.CreateView;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.impl.OperationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Create View</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getParentViewExpression <em>Parent View
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getElementDescription <em>Element
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getSemanticElementExpression <em>Semantic
 * Element Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getVariableName <em>Variable
 * Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getContainmentKind <em>Containment
 * Kind</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateViewImpl extends OperationImpl implements CreateView {
    /**
     * The default value of the '{@link #getParentViewExpression() <em>Parent View Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getParentViewExpression()
     * @generated
     * @ordered
     */
    protected static final String PARENT_VIEW_EXPRESSION_EDEFAULT = "aql:selectedNode";

    /**
     * The cached value of the '{@link #getParentViewExpression() <em>Parent View Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getParentViewExpression()
     * @generated
     * @ordered
     */
    protected String parentViewExpression = PARENT_VIEW_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getElementDescription() <em>Element Description</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElementDescription()
     * @generated
     * @ordered
     */
    protected DiagramElementDescription elementDescription;

    /**
     * The default value of the '{@link #getSemanticElementExpression() <em>Semantic Element Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSemanticElementExpression()
     * @generated
     * @ordered
     */
    protected static final String SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getSemanticElementExpression() <em>Semantic Element Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSemanticElementExpression()
     * @generated
     * @ordered
     */
    protected String semanticElementExpression = SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getVariableName()
     * @generated
     * @ordered
     */
    protected static final String VARIABLE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getVariableName()
     * @generated
     * @ordered
     */
    protected String variableName = VARIABLE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getContainmentKind() <em>Containment Kind</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getContainmentKind()
     * @generated
     * @ordered
     */
    protected static final NodeContainmentKind CONTAINMENT_KIND_EDEFAULT = NodeContainmentKind.CHILD_NODE;

    /**
     * The cached value of the '{@link #getContainmentKind() <em>Containment Kind</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getContainmentKind()
     * @generated
     * @ordered
     */
    protected NodeContainmentKind containmentKind = CONTAINMENT_KIND_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CreateViewImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.CREATE_VIEW;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getParentViewExpression() {
        return this.parentViewExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParentViewExpression(String newParentViewExpression) {
        String oldParentViewExpression = this.parentViewExpression;
        this.parentViewExpression = newParentViewExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION, oldParentViewExpression, this.parentViewExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramElementDescription getElementDescription() {
        if (this.elementDescription != null && this.elementDescription.eIsProxy()) {
            InternalEObject oldElementDescription = (InternalEObject) this.elementDescription;
            this.elementDescription = (DiagramElementDescription) this.eResolveProxy(oldElementDescription);
            if (this.elementDescription != oldElementDescription) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION, oldElementDescription, this.elementDescription));
            }
        }
        return this.elementDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DiagramElementDescription basicGetElementDescription() {
        return this.elementDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setElementDescription(DiagramElementDescription newElementDescription) {
        DiagramElementDescription oldElementDescription = this.elementDescription;
        this.elementDescription = newElementDescription;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION, oldElementDescription, this.elementDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSemanticElementExpression() {
        return this.semanticElementExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSemanticElementExpression(String newSemanticElementExpression) {
        String oldSemanticElementExpression = this.semanticElementExpression;
        this.semanticElementExpression = newSemanticElementExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION, oldSemanticElementExpression, this.semanticElementExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getVariableName() {
        return this.variableName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setVariableName(String newVariableName) {
        String oldVariableName = this.variableName;
        this.variableName = newVariableName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__VARIABLE_NAME, oldVariableName, this.variableName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeContainmentKind getContainmentKind() {
        return this.containmentKind;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setContainmentKind(NodeContainmentKind newContainmentKind) {
        NodeContainmentKind oldContainmentKind = this.containmentKind;
        this.containmentKind = newContainmentKind == null ? CONTAINMENT_KIND_EDEFAULT : newContainmentKind;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND, oldContainmentKind, this.containmentKind));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
                return this.getParentViewExpression();
            case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
                if (resolve)
                    return this.getElementDescription();
                return this.basicGetElementDescription();
            case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
                return this.getSemanticElementExpression();
            case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
                return this.getVariableName();
            case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
                return this.getContainmentKind();
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
            case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
                this.setParentViewExpression((String) newValue);
                return;
            case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
                this.setElementDescription((DiagramElementDescription) newValue);
                return;
            case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
                this.setSemanticElementExpression((String) newValue);
                return;
            case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
                this.setVariableName((String) newValue);
                return;
            case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
                this.setContainmentKind((NodeContainmentKind) newValue);
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
            case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
                this.setParentViewExpression(PARENT_VIEW_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
                this.setElementDescription((DiagramElementDescription) null);
                return;
            case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
                this.setSemanticElementExpression(SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
                this.setVariableName(VARIABLE_NAME_EDEFAULT);
                return;
            case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
                this.setContainmentKind(CONTAINMENT_KIND_EDEFAULT);
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
            case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
                return PARENT_VIEW_EXPRESSION_EDEFAULT == null ? this.parentViewExpression != null : !PARENT_VIEW_EXPRESSION_EDEFAULT.equals(this.parentViewExpression);
            case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
                return this.elementDescription != null;
            case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
                return SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT == null ? this.semanticElementExpression != null : !SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT.equals(this.semanticElementExpression);
            case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
                return VARIABLE_NAME_EDEFAULT == null ? this.variableName != null : !VARIABLE_NAME_EDEFAULT.equals(this.variableName);
            case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
                return this.containmentKind != CONTAINMENT_KIND_EDEFAULT;
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
        result.append(" (parentViewExpression: ");
        result.append(this.parentViewExpression);
        result.append(", semanticElementExpression: ");
        result.append(this.semanticElementExpression);
        result.append(", variableName: ");
        result.append(this.variableName);
        result.append(", containmentKind: ");
        result.append(this.containmentKind);
        result.append(')');
        return result.toString();
    }

} // CreateViewImpl
