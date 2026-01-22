/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getParentViewExpression <em>Parent View Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getElementDescription <em>Element Description</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getSemanticElementExpression <em>Semantic Element Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getVariableName <em>Variable Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl#getContainmentKind <em>Containment Kind</em>}</li>
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
	 * The default value of the '{@link #getSemanticElementExpression() <em>Semantic Element Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSemanticElementExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT = "aql:self";

    /**
	 * The cached value of the '{@link #getSemanticElementExpression() <em>Semantic Element Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSemanticElementExpression()
	 * @generated
	 * @ordered
	 */
    protected String semanticElementExpression = SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVariableName()
	 * @generated
	 * @ordered
	 */
    protected static final String VARIABLE_NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVariableName()
	 * @generated
	 * @ordered
	 */
    protected String variableName = VARIABLE_NAME_EDEFAULT;

    /**
	 * The default value of the '{@link #getContainmentKind() <em>Containment Kind</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getContainmentKind()
	 * @generated
	 * @ordered
	 */
    protected static final NodeContainmentKind CONTAINMENT_KIND_EDEFAULT = NodeContainmentKind.CHILD_NODE;

    /**
	 * The cached value of the '{@link #getContainmentKind() <em>Containment Kind</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getContainmentKind()
	 * @generated
	 * @ordered
	 */
    protected NodeContainmentKind containmentKind = CONTAINMENT_KIND_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected CreateViewImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.CREATE_VIEW;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getParentViewExpression() {
		return parentViewExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setParentViewExpression(String newParentViewExpression) {
		String oldParentViewExpression = parentViewExpression;
		parentViewExpression = newParentViewExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION, oldParentViewExpression, parentViewExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DiagramElementDescription getElementDescription() {
		if (elementDescription != null && elementDescription.eIsProxy())
		{
			InternalEObject oldElementDescription = (InternalEObject)elementDescription;
			elementDescription = (DiagramElementDescription)eResolveProxy(oldElementDescription);
			if (elementDescription != oldElementDescription)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION, oldElementDescription, elementDescription));
			}
		}
		return elementDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public DiagramElementDescription basicGetElementDescription() {
		return elementDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setElementDescription(DiagramElementDescription newElementDescription) {
		DiagramElementDescription oldElementDescription = elementDescription;
		elementDescription = newElementDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION, oldElementDescription, elementDescription));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSemanticElementExpression() {
		return semanticElementExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSemanticElementExpression(String newSemanticElementExpression) {
		String oldSemanticElementExpression = semanticElementExpression;
		semanticElementExpression = newSemanticElementExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION, oldSemanticElementExpression, semanticElementExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getVariableName() {
		return variableName;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setVariableName(String newVariableName) {
		String oldVariableName = variableName;
		variableName = newVariableName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__VARIABLE_NAME, oldVariableName, variableName));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NodeContainmentKind getContainmentKind() {
		return containmentKind;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setContainmentKind(NodeContainmentKind newContainmentKind) {
		NodeContainmentKind oldContainmentKind = containmentKind;
		containmentKind = newContainmentKind == null ? CONTAINMENT_KIND_EDEFAULT : newContainmentKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND, oldContainmentKind, containmentKind));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
				return getParentViewExpression();
			case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
				if (resolve) return getElementDescription();
				return basicGetElementDescription();
			case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
				return getSemanticElementExpression();
			case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
				return getVariableName();
			case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
				return getContainmentKind();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
				setParentViewExpression((String)newValue);
				return;
			case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
				setElementDescription((DiagramElementDescription)newValue);
				return;
			case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
				setSemanticElementExpression((String)newValue);
				return;
			case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
				setVariableName((String)newValue);
				return;
			case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
				setContainmentKind((NodeContainmentKind)newValue);
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
			case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
				setParentViewExpression(PARENT_VIEW_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
				setElementDescription((DiagramElementDescription)null);
				return;
			case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
				setSemanticElementExpression(SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
				setVariableName(VARIABLE_NAME_EDEFAULT);
				return;
			case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
				setContainmentKind(CONTAINMENT_KIND_EDEFAULT);
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
			case DiagramPackage.CREATE_VIEW__PARENT_VIEW_EXPRESSION:
				return PARENT_VIEW_EXPRESSION_EDEFAULT == null ? parentViewExpression != null : !PARENT_VIEW_EXPRESSION_EDEFAULT.equals(parentViewExpression);
			case DiagramPackage.CREATE_VIEW__ELEMENT_DESCRIPTION:
				return elementDescription != null;
			case DiagramPackage.CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION:
				return SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT == null ? semanticElementExpression != null : !SEMANTIC_ELEMENT_EXPRESSION_EDEFAULT.equals(semanticElementExpression);
			case DiagramPackage.CREATE_VIEW__VARIABLE_NAME:
				return VARIABLE_NAME_EDEFAULT == null ? variableName != null : !VARIABLE_NAME_EDEFAULT.equals(variableName);
			case DiagramPackage.CREATE_VIEW__CONTAINMENT_KIND:
				return containmentKind != CONTAINMENT_KIND_EDEFAULT;
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
		result.append(" (parentViewExpression: ");
		result.append(parentViewExpression);
		result.append(", semanticElementExpression: ");
		result.append(semanticElementExpression);
		result.append(", variableName: ");
		result.append(variableName);
		result.append(", containmentKind: ");
		result.append(containmentKind);
		result.append(')');
		return result.toString();
	}

} // CreateViewImpl
