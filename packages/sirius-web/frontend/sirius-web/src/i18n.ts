/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { siriusComponentsCoreEn } from '@eclipse-sirius/sirius-components-core';
import { siriusComponentsDiagramsEn } from '@eclipse-sirius/sirius-components-diagrams';
import { siriusComponentsFormDescriptionEditorsEn } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import { siriusComponentsFormsEn } from '@eclipse-sirius/sirius-components-forms';
import { siriusComponentsTreesEn } from '@eclipse-sirius/sirius-components-trees';
import { siriusComponentsValidationEn } from '@eclipse-sirius/sirius-components-validation';
import { siriusComponentsWidgetReferenceEn } from '@eclipse-sirius/sirius-components-widget-reference';
import { siriusWebApplicationEn } from '@eclipse-sirius/sirius-web-application';
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
  },
});
export default i18next;
