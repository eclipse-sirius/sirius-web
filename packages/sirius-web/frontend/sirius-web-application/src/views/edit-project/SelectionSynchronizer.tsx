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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { SelectionSynchronizerProps } from './SelectionSynchronizer.types';

export const SelectionSynchronizer = ({ children }: SelectionSynchronizerProps) => {
  const { selection } = useSelection();
  const [_urlSearchParams, setUrlSearchParams] = useSearchParams();

  useEffect(() => {
    setUrlSearchParams((urlSearchParams: URLSearchParams) => {
      if (selection.entries.length > 0) {
        urlSearchParams.set('selection', selectionToSearchParamsValue(selection));
      } else {
        if (urlSearchParams.has('selection')) {
          urlSearchParams.delete('selection');
        }
      }
      return urlSearchParams;
    });
  }, [selection]);

  return children;
};

export const selectionToSearchParamsValue: (selection: Selection) => string = (selection) => {
  return selection.entries.map((entry) => entry.id).join(',');
};
