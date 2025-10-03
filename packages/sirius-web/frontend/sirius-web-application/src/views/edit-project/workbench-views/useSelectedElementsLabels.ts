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

import { useObjectsLabels } from '@eclipse-sirius/sirius-components-core';

export const useSelectedElementsLabels = () => {
  const { fetchObjectLabels, labels } = useObjectsLabels();

  let result = null;
  if (labels) {
    const maxLabelsToShow = 5;
    if (labels.length > maxLabelsToShow) {
      result = `${labels.slice(0, maxLabelsToShow).join(', ')} (and ${labels.length - maxLabelsToShow} more)`;
    } else {
      result = `${labels.join(', ')}`;
    }
  }

  return [fetchObjectLabels, result];
};
