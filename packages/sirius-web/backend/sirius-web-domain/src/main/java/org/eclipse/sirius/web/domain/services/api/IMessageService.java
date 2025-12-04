/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.domain.services.api;

/**
 * Used to compute internationalized messages.
 *
 * @author sbegaudeau
 */
public interface IMessageService {

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String upperBoundaryReached(String newInstanceClass, String feature);

    String revealSelectedFadedElements();

    String collapseSelectedElements();

    String expandSelectedElements();

    String fadeSelectedElements();

    String hideSelectedElements();

    String invalidName();

    String notFound();

    String pinSelectedElements();

    String showSelectedElements();

    String unexpectedError();

    String unpinSelectedElements();

    String unavailableFeature();

    String alreadySetFeature();

    String invalidDroppedObject();

    String objectDuplicationFailed();

    String objectDoesNotExist(String objectId);

    String unauthorized();

    String studioTemplateName();

    String blankStudioTemplateName();

    String representationsViewTitle();

    String relatedElementsViewIncomingTitle();

    String relatedElementsViewCurrentTitle();

    String relatedElementsViewCurrentCategoryParent();

    String relatedElementsViewCurrentCategoryChildren();

    String relatedElementsViewOutgoingTitle();

    String diagramFilterFormTitle();

    String diagramFilterElementsOnDiagram();

    String diagramFilterElementsSelected(int count);

    String diagramFilterElementsSelectedPlural(int count);

    String diagramFilterApplyTo(int count);

    String diagramFilterApplyToPlural(int count);

    String diagramFilterCollapseElements();

    String diagramFilterExpandElements();

    String diagramFilterFadeElements();

    String diagramFilterHideElements();

    String diagramFilterPinElements();

    String diagramFilterRevealFadedElements();

    String diagramFilterShowElements();

    String diagramFilterUnpinElements();

    String blankProjectName();

    String blankProjectDescription();

    String coreProperties();

    String operationExecutionFailed(String failureMessage);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return "";
        }

        @Override
        public String upperBoundaryReached(String newInstanceClass, String feature) {
            return "";
        }

        @Override
        public String revealSelectedFadedElements() {
            return "";
        }

        @Override
        public String collapseSelectedElements() {
            return "";
        }

        @Override
        public String expandSelectedElements() {
            return "";
        }

        @Override
        public String fadeSelectedElements() {
            return "";
        }

        @Override
        public String hideSelectedElements() {
            return "";
        }

        @Override
        public String invalidName() {
            return "";
        }

        @Override
        public String notFound() {
            return "";
        }

        @Override
        public String pinSelectedElements() {
            return "";
        }

        @Override
        public String showSelectedElements() {
            return "";
        }

        @Override
        public String unexpectedError() {
            return "";
        }

        @Override
        public String unpinSelectedElements() {
            return "";
        }

        @Override
        public String unavailableFeature() {
            return "";
        }

        @Override
        public String alreadySetFeature() {
            return "";
        }

        @Override
        public String invalidDroppedObject() {
            return "";
        }

        @Override
        public String objectDuplicationFailed() {
            return "";
        }

        @Override
        public String objectDoesNotExist(String objectId) {
            return "";
        }

        @Override
        public String unauthorized() {
            return "";
        }

        @Override
        public String studioTemplateName() {
            return "";
        }

        @Override
        public String blankStudioTemplateName() {
            return "";
        }

        @Override
        public String representationsViewTitle() {
            return "";
        }

        @Override
        public String relatedElementsViewIncomingTitle() {
            return "";
        }

        @Override
        public String relatedElementsViewCurrentTitle() {
            return "";
        }

        @Override
        public String relatedElementsViewCurrentCategoryParent() {
            return "";
        }

        @Override
        public String relatedElementsViewCurrentCategoryChildren() {
            return "";
        }

        @Override
        public String relatedElementsViewOutgoingTitle() {
            return "";
        }

        @Override
        public String diagramFilterFormTitle() {
            return "";
        }

        @Override
        public String diagramFilterElementsOnDiagram() {
            return "";
        }

        @Override
        public String diagramFilterElementsSelected(int count) {
            return "";
        }

        @Override
        public String diagramFilterElementsSelectedPlural(int count) {
            return "";
        }

        @Override
        public String diagramFilterApplyTo(int count) {
            return "";
        }

        @Override
        public String diagramFilterApplyToPlural(int count) {
            return "";
        }

        @Override
        public String diagramFilterCollapseElements() {
            return "";
        }

        @Override
        public String diagramFilterExpandElements() {
            return "";
        }

        @Override
        public String diagramFilterFadeElements() {
            return "";
        }

        @Override
        public String diagramFilterHideElements() {
            return "";
        }

        @Override
        public String diagramFilterPinElements() {
            return "";
        }

        @Override
        public String diagramFilterRevealFadedElements() {
            return "";
        }

        @Override
        public String diagramFilterShowElements() {
            return "";
        }

        @Override
        public String diagramFilterUnpinElements() {
            return "";
        }

        @Override
        public String blankProjectName() {
            return "";
        }

        @Override
        public String blankProjectDescription() {
            return "";
        }

        @Override
        public String coreProperties() {
            return "";
        }

        @Override
        public String operationExecutionFailed(String failureMessage) {
            return "";
        }
    }
}
