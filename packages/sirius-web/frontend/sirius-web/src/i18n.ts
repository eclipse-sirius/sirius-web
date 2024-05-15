/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { siriusComponentsCoreEn, siriusComponentsCoreRu } from '@eclipse-sirius/sirius-components-core';
import { siriusComponentsDiagramsEn, siriusComponentsDiagramsRu } from '@eclipse-sirius/sirius-components-diagrams';
import {
  siriusComponentsFormDescriptionEditorsEn,
  siriusComponentsFormDescriptionEditorsRu,
} from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import { siriusComponentsFormsEn, siriusComponentsFormsRu } from '@eclipse-sirius/sirius-components-forms';
import { siriusComponentsTreesEn, siriusComponentsTreesRu } from '@eclipse-sirius/sirius-components-trees';
import {
  siriusComponentsValidationEn,
  siriusComponentsValidationRu,
} from '@eclipse-sirius/sirius-components-validation';
import {
  siriusComponentsWidgetReferenceEn,
  siriusComponentsWidgetReferenceRu,
} from '@eclipse-sirius/sirius-components-widget-reference';
import { siriusWebApplicationEn, siriusWebApplicationRu } from '@eclipse-sirius/sirius-web-application';
import i18next from 'i18next';
import { initReactI18next } from 'react-i18next';

i18next.use(initReactI18next).init({
  lng: 'en',
  interpolation: {
    escapeValue: false, // React already protects from XSS
  },
  resources: {
    en: {
      ...siriusComponentsCoreEn,
      ...siriusComponentsDiagramsEn,
      ...siriusComponentsFormDescriptionEditorsEn,
      ...siriusComponentsFormsEn,
      ...siriusComponentsTreesEn,
      ...siriusComponentsValidationEn,
      ...siriusComponentsWidgetReferenceEn,
      ...siriusWebApplicationEn,
    },
    ru: {
      ...siriusComponentsCoreRu,
      ...siriusComponentsDiagramsRu,
      ...siriusComponentsFormDescriptionEditorsRu,
      ...siriusComponentsFormsRu,
      ...siriusComponentsTreesRu,
      ...siriusComponentsValidationRu,
      ...siriusComponentsWidgetReferenceRu,
      ...siriusWebApplicationRu,
    },
  },
});
export default i18next;
