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
package org.eclipse.sirius.web.view.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.web.view.Conditional;
import org.eclipse.sirius.web.view.ConditionalEdgeStyle;
import org.eclipse.sirius.web.view.ConditionalNodeStyle;
import org.eclipse.sirius.web.view.DiagramDescription;
import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.EdgeStyle;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.NodeStyle;
import org.eclipse.sirius.web.view.RepresentationDescription;
import org.eclipse.sirius.web.view.Style;
import org.eclipse.sirius.web.view.View;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.web.view.ViewPackage
 * @generated
 */
public class ViewSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static ViewPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ViewSwitch() {
        if (modelPackage == null) {
            modelPackage = ViewPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *            the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
     * result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case ViewPackage.VIEW: {
            View view = (View) theEObject;
            T result = this.caseView(view);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.REPRESENTATION_DESCRIPTION: {
            RepresentationDescription representationDescription = (RepresentationDescription) theEObject;
            T result = this.caseRepresentationDescription(representationDescription);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.DIAGRAM_DESCRIPTION: {
            DiagramDescription diagramDescription = (DiagramDescription) theEObject;
            T result = this.caseDiagramDescription(diagramDescription);
            if (result == null)
                result = this.caseRepresentationDescription(diagramDescription);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION: {
            DiagramElementDescription diagramElementDescription = (DiagramElementDescription) theEObject;
            T result = this.caseDiagramElementDescription(diagramElementDescription);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.NODE_DESCRIPTION: {
            NodeDescription nodeDescription = (NodeDescription) theEObject;
            T result = this.caseNodeDescription(nodeDescription);
            if (result == null)
                result = this.caseDiagramElementDescription(nodeDescription);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.EDGE_DESCRIPTION: {
            EdgeDescription edgeDescription = (EdgeDescription) theEObject;
            T result = this.caseEdgeDescription(edgeDescription);
            if (result == null)
                result = this.caseDiagramElementDescription(edgeDescription);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.STYLE: {
            Style style = (Style) theEObject;
            T result = this.caseStyle(style);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.NODE_STYLE: {
            NodeStyle nodeStyle = (NodeStyle) theEObject;
            T result = this.caseNodeStyle(nodeStyle);
            if (result == null)
                result = this.caseStyle(nodeStyle);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.EDGE_STYLE: {
            EdgeStyle edgeStyle = (EdgeStyle) theEObject;
            T result = this.caseEdgeStyle(edgeStyle);
            if (result == null)
                result = this.caseStyle(edgeStyle);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.CONDITIONAL: {
            Conditional conditional = (Conditional) theEObject;
            T result = this.caseConditional(conditional);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.CONDITIONAL_NODE_STYLE: {
            ConditionalNodeStyle conditionalNodeStyle = (ConditionalNodeStyle) theEObject;
            T result = this.caseConditionalNodeStyle(conditionalNodeStyle);
            if (result == null)
                result = this.caseConditional(conditionalNodeStyle);
            if (result == null)
                result = this.caseNodeStyle(conditionalNodeStyle);
            if (result == null)
                result = this.caseStyle(conditionalNodeStyle);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case ViewPackage.CONDITIONAL_EDGE_STYLE: {
            ConditionalEdgeStyle conditionalEdgeStyle = (ConditionalEdgeStyle) theEObject;
            T result = this.caseConditionalEdgeStyle(conditionalEdgeStyle);
            if (result == null)
                result = this.caseConditional(conditionalEdgeStyle);
            if (result == null)
                result = this.caseEdgeStyle(conditionalEdgeStyle);
            if (result == null)
                result = this.caseStyle(conditionalEdgeStyle);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        default:
            return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>View</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>View</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseView(View object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Representation Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Representation Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepresentationDescription(RepresentationDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Diagram Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Diagram Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiagramDescription(DiagramDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Diagram Element Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Diagram Element Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiagramElementDescription(DiagramElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeDescription(NodeDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgeDescription(EdgeDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Style</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStyle(Style object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeStyle(NodeStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgeStyle(EdgeStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditional(Conditional object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Node Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Node Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalNodeStyle(ConditionalNodeStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Edge Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Edge Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalEdgeStyle(ConditionalEdgeStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // ViewSwitch
