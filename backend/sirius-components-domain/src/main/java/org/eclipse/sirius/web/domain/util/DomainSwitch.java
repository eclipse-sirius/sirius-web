/**
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.domain.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.web.domain.Attribute;
import org.eclipse.sirius.web.domain.Domain;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.domain.Entity;
import org.eclipse.sirius.web.domain.Feature;
import org.eclipse.sirius.web.domain.NamedElement;
import org.eclipse.sirius.web.domain.Relation;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.web.domain.DomainPackage
 * @generated
 */
public class DomainSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static DomainPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DomainSwitch() {
        if (modelPackage == null) {
            modelPackage = DomainPackage.eINSTANCE;
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
        case DomainPackage.NAMED_ELEMENT: {
            NamedElement namedElement = (NamedElement) theEObject;
            T result = this.caseNamedElement(namedElement);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case DomainPackage.DOMAIN: {
            Domain domain = (Domain) theEObject;
            T result = this.caseDomain(domain);
            if (result == null)
                result = this.caseNamedElement(domain);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case DomainPackage.ENTITY: {
            Entity entity = (Entity) theEObject;
            T result = this.caseEntity(entity);
            if (result == null)
                result = this.caseNamedElement(entity);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case DomainPackage.FEATURE: {
            Feature feature = (Feature) theEObject;
            T result = this.caseFeature(feature);
            if (result == null)
                result = this.caseNamedElement(feature);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case DomainPackage.ATTRIBUTE: {
            Attribute attribute = (Attribute) theEObject;
            T result = this.caseAttribute(attribute);
            if (result == null)
                result = this.caseFeature(attribute);
            if (result == null)
                result = this.caseNamedElement(attribute);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        case DomainPackage.RELATION: {
            Relation relation = (Relation) theEObject;
            T result = this.caseRelation(relation);
            if (result == null)
                result = this.caseFeature(relation);
            if (result == null)
                result = this.caseNamedElement(relation);
            if (result == null)
                result = this.defaultCase(theEObject);
            return result;
        }
        default:
            return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Domain</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Domain</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDomain(Domain object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Entity</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntity(Entity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Feature</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Feature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFeature(Feature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttribute(Attribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Relation</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Relation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRelation(Relation object) {
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

} // DomainSwitch
